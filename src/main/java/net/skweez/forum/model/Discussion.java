package net.skweez.forum.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * A discussion with a title, a date, an id, a category and posts
 * 
 * @author mks
 * 
 */
@XStreamAlias("Discussion")
public class Discussion {

	/**
	 * the id
	 */
	private Integer id;

	/**
	 * the title
	 */
	private String title;

	/**
	 * the date
	 */
	private Date date;

	/**
	 * the user
	 */
	private User user;

	/**
	 * the posts in this discussion
	 */
	private final List<Post> posts;

	/**
	 * the category this discussion belongs to
	 */
	private Category category;

	/**
	 * constructor
	 */
	public Discussion() {
		posts = new ArrayList<>();
		category = new Category("general");
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return all posts
	 */
	public List<Post> getPosts() {
		return posts;
	}

	/**
	 * @param post
	 *            the post to add
	 * @return the id of the post
	 */
	public int addPost(Post post) {
		posts.add(post);
		return posts.indexOf(post);
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
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
