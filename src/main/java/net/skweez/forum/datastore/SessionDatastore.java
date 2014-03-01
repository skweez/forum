/**
 * 
 */
package net.skweez.forum.datastore;

import java.util.Date;
import java.util.Map;

import net.skweez.forum.model.UserSession;

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
	public UserSession createSession(String uid, Date expireDate);

	/**
	 * Finds a session by uid
	 * 
	 * @param uid
	 *            the uid
	 * @return the existing session. Returns an empty map if no sessions exist.
	 */
	public Map<Integer, UserSession> findSessions(String uid);

	/**
	 * @param uid
	 *            the uid
	 * @param sessionId
	 *            the session id
	 * @return the session or null of none is found.
	 */
	public UserSession findSession(String uid, int sessionId);

	/**
	 * Update an existing session with a new session for a given uid.
	 * 
	 * @param uid
	 *            the uid
	 * @param session
	 *            the session
	 * @return true if the update was successful.
	 */
	public boolean updateSession(String uid, UserSession session);

	/**
	 * Deletes a session for a given uid.
	 * 
	 * @param uid
	 *            the uid
	 */
	public void deleteSession(String uid, int sessionId);
}
