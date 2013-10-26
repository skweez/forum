package net.skweez.forum.config;

/**
 * Enumeration of all available settings keys.
 * 
 * @author mks
 */
public enum Setting {

	/** For datastore factory class to use. */
	DATASTORE_FACTORY("datastore.factory"),

	/** The admins role name setting. */
	ROLE_NAME_ADMIN("rolename.admin"),

	/** The users role name setting. */
	ROLE_NAME_USER("rolename.user"),

	/** The login service to use. */
	LOGIN_SERVICE("login.service");

	/** The key. */
	private String key;

	/** Constructor. */
	private Setting(String key) {
		this.key = key;
	}

	/** @return The key name. */
	/* package */String getKey() {
		return key;
	}
}
