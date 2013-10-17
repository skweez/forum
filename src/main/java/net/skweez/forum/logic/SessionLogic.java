/**
 * 
 */
package net.skweez.forum.logic;

import java.util.Calendar;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.SessionDatastore;
import net.skweez.forum.model.Session;

/**
 * @author elm
 * 
 */
public class SessionLogic {
	/** The lifetime for temporary sessions in days */
	private static final int SHORT_SESSION_LIFETIME = 2;

	/** The lifetime for permanent sessions in days */
	public static final int LONG_SESSION_LIFETIME = 365;

	/** singelton instance */
	private static SessionLogic INSTANCE = new SessionLogic();

	/** the session datastore */
	private SessionDatastore sessionDatastore;

	/**
	 * hidden constructor
	 */
	private SessionLogic() {
		sessionDatastore = DatastoreFactory.createConfigured()
				.getSessionDatastore();
	}

	/**
	 * @return the INSTANCE
	 */
	public static SessionLogic getInstance() {
		return INSTANCE;
	}

	// TODO (mks) Rename shouldLast to longSession?
	/**
	 * Creates a new session. If shouldLast is set to true the session will last
	 * for {@value #LONG_SESSION_LIFETIME} days.
	 * 
	 * @param uid
	 *            the uid
	 * @param shouldLast
	 *            indicates if the session should last for LONG_SESSION_LIFETIME
	 *            days
	 * @return the new session
	 */
	public Session createSession(final String uid, final boolean shouldLast) {
		// TODO (mks) Trivial comment?
		// delete old session
		sessionDatastore.destroySession(uid);

		Calendar cal = Calendar.getInstance();
		if (shouldLast) {
			cal.add(Calendar.DATE, LONG_SESSION_LIFETIME);
		} else {
			cal.add(Calendar.DATE, SHORT_SESSION_LIFETIME);
		}

		// TODO (mks) Inline variable (return directly)
		Session session = sessionDatastore.createSession(uid, cal.getTime());

		return session;
	}

	/**
	 * Destroys a session for a given uid.
	 * 
	 * @param uid
	 *            the uid
	 */
	public void destroySession(String uid) {
		sessionDatastore.destroySession(uid);
	}

	/**
	 * @param authToken
	 *            the authToken to check
	 * @param uid
	 *            the uid for the authToken
	 * @return true if the authToken is valid. False else.
	 */
	public boolean validateAuthTokenForUID(final String authToken,
			final String uid) {
		Session session = sessionDatastore.findSession(uid);
		if (session == null) {
			return false;
		}

		// TODO (mks) Merge with above condition (… != null && …equals)
		if (authToken.equals(session.getAuthToken())) {
			return true;
		}

		return false;
	}
}
