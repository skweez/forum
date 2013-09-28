package net.skweez.forum.datastore;

import net.skweez.forum.datastore.simple.SimpleDatastoreFactory;

public abstract class DatastoreFactory {

	public abstract DiscussionDatastore getDiscussionDatastore();

	public static DatastoreFactory getDefault() {
		return new SimpleDatastoreFactory();
	}

}