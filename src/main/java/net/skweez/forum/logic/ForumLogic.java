package net.skweez.forum.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;
import net.skweez.forum.datastore.PostDatastore;
import net.skweez.forum.model.Discussion;
import net.skweez.forum.model.DiscussionProxy;
import net.skweez.forum.model.Post;

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
	public int createDiscussion(Discussion discussion) throws LogicException {
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
	public Discussion getDiscussion(int discussionId) {
		return discussionDatastore.findDiscussion(discussionId);
	}

	public Collection<DiscussionProxy> getDiscussionProxies() {
		Collection<Discussion> discussions = getDiscussions();
		Collection<DiscussionProxy> proxies = new ArrayList<>(
				discussions.size());

		Iterator<Discussion> iter = discussions.iterator();
		while (iter.hasNext()) {
			proxies.add(new DiscussionProxy(iter.next()));
		}
		return proxies;
	}

	/**
	 * @return all discussions
	 */
	public Collection<Discussion> getDiscussions() {
		return discussionDatastore.selectAllDiscussions();
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
	public int addPostToDiscussion(Post post, int discussionId)
			throws LogicException {
		Discussion discussion = discussionDatastore
				.findDiscussion(discussionId);

		if (discussion == null) {
			throw new LogicException("No such discussion with id"
					+ discussionId);
		}

		post.setDate(new Date());
		post.setContent(htmlSanitizer.sanitize(post.getContent()));

		postDatastore.createPost(post);

		int postId = discussion.addPost(post);
		discussionDatastore.updateDiscussion(discussionId, discussion);
		return postId;
	}
}
