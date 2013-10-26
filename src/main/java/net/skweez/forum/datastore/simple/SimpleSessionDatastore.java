/**
 * 
 */
package net.skweez.forum.datastore.simple;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.skweez.forum.datastore.SessionDatastore;
import net.skweez.forum.model.Session;

/**
 * A simple session datastore that holds the sessions in memory.
 * 
 * @author elm
 * 
 */
public class SimpleSessionDatastore implements SessionDatastore {
	/** The sessions for every user. */
	private Map<String, Map<Integer, Session>> sessions = new HashMap<>();

	/** The session id. */
	private int nextSessionId = 0;

	@Override
	public Session createSession(String uid, Date expireDate) {
		Session session = new Session(expireDate);

		session.setId(nextSessionId++);
		findSessions(uid).put(session.getId(), session);
		return session;
	}

	@Override
	public Map<Integer, Session> findSessions(String uid) {
		Map<Integer, Session> userSessions = sessions.get(uid);

		if (userSessions == null) {
			userSessions = new HashMap<>();
			sessions.put(uid, userSessions);
		}
		return userSessions;
	}

	@Override
	public Session findSession(String uid, int sessionId) {
		return findSessions(uid).get(sessionId);
	}

	@Override
	public boolean updateSession(String uid, Session session) {
		findSessions(uid).put(session.getId(), session);
		return true;
	}

	@Override
	public void deleteSession(String uid, int sessionId) {
		findSessions(uid).remove(sessionId);
	}

}
