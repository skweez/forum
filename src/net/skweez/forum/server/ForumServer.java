package net.skweez.forum.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class ForumServer extends Server {

	public ForumServer() {
		super(8080);

		ServletContextHandler context = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		setHandler(context);

		context.addServlet(LatestDiscussionsServlet.class, "/latest");
	}

	public static void main(String... args) throws Exception {
		Server server = new ForumServer();
		server.start();
		server.join();
	}
}
