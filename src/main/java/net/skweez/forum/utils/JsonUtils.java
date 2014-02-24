package net.skweez.forum.utils;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;

public class JsonUtils {

	public static String serialize(Object object) {
		return new Gson().toJson(object);
	}

	@SuppressWarnings("unchecked")
	public static <T> T deserialize(InputStream inputStream,
			Class<T> expectedClass) throws Exception {
		return new Gson().fromJson(new InputStreamReader(inputStream),
				expectedClass);
	}
}
