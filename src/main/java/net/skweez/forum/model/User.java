package net.skweez.forum.model;

import java.util.LinkedList;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * User class.
 * 
 * @author elm
 * 
 */
@XStreamAlias("User")
public class User {
	/** the uid */
	private String uid;

	/** the roles */
	private LinkedList<String> roles;

	/**
	 * constructor
	 * 
	 * @param uid
	 *            the uid
	 */
	public User(String uid) {
		setUid(uid);
		roles = new LinkedList<>();
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
	public LinkedList<String> getRoles() {
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
