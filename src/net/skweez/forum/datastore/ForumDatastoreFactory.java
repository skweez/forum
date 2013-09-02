package net.skweez.forum.datastore;

public class ForumDatastoreFactory {
	
	private static IForumDatastore datastore;
	
	public static IForumDatastore getConfiguredDatastore() {
		if (datastore == null) {
			datastore = new SimpleForumDatastore();
		}
		
		return datastore;
	}

}
