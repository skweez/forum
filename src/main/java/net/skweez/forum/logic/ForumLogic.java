package net.skweez.forum.logic;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import net.skweez.forum.model.Discussion;
import net.skweez.forum.model.Post;
import net.skweez.forum.model.User;
import net.skweez.forum.server.HibernateHelper;

import org.apache.commons.lang3.Validate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
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
		Validate.isTrue(discussion.getPosts().size() == 1);

		discussion.setUser(user);
		discussion.setDate(new Date());

		Session session = HibernateHelper.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();

		session.save(discussion.getPosts().get(0));
		session.save(discussion);

		try {
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			throw e;
		}

		return discussion.getId();
	}

	/**
	 * @param discussionId
	 * @return the discussion. null if no discussion is found
	 */
	public Discussion getDiscussion(int discussionId) {
		Session session = HibernateHelper.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Discussion discussion = (Discussion) session.get(Discussion.class,
				discussionId);
		session.getTransaction().commit();
		return discussion;
	}

	/**
	 * @return all discussions
	 */
	public Collection<Discussion> getDiscussions() {
		Session session = HibernateHelper.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		Collection<Discussion> discussions = session.createCriteria(
				Discussion.class).list();
		session.getTransaction().commit();
		return discussions;
	}

	/**
	 * @param discussionId
	 *            the discussionId
	 * @return the posts in a simple ArrayList
	 */
	public List<Post> getPostsAsListForDiscussion(int discussionId) {
		Discussion discussion = getDiscussion(discussionId);
		if (discussion == null) {
			return null;
		}
		return discussion.getPosts();
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
		Discussion discussion = getDiscussion(discussionId);

		if (discussion == null) {
			throw new LogicException("No such discussion with id"
					+ discussionId);
		}

		post.setUser(user);
		post.setDate(new Date());
		post.setContent(htmlSanitizer.sanitize(post.getContent()));

		discussion.addPost(post);

		Session session = HibernateHelper.getSessionFactory()
				.getCurrentSession();
		session.beginTransaction();
		session.save(discussion);
		session.getTransaction().commit();

		return post.getId();
	}
}
