/**
 * 
 */
package net.skweez.forum.server;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.skweez.forum.auth.AuthenticationService;

/**
 * @author elm
 * 
 */
@Path("login")
public class LoginResource {

	/**
	 * @param name
	 *            the name
	 * @param password
	 *            the password
	 * @return a cookie with the secret that identifies the user at api requests
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response login(@FormParam("name") String name,
			@FormParam("password") String password) {
		if (name == null || password == null) {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}

		if (AuthenticationService.getService().authenticate(name,
				password.toCharArray())) {
			return Response.ok().build();
		}

		throw new WebApplicationException(Response.Status.FORBIDDEN);
	}
}
