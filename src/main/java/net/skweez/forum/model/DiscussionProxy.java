/**
 * 
 */
package net.skweez.forum.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * Adaptor/Proxy for Discussions. Provides all the members of discussions that
 * are needed by XStream to serialize and deserialize Discussions.
 * 
 * @author elm
 * 
 */
@XStreamAlias("Discussion")
public class DiscussionProxy {
	@XStreamOmitField
	private Discussion discussion;

	/**
	 * 
	 */
	public DiscussionProxy() {
		discussion = new Discussion();
	}

	public DiscussionProxy(Discussion discussion) {
		this.discussion = discussion;
	}

	public Discussion getDiscussion() {
		return discussion;
	}

	public void setPost(Post post) {
		discussion.addPost(post);
	}

	public void setTitle(String title) {
		discussion.setTitle(title);
	}

	public String getTitle(String title) {
		return discussion.getTitle();
	}
}
