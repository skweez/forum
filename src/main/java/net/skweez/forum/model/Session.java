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
public class Session {
	/** the session id */
	private int id;

	/** the auth token */
	private String authToken;

	/** the date when the session expires */
	private Date expireDate;

	/**
	 * constructor
	 */
	public Session(Date expireDate) {
		setExpireDate(expireDate);

		// create authToken. This authToken is immutable.
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
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
