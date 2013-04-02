package org.flowframe.kernel.json.impl.transformer;

import org.flowframe.kernel.json.JSONArray;

import flexjson.JSONContext;


public class JSONArrayJSONTransformer extends BaseJSONTransformer {

	public void transform(Object object) {
		JSONArray jsonArray = (JSONArray)object;

		JSONContext jsonContext = getContext();

		jsonContext.write(jsonArray.toString());
	}

}