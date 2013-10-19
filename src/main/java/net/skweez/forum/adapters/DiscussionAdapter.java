/**
 * 
 */
package net.skweez.forum.adapters;

import net.skweez.forum.model.Discussion;
import net.skweez.forum.model.Post;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Adapter for Discussions. Provides all the members of discussions that are
 * needed by XStream to serialize and deserialize Discussions.
 * 
 * @author elm
 * 
 */
@XStreamAlias("Discussion")
public class DiscussionAdapter extends ModelAdapter<Discussion> {
	/**
	 * Creates a new DiscussionAdapter for a new discussion.
	 */
	public DiscussionAdapter() {
		model = new Discussion();
	}

	/**
	 * Creates a new DiscussionAdapter for a given discussion.
	 * 
	 * @param discussion
	 *            the discussion
	 */
	public DiscussionAdapter(Discussion discussion) {
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
}
