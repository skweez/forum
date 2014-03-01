/**
 * 
 */
package net.skweez.forum.model;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

/**
 * A session that holds the expire date and the auth token for a logged in user.
 * 
 * @author elm
 * 
 */
public class UserSession {
	/** The session id. */
	private int id;

	/** The auth token. */
	private String authToken;

	/** The date when the session expires. */
	private Date expireDate;

	/**
	 * Constructor.
	 */
	public UserSession(Date expireDate) {
		setExpireDate(expireDate);
		authToken = new BigInteger(130, new SecureRandom()).toString(32);
	}

	/**
	 * @return the authToken
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * @return the expireDate
	 */
	public Date getExpireDate() {
		return expireDate;
	}

	/**
	 * @param expireDate
	 *            the expireDate to set
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
