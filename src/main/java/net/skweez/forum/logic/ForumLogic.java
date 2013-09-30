package net.skweez.forum.logic;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;
import net.skweez.forum.datastore.PostDatastore;
import net.skweez.forum.model.Discussion;

import org.apache.commons.lang3.Validate;

public class ForumLogic {

	private final DatastoreFactory factory = DatastoreFactory.getDefault();

	private final DiscussionDatastore discussionDatastore = factory
			.getDiscussionDatastore();

	private final PostDatastore postDatastore = factory.getPostDatastore();

	public void createDiscussion(Discussion discussion) {
		Validate.isTrue(discussion.getPosts().size() == 1);
		discussionDatastore.createDiscussion(discussion);
		postDatastore.createPost(discussion.getPosts().get(0));
	}

}
