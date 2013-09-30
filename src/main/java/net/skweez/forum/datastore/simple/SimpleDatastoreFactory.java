package net.skweez.forum.datastore.simple;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;
import net.skweez.forum.datastore.PostDatastore;

/**
 * Factory that creates simple datastore objects with in-memory storage. This
 * factory ensures, that the returned Datastores are singletons.
 */
public class SimpleDatastoreFactory extends DatastoreFactory {

	/** Lazily initialized datastore singleton. */
	private static DiscussionDatastore discussionDatastore;

	private static PostDatastore postDatastore;

	/** {@inheritDoc} */
	@Override
	public DiscussionDatastore getDiscussionDatastore() {
		if (discussionDatastore == null) {
			discussionDatastore = new SimpleDiscussionDatastore();
		}
		return discussionDatastore;
	}

	@Override
	public PostDatastore getPostDatastore() {
		if (postDatastore == null) {
			postDatastore = new SimplePostDatastore();
		}
		return postDatastore;
	}

}
