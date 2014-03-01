/**
 * 
 */
package net.skweez.forum.logic;

import java.util.Calendar;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.SessionDatastore;
import net.skweez.forum.model.UserSession;

/**
 * The session logic. Handles all session related tasks.
 * 
 * @author elm
 * 
 */
public class SessionLogic {
	/** The lifetime for temporary sessions in days */
	private static final int SHORT_SESSION_LIFETIME = 2;

	/** The lifetime for permanent sessions in days */
	public static final int LONG_SESSION_LIFETIME = 365;

	/** the session datastore */
	private final SessionDatastore sessionDatastore = DatastoreFactory
			.createConfigured().getSessionDatastore();

	/**
	 * Creates a new session. If longSession is set to true the session will
	 * last for {@value #LONG_SESSION_LIFETIME} days.
	 * 
	 * @param uid
	 *            the uid
	 * @param longSession
	 *            indicates if the session should last for LONG_SESSION_LIFETIME
	 *            days
	 * @return the new session
	 */
	public UserSession createSession(final String uid, final boolean longSession) {
		Calendar cal = Calendar.getInstance();
		if (longSession) {
			cal.add(Calendar.DATE, LONG_SESSION_LIFETIME);
		} else {
			cal.add(Calendar.DATE, SHORT_SESSION_LIFETIME);
		}

		return sessionDatastore.createSession(uid, cal.getTime());
	}

	/**
	 * Deletes a session for a given uid.
	 * 
	 * @param uid
	 *            the uid
	 */
	public void deleteSession(String uid, int sessionId) {
		sessionDatastore.deleteSession(uid, sessionId);
	}

	/**
	 * Validate an authToken for a given uid.
	 * 
	 * @param authToken
	 *            the authToken to check
	 * @param uid
	 *            the uid for the authToken
	 * @return true if the authToken is valid. False else.
	 */
	public boolean validateAuthTokenForUID(final String authToken,
			final String uid, int sessionId) {
		UserSession session = sessionDatastore.findSession(uid, sessionId);
		return (session != null && authToken.equals(session.getAuthToken()));
	}
}