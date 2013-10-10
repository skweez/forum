package net.skweez.forum.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Category")
public class Category {

	private final String name;

	public Category(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
