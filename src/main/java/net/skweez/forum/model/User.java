package net.skweez.forum.model;

import java.util.LinkedList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * User class.
 * 
 * @author elm
 * 
 */
@XStreamAlias("User")
public class User {

	/**
	 * the auth token
	 */
	@XStreamOmitField
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
		setUid(uid);
		roles = new LinkedList<>();
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
		roles.add(role);
	}
}
