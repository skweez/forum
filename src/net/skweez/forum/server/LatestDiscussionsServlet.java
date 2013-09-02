package net.skweez.forum.server;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.skweez.forum.datastore.ForumDatastoreFactory;
import net.skweez.forum.model.Discussion;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

@SuppressWarnings("serial")
public class LatestDiscussionsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		List<Discussion> discussions = ForumDatastoreFactory
				.getConfiguredDatastore().getDiscussions();

		XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
		xstream.toXML(discussions, response.getOutputStream());
	}

}