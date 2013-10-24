/**
 * 
 */
package net.skweez.forum.datastore;

import java.util.Date;

import net.skweez.forum.model.Session;

/**
 * The session datastore.
 * 
 * @author elm
 * 
 */
public interface SessionDatastore {
	/**
	 * Create a new session.
	 * 
	 * @param uid
	 *            the uid
	 * @return a new session
	 */
	public Session createSession(String uid, Date expireDate);

	/**
	 * Finds a session by uid
	 * 
	 * @param uid
	 *            the uid
	 * @return the existing session. null if none exists.
	 */
	public Session findSession(String uid);

	/**
	 * Update an existing session with a new session for a given uid.
	 * 
	 * @param uid
	 *            the uid
	 * @param session
	 *            the session
	 * @return true if the update was successful.
	 */
	public boolean updateSession(String uid, Session session);

	/**
	 * Deletes a session for a given uid.
	 * 
	 * @param uid
	 *            the uid
	 */
	public void deleteSession(String uid);
}
