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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import net.skweez.forum.datastore.DatastoreFactory;
import net.skweez.forum.datastore.DiscussionDatastore;
import net.skweez.forum.model.Discussion;

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
	
	@Context UriInfo uriInfo;

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
	 * 
	 */
	public DiscussionResource() {
		jsonInStream.alias("Discussion", Discussion.class);
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllDiscussions() {
		Collection<Discussion> allDiscussions = datastore.selectAllDiscussions();
		
        return jsonOutStream.toXML(allDiscussions);
    }
    
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDiscussion(@PathParam("id") int id) {
		Discussion discussion = datastore.findDiscussion(id);

		return jsonOutStream.toXML(discussion);
	}

    @POST
	@Consumes(MediaType.APPLICATION_JSON)
    public Response createDiscussion(InputStream inputStream) {
    	Discussion newDiscussion;
    	ResponseBuilder builder;
		try {
			newDiscussion = (Discussion)jsonInStream.fromXML(inputStream);
		} catch (XStreamException e) {
			System.err.println(e.getMessage());
			builder = Response.status(Status.BAD_REQUEST);
			return null;
		}
		
		int newId = datastore.createDiscussion(newDiscussion);
		
		builder = Response.ok();
		UriBuilder newResourceUri = uriInfo.getRequestUriBuilder().path(String.valueOf(newId));
		builder.location(newResourceUri.build());
		
    	return builder.build();
    }
}
