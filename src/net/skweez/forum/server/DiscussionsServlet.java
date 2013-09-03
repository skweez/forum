package net.skweez.forum.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

@SuppressWarnings("serial")
public class DiscussionsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		System.out.println(request.getRequestURI());

		XStream xstream = new XStream(new JsonHierarchicalStreamDriver());

		DiscussionDatastore datastore = DatastoreFactory.getDefault()
				.getDiscussionDatastore();
		xstream.toXML(datastore.selectAllDiscussions(),
				response.getOutputStream());
	}

}