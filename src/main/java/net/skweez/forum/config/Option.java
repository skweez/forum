package net.skweez.forum.config;

public enum Option {

	DATASTORE_FACTORY("datastore.factory");

	private String key;

	private Option(String key) {
		this.key = key;
	}

	/* package */String getKey() {
		return key;
	}
}
