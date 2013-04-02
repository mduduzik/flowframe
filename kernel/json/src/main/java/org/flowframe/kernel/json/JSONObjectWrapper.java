package org.flowframe.kernel.json;


public class JSONObjectWrapper {

	public JSONObjectWrapper(Object value) {
		_value = value;
	}

	public Object getValue() {
		return _value;
	}

	private Object _value;

}