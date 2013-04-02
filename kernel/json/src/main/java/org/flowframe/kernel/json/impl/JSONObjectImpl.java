package org.flowframe.kernel.json.impl;


import java.io.Writer;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.flowframe.kernel.common.utils.StringPool;
import org.flowframe.kernel.json.JSONArray;
import org.flowframe.kernel.json.JSONException;
import org.flowframe.kernel.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JSONObjectImpl implements JSONObject {
	
	private static Logger _log = LoggerFactory.getLogger(JSONArrayImpl.class);
	

	public JSONObjectImpl() {
		_jsonObject = new org.json.JSONObject();
	}

	public JSONObjectImpl(JSONObject jsonObject, String[] names)
		throws JSONException {

		try {
			JSONObjectImpl jsonObjectImpl = (JSONObjectImpl)jsonObject;

			_jsonObject = new org.json.JSONObject(
				jsonObjectImpl.getJSONObject(), names);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	public JSONObjectImpl(Map<?, ?> map) {
		_jsonObject = new org.json.JSONObject(map);
	}

	public JSONObjectImpl(Object bean) {
		_jsonObject = new org.json.JSONObject(bean);
	}

	public JSONObjectImpl(Object obj, String[] names) {
		_jsonObject = new org.json.JSONObject(obj, names);
	}

	public JSONObjectImpl(String json) throws JSONException {
		try {
			_jsonObject = new org.json.JSONObject(json);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	public JSONObjectImpl(org.json.JSONObject jsonObj) {
		_jsonObject = jsonObj;
	}

	public boolean getBoolean(String key) {
		return _jsonObject.optBoolean(key);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return _jsonObject.optBoolean(key, defaultValue);
	}

	public double getDouble(String key) {
		return _jsonObject.optDouble(key);
	}

	public double getDouble(String key, double defaultValue) {
		return _jsonObject.optDouble(key, defaultValue);
	}

	public int getInt(String key) {
		return _jsonObject.optInt(key);
	}

	public int getInt(String key, int defaultValue) {
		return _jsonObject.optInt(key, defaultValue);
	}

	public JSONArray getJSONArray(String key) {
		org.json.JSONArray jsonArray = _jsonObject.optJSONArray(key);

		if (jsonArray == null) {
			return null;
		}

		return new JSONArrayImpl(jsonArray);
	}

	public org.json.JSONObject getJSONObject() {
		return _jsonObject;
	}

	public JSONObject getJSONObject(String key) {
		org.json.JSONObject jsonObj = _jsonObject.optJSONObject(key);

		if (jsonObj == null) {
			return null;
		}

		return new JSONObjectImpl(jsonObj);
	}

	public long getLong(String key) {
		return _jsonObject.optLong(key);
	}

	public long getLong(String key, long defaultValue) {
		return _jsonObject.optLong(key, defaultValue);
	}

	public String getString(String key) {
		return _jsonObject.optString(key);
	}

	public String getString(String key, String defaultValue) {
		return _jsonObject.optString(key, defaultValue);
	}

	public boolean has(String key) {
		return _jsonObject.has(key);
	}

	public boolean isNull(String key) {
		return _jsonObject.isNull(key);
	}

	public Iterator<String> keys() {
		return _jsonObject.keys();
	}

	public int length() {
		return _jsonObject.length();
	}

	public JSONArray names() {
		return new JSONArrayImpl(_jsonObject.names());
	}

	public JSONObject put(String key, boolean value) {
		try {
			_jsonObject.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}
		}

		return this;
	}

	public JSONObject put(String key, Date value) {
		try {
			_jsonObject.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}
		}

		return this;
	}

	public JSONObject put(String key, double value) {
		try {
			_jsonObject.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}
		}

		return this;
	}

	public JSONObject put(String key, int value) {
		try {
			_jsonObject.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}
		}

		return this;
	}

	public JSONObject put(String key, JSONArray value) {
		try {
			_jsonObject.put(key, ((JSONArrayImpl)value).getJSONArray());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}
		}

		return this;
	}

	public JSONObject put(String key, JSONObject value) {
		try {
			_jsonObject.put(key, ((JSONObjectImpl)value).getJSONObject());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}
		}

		return this;
	}

	public JSONObject put(String key, long value) {
		try {
			_jsonObject.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}
		}

		return this;
	}

	public JSONObject put(String key, String value) {
		try {
			_jsonObject.put(key, value);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}
		}

		return this;
	}

	public JSONObject putException(Exception exception) {
		try {
			_jsonObject.put(
				"exception",
				exception.getClass() + StringPool.COLON +
					exception.getMessage());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e.getMessage(), e);
			}
		}

		return this;
	}

	public Object remove(String key) {
		return _jsonObject.remove(key);
	}

	@Override
	public String toString() {
		return _jsonObject.toString();
	}

	public String toString(int indentFactor) throws JSONException {
		try {
			return _jsonObject.toString(indentFactor);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	public Writer write(Writer writer) throws JSONException {
		try {
			return _jsonObject.write(writer);
		}
		catch (Exception e) {
			throw new JSONException(e);
		}
	}

	private org.json.JSONObject _jsonObject;
}