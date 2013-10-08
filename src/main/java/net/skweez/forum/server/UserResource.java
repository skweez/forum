/**
 * 
 */
package net.skweez.forum.server;

import java.math.BigInteger;
import java.security.SecureRandom;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import net.skweez.forum.config.Config;
import net.skweez.forum.config.Setting;
import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.UserDatastore;
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

	/**
	 * initialize secure random rng
	 */
	private SecureRandom random = new SecureRandom();

	private final Logger logger = LoggerFactory.getLogger(UserResource.class);

	/**
	 * the user datastore
	 */
	final UserDatastore userDatastore = DatastoreFactory.createConfigured()
			.getUserDatastore();

	/**
	 * 
	 * @return 401 Unauthorized if user is not logged in. Returns 200 OK if user
	 *         is logged in.
	 */
	@GET
	@Path("login")
	public Response login(@Context SecurityContext sec) {
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

		// generate secure authentication token
		String authToken = new BigInteger(130, random).toString(32);

		User user = new User(sec.getUserPrincipal().getName());
		user.setAuthToken(authToken);

		// TODO: Do not test each role but iterate over them. Maybe use a sub
		// enum or something.
		if (sec.isUserInRole(Config.getValue(Setting.ROLE_NAME_USER))) {
			user.addRole(Config.getValue(Setting.ROLE_NAME_USER));
		}
		if (sec.isUserInRole(Config.getValue(Setting.ROLE_NAME_ADMIN))) {
			user.addRole(Config.getValue(Setting.ROLE_NAME_ADMIN));
		}

		userDatastore.createUser(user);

		return Response
				.ok()
				.cookie(new NewCookie("uid", // name
						user.getUid(), // content
						"/", // path, we need the uid cookie in the js so it
								// needs to be accessible from everywhere
						uriInfo.getBaseUri().getHost(), // host
						Cookie.DEFAULT_VERSION, // version
						null, // comment
						NewCookie.DEFAULT_MAX_AGE, // max_age
						null, // expire date
						false, // secure (https only)
						false // httpOnly (no js access allowed. This is set to
								// true for the authToken)
				))
				.cookie(new NewCookie("authToken", authToken, "/api", uriInfo
						.getBaseUri().getHost(), Cookie.DEFAULT_VERSION, null,
						NewCookie.DEFAULT_MAX_AGE, null, false, true)).build();
	}

	/**
	 * 
	 * Revokes the auth token and deletes all cookies from browser.
	 * 
	 * @return 401 Unauthorized so that browsers delete their authentication
	 *         data
	 */
	@GET
	@Path("logout")
	public Response logout(@Context SecurityContext sec) {
		if (sec.getUserPrincipal() == null) {
			throw new WebApplicationException(Response.status(
					Status.UNAUTHORIZED).build());
		}

		String uid = sec.getUserPrincipal().getName();

		User user = userDatastore.findUser(uid);
		// TODO: find a better way to revoke auth token
		user.setAuthToken(new BigInteger(130, random).toString(32));
		userDatastore.updateUser(user);

		// delete the cookies from the browser by setting the max age to 0
		// seconds
		return Response
				.status(Status.UNAUTHORIZED)
				.cookie(new NewCookie("uid", "", "/", uriInfo.getBaseUri()
						.getHost(), Cookie.DEFAULT_VERSION, null, 0, null,
						false, true))
				.cookie(new NewCookie("authToken", "", "/api", uriInfo
						.getBaseUri().getHost(), Cookie.DEFAULT_VERSION, null,
						0, null, false, true)).build();

	}
}
