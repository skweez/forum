package net.skweez.forum.datastore;

import net.skweez.forum.config.Config;
import net.skweez.forum.config.Setting;
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

	/**
	 * @return The configured datastore factory or the default one if none was
	 *         configured.
	 */
	public static DatastoreFactory createConfigured() {
		try {
			return createForClassName(Config.getValue(Setting.DATASTORE_FACTORY));
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
			return createDefault();
		}
	}

	/**
	 * @return The {@link DatastoreFactory} for the given fully-qualified class
	 *         name.
	 */
	@SuppressWarnings("unchecked")
	public static DatastoreFactory createForClassName(String className)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		return createForClass((Class<? extends DatastoreFactory>) Class
				.forName(className));
	}

	/** @return The {@link DatastoreFactory} for the given class. */
	public static DatastoreFactory createForClass(
			Class<? extends DatastoreFactory> factoryClass)
			throws InstantiationException, IllegalAccessException {
		return factoryClass.newInstance();
	}

	/** @return A new instance of {@link SimpleDatastoreFactory}. */
	private static DatastoreFactory createDefault() {
		return new SimpleDatastoreFactory();
	}

}