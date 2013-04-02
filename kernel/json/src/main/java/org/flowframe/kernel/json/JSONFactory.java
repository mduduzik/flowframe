package org.flowframe.kernel.json;

import java.util.List;


public interface JSONFactory {

	public String convertJSONMLArrayToXML(String jsonml);

	public String convertJSONMLObjectToXML(String jsonml);

	public String convertXMLtoJSONMLArray(String xml);

	public String convertXMLtoJSONMLObject(String xml);

	public JSONTransformer createJavaScriptNormalizerJSONTransformer(
		List<String> javaScriptAttributes);

	public JSONArray createJSONArray();

	public JSONArray createJSONArray(String json) throws JSONException;

	public <T> JSONDeserializer<T> createJSONDeserializer();

	public JSONObject createJSONObject();

	public JSONObject createJSONObject(String json) throws JSONException;

	public JSONSerializer createJSONSerializer();

	public Object deserialize(JSONObject jsonObj);

	public Object deserialize(String json);

	public String getNullJSON();

	public Object looseDeserialize(String json);

	public <T> T looseDeserialize(String json, Class<T> clazz);

	public Object looseDeserializeSafe(String json);

	public <T> T looseDeserializeSafe(String json, Class<T> clazz);

	public String looseSerialize(Object object);

	public String looseSerialize(
		Object object, JSONTransformer jsonTransformer, Class<?> clazz);

	public String looseSerialize(Object object, String... includes);

	public String looseSerializeDeep(Object object);

	public String looseSerializeDeep(
		Object object, JSONTransformer jsonTransformer, Class<?> clazz);

	public String serialize(Object object);

	public String serializeException(Exception exception);

	public String serializeThrowable(Throwable throwable);
}