/**
 * 
 */
package net.skweez.forum.adapters;

import net.skweez.forum.model.Discussion;
import net.skweez.forum.model.Post;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Adapter for Discussions. Provides all the members of discussions that are
 * needed by XStream to serialize and deserialize Discussions.
 * 
 * @author elm
 * 
 */
@XStreamAlias("Discussion")
public class DiscussionAdapter implements ModelAdapter<Discussion> {
	/** the actual discussion */
	@XStreamOmitField
	private Discussion discussion;

	/**
	 * Creates a new DiscussionAdapter for a new discussion.
	 */
	public DiscussionAdapter() {
		discussion = new Discussion();
	}

	/**
	 * Creates a new DiscussionAdapter for a given discussion.
	 * 
	 * @param discussion
	 *            the discussion
	 */
	public DiscussionAdapter(Discussion discussion) {
		this.discussion = discussion;
	}

	@Override
	public Discussion getModel() {
		return discussion;
	}

	/**
	 * @return the actual discussion
	 */
	public Discussion getDiscussion() {
		return discussion;
	}

	/**
	 * Sets a post for the discussion. This is used by XStream when
	 * deserializing a DiscussionAdapter object.
	 * 
	 * @param post
	 */
	public void setPost(Post post) {
		assert (discussion.getPosts().size() == 0);
		discussion.addPost(post);
	}

	/**
	 * @param title
	 *            the title
	 */
	public void setTitle(String title) {
		discussion.setTitle(title);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return discussion.getTitle();
	}

	/**
	 * @return the uid
	 */
	public String getUser() {
		return discussion.getUser().getUid();
	}

	/**
	 * do nothing
	 */
	public void setId(int id) {
		// empty
	}

	/**
	 * @return the discussion id
	 */
	public int getId() {
		return discussion.getId();
	}
}
