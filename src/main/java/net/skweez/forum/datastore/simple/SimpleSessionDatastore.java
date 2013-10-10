/**
 * 
 */
package net.skweez.forum.datastore.simple;

import java.util.HashMap;
import java.util.Map;

import net.skweez.forum.datastore.SessionDatastore;
import net.skweez.forum.model.Session;

/**
 * @author elm
 * 
 */
public class SimpleSessionDatastore implements SessionDatastore {
	/** the sessions */
	private Map<String, Session> sessions = new HashMap<>();

	@Override
	public Session createSession(String uid) {
		Session session = new Session(uid);
		sessions.put(uid, session);
		return session;
	}

	@Override
	public Session findSession(String uid) {
		Session session = sessions.get(uid);
		if (session != null) {
			return session;
		}
		return null;
	}

	@Override
	public boolean updateSession(String uid, Session session) {
		sessions.put(uid, session);
		return true;
	}

	@Override
	public void destroySession(String uid) {
		sessions.remove(uid);
	}

}
