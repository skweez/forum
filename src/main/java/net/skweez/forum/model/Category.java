package net.skweez.forum.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * A category a discussion can belong to.
 * 
 * @author elm
 * 
 */
@XStreamAlias("Category")
public class Category {
	/** the category name */
	private final String name;

	/**
	 * Create a new category with a name
	 * 
	 * @param name
	 *            the name
	 */
	public Category(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
