/**
 * 
 */
package net.skweez.forum.model;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * @author elm
 * 
 */
public class PostProxy {
	@XStreamOmitField
	private Post post;

	public String getContent() {
		return post.getContent();
	}

	public void setContent(String content) {
		post.setContent(content);
	}

	public Date getDate() {
		return post.getDate();
	}

	/**
	 * 
	 */
	public PostProxy() {
		post = new Post();
	}
}
