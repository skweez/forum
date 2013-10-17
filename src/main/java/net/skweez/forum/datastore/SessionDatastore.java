/**
 * 
 */
package net.skweez.forum.datastore;

import java.util.Date;

import net.skweez.forum.model.Session;

/**
 * Holds all the session informations about the users.
 * 
 * @author elm
 * 
 */
public interface SessionDatastore {
	/**
	 * @param uid
	 *            the uid
	 * @return a new session
	 */
	public Session createSession(String uid, Date expireDate);

	/**
	 * @param uid
	 *            the uid
	 * @return the existing session. null if none exists.
	 */
	public Session findSession(String uid);

	/**
	 * sets the new session for the uid
	 * 
	 * @param uid
	 *            the uid
	 * @param session
	 *            the session
	 * @return true if the update was successful.
	 */
	public boolean updateSession(String uid, Session session);

	/**
	 * @param uid
	 *            the uid
	 */
	// TODO (mks) Rename to deleteSession to be consistent with other DAOs?
	public void destroySession(String uid);
}
