package org.flowframe.kernel.json;


public class JSONException extends Exception {

	public JSONException() {
		super();
	}

	public JSONException(String msg) {
		super(msg);
	}
	
	public JSONException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public JSONException(Throwable cause) {
		super(cause);
	}
}