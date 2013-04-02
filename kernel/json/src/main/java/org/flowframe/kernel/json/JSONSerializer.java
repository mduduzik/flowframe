package org.flowframe.kernel.json;


public interface JSONSerializer {

	public JSONSerializer exclude(String... fields);

	public JSONSerializer include(String... fields);

	public String serialize(Object target);

	public String serializeDeep(Object target);

	public JSONSerializer transform(
		JSONTransformer jsonTransformer, Class<?>... types);

	public JSONSerializer transform(
		JSONTransformer jsonTransformer, String... fields);

}