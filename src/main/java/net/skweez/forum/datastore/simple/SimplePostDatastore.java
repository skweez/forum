package net.skweez.forum.datastore.simple;

import java.util.List;

import net.skweez.forum.datastore.PostDatastore;
import net.skweez.forum.model.Post;

public class SimplePostDatastore implements PostDatastore {

	@Override
	public int createPost(Post post) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Post findPost(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> findPostsForDiscussion(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updatePost(int id, Post post) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletePost(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
