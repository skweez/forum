package net.skweez.forum.logic;

import java.util.Collection;
import java.util.Date;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;
import net.skweez.forum.datastore.PostDatastore;
import net.skweez.forum.model.Discussion;
import net.skweez.forum.model.Post;

import org.apache.commons.lang3.Validate;

/**
 * The forum logic. Static for now.
 * 
 */
public class ForumLogic {

	/** the datastore factory */
	private static final DatastoreFactory factory = DatastoreFactory
			.createConfigured();

	/** the discussion datastore */
	private static final DiscussionDatastore discussionDatastore = factory
			.getDiscussionDatastore();

	/** the post datastore */
	private static final PostDatastore postDatastore = factory
			.getPostDatastore();

	/**
	 * @param discussion
	 *            the new discussion
	 * @return the discussion id
	 * @throws IllegalArgumentException
	 *             if the discussion has no post attached
	 */
	public static int createDiscussion(Discussion discussion) {
		int discussionId;

		Validate.isTrue(discussion.getPosts().size() == 1);

		discussion.setDate(new Date());

		discussionId = discussionDatastore.createDiscussion(discussion);
		postDatastore.createPost(discussion.getPosts().get(0));

		return discussionId;
	}

	/**
	 * @param discussionId
	 * @return the discussion. null if no discussion is found
	 */
	public static Discussion getDiscussion(int discussionId) {
		Discussion discussion = discussionDatastore
				.findDiscussion(discussionId);

		return discussion;
	}

	/**
	 * @return all discussions
	 */
	public static Collection<Discussion> getDiscussions() {
		return discussionDatastore.selectAllDiscussions();
	}

	/**
	 * @param post
	 *            the new Post
	 * @param discussionId
	 *            the id of the discussion to add the post to
	 * @return the id of the new post
	 */
	public static int addPostToDiscussion(Post post, int discussionId) {
		Discussion discussion = discussionDatastore
				.findDiscussion(discussionId);

		if (discussion == null) {
			throw new IllegalArgumentException("No such discussion with id"
					+ discussionId);
		}

		post.setDate(new Date());
		int postId = discussion.addPost(post);
		discussionDatastore.updateDiscussion(discussionId, discussion);
		return postId;
	}

}
