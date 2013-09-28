package net.skweez.forum.datastore.simple;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;

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
		}
		return datastore;
	}

}
