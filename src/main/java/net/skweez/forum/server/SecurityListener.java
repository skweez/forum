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
	/** The java.security.auth.login.config property */
	private static final String JAVA_SECURITY_AUTH_LOGIN_CONFIG_PROPERTY = "java.security.auth.login.config";

	/**
	 * Constructor.
	 */
	public SecurityListener() {
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// do nothing
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		if (System.getProperty(JAVA_SECURITY_AUTH_LOGIN_CONFIG_PROPERTY) == null) {
			String jaasConfigFile = servletContextEvent.getServletContext()
					.getRealPath("/WEB-INF/login.config");
			System.setProperty(JAVA_SECURITY_AUTH_LOGIN_CONFIG_PROPERTY,
					jaasConfigFile);
		}
	}
}