package net.skweez.forum.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * A discussion with a title, a date, an id, a category and posts
 * 
 * @author mks
 * 
 */
@XStreamAlias("Discussion")
@Entity
public class Discussion {

	/**
	 * the id
	 */
	@Id
	@GeneratedValue
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
	@ManyToOne
	private User user;

	/**
	 * the posts in this discussion
	 */
	@OneToMany(cascade = CascadeType.ALL)
	private final List<Post> posts;

	/**
	 * the category this discussion belongs to
	 */
	@ManyToOne(cascade = CascadeType.ALL)
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
	public void addPost(Post post) {
		posts.add(post);
	}

	/**
	 * @param postId
	 *            the index of the post to remove
	 */
	public void deletePost(int postId) {
		Iterator<Post> postsIterator = posts.iterator();
		while (postsIterator.hasNext()) {
			Post post = postsIterator.next();
			if (post.getId().equals(postId)) {
				postsIterator.remove();
			}
		}
	}

	/**
	 * @param category
	 *            the category
	 */
	public void setCategory(Category category) {
		this.category = category;
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
