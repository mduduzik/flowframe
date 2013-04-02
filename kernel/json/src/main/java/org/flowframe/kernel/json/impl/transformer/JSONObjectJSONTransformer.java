package org.flowframe.kernel.json.impl.transformer;

import org.flowframe.kernel.json.JSONObject;

import flexjson.JSONContext;

public class JSONObjectJSONTransformer extends BaseJSONTransformer {

	public void transform(Object object) {
		JSONObject jsonObject = (JSONObject)object;

		JSONContext jsonContext = getContext();

		jsonContext.write(jsonObject.toString());
	}

}