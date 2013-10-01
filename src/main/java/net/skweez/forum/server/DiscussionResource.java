package net.skweez.forum.server;

import java.io.InputStream;
import java.io.Writer;
import java.util.Collection;

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
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;
import net.skweez.forum.model.Category;
import net.skweez.forum.model.Discussion;
import net.skweez.forum.model.Post;
import net.skweez.forum.model.User;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

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

	/**
	 * Initialize datastore.
	 */
	final DiscussionDatastore datastore = DatastoreFactory.getDefault()
			.getDiscussionDatastore();

	/** The XStream object used for serialization. */
	private final XStream jsonOutStream = new XStream(
			new JsonHierarchicalStreamDriver() {
				@Override
				public HierarchicalStreamWriter createWriter(Writer writer) {
					return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
				}
			});

	/**
	 * XStream object used to parse incoming objects. This needs to use
	 * JettisonMappedXmlDriver.
	 */
	private final XStream jsonInStream = new XStream(
			new JettisonMappedXmlDriver());

	/**
	 * Constructor
	 */
	public DiscussionResource() {
		// Map json object names to java objects.
		jsonInStream.alias("Discussion", Discussion.class);
		jsonInStream.alias("Post", Post.class);
		jsonInStream.alias("User", User.class);
		jsonInStream.alias("Category", Category.class);
	}

	/**
	 * @return all discussions.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllDiscussions() {
		Collection<Discussion> allDiscussions = datastore
				.selectAllDiscussions();

		return jsonOutStream.toXML(allDiscussions);
	}

	/**
	 * @param id
	 *            The id of the requested discussion
	 * @return the discussion. Returns 404 if discussion is not found.
	 */
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDiscussion(@PathParam("id") int id) {
		Discussion discussion = datastore.findDiscussion(id);

		// Return 404 if discussion is not found
		if (discussion == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		return jsonOutStream.toXML(discussion);
	}

	/**
	 * @param id
	 *            The id of the discussion for which to get the posts
	 * @return the posts. Returns 404 if discussion is not found.
	 */
	@GET
	@Path("{id}/posts")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPostsForDiscussion(@PathParam("id") int id) {
		Discussion discussion = datastore.findDiscussion(id);

		// Return 404 if discussion is not found
		if (discussion == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		return jsonOutStream.toXML(discussion.getPosts());
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
		Discussion discussion = datastore.findDiscussion(discussionId);

		// Return 404 if discussion is not found
		if (discussion == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		try {
			post = discussion.getPosts().get(postId);
		} catch (IndexOutOfBoundsException e) {
			// Return 404 if post is not found
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		return jsonOutStream.toXML(post);
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
	public Response createDiscussion(InputStream inputStream) {
		Discussion newDiscussion;
		ResponseBuilder builder;
		try {
			newDiscussion = (Discussion) jsonInStream.fromXML(inputStream);
		} catch (XStreamException e) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}

		int newId = datastore.createDiscussion(newDiscussion);

		builder = Response.ok();
		UriBuilder newResourceUri = uriInfo.getRequestUriBuilder().path(
				String.valueOf(newId));
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
	public Response createPost(@PathParam("id") int id, InputStream inputStream) {
		ResponseBuilder builder;
		Post newPost;
		Discussion discussion = datastore.findDiscussion(id);

		// Return 404 if discussion is not found
		if (discussion == null)
			throw new WebApplicationException(Response.Status.NOT_FOUND);

		try {
			newPost = (Post) jsonInStream.fromXML(inputStream);
		} catch (XStreamException e) {
			System.err.println(e.getMessage());
			builder = Response.status(Status.BAD_REQUEST);
			return builder.build();
		}

		int postIndex = discussion.addPost(newPost);
		datastore.updateDiscussion(id, discussion);

		builder = Response.ok();
		UriBuilder newResourceUri = uriInfo.getRequestUriBuilder().path(
				String.valueOf(postIndex));
		builder.location(newResourceUri.build());

		return builder.build();
	}
}
