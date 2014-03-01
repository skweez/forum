package net.skweez.forum.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * User class.
 * 
 * @author elm
 * 
 */
@XStreamAlias("User")
@Entity
public class User {
	/** The name of the uid column. */
	public static final String UID_COLUMN_NAME = "uid";

	/** The id */
	@Id
	@GeneratedValue
	private int id;

	/** The uid. */
	@Column(unique = true, name = UID_COLUMN_NAME)
	private String uid;

	/** The roles. */
	@ElementCollection(fetch = FetchType.EAGER)
	private Set<String> roles;

	/**
	 * Only used by hibernate. Needs to be package visible.
	 */
	/* package */User() {
	}

	/**
	 * Constructor.
	 * 
	 * @param uid
	 *            the uid
	 */
	public User(String uid) {
		setUid(uid);
		roles = new HashSet<>();
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the roles
	 */
	public Set<String> getRoles() {
		return roles;
	}

	/**
	 * @param role
	 *            the role to add
	 */
	public void addRole(String role) {
		roles.add(role);
	}
}
