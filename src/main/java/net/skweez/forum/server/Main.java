/**
 * 
 */
package net.skweez.forum.server;

import net.skweez.forum.config.Config;
import net.skweez.forum.config.Setting;

import org.eclipse.jetty.jaas.JAASLoginService;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.security.Credential;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @author elm
 * 
 */
public class Main {

	/** The name of the login module. Used in login.conf. */
	private static final String JAAS_LOGIN_MODULE_NAME = "net.skweez.forum.jaas";

	/**
	 * The realm name of the LoginService. If a container-configured
	 * LoginService is used, it has to be configured with the realm-name
	 * <i>{@value} </i> in <code>web.xml</code>.
	 */
	private static final String REALM_NAME = "net.skweez.forum";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Server server = new Server(8080);

		WebAppContext context = new WebAppContext();
		context.setDescriptor("../WEB-INF/web.xml");
		context.setResourceBase("src/main/webapp");
		context.setContextPath("/");
		context.setParentLoaderPriority(true);

		LoginService loginService = createLoginService(Config
				.getValue(Setting.LOGIN_SERVICE));
		context.addBean(loginService);

		ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
		securityHandler.setLoginService(loginService);
		securityHandler.setRealmName(REALM_NAME);

		context.setSecurityHandler(securityHandler);

		server.setHandler(context);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates the login service for a given login service name.
	 * 
	 * @param loginServiceName
	 *            the name of the login service
	 * @return the login service.
	 * @throws IllegalArgumentException
	 *             if no such login service exists.
	 */
	private static LoginService createLoginService(String loginServiceName) {
		switch (loginServiceName) {
		case "jaas":
			JAASLoginService jaasLoginService = new JAASLoginService(REALM_NAME);
			jaasLoginService.setLoginModuleName(JAAS_LOGIN_MODULE_NAME);
			return jaasLoginService;
		case "test":
			HashLoginService hashLoginService = new HashLoginService();
			hashLoginService.putUser("testUser1",
					Credential.getCredential("testPassword1"),
					new String[] { "users" });
			hashLoginService.setName(REALM_NAME);
			return hashLoginService;
		default:
			throw new IllegalArgumentException("No such LoginService: "
					+ loginServiceName);
		}
	}
}
