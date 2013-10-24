/**
 * 
 */
package net.skweez.forum.server;

import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.ForwardedRequestCustomizer;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
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
		Server server = new Server();
		HttpConfiguration http_config = new HttpConfiguration();
		
		http_config.addCustomizer(new ForwardedRequestCustomizer());

		HttpConnectionFactory http = new HttpConnectionFactory(http_config);
		ServerConnector httpConnector = new ServerConnector(server, http);
		httpConnector.setPort(Integer.valueOf(System.getenv("PORT")));
		server.addConnector(httpConnector);

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
