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
			// JAASLoginService name and realm-name in web.xml need to be the
			// same.
			JAASLoginService jaasLoginService = new JAASLoginService(
					"net.skweez.forum");
			jaasLoginService.setLoginModuleName("net.skweez.forum.ldapTest");
			context.addBean(jaasLoginService);
			loginService = jaasLoginService;
		} else {
			HashLoginService hashLoginService = new HashLoginService();
			hashLoginService.putUser("testUser1",
					Credential.getCredential("testPassword1"),
					new String[] { "users" }); // Role of this user
			hashLoginService.setName("net.skweez.forum");
			loginService = hashLoginService;
		}

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
