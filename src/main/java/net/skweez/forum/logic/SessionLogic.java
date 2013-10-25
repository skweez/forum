/**
 * 
 */
package net.skweez.forum.logic;

import java.util.Calendar;
import java.util.Date;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.UserDatastore;
import net.skweez.forum.model.Session;
import net.skweez.forum.model.User;

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

	/** the user datastore */
	private final UserDatastore userDatastore = DatastoreFactory
			.createConfigured().getUserDatastore();

	/**
	 * Creates a new session. If longSession is set to true the session will
	 * last for {@value #LONG_SESSION_LIFETIME} days.
	 * 
	 * @param user
	 *            the user
	 * @param longSession
	 *            indicates if the session should last for LONG_SESSION_LIFETIME
	 *            days
	 * @return the new session
	 */
	public Session createSession(final User user, final boolean longSession) {
		Calendar cal = Calendar.getInstance();
		if (longSession) {
			cal.add(Calendar.DATE, LONG_SESSION_LIFETIME);
		} else {
			cal.add(Calendar.DATE, SHORT_SESSION_LIFETIME);
		}

		Session session = new Session(cal.getTime());

		user.addSessions(session);

		return session;
	}

	/**
	 * Deletes a session for a given uid.
	 * 
	 * @param user
	 *            the user
	 * @param sessionId
	 *            the id of the session to delete
	 */
	public void deleteSession(User user, int sessionId) {
		user.deleteSession(sessionId);
	}

	/**
	 * Validate an authToken for a given uid.
	 * 
	 * @param authToken
	 *            the authToken to check
	 * @param uid
	 *            the uid for the authToken
	 * @param sessionId
	 *            the id of the session
	 * @return true if the authToken is valid. False else.
	 */
	public boolean validateAuthTokenForUID(final String authToken,
			final String uid, final int sessionId) {
		User user = userDatastore.findUser(uid);
		if (user == null) {
			return false;
		}
		Session session = user.getSessions().get(sessionId);

		if (session != null && session.getExpireDate().before(new Date())) {
			deleteSession(user, sessionId);
			return false;
		}

		return (session != null && session.getAuthToken().equals(authToken));
	}
}
