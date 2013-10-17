package net.skweez.forum.model;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author elm
 * 
 */
@XStreamAlias("Post")
public class Post {
	/** The id of the post */
	private Integer id;

	/** The content of the post */
	private String content;

	/** The date of the post */
	private Date date;

	/** The User that created this post */
	private User user;

	/**
	 * @return the post id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id of the post
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the content of the post
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the posts content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
