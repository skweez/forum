package net.skweez.forum.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Discussion {

	private Integer id;

	private String title;

	private final List<Post> posts;

	private Category category;

	public Discussion() {
		posts = new ArrayList<>();
		category = new Category("general");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public int addPost(Post post) {
		this.posts.add(post);
		return this.posts.indexOf(post);
	}

	public Category getCategory() {
		return category;
	}
}
