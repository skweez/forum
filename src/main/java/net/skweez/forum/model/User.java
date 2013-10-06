package net.skweez.forum.model;

import java.util.LinkedList;

/**
 * User class.
 * 
 * @author elm
 * 
 */
public class User {

	/**
	 * the auth token
	 */
	private String authToken;

	/**
	 * the uid
	 */
	private String uid;

	/**
	 * the roles
	 */
	private LinkedList<String> roles;

	/**
	 * constructor
	 * 
	 * @param uid
	 *            the uid
	 */
	public User(String uid) {
		this.setUid(uid);
		this.roles = new LinkedList<>();
	}

	/**
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * @param authToken
	 *            the authToken to set
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
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
		this.roles.add(role);
	}
}
