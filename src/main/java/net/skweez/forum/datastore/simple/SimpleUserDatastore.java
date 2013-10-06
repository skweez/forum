/**
 * 
 */
package net.skweez.forum.datastore.simple;

import java.util.HashMap;
import java.util.Map;

import net.skweez.forum.datastore.UserDatastore;
import net.skweez.forum.model.User;

/**
 * @author elm
 * 
 */
public class SimpleUserDatastore implements UserDatastore {

	/**
	 * all the users
	 */
	private final Map<String, User> users = new HashMap<>();

	/**
	 * @see net.skweez.forum.datastore.UserDatastore#createUser(net.skweez.forum.
	 *      model.User)
	 */
	@Override
	public boolean createUser(User user) {
		this.users.put(user.getUid(), user);
		return true;
	}

	/**
	 * @see net.skweez.forum.datastore.UserDatastore#findUser(int)
	 */
	@Override
	public User findUser(String uid) {
		return this.users.get(uid);
	}

	/**
	 * @see net.skweez.forum.datastore.UserDatastore#updateUser(int,
	 *      net.skweez.forum.model.User)
	 */
	@Override
	public boolean updateUser(User user) {
		this.users.put(user.getUid(), user);
		return true;
	}

	/**
	 * @see net.skweez.forum.datastore.UserDatastore#deleteUser(int)
	 */
	@Override
	public boolean deleteUser(String uid) {
		if (this.users.remove(uid) == null) {
			return false;
		}
		return true;
	}

}
