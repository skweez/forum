/**
 * 
 */
package net.skweez.forum.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author elm
 * 
 */
@Path("user")
public class UserResource {

	private final Logger logger = LoggerFactory.getLogger(UserResource.class);

	/**
	 * 
	 * @return 401 Unauthorized if user is not logged in. Returns 200 OK if user
	 *         is logged in.
	 */
	@GET
	@Path("login")
	public Response login(@Context SecurityContext sec) {
		if (sec.getUserPrincipal() == null) {
			throw new WebApplicationException(Response.status(
					Status.UNAUTHORIZED).build());
		}

		logger.info("User login: " + sec.getUserPrincipal().getName());

		// TODO: Create user object if the user signs in for the first time

		return Response.ok().build();
	}

	/**
	 * 
	 * @return always 401 Unauthorized. This is used to make browsers clear
	 *         their authentication store.
	 */
	@GET
	@Path("logout")
	public Response logout() {
		throw new WebApplicationException(Response.status(Status.UNAUTHORIZED)
				.build());

	}
}
