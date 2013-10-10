/**
 * 
 */
package net.skweez.forum.model;

/**
 * @author elm
 * 
 */
public class Session {
	/** the uid for this session */
	private String uid;

	/** the auth token */
	private String authToken;

	/**
	 * constructor
	 */
	public Session(String uid) {
		this.uid = uid;
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
	 * @param authToken
	 *            the authToken to set
	 */
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
