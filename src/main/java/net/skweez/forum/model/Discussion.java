package net.skweez.forum.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.skweez.forum.adapters.DiscussionAdapter;

/**
 * A discussion with a title, a date, an id, a category and posts
 * 
 * @author mks
 * 
 */
public class Discussion implements AdaptableModel {

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
	private final Map<Integer, Post> posts;

	/**
	 * the category this discussion belongs to
	 */
	private Category category;

	/**
	 * constructor
	 */
	public Discussion() {
		posts = new HashMap<>();
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
	public Map<Integer, Post> getPosts() {
		return posts;
	}

	/**
	 * @param post
	 *            the post to add
	 * @return the id of the post
	 */
	public void addPost(Post post) {
		posts.put(post.getId(), post);
	}

	/**
	 * @param postId
	 *            the index of the post to remove
	 */
	public void deletePost(int postId) {
		posts.remove(postId);
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

	@Override
	public boolean adapterExists() {
		return true;
	}

	@Override
	public Class<?> adapterClass() {
		return DiscussionAdapter.class;
	}
}
