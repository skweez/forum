/**
 * 
 */
package net.skweez.forum.server;

import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.security.Credential;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author elm
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server(Integer.valueOf(System.getenv("PORT")));

		WebAppContext context = new WebAppContext();
		context.setDescriptor("../WEB-INF/web.xml");
		context.setResourceBase("src/main/webapp");
		context.setContextPath("/");
		context.setParentLoaderPriority(true);

		HashLoginService loginService = new HashLoginService();
		loginService.putUser("testUser1",
				Credential.getCredential("testPassword1"),
				new String[] { "user" }); // Role of this user
		loginService.setName("net.skweez.forum");

		ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
		securityHandler.setLoginService(loginService);
		securityHandler.setRealmName("net.skweez.forum");

		context.setSecurityHandler(securityHandler);

		server.setHandler(context);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
