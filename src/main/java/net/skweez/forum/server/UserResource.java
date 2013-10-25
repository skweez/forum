/**
 * 
 */
package net.skweez.forum.server;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import net.skweez.forum.logic.SessionLogic;
import net.skweez.forum.logic.UserLogic;
import net.skweez.forum.model.Session;
import net.skweez.forum.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author elm
 * 
 */
@Path("user")
public class UserResource {

	/**
	 * The UriInfo in this context.
	 */
	@Context
	UriInfo uriInfo;

	/** logger */
	private final Logger logger = LoggerFactory.getLogger(UserResource.class);

	/** userLogic */
	private final UserLogic userLogic = new UserLogic();
	/** sessionLogic */
	private final SessionLogic sessionLogic = new SessionLogic();

	/**
	 * 
	 * @return 401 Unauthorized if user is not logged in. Returns 200 OK if user
	 *         is logged in.
	 */
	@GET
	@Path("login")
	public Response login(@Context SecurityContext sec,
			@QueryParam("stayloggedin") boolean stayloggedin) {
		// If the user is not authenticated by the servlet container (Jetty with
		// JAAS or something) or by cookie we return 401 Unauthorized
		if (sec.getUserPrincipal() == null) {
			throw new WebApplicationException(Response.status(
					Status.UNAUTHORIZED).build());
		}

		// If the user is authenticated by our AuthCookieFilter we send 200 OK
		// to let the client know the cookie credentials are correct
		if (sec.getAuthenticationScheme() == AuthCookieFilter.AuthenticationScheme) {
			return Response.ok().build();
		}

		String uid = sec.getUserPrincipal().getName();

		User user = userLogic.findOrCreateUser(uid, sec);

		logger.debug("Login successful: " + uid);

		// get authentication token
		Session session = sessionLogic.createSession(user, stayloggedin);
		String authToken = session.getAuthToken();

		int max_age = NewCookie.DEFAULT_MAX_AGE;
		if (stayloggedin) {
			max_age = SessionLogic.LONG_SESSION_LIFETIME * 24 * 60 * 60;
		}

		return Response
				.ok()
				// The uid cookie and the sessionid cookie has to be readable
				// from javascript. Therefore the path is / and httpOnly is
				// false.
				.cookie(new NewCookie("uid", user.getUid(), "/", uriInfo
						.getBaseUri().getHost(), Cookie.DEFAULT_VERSION, null,
						max_age, null, false, false))
				.cookie(new NewCookie("sessionId", String.valueOf(session
						.getId()), "/", uriInfo.getBaseUri().getHost(),
						Cookie.DEFAULT_VERSION, null, max_age, null, false,
						false))
				.cookie(new NewCookie("authToken", authToken, "/api", uriInfo
						.getBaseUri().getHost(), Cookie.DEFAULT_VERSION, null,
						max_age, null, false, true)).build();
	}

	/**
	 * 
	 * Revokes the auth token and deletes all cookies from browser by setting
	 * their max_age to 0.
	 * 
	 * @return 401 Unauthorized so that browsers delete their authentication
	 *         data
	 */
	@GET
	@Path("logout")
	public Response logout(@Context SecurityContext sec,
			@CookieParam(value = "sessionId") int sessionId) {
		if (sec.getUserPrincipal() == null) {
			throw new WebApplicationException(Response.status(
					Status.UNAUTHORIZED).build());
		}

		User user = userLogic.getUser(sec.getUserPrincipal().getName());
		if (user == null) {
			throw new WebApplicationException();
		}

		sessionLogic.deleteSession(user, sessionId);

		logger.debug("Logout: " + user.getUid());

		return Response
				.status(Status.UNAUTHORIZED)
				.cookie(new NewCookie("uid", "", "/", uriInfo.getBaseUri()
						.getHost(), Cookie.DEFAULT_VERSION, null, 0, null,
						false, false))
				.cookie(new NewCookie("sessionId", "", "/", uriInfo
						.getBaseUri().getHost(), Cookie.DEFAULT_VERSION, null,
						0, null, false, false))
				.cookie(new NewCookie("authToken", "", "/api", uriInfo
						.getBaseUri().getHost(), Cookie.DEFAULT_VERSION, null,
						0, null, false, true)).build();

	}
}
