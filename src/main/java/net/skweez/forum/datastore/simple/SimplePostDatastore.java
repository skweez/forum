package net.skweez.forum.datastore.simple;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.skweez.forum.datastore.PostDatastore;
import net.skweez.forum.model.Post;

public class SimplePostDatastore implements PostDatastore {
	/** The next id to be used. */
	private int nextId = 0;

	/** Internal "storage" for the discussions. */
	private final Map<Integer, Post> posts = new HashMap<>();

	/** Hidden constructor. Use SimpleDatastoreFactory for object creation. */
	/* package */SimplePostDatastore() {
		// Just hiding constructor
	}

	@Override
	public int createPost(Post post) {
		post.setId(nextId++);
		posts.put(post.getId(), post);
		return post.getId();
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
