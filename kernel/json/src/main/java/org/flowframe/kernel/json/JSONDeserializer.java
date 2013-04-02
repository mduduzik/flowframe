package org.flowframe.kernel.json;

import java.io.Reader;


public interface JSONDeserializer<T> {
	public T deserialize(Reader input);

	public T deserialize(String input);

	public JSONDeserializer<T> safeMode(boolean safeMode);

	public JSONDeserializer<T> use(String path, Class<?> clazz);
}
