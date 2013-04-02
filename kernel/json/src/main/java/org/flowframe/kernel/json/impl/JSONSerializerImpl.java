package org.flowframe.kernel.json.impl;


import org.flowframe.kernel.json.JSONSerializer;
import org.flowframe.kernel.json.JSONTransformer;

import flexjson.transformer.Transformer;


public class JSONSerializerImpl implements JSONSerializer {

	public JSONSerializerImpl() {
		_jsonSerializer = new flexjson.JSONSerializer();
	}

	public JSONSerializerImpl exclude(String... fields) {
		_jsonSerializer.exclude(fields);

		return this;
	}

	public JSONSerializerImpl include(String... fields) {
		_jsonSerializer.include(fields);

		return this;
	}

	public String serialize(Object target) {
		return _jsonSerializer.serialize(target);
	}

	public String serializeDeep(Object target) {
		return _jsonSerializer.deepSerialize(target);
	}

	public JSONSerializerImpl transform(
		JSONTransformer jsonTransformer, Class<?>... types) {

		Transformer transformer = null;

		if (jsonTransformer instanceof Transformer) {
			transformer = (Transformer)jsonTransformer;
		}
		else {
			transformer = new FlexjsonTransformer(jsonTransformer);
		}

		_jsonSerializer.transform(transformer, types);

		return this;
	}

	public JSONSerializerImpl transform(
		JSONTransformer jsonTransformer, String... fields) {

		Transformer transformer = null;

		if (jsonTransformer instanceof Transformer) {
			transformer = (Transformer)jsonTransformer;
		}
		else {
			transformer = new FlexjsonTransformer(jsonTransformer);
		}

		_jsonSerializer.transform(transformer, fields);

		return this;
	}

	private final flexjson.JSONSerializer _jsonSerializer;

}