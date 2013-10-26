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

		LoginService loginService;
		if (Config.getValue(Setting.LOGIN_SERVICE).equals("ldapTest")) {
			JAASLoginService jaasLoginService = new JAASLoginService(REALM_NAME);
			jaasLoginService.setLoginModuleName("net.skweez.forum.ldapTest");
			context.addBean(jaasLoginService);
			loginService = jaasLoginService;
		} else {
			HashLoginService hashLoginService = new HashLoginService(REALM_NAME);
			hashLoginService.putUser("testUser1",
					Credential.getCredential("testPassword1"),
					new String[] { "users" }); // Role of this user
			loginService = hashLoginService;
		}

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
}
