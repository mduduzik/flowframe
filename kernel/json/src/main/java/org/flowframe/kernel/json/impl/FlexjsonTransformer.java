package org.flowframe.kernel.json.impl;

import org.flowframe.kernel.json.JSONTransformer;

import flexjson.transformer.Transformer;

public class FlexjsonTransformer implements Transformer {

	public FlexjsonTransformer(JSONTransformer jsonTransformer) {
		_jsonTransformer = jsonTransformer;
	}

	public void transform(Object object) {
		_jsonTransformer.transform(object);
	}

	private JSONTransformer _jsonTransformer;

}