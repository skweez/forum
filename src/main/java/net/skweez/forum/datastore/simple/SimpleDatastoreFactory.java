package net.skweez.forum.datastore.simple;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.SessionDatastore;

/**
 * Factory that creates simple datastore objects with in-memory storage. This
 * factory ensures, that the returned Datastores are singletons.
 */
public class SimpleDatastoreFactory extends DatastoreFactory {

	/** Session datastore */
	private static SessionDatastore sessionDatastore = new SimpleSessionDatastore();

	@Override
	public SessionDatastore getSessionDatastore() {
		return sessionDatastore;
	}

}
