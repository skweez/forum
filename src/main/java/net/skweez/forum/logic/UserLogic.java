/**
 * 
 */
package net.skweez.forum.logic;

import javax.ws.rs.core.SecurityContext;

import net.skweez.forum.config.Config;
import net.skweez.forum.config.Setting;
import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.UserDatastore;
import net.skweez.forum.model.User;

/**
 * @author elm
 * 
 */
public class UserLogic {
	/** the datastore factory */
	private static final DatastoreFactory CONFIGURED_DATASTORE_FACTORY = DatastoreFactory
			.createConfigured();
	/** the user datastore */
	private static final UserDatastore userDatastore = CONFIGURED_DATASTORE_FACTORY
			.getUserDatastore();

	/**
	 * Creates a new user. Role informations for the new user are extracted from
	 * the security context.
	 * 
	 * @param uid
	 *            the uid of the new user
	 * @param sec
	 *            the security context
	 * @return the new user. If a user with that uid already exists this user is
	 *         returned
	 */
	public static User createUser(String uid, SecurityContext sec) {
		User user = userDatastore.findUser(uid);

		if (user != null) {
			return user;
		}

		// create new user
		user = new User(uid);

		// TODO: Do not test each role but iterate over them. Maybe use a sub
		// enum or something.
		if (sec.isUserInRole(Config.getValue(Setting.ROLE_NAME_USER))) {
			user.addRole(Config.getValue(Setting.ROLE_NAME_USER));
		}
		if (sec.isUserInRole(Config.getValue(Setting.ROLE_NAME_ADMIN))) {
			user.addRole(Config.getValue(Setting.ROLE_NAME_ADMIN));
		}

		userDatastore.createUser(user);

		return user;
	}

	/**
	 * @param uid
	 *            the uid
	 * @return the user for the uid. Returns null if no such user exists.
	 */
	public static User getUser(String uid) {
		return userDatastore.findUser(uid);
	}
}
