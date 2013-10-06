/**
 * 
 */
package net.skweez.forum.datastore;

import net.skweez.forum.model.User;

/**
 * @author elm
 * 
 */
public interface UserDatastore {

	/**
	 * @param user
	 *            the new user
	 * @return the new user id. Return -1 if an error occured.
	 */
	boolean createUser(User user);

	/**
	 * @param uid
	 *            the uid
	 * @return the user. null if the user was not found
	 */
	User findUser(String uid);

	/**
	 * @param uid
	 *            the uid
	 * @param user
	 *            the updated user
	 * @return true if update was successful
	 */
	boolean updateUser(User user);

	/**
	 * @param uid
	 *            the uid
	 * @return true if delete was successful
	 */
	boolean deleteUser(String uid);

}
