package net.skweez.forum.datastore.simple;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;
import net.skweez.forum.model.Discussion;

/**
 * Factory that creates simple datastore objects with in-memory storage. This
 * factory ensures, that the returned Datastores are singletons.
 */
public class SimpleDatastoreFactory extends DatastoreFactory {

	/** Lazily initialized datastore singleton. */
	private static DiscussionDatastore datastore;

	/** {@inheritDoc} */
	@Override
	public DiscussionDatastore getDiscussionDatastore() {
		if (datastore == null) {
			datastore = new SimpleDiscussionDatastore();

			Discussion discussion = new Discussion();
			discussion.setId(-1);
			discussion.setTitle("Test discussion");
			datastore.createDiscussion(discussion);
		}
		return datastore;
	}

}
