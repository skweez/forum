package net.skweez.forum.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class ForumServer extends Server {

	public static void main(String... args) throws Exception {
		Server server = new ForumServer();
		server.start();
		server.join();
	}

	public ForumServer() {
		super(8080);

		ServletContextHandler servletHandler = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		servletHandler.setContextPath("/");
		servletHandler.setResourceBase("htdocs");

		servletHandler.addServlet(DefaultServlet.class, "/");
		servletHandler.addServlet(DiscussionsServlet.class, "/discussion/*");

		setHandler(servletHandler);
	}
}
