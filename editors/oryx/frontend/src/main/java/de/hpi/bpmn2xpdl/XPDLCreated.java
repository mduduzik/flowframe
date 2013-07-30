package de.hpi.bpmn2xpdl;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.xmappr.RootElement;
import org.xmappr.Text;

@RootElement("Created")
public class XPDLCreated extends XMLConvertible {

	@Text
	protected String content;

	public String getContent() {
		return content;
	}

	public void readJSONcreationdate(JSONObject modelElement) {
		setContent(modelElement.optString("creationdate"));
	}
	
	public void readJSONcreationdateunknowns(JSONObject modelElement) {
		readUnknowns(modelElement, "creationdateunknowns");
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void writeJSONcreationdate(JSONObject modelElement) throws JSONException {
		modelElement.put("creationdate", getContent());
	}
	
	public void writeJSONcreationdateunknowns(JSONObject modelElement) throws JSONException {
		writeUnknowns(modelElement, "creationdateunknowns");
	}
}
