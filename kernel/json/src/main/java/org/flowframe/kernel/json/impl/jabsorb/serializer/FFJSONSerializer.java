package org.flowframe.kernel.json.impl.jabsorb.serializer;

import org.jabsorb.JSONSerializer;
import org.jabsorb.serializer.ObjectMatch;
import org.jabsorb.serializer.SerializerState;
import org.jabsorb.serializer.UnmarshallException;


public class FFJSONSerializer extends JSONSerializer {

	@Override
	public ObjectMatch tryUnmarshall(
			SerializerState serializerState,
			@SuppressWarnings("rawtypes") Class clazz, Object json)
		throws UnmarshallException {

		if (!(serializerState instanceof FFSerializerState)) {
			serializerState = new FFSerializerState();
		}

		return super.tryUnmarshall(serializerState, clazz, json);
	}

	@Override
	public Object unmarshall(
			SerializerState serializerState,
			@SuppressWarnings("rawtypes") Class clazz, Object json)
		throws UnmarshallException {

		if (!(serializerState instanceof FFSerializerState)) {
			serializerState = new FFSerializerState();
		}

		return super.unmarshall(serializerState, clazz, json);
	}

}