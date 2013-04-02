package org.flowframe.kernel.json;

import java.io.Writer;

public interface JSONArray {

	public boolean getBoolean(int index);

	public double getDouble(int index);

	public int getInt(int index);

	public JSONObject getJSONObject(int index);
	
	public JSONArray getJSONArray(int index);

	public long getLong(int index);

	public String getString(int index);

	public boolean isNull(int index);

	public String join(String separator) throws JSONException;

	public int length();

	public JSONArray put(boolean value);

	public JSONArray put(double value);

	public JSONArray put(int value);

	public JSONArray put(JSONArray value);

	public JSONArray put(JSONObject value);

	public JSONArray put(long value);

	public JSONArray put(String value);

	public String toString();

	public String toString(int indentFactor) throws JSONException;

	public Writer write(Writer writer) throws JSONException;

}