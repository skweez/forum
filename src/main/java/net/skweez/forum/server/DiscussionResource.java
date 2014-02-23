package net.skweez.forum.server;

import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import net.skweez.forum.config.Config;
import net.skweez.forum.config.Setting;
import net.skweez.forum.logic.ForumLogic;
import net.skweez.forum.logic.LogicException;
import net.skweez.forum.logic.UserLogic;
import net.skweez.forum.model.Discussion;
import net.skweez.forum.model.Post;
import net.skweez.forum.model.User;
import net.skweez.forum.utils.JsonUtils;

/**
 * Discussions resource
 */
@Path("discussions")
public class DiscussionResource {

	/**
	 * The UriInfo in this context.
	 */
	@Context
	UriInfo uriInfo;

	/** userLogic */
	private final UserLogic userLogic = new UserLogic();
	/** forumLogic */
	private final ForumLogic forumLogic = new ForumLogic();

	/**
	 * @return all discussions.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllDiscussions() {
		return JsonUtils.serialize(forumLogic.getDiscussions());
	}

	/**
	 * @param id
	 *            The id of the requested discussion
	 * @return the discussion. Returns 404 if discussion is not found.
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDiscussion(@PathParam("id") int discussionId) {
		Discussion discussion = forumLogic.getDiscussion(discussionId);

		// Return 404 if discussion is not found
		if (discussion == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		return JsonUtils.serialize(discussion);
	}

	/**
	 * @param id
	 *            The id of the discussion for which to get the posts
	 * @return the posts. Returns 404 if discussion is not found.
	 */
	@GET
	@Path("{id}/posts")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPostsForDiscussion(@PathParam("id") int discussionId) {
		Discussion discussion = forumLogic.getDiscussion(discussionId);

		// Return 404 if discussion is not found
		if (discussion == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		return JsonUtils.serialize(discussion.getPosts());
	}

	/**
	 * @param discussionId
	 *            the discussion id
	 * @param postId
	 *            the post id
	 * @return the post. Returns 404 if the discussion or the post is not found
	 */
	@GET
	@Path("{discussionId}/posts/{postId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPost(@PathParam("discussionId") int discussionId,
			@PathParam("postId") int postId) {
		Post post;
		Discussion discussion = forumLogic.getDiscussion(discussionId);

		// Return 404 if discussion is not found
		if (discussion == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		try {
			post = discussion.getPosts().get(postId);
		} catch (IndexOutOfBoundsException e) {
			// Return 404 if post is not found
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		return JsonUtils.serialize(post);
	}

	/**
	 * Create a new discussion.
	 * 
	 * @param inputStream
	 * @return the location of the created discussion. Returns 400 if there is a
	 *         error.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createDiscussion(@Context SecurityContext sec,
			InputStream inputStream) {
		if (!sec.isUserInRole(Config.getValue(Setting.ROLE_NAME_USER))) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

		Discussion discussion;
		ResponseBuilder builder;
		try {
			discussion = JsonUtils.deserialize(inputStream, Discussion.class);
		} catch (Exception e) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}

		User user = userLogic.getUser(sec.getUserPrincipal().getName());
		discussion.setUser(user);

		int discussionId;

		try {
			discussionId = forumLogic.createDiscussion(discussion);
		} catch (LogicException e) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}

		builder = Response.ok();
		UriBuilder newResourceUri = uriInfo.getRequestUriBuilder().path(
				String.valueOf(discussionId));
		builder.location(newResourceUri.build());

		return builder.build();
	}

	/**
	 * Create a new post for a discussion.
	 * 
	 * @param inputStream
	 * @return the location of the post. Returns 400 if there is a error.
	 *         Returns 404 if the discussion does not exist.
	 */
	@POST
	@Path("{id}/posts")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createPost(@Context SecurityContext sec,
			@PathParam("id") int discussionId, InputStream inputStream) {
		if (!sec.isUserInRole(Config.getValue(Setting.ROLE_NAME_USER))) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}

		ResponseBuilder builder;
		Post post;
		int postIndex;

		try {
			post = JsonUtils.deserialize(inputStream, Post.class);
		} catch (Exception e) {
			builder = Response.status(Status.BAD_REQUEST);
			return builder.build();
		}

		post.setUser(userLogic.getUser(sec.getUserPrincipal().getName()));

		try {
			postIndex = forumLogic.addPostToDiscussion(post, discussionId);
		} catch (LogicException e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		builder = Response.ok();
		UriBuilder newResourceUri = uriInfo.getRequestUriBuilder().path(
				String.valueOf(postIndex));
		builder.location(newResourceUri.build());

		return builder.build();
	}
}
