package net.skweez.forum.utils;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;

/**
 * Utility class for serialization and deserialization of objects to/from JSON.
 * 
 * @author mks
 */
public class JsonUtils {

	/** Serialize the given object. */
	public static String serialize(Object object) {
		return new Gson().toJson(object);
	}

	/**
	 * Deserialize an object from the stream.
	 * 
	 * @throws Exception
	 *             if the object cannot be deserialized.
	 */
	public static <T> T deserialize(InputStream inputStream,
			Class<T> expectedClass) throws Exception {
		return new Gson().fromJson(new InputStreamReader(inputStream),
				expectedClass);
	}
}
