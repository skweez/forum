package net.skweez.forum.model;

/**
 * @author elm
 * 
 */
public class Post {
	/**
	 * The id of the post
	 */
	private Integer id;

	/**
	 * The content of the post
	 */
	private String content;

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
}
