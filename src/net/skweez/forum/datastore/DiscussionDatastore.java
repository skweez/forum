package net.skweez.forum.datastore;

import java.util.Collection;

import net.skweez.forum.model.Discussion;

/**
 * A Data Access Object for {@link Discussion}.
 * 
 * @author mks
 */
public interface DiscussionDatastore {

	/** Retrieves a collection of all discussions in the datastore. */
	Collection<Discussion> selectAllDiscussions();

	/** Inserts the new given discussion into the datastore. */
	int createDiscussion(Discussion discussion);

	/** Retrieves the discussion with the given id from the datastore. */
	Discussion findDiscussion(int id);

	/**
	 * Updates the given discussion in the datastore. Returns <code>true</code>
	 * on success, <code>false</code> otherwise.
	 */
	boolean updateDiscussion(Discussion discussion);

	/**
	 * Deletes the discussion from the datastore. Returns <code>true</code> on
	 * success, <code>false</code> otherwise.
	 */
	boolean deleteDiscussion(Discussion discussion);

}
