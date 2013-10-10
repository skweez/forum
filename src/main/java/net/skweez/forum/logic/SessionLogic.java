/**
 * 
 */
package net.skweez.forum.logic;

import java.math.BigInteger;
import java.security.SecureRandom;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.SessionDatastore;
import net.skweez.forum.model.Session;

/**
 * @author elm
 * 
 */
public class SessionLogic {
	/** singelton instance */
	private static SessionLogic INSTANCE = new SessionLogic();

	/** the session datastore */
	private SessionDatastore sessionDatastore;

	/** A secure rng */
	private SecureRandom random;

	/**
	 * hidden constructor
	 */
	private SessionLogic() {
		sessionDatastore = DatastoreFactory.createConfigured()
				.getSessionDatastore();
		random = new SecureRandom();
	}

	/**
	 * @return the INSTANCE
	 */
	public static SessionLogic getInstance() {
		return INSTANCE;
	}

	/**
	 * @param uid
	 *            the uid for which to create the authToken
	 * @return the newly created authToken
	 */
	public String createAuthTokenForUID(final String uid) {
		Session session = sessionDatastore.findSession(uid);

		if (session == null) {
			session = sessionDatastore.createSession(uid);
		}

		String authToken = new BigInteger(130, random).toString(32);
		session.setAuthToken(authToken);

		sessionDatastore.updateSession(uid, session);

		return authToken;
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

		if (authToken.equals(session.getAuthToken())) {
			return true;
		}

		return false;
	}

	/**
	 * @param uid
	 *            the uid for which the auth token should be invalidated.
	 */
	public void invalidateAuthTokenForUID(final String uid) {
		Session session = sessionDatastore.findSession(uid);

		if (session != null) {
			session.setAuthToken(null);
			sessionDatastore.updateSession(uid, session);
		}
	}
}
