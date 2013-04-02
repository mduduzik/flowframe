package org.flowframe.kernel.json.impl;


import java.io.Reader;

import org.flowframe.kernel.json.JSONDeserializer;

public class JSONDeserializerImpl<T> implements JSONDeserializer<T> {

	public JSONDeserializerImpl() {
		_jsonDeserializer = new flexjson.JSONDeserializer<T>();
	}

	public T deserialize(Reader input) {
		return _jsonDeserializer.deserialize(input);
	}

	public T deserialize(String input) {
		return _jsonDeserializer.deserialize(input);
	}

	public JSONDeserializer<T> safeMode(boolean safeMode) {
		return this;
	}

	public JSONDeserializer<T> use(String path, Class<?> clazz) {
		_jsonDeserializer.use(path, clazz);

		return this;
	}

	private flexjson.JSONDeserializer<T> _jsonDeserializer;
}