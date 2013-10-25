package net.skweez.forum.model;

import java.util.HashMap;
import java.util.LinkedList;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * User class.
 * 
 * @author elm
 * 
 */
@XStreamAlias("User")
public class User {
	/** the uid */
	private String uid;

	/** the roles */
	private LinkedList<String> roles;

	/** the sessions */
	@XStreamOmitField
	private HashMap<Integer, Session> sessions;

	/** the id of the next session */
	private int nextSessionId = 0;

	/**
	 * constructor
	 * 
	 * @param uid
	 *            the uid
	 */
	public User(String uid) {
		setUid(uid);
		roles = new LinkedList<>();
		sessions = new HashMap<>();
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the roles
	 */
	public LinkedList<String> getRoles() {
		return roles;
	}

	/**
	 * @param role
	 *            the role to add
	 */
	public void addRole(String role) {
		roles.add(role);
	}

	/**
	 * @return the sessions
	 */
	public HashMap<Integer, Session> getSessions() {
		return sessions;
	}

	/**
	 * @param session
	 *            the session to add
	 * @return the session id
	 */
	public int addSessions(Session session) {
		session.setId(nextSessionId++);
		sessions.put(session.getId(), session);
		return session.getId();
	}

	/**
	 * @param sessionId
	 *            the id of the session to remove
	 */
	public void deleteSession(int sessionId) {
		sessions.remove(sessionId);
	}
}
