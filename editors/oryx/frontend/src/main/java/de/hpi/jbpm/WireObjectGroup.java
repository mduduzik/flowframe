package de.hpi.jbpm;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class WireObjectGroup {
	
	protected String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toJpdl() {
		return "";
	}

	public JSONObject toJson() throws JSONException {
		return new JSONObject();
	}
}
