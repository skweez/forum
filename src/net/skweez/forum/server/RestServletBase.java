package net.skweez.forum.server;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

/**
 * Abstract base class for servlets that behave REST-like. Sub-classes should
 * implement {@link #get(String)}, {@link #getCollection(Map)},
 * {@link #put(String, Object)},
 * 
 * @author mks
 * 
 * @param <T>
 *            The type of resource objects the servlet handles.
 * 
 * @see http://www.restapitutorial.com/
 */
@SuppressWarnings("serial")
public abstract class RestServletBase<T> extends HttpServlet {

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

	/** {@inheritDoc} */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getPathInfo() != null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		@SuppressWarnings("unchecked")
		T newObject = (T) jsonInStream.fromXML(request.getInputStream());
		String pathInfo = post((T) newObject);

		response.setStatus(HttpServletResponse.SC_CREATED);
		response.setHeader("Location", pathInfo);
	};

	/**
	 * Creates the given object.
	 * 
	 * @return The path of the newly created resource.
	 */
	protected abstract String post(T newObject);

	/** {@inheritDoc} */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();

		if (path == null) {
			doGetCollection(request, response);
		} else {
			T resource = get(path);
			if (resource != null) {
				response.setContentType("text/json;charset=utf-8");
				response.setStatus(HttpServletResponse.SC_OK);
				jsonOutStream.toXML(resource, response.getOutputStream());
			} else {
				response.setContentType("text/plain;charset=utf-8");
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				response.getOutputStream().println(
						HttpServletResponse.SC_NOT_FOUND + " - Not found");
			}
		}
	}

	/**
	 * Returns the resource for the given path or <code>null</code> if it
	 * doesn't exist.
	 */
	protected abstract T get(String pathInfo);

	/** Implementation of the GET method for a collection. */
	private void doGetCollection(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/json;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		jsonOutStream.toXML(getCollection(request.getParameterMap()),
				response.getOutputStream());
	}

	/**
	 * Returns the collection of resources for the specified query. Does
	 * <b>not</b> return <code>null</code>.
	 */
	protected abstract Collection<T> getCollection(
			Map<String, String[]> queryParams);

	/** {@inheritDoc} */
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPut(req, resp);
	}

	/**
	 * Updates the resource at the path to given new object.
	 * 
	 * @return <code>true</code> on success, <code>false</code> if the resource
	 *         for pathInfo doesn't exist.
	 */
	protected abstract boolean put(String pathInfo, T newObject);

	/** {@inheritDoc} */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(req, resp);
	}

	/**
	 * Deletes the resource at the given path.
	 * 
	 * @return <code>true</code> on success, <code>false</code> if the resource
	 *         for pathInfo doesn't exist.
	 */
	protected abstract boolean delete(String pathInfo);
}