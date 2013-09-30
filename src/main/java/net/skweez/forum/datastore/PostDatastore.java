package net.skweez.forum.datastore;

import java.util.List;

import net.skweez.forum.model.Post;

public interface PostDatastore {

	int createPost(Post post);

	Post findPost(int id);

	List<Post> findPostsForDiscussion(int id);

	boolean updatePost(int id, Post post);

	boolean deletePost(int id);

}
