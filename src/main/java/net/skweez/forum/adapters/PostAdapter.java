/**
 * 
 */
package net.skweez.forum.adapters;

import java.util.Date;

import net.skweez.forum.model.Post;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Adapter for Posts. Provides all the members of post that are needed by
 * XStream to serialize and deserialize a post.
 * 
 * @author elm
 * 
 */
@XStreamAlias("Post")
public class PostAdapter implements ModelAdapter<Post> {
	/** the actual post */
	@XStreamOmitField
	private Post post;

	/**
	 * Creates a new PostAdapter for a new Post object.
	 */
	public PostAdapter() {
		post = new Post();
	}

	/**
	 * @param post
	 *            the post
	 */
	public PostAdapter(Post post) {
		this.post = post;
	}

	@Override
	public Post getModel() {
		return post;
	}

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
	 * @return the uid
	 */
	public String getUser() {
		return post.getUser().getUid();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return post.getId();
	}
}
