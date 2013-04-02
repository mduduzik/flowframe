package org.flowframe.kernel.json.impl.transformer;

import org.flowframe.kernel.json.JSONSerializable;

import flexjson.JSONContext;


public class JSONSerializableJSONTransformer extends BaseJSONTransformer {

	public void transform(Object object) {
		JSONSerializable jsonSerializable = (JSONSerializable)object;

		JSONContext jsonContext = getContext();

		jsonContext.write(jsonSerializable.toJSONString());
	}

}