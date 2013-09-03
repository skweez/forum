package net.skweez.forum.server;

import java.util.Collection;
import java.util.Map;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;
import net.skweez.forum.model.Discussion;

@SuppressWarnings("serial")
public class DiscussionsServlet extends RestServletBase<Discussion> {

	final DiscussionDatastore datastore = DatastoreFactory.getDefault()
			.getDiscussionDatastore();

	/** {@inheritDoc} */
	@Override
	protected Collection<Discussion> getCollection(
			Map<String, String[]> queryParams) {
		return datastore.selectAllDiscussions();
	}

	/** {@inheritDoc} */
	@Override
	protected Discussion get(String id) {
		return datastore.findDiscussion(getIdFromPathInfo(id));
	}

	private int getIdFromPathInfo(String pathInfo) {
		int id = -1;
		try {
			id = Integer.parseInt(pathInfo.substring(1));
		} catch (NumberFormatException e) {
			System.out.println("Unparsable ID supplied:\n"
					+ e.getLocalizedMessage());
		}
		return id;
	}

	/** {@inheritDoc} */
	@Override
	protected String post(Discussion discussion) {
		return "api/discussions/" + datastore.createDiscussion(discussion);
	}

	/** {@inheritDoc} */
	@Override
	protected boolean put(String pathInfo, Discussion discussion) {
		return datastore.updateDiscussion(getIdFromPathInfo(pathInfo),
				discussion);
	}

	/** {@inheritDoc} */
	@Override
	protected boolean delete(String pathInfo) {
		return datastore.deleteDiscussion(getIdFromPathInfo(pathInfo));
	}
}