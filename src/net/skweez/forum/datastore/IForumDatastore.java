package net.skweez.forum.datastore;

import java.util.List;

import net.skweez.forum.model.Discussion;


public interface IForumDatastore {

	List<Discussion> getDiscussions();
	
}
