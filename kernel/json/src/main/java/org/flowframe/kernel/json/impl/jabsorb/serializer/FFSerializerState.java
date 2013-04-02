package org.flowframe.kernel.json.impl.jabsorb.serializer;

import org.jabsorb.serializer.ProcessedObject;
import org.jabsorb.serializer.SerializerState;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FFSerializerState extends SerializerState {
	private static Logger _log = LoggerFactory.getLogger(FFSerializerState.class);

	@Override
	public ProcessedObject store(Object object) {
		if (!(object instanceof JSONObject)) {
			return super.store(object);
		}

		JSONObject jsonObject = (JSONObject)object;

		if (jsonObject.has("javaClass")) {
			try {
				String javaClass = jsonObject.getString("javaClass");

				if (javaClass.contains("com.liferay") &&
					javaClass.contains("Util")) {

					throw new RuntimeException(
						"Not instantiating " + javaClass);
				}
			}
			catch (JSONException jsone) {
				_log.error("Unable to parse object", jsone);
			}
		}

		return super.store(object);
	}
}