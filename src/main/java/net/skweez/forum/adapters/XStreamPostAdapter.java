/**
 * 
 */
package net.skweez.forum.adapters;

import java.util.Date;

import net.skweez.forum.model.Post;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Adapter for Posts.
 * 
 * @author elm
 * 
 */
@XStreamAlias("Post")
public class XStreamPostAdapter extends XStreamModelAdapter<Post> {

	/**
	 * Creates a new PostAdapter for a new Post object.
	 */
	public XStreamPostAdapter() {
		model = new Post();
	}

	/**
	 * @param post
	 *            the post
	 */
	public XStreamPostAdapter(Post post) {
		model = post;
	}

	/**
	 * Set the content of the post.
	 * 
	 * @param content
	 *            the content of the post
	 */
	public void setContent(String content) {
		model.setContent(content);
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return model.getContent();
	}

	/**
	 * @return the date of the post
	 */
	public Date getDate() {
		return model.getDate();
	}

	/**
	 * @return the uid
	 */
	public String getUser() {
		return model.getUser().getUid();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return model.getId();
	}
}
