package de.hpi.bpmn2xpdl;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import org.xmappr.RootElement;
import org.xmappr.Text;

@RootElement("Documentation")
public class XPDLDocumentation extends XMLConvertible {

	@Text
	protected String content;

	public String getContent() {
		return content;
	}
	
	public void readJSONdocumentation(JSONObject modelElement) {
		setContent(modelElement.optString("documentation"));
	}
	
	public void readJSONdocumentationunknowns(JSONObject modelElement) {
		readUnknowns(modelElement, "documentationunknowns");
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public void writeJSONdocumentation(JSONObject modelElement) throws JSONException {
		modelElement.put("documentation", getContent());
	}
	
	public void writeJSONdocumentationunknowns(JSONObject modelElement) throws JSONException {
		writeUnknowns(modelElement, "documentationunknowns");
	}
}