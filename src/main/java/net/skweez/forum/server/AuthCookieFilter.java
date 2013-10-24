/**
 * 
 */
package net.skweez.forum.server;

import java.io.IOException;
import java.security.Principal;
import java.util.LinkedList;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import net.skweez.forum.logic.SessionLogic;
import net.skweez.forum.logic.UserLogic;
import net.skweez.forum.model.User;

/**
 * @author elm
 * 
 */
@Provider
public class AuthCookieFilter implements ContainerRequestFilter {

	/** the authentication scheme name */
	public static final String AuthenticationScheme = "net.skweez.forum.cookie";

	/** userLogic */
	private final UserLogic userLogic = new UserLogic();
	/** sessionLogic */
	private final SessionLogic sessionLogic = new SessionLogic();

	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		Map<String, Cookie> cookies = requestContext.getCookies();

		// return without security context if one cookie is missing
		if (!cookies.containsKey("uid") || !cookies.containsKey("authToken")) {
			return;
		}

		final String uid = cookies.get("uid").getValue();
		String authToken = cookies.get("authToken").getValue();

		// return without security context if the auth token is invalid
		if (!sessionLogic.validateAuthTokenForUID(authToken, uid)) {
			return;
		}

		User user = userLogic.findOrCreateUser(uid, null);

		ForumSecurityContext sec = new ForumSecurityContext(user);
		requestContext.setSecurityContext(sec);
	}

	/**
	 * Our own security context
	 * 
	 * @author elm
	 * 
	 */
	private class ForumSecurityContext implements SecurityContext {
		/**
		 * the principal
		 */
		private Principal principal;

		/**
		 * the roles
		 */
		private LinkedList<String> roles;

		/**
		 * constructor
		 */
		public ForumSecurityContext(final User user) {
			principal = new Principal() {
				@Override
				public String getName() {
					return user.getUid();
				}
			};
			roles = user.getRoles();
		}

		@Override
		public String getAuthenticationScheme() {
			return AuthCookieFilter.AuthenticationScheme;
		}

		@Override
		public Principal getUserPrincipal() {
			return principal;
		}

		@Override
		public boolean isSecure() {
			return false;
		}

		@Override
		public boolean isUserInRole(String role) {
			return roles.contains(role);
		}

	}
}
