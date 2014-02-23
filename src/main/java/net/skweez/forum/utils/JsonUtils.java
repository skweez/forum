package net.skweez.forum.utils;

import java.io.InputStream;
import java.io.Writer;

import net.skweez.forum.model.Category;
import net.skweez.forum.model.Discussion;
import net.skweez.forum.model.Post;
import net.skweez.forum.model.User;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.ISO8601DateConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.AbstractJsonWriter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class JsonUtils {

	public static String serialize(Object object) {
		return getOutStream().toXML(object);
	}

	private static XStream getOutStream() {
		XStream stream = new XStream(new JsonHierarchicalStreamDriver() {
			@Override
			public HierarchicalStreamWriter createWriter(Writer writer) {
				return new JsonWriter(writer, AbstractJsonWriter.DROP_ROOT_MODE);
			}
		});
		stream.autodetectAnnotations(true);
		stream.registerConverter(new ISO8601DateConverter());

		return stream;
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserialize(InputStream inputStream,
			Class<T> expectedClass) throws Exception {
		return (T) getInStream().fromXML(inputStream);
	}

	private static XStream getInStream() {
		XStream stream = new XStream(new JettisonMappedXmlDriver());
		stream.processAnnotations(new Class[] { Discussion.class, Post.class,
				User.class, Category.class });
		stream.registerConverter(new ISO8601DateConverter());
		return stream;
	}

}
