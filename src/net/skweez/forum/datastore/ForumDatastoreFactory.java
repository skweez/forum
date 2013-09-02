package net.skweez.forum.datastore;

public class ForumDatastoreFactory {
	
	private static IForumDatastore datastore = new SimpleForumDatastore();
	
	public static IForumDatastore getConfiguredDatastore() {
		return datastore;
	}

}
