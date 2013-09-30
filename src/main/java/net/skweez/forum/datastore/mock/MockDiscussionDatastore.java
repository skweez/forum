package net.skweez.forum.datastore.mock;

import java.util.Collection;

import net.skweez.forum.datastore.DiscussionDatastore;
import net.skweez.forum.datastore.simple.SimpleDatastoreFactory;
import net.skweez.forum.model.Discussion;

/**
 * A {@link DiscussionDatastore} with mock data.
 * 
 * @author mks
 */
public class MockDiscussionDatastore implements DiscussionDatastore {

	private DiscussionDatastore delegate;

	/** Constructor. */
	/* package */
	MockDiscussionDatastore() {
		delegate = new SimpleDatastoreFactory().getDiscussionDatastore();
		Discussion discussion = new Discussion();
		discussion.setTitle("This is a discussion");
		createDiscussion(discussion);
	}

	@Override
	public Collection<Discussion> selectAllDiscussions() {
		return delegate.selectAllDiscussions();
	}

	@Override
	public int createDiscussion(Discussion discussion) {
		return delegate.createDiscussion(discussion);
	}

	@Override
	public Discussion findDiscussion(int id) {
		return delegate.findDiscussion(id);
	}

	@Override
	public boolean updateDiscussion(int id, Discussion discussion) {
		return delegate.updateDiscussion(id, discussion);
	}

	@Override
	public boolean deleteDiscussion(int id) {
		return delegate.deleteDiscussion(id);
	}

}
