/**
 * 
 */
package net.skweez.forum.model;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Proxy for Posts. Provides all the members of post that are needed by XStream
 * to serialize and deserialize a post.
 * 
 * @author elm
 * 
 */
public class PostProxy {
	/** the actual post */
	@XStreamOmitField
	private Post post;

	/**
	 * @return the content
	 */
	public String getContent() {
		return post.getContent();
	}

	/**
	 * Set the content of the post.
	 * 
	 * @param content
	 *            the content of the post
	 */
	public void setContent(String content) {
		post.setContent(content);
	}

	/**
	 * @return the date of the post
	 */
	public Date getDate() {
		return post.getDate();
	}

	/**
	 * Creates a new PostProxy for a new Post object.
	 */
	public PostProxy() {
		post = new Post();
	}
}
