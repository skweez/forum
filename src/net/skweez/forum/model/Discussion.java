package net.skweez.forum.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Discussion {

	private Integer id;

	private String title;

	private final List<Post> comments;

	private Category category;

	public Discussion() {
		comments = new ArrayList<>();
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

	public List<Post> getComments() {
		return comments;
	}

	public Category getCategory() {
		return category;
	}
}
