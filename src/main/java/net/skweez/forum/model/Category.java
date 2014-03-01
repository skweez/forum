package net.skweez.forum.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * A category a discussion can belong to.
 * 
 * @author elm
 * 
 */
@XStreamAlias("Category")
@Entity
public class Category {
	/**
	 * The id of the category
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/** the category name */
	private final String name;

	/**
	 * Only used by hibernate. Needs to be package visible.
	 */
	/* package */Category() {
		this(null);
	}

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
}
