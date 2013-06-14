package org.flowframe.kernel.json.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.flowframe.kernel.json.JSONArray;
import org.flowframe.kernel.json.JSONDeserializer;
import org.flowframe.kernel.json.JSONException;
import org.flowframe.kernel.json.JSONFactory;
import org.flowframe.kernel.json.JSONObject;
import org.flowframe.kernel.json.JSONSerializer;
import org.flowframe.kernel.json.JSONTransformer;
import org.flowframe.kernel.json.impl.JSONUtil.Validator;
import org.flowframe.kernel.json.impl.jabsorb.serializer.FFJSONSerializer;
import org.flowframe.kernel.json.impl.jabsorb.serializer.FFSerializer;
import org.flowframe.kernel.json.impl.jabsorb.serializer.LocaleSerializer;
import org.flowframe.kernel.json.impl.transformer.StringTransformer;
import org.jabsorb.serializer.MarshallException;
import org.json.JSONML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONFactoryImpl implements JSONFactory {
	
	private static Logger _log = LoggerFactory.getLogger(JSONFactoryImpl.class);

	public JSONFactoryImpl() {
		JSONInit.init();

		_jsonSerializer = new FFJSONSerializer();

		try {
			_jsonSerializer.registerDefaultSerializers();

			_jsonSerializer.registerSerializer(new FFSerializer());
			_jsonSerializer.registerSerializer(new LocaleSerializer());
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	public String convertJSONMLArrayToXML(String jsonml) {
		try {
			org.json.JSONArray jsonArray = new org.json.JSONArray(jsonml);

			return JSONML.toString(jsonArray);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}

			throw new IllegalStateException("Unable to convert to XML", e);
		}
	}

	public String convertJSONMLObjectToXML(String jsonml) {
		try {
			org.json.JSONObject jsonObject = new org.json.JSONObject(jsonml);

			return JSONML.toString(jsonObject);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}

			throw new IllegalStateException("Unable to convert to XML", e);
		}
	}

	public String convertXMLtoJSONMLArray(String xml) {
		try {
			org.json.JSONArray jsonArray = JSONML.toJSONArray(xml);

			return jsonArray.toString();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}

			throw new IllegalStateException("Unable to convert to JSONML", e);
		}
	}

	public String convertXMLtoJSONMLObject(String xml) {
		try {
			org.json.JSONObject jsonObject = JSONML.toJSONObject(xml);

			return jsonObject.toString();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}

			throw new IllegalStateException("Unable to convert to JSONML", e);
		}
	}

	public JSONTransformer createJavaScriptNormalizerJSONTransformer(
		List<String> javaScriptAttributes) {

		StringTransformer stringTransformer = new StringTransformer();

		stringTransformer.setJavaScriptAttributes(javaScriptAttributes);

		return stringTransformer;
	}

	public JSONArray createJSONArray() {
		return new JSONArrayImpl();
	}

	public JSONArray createJSONArray(String json) throws JSONException {
		return new JSONArrayImpl(json);
	}

	public <T> JSONDeserializer<T> createJSONDeserializer() {
		return new JSONDeserializerImpl<T>();
	}

	public JSONObject createJSONObject() {
		return new JSONObjectImpl();
	}

	public JSONObject createJSONObject(String json) throws JSONException {
		return new JSONObjectImpl(json);
	}

	public JSONSerializer createJSONSerializer() {
		return new JSONSerializerImpl();
	}

	public Object deserialize(JSONObject jsonObj) {
		return deserialize(jsonObj.toString());
	}

	public Object deserialize(String json) {
		try {
			return _jsonSerializer.fromJSON(json);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}

			throw new IllegalStateException("Unable to deserialize object", e);
		}
	}

	public String getNullJSON() {
		return _NULL_JSON;
	}

	public Object looseDeserialize(String json) {
		try {
			JSONDeserializer<?> jsonDeserializer = createJSONDeserializer();

			return jsonDeserializer.deserialize(json);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}

			throw new IllegalStateException("Unable to deserialize object", e);
		}
	}

	public <T> T looseDeserialize(String json, Class<T> clazz) {
		JSONDeserializer<?> jsonDeserializer = createJSONDeserializer();

		jsonDeserializer.use(null, clazz);

		return (T)jsonDeserializer.deserialize(json);
	}

	public Object looseDeserializeSafe(String json) {
		try {
			JSONDeserializer<?> jsonDeserializer = createJSONDeserializer();

			jsonDeserializer.safeMode(true);

			return jsonDeserializer.deserialize(json);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}

			throw new IllegalStateException("Unable to deserialize object", e);
		}
	}

	public <T> T looseDeserializeSafe(String json, Class<T> clazz) {
		JSONDeserializer<?> jsonDeserializer = createJSONDeserializer();

		jsonDeserializer.safeMode(true);

		jsonDeserializer.use(null, clazz);

		return (T)jsonDeserializer.deserialize(json);
	}

	public String looseSerialize(Object object) {
		JSONSerializer jsonSerializer = createJSONSerializer();

		return jsonSerializer.serialize(object);
	}

	public String looseSerialize(
		Object object, JSONTransformer jsonTransformer, Class<?> clazz) {

		JSONSerializer jsonSerializer = createJSONSerializer();

		jsonSerializer.transform(jsonTransformer, clazz);

		return jsonSerializer.serialize(object);
	}

	public String looseSerialize(Object object, String... includes) {
		JSONSerializer jsonSerializer = createJSONSerializer();

		jsonSerializer.include(includes);

		return jsonSerializer.serialize(object);
	}

	public String looseSerializeDeep(Object object) {
		JSONSerializer jsonSerializer = createJSONSerializer();

		return jsonSerializer.serializeDeep(object);
	}

	public String looseSerializeDeep(
		Object object, JSONTransformer jsonTransformer, Class<?> clazz) {

		JSONSerializer jsonSerializer = createJSONSerializer();

		jsonSerializer.transform(jsonTransformer, clazz);

		return jsonSerializer.serializeDeep(object);
	}

	public String serialize(Object object) {
		try {
			return _jsonSerializer.toJSON(object);
		}
		catch (MarshallException me) {
			if (_log.isWarnEnabled()) {
				_log.warn(me.getMessage(), me);
			}

			throw new IllegalStateException("Unable to serialize oject", me);
		}
	}

	public String serializeException(Exception exception) {
		JSONObject jsonObject = createJSONObject();

		String message = null;

		if (exception instanceof InvocationTargetException) {
			Throwable cause = exception.getCause();

			message = cause.toString();
		}
		else {
			message = exception.getMessage();
		}

		if (Validator.isNull(message)) {
			message = exception.toString();
		}

		jsonObject.put("exception", message);

		return jsonObject.toString();
	}

	public String serializeThrowable(Throwable throwable) {
		if (throwable instanceof Exception) {
			return serializeException((Exception)throwable);
		}

		JSONObject jsonObject = createJSONObject();

		String message = throwable.getMessage();

		if (Validator.isNull(message)) {
			message = throwable.toString();
		}

		jsonObject.put("throwable", message);

		return jsonObject.toString();
	}

	private static final String _NULL_JSON = "{}";

	private org.jabsorb.JSONSerializer _jsonSerializer;

}