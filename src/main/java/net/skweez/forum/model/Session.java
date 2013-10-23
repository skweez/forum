/**
 * 
 */
package net.skweez.forum.model;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

/**
 * A session that holds the uid, the expire date and the auth token for a logged
 * in user.
 * 
 * @author elm
 * 
 */
public class Session {
	/** the uid for this session */
	private String uid;

	/** the auth token */
	private String authToken;

	/** the date when the session expires */
	private Date expireDate;

	/**
	 * constructor
	 */
	public Session(String uid, Date expireDate) {
		this.uid = uid;
		setExpireDate(expireDate);

		// create authToken. This authToken is immutable.
		authToken = new BigInteger(130, new SecureRandom()).toString(32);
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
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
}
