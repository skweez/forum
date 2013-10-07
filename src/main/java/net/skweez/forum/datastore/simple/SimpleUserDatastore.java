package net.skweez.forum.datastore.simple;

import java.util.HashMap;
import java.util.Map;

import net.skweez.forum.datastore.UserDatastore;
import net.skweez.forum.model.User;

/**
 * A simple in memory user datastore.
 * 
 * @author elm
 * 
 */
public class SimpleUserDatastore implements UserDatastore {

	/**
	 * all the users
	 */
	private final Map<String, User> users = new HashMap<>();

	@Override
	public boolean createUser(User user) {
		this.users.put(user.getUid(), user);
		return true;
	}

	@Override
	public User findUser(String uid) {
		return this.users.get(uid);
	}

	@Override
	public boolean updateUser(User user) {
		this.users.put(user.getUid(), user);
		return true;
	}

	@Override
	public boolean deleteUser(String uid) {
		if (this.users.remove(uid) == null) {
			return false;
		}
		return true;
	}

}
