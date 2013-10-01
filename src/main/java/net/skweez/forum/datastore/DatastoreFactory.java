package net.skweez.forum.datastore;

import net.skweez.forum.config.Config;
import net.skweez.forum.config.Option;
import net.skweez.forum.datastore.simple.SimpleDatastoreFactory;

/**
 * Abstract factory that creates datastore objects.
 * 
 * @author mks
 */
public abstract class DatastoreFactory {

	/** @return The discussions datastore. */
	public abstract DiscussionDatastore getDiscussionDatastore();

	/** @return The posts datastore. */
	public abstract PostDatastore getPostDatastore();

	/** @return The configured datastore factory. */
	public static DatastoreFactory createConfigured() {
		try {
			return createForClassName(Config.getValue(Option.DATASTORE_FACTORY));
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
			return createDefault();
		}
	}

	public static DatastoreFactory createForClassName(String className)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		return createForClass((Class<? extends DatastoreFactory>) Class
				.forName(className));
	}

	public static DatastoreFactory createForClass(
			Class<? extends DatastoreFactory> factoryClass)
			throws InstantiationException, IllegalAccessException {
		return factoryClass.newInstance();
	}

	private static DatastoreFactory createDefault() {
		return new SimpleDatastoreFactory();
	}

}