package net.skweez.forum.datastore.simple;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;
import net.skweez.forum.datastore.PostDatastore;

/**
 * Factory that creates simple datastore objects with in-memory storage. This
 * factory ensures, that the returned Datastores are singletons.
 */
public class SimpleDatastoreFactory extends DatastoreFactory {

	/** Discussions datastore. */
	private static DiscussionDatastore discussionDatastore = new SimpleDiscussionDatastore();

	/** Posts datastore. */
	private static PostDatastore postDatastore = new SimplePostDatastore();

	@Override
	public DiscussionDatastore getDiscussionDatastore() {
		return discussionDatastore;
	}

	@Override
	public PostDatastore getPostDatastore() {
		return postDatastore;
	}

}
