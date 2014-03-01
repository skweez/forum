/**
 * 
 */
package net.skweez.forum.datastore.simple;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.skweez.forum.datastore.SessionDatastore;
import net.skweez.forum.model.UserSession;

/**
 * A simple session datastore that holds the sessions in memory.
 * 
 * @author elm
 * 
 */
public class SimpleSessionDatastore implements SessionDatastore {
	/** The sessions for every user. */
	private Map<String, Map<Integer, UserSession>> sessions = new HashMap<>();

	/** The session id. */
	private int nextSessionId = 0;

	@Override
	public UserSession createSession(String uid, Date expireDate) {
		UserSession session = new UserSession(expireDate);

		session.setId(nextSessionId++);
		findSessions(uid).put(session.getId(), session);
		return session;
	}

	@Override
	public Map<Integer, UserSession> findSessions(String uid) {
		Map<Integer, UserSession> userSessions = sessions.get(uid);

		if (userSessions == null) {
			userSessions = new HashMap<>();
			sessions.put(uid, userSessions);
		}
		return userSessions;
	}

	@Override
	public UserSession findSession(String uid, int sessionId) {
		return findSessions(uid).get(sessionId);
	}

	@Override
	public boolean updateSession(String uid, UserSession session) {
		findSessions(uid).put(session.getId(), session);
		return true;
	}

	@Override
	public void deleteSession(String uid, int sessionId) {
		findSessions(uid).remove(sessionId);
	}

}
