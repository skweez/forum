package net.skweez.forum.datastore;

import net.skweez.forum.datastore.mock.MockDatastoreFactory;

/**
 * Abstract factory that returns datastore objects.
 * 
 * @author mks
 * 
 */
public abstract class DatastoreFactory {

	/**
	 * @return the discussion datastore
	 */
	public abstract DiscussionDatastore getDiscussionDatastore();

	public abstract PostDatastore getPostDatastore();

	/**
	 * @return the default datastore factory
	 */
	public static DatastoreFactory getDefault() {
		return new MockDatastoreFactory();
	}

}