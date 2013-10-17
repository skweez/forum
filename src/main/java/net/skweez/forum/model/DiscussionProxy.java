/**
 * 
 */
package net.skweez.forum.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Proxy for Discussions. Provides all the members of discussions that are
 * needed by XStream to serialize and deserialize Discussions.
 * 
 * @author elm
 * 
 */
@XStreamAlias("Discussion")
public class DiscussionProxy {
	/** the actual discussion */
	@XStreamOmitField
	private Discussion discussion;

	/**
	 * Creates a new DiscussionProxy for a new discussion.
	 */
	public DiscussionProxy() {
		discussion = new Discussion();
	}

	/**
	 * Creates a new DiscussionProxy for a given discussion.
	 * 
	 * @param discussion
	 *            the discussion
	 */
	public DiscussionProxy(Discussion discussion) {
		this.discussion = discussion;
	}

	/**
	 * @return the actual discussion
	 */
	public Discussion getDiscussion() {
		return discussion;
	}

	/**
	 * Sets a post for the discussion. This is used by XStream when
	 * deserializing a DiscussionProxy object.
	 * 
	 * @param post
	 */
	public void setPost(Post post) {
		assert (discussion.getPosts().size() == 0);
		discussion.addPost(post);
	}

	/**
	 * Sets the discussion title.
	 * 
	 * @param title
	 *            the title
	 */
	public void setTitle(String title) {
		discussion.setTitle(title);
	}

	/**
	 * Returns the discussions title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return discussion.getTitle();
	}
}
