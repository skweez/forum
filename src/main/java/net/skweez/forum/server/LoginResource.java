/**
 * 
 */
package net.skweez.forum.server;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

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
	public Response login(@FormParam("name") String name,
			@FormParam("password") String password) {
		System.out.println("Login: " + name + " with password: " + password);
		return Response.ok().build();
	}

}
