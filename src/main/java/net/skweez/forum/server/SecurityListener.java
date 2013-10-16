package net.skweez.forum.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Dynamically set java.security.auth.login.config if not already defined (by
 * tomcat or something).
 * 
 * @author elm
 * 
 */
public class SecurityListener implements ServletContextListener {
	/**
	 * constructor
	 */
	public SecurityListener() {
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// do nothing
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		if (System.getProperty("java.security.auth.login.config") == null) {
			String jaasConfigFile = sce.getServletContext().getRealPath(
					"/WEB-INF/login.config");
			System.setProperty("java.security.auth.login.config",
					jaasConfigFile);
		}
	}
}