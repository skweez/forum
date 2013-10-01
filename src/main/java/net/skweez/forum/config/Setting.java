package net.skweez.forum.config;

/**
 * Enumeration of all available settings keys.
 * 
 * @author mks
 */
public enum Setting {

	/** For datastore factory class to use. */
	DATASTORE_FACTORY("datastore.factory");

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
