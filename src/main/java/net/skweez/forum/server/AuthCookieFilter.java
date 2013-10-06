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

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.UserDatastore;
import net.skweez.forum.model.User;

/**
 * @author elm
 * 
 */
@Provider
public class AuthCookieFilter implements ContainerRequestFilter {

	static String AuthenticationScheme = "Cookie";

	/**
	 * the user datastore
	 */
	final UserDatastore userDatastore = DatastoreFactory.createConfigured()
			.getUserDatastore();

	/**
	 * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		Map<String, Cookie> cookies = requestContext.getCookies();

		if (!cookies.containsKey("uid") || !cookies.containsKey("authToken"))
			return;

		final String uid = cookies.get("uid").getValue();
		String authToken = cookies.get("authToken").getValue();
		User user = userDatastore.findUser(uid);

		if (user == null || !authToken.equals(user.getAuthToken()))
			return;

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
			this.principal = new Principal() {
				@Override
				public String getName() {
					return user.getUid();
				}
			};
			this.roles = user.getRoles();
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
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isUserInRole(String role) {
			return this.roles.contains(role);
		}

	}
}
