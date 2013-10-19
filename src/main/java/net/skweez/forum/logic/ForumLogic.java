package net.skweez.forum.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;
import net.skweez.forum.datastore.PostDatastore;
import net.skweez.forum.model.Discussion;
import net.skweez.forum.model.Post;
import net.skweez.forum.model.User;

import org.apache.commons.lang3.Validate;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

/**
 * The forum logic. Static for now.
 * 
 */
public class ForumLogic {
	/** the html sanitizer */
	private final PolicyFactory htmlSanitizer = Sanitizers.BLOCKS
			.and(Sanitizers.IMAGES).and(Sanitizers.LINKS)
			.and(Sanitizers.FORMATTING);

	/** the datastore factory */
	private final DatastoreFactory factory = DatastoreFactory
			.createConfigured();

	/** the discussion datastore */
	private final DiscussionDatastore discussionDatastore = factory
			.getDiscussionDatastore();

	/** the post datastore */
	private final PostDatastore postDatastore = factory.getPostDatastore();

	/**
	 * @param discussion
	 *            the new discussion
	 * @return the discussion id
	 * @throws LogicException
	 * @throws IllegalArgumentException
	 *             if the discussion has no post attached
	 */
	public int createDiscussion(Discussion discussion, User user)
			throws LogicException {
		int discussionId;

		Validate.isTrue(discussion.getPosts().size() == 1);

		Post post = discussion.getPosts().get(0);
		discussion.deletePost(0);

		discussion.setUser(user);
		discussion.setDate(new Date());

		discussionId = discussionDatastore.createDiscussion(discussion);
		addPostToDiscussion(post, discussionId, user);

		return discussionId;
	}

	/**
	 * @param discussionId
	 * @return the discussion. null if no discussion is found
	 */
	public Discussion getDiscussion(int discussionId) {
		return discussionDatastore.findDiscussion(discussionId);
	}

	/**
	 * @return all discussions
	 */
	public Collection<Discussion> getDiscussions() {
		return discussionDatastore.selectAllDiscussions();
	}

	/**
	 * @param discussionId
	 *            the discussionId
	 * @return the posts in a simple ArrayList
	 */
	public ArrayList<Post> getPostsAsListForDiscussion(int discussionId) {
		Discussion discussion = discussionDatastore
				.findDiscussion(discussionId);
		if (discussion == null) {
			return null;
		}
		return new ArrayList<Post>(discussion.getPosts().values());
	}

	/**
	 * @param post
	 *            the new Post
	 * @param discussionId
	 *            the id of the discussion to add the post to
	 * @return the id of the new post
	 * @throws LogicException
	 *             Exception is thrown if no discussion with discusionId exists.
	 */
	public int addPostToDiscussion(Post post, int discussionId, User user)
			throws LogicException {
		Discussion discussion = discussionDatastore
				.findDiscussion(discussionId);

		if (discussion == null) {
			throw new LogicException("No such discussion with id"
					+ discussionId);
		}

		post.setUser(user);
		post.setDate(new Date());
		post.setContent(htmlSanitizer.sanitize(post.getContent()));

		int postId = postDatastore.createPost(post);

		post.setId(postId);

		discussion.addPost(post);
		discussionDatastore.updateDiscussion(discussionId, discussion);
		return postId;
	}
}
