package net.skweez.forum.server;

import java.io.IOException;

import net.skweez.forum.server.js.JsServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Main class.
 *
 */
public class Main {
    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
		Server server = new Server(8080);

		ServletContextHandler servletHandler = new ServletContextHandler(
				ServletContextHandler.SESSIONS);
		servletHandler.setContextPath("/");
		servletHandler.setResourceBase("htdocs");
		servletHandler.setWelcomeFiles(new String[] { "index.html" });

		servletHandler.addServlet(DefaultServlet.class, "/");
		servletHandler.addServlet(JsServlet.class, "/skweez.js");

		ServletHolder jerseyServlet = servletHandler.addServlet(
				org.glassfish.jersey.servlet.ServletContainer.class,
 "/api/*");
		jerseyServlet.setInitOrder(1);
		jerseyServlet.setInitParameter(
				"jersey.config.server.provider.packages",
				"net.skweez.forum.server");

		server.setHandler(servletHandler);

		try {
			server.start();
			server.join();
		} catch (Throwable t) {
			t.printStackTrace(System.err);
		}
    }
}

