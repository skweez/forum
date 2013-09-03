package net.skweez.forum.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;
import net.skweez.forum.model.Discussion;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

@SuppressWarnings("serial")
public class DiscussionsServlet extends HttpServlet {

	private final XStream xstream = new XStream(
			new JsonHierarchicalStreamDriver());

	private final DiscussionDatastore datastore = DatastoreFactory.getDefault()
			.getDiscussionDatastore();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();

		response.setContentType("text/json;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		if (pathInfo != null) {
			respond(response,
					datastore.findDiscussion(getIdFromPathInfo(pathInfo)));
		} else {
			respond(response, datastore.selectAllDiscussions());
		}
	}

	private int getIdFromPathInfo(String pathInfo) {
		return 0;
	}

	private void respond(HttpServletResponse response, Object answer)
			throws IOException {
		if (answer != null) {
			response.setContentType("text/json;charset=utf-8");
			response.setStatus(HttpServletResponse.SC_OK);
			xstream.toXML(answer, response.getOutputStream());
		} else {
			response.setContentType("text/plain;charset=utf-8");
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getOutputStream().println(
					HttpServletResponse.SC_NOT_FOUND + " - Not found");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println(request.getRequestURI());

		DiscussionDatastore datastore = DatastoreFactory.getDefault()
				.getDiscussionDatastore();

		Discussion discussion = new Discussion();

		String title = request.getParameter("title");
		discussion.setTitle(title);

		datastore.createDiscussion(discussion);

		response.sendRedirect("/");
	}
}