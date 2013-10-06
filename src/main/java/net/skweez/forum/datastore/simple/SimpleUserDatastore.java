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
	 * the next id to use when a new user is created
	 */
	private int nextId = 0;

	/**
	 * all the users
	 */
	private final Map<Integer, User> users = new HashMap<>();

	/**
	 * @see net.skweez.forum.datastore.UserDatastore#createUser(net.skweez.forum.
	 *      model.User)
	 */
	@Override
	public int createUser(User user) {
		user.setId(nextId++);
		this.users.put(user.getId(), user);
		return user.getId();
	}

	/**
	 * @see net.skweez.forum.datastore.UserDatastore#findUser(int)
	 */
	@Override
	public User findUser(int id) {
		return this.users.get(id);
	}

	/**
	 * @see net.skweez.forum.datastore.UserDatastore#updateUser(int,
	 *      net.skweez.forum.model.User)
	 */
	@Override
	public boolean updateUser(int id, User user) {
		this.users.put(id, user);
		return true;
	}

	/**
	 * @see net.skweez.forum.datastore.UserDatastore#deleteUser(int)
	 */
	@Override
	public boolean deleteUser(int id) {
		if (this.users.remove(id) == null) {
			return false;
		}
		return true;
	}

}
