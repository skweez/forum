package net.skweez.forum.datastore.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.skweez.forum.datastore.DiscussionDatastore;
import net.skweez.forum.model.Discussion;

/**
 * An implementation of a {@link DiscussionDatastore} using a simple in-memory
 * {@link Map} for storage.
 * 
 * @author mks
 */
public class SimpleDiscussionDatastore implements DiscussionDatastore {

	/** The next id to be used. */
	private int nextId = 0;

	/** Internal "storage" for the discussions. */
	private final Map<Integer, Discussion> discussions = new HashMap<>();

	/** Hidden constructor. Use SimpleDatastoreFactory for object creation. */
	/* package */SimpleDiscussionDatastore() {
		// Just hiding constructor
	}

	/** {@inheritDoc} */
	@Override
	public synchronized List<Discussion> selectAllDiscussions() {
		List<Discussion> discussions = new ArrayList<>(
				this.discussions.values());
		Collections.sort(discussions, new Comparator<Discussion>() {
			@Override
			public int compare(Discussion d1, Discussion d2) {
				return d1.getId().compareTo(d2.getId());
			}
		});
		return discussions;
	}

	/** {@inheritDoc} */
	@Override
	public synchronized int createDiscussion(Discussion discussion) {
		discussion.setId(nextId++);
		updateDiscussion(discussion.getId(), discussion);
		return discussion.getId();
	}

	/** {@inheritDoc} */
	@Override
	public synchronized Discussion findDiscussion(int id) {
		return discussions.get(id);
	}

	/** {@inheritDoc} */
	@Override
	public synchronized boolean updateDiscussion(int id, Discussion discussion) {
		discussions.put(id, discussion);
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public synchronized boolean deleteDiscussion(int id) {
		discussions.remove(id);
		return true;
	}

}
