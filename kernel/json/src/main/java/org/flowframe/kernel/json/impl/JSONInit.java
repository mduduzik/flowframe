package org.flowframe.kernel.json.impl;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.flowframe.kernel.json.JSONArray;
import org.flowframe.kernel.json.JSONObject;
import org.flowframe.kernel.json.JSONSerializable;
import org.flowframe.kernel.json.impl.transformer.FlexjsonObjectJSONTransformer;
import org.flowframe.kernel.json.impl.transformer.JSONArrayJSONTransformer;
import org.flowframe.kernel.json.impl.transformer.JSONObjectJSONTransformer;
import org.flowframe.kernel.json.impl.transformer.JSONSerializableJSONTransformer;

import flexjson.TransformerUtil;
import flexjson.transformer.NullTransformer;
import flexjson.transformer.Transformer;
import flexjson.transformer.TransformerWrapper;
import flexjson.transformer.TypeTransformerMap;

public class JSONInit {

	@SuppressWarnings("rawtypes")
	public static synchronized void init() {
		try {
			if (_initalized) {
				return;
			}

			Field defaultTransformersField =
				TransformerUtil.class.getDeclaredField("defaultTransformers");

			defaultTransformersField.setAccessible(true);

			TypeTransformerMap oldTransformersMap =
				TransformerUtil.getDefaultTypeTransformers();

			TypeTransformerMap newTransformersMap = new TypeTransformerMap();

			for (Map.Entry<Class, Transformer> entry :
					oldTransformersMap.entrySet()) {

				newTransformersMap.put(entry.getKey(), entry.getValue());
			}

			_registerDefaultTransformers(newTransformersMap);

			Field modifiersField = Field.class.getDeclaredField("modifiers");

			modifiersField.setAccessible(true);

			modifiersField.setInt(
				defaultTransformersField,
				defaultTransformersField.getModifiers() & ~Modifier.FINAL);

			defaultTransformersField.set(null, newTransformersMap);

			_initalized = true;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void _registerDefaultTransformers(
		TypeTransformerMap transformersMap) {

		transformersMap.put(
			InputStream.class, new TransformerWrapper(new NullTransformer()));

		transformersMap.put(
			JSONArray.class,
			new TransformerWrapper(new JSONArrayJSONTransformer()));

		transformersMap.put(
			JSONObject.class,
			new TransformerWrapper(new JSONObjectJSONTransformer()));

		transformersMap.put(
			JSONSerializable.class,
			new TransformerWrapper(new JSONSerializableJSONTransformer()));

		transformersMap.put(
			Object.class,
			new TransformerWrapper(new FlexjsonObjectJSONTransformer()));

/*		transformersMap.put(
			RepositoryModel.class,
			new TransformerWrapper(new RepositoryModelJSONTransformer()));

		transformersMap.put(
			User.class, new TransformerWrapper(new UserJSONTransformer()));*/
	}

	private static boolean _initalized = false;

}