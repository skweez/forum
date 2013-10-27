/**
 * 
 */
package net.skweez.forum.adapters;

import java.util.Date;

import net.skweez.forum.model.Discussion;
import net.skweez.forum.model.Post;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * XStream adapter for Discussions.
 * 
 * @author elm
 * 
 */
@XStreamAlias("Discussion")
public class XStreamDiscussionAdapter extends XStreamModelAdapter<Discussion> {
	/**
	 * Creates a new XStreamDiscussionAdapter with a new discussion.
	 */
	public XStreamDiscussionAdapter() {
		model = new Discussion();
	}

	/**
	 * Creates a new XStreamDiscussionAdapter for a given discussion.
	 * 
	 * @param discussion
	 *            the discussion
	 */
	public XStreamDiscussionAdapter(Discussion discussion) {
		model = discussion;
	}

	/**
	 * Sets a post for the discussion. This is used by XStream when
	 * deserializing a DiscussionAdapter object.
	 * 
	 * @param post
	 */
	public void setPost(Post post) {
		assert (model.getPosts().size() == 0);
		if (post.getId() == null) {
			post.setId(0);
		}
		model.addPost(post);
	}

	/**
	 * @param title
	 *            the title
	 */
	public void setTitle(String title) {
		model.setTitle(title);
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * @return the uid
	 */
	public String getUser() {
		return model.getUser().getUid();
	}

	/**
	 * @return the discussion id
	 */
	public int getId() {
		return model.getId();
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return model.getDate();
	}
}
