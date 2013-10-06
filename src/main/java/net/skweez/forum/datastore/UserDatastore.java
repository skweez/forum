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
	int createUser(User user);

	/**
	 * @param id
	 *            the id
	 * @return the user. null if the user was not found
	 */
	User findUser(int id);

	/**
	 * @param id
	 *            the id
	 * @param user
	 *            the updated user
	 * @return true if update was successful
	 */
	boolean updateUser(int id, User user);

	/**
	 * @param id
	 *            the is
	 * @return true if delete was successful
	 */
	boolean deleteUser(int id);

}
