package net.skweez.forum.config;

/**
 * Enumeration of all available settings keys.
 * 
 * @author mks
 */
public enum Setting {

	/** For datastore factory class to use. */
	DATASTORE_FACTORY("datastore.factory"),

	/** the admins role name */
	ROLE_NAME_ADMIN("rolename.admin"),
	/** the users role name */
	ROLE_NAME_USER("rolename.user");

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
