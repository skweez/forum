package net.skweez.forum.datastore;

import java.util.ArrayList;
import java.util.List;

import net.skweez.forum.model.Discussion;

public class SimpleForumDatastore implements IForumDatastore {
	
	private final List<Discussion> discussions = new ArrayList<>();

	/* package */SimpleForumDatastore() {
		// Just hiding constructor
	}

	@Override
	public List<Discussion> getDiscussions() {
		return discussions;
	}

}
