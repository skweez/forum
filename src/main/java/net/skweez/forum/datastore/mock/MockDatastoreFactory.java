package net.skweez.forum.datastore.mock;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;

public class MockDatastoreFactory extends DatastoreFactory {

	private static final MockDiscussionDatastore mockDiscussionDatastore = new MockDiscussionDatastore();

	@Override
	public DiscussionDatastore getDiscussionDatastore() {
		return mockDiscussionDatastore;
	}

}
