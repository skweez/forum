/**
 * 
 */
package net.skweez.forum.server;

import org.eclipse.jetty.server.Server;
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

		server.setHandler(context);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
