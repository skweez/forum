package net.skweez.forum.model;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Discussion {
	
	private final List<Post> comments;
	
	private Category category;

	public Discussion() {
		comments = new ArrayList<>();
		category = new Category("general");
	}
}
