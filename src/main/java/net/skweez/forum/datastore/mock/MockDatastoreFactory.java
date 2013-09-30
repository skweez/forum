package net.skweez.forum.datastore.mock;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;
import net.skweez.forum.datastore.PostDatastore;
import net.skweez.forum.datastore.simple.SimplePostDatastore;

public class MockDatastoreFactory extends DatastoreFactory {

	private static final DiscussionDatastore discussionDatastore = new MockDiscussionDatastore();

	private static final PostDatastore postDatastore = new SimplePostDatastore();

	@Override
	public DiscussionDatastore getDiscussionDatastore() {
		return discussionDatastore;
	}

	@Override
	public PostDatastore getPostDatastore() {
		return postDatastore;
	}

}
