package com.conx.bi.etl.services.plugins;

public class ETLPluginTypeInfo {
    private String        stepid;   // --> StepPlugin.id
	private String        stepname;
	private String        category;
	private String        description;
	private String         pluginDialogClassName;
	
	
	
	public ETLPluginTypeInfo() {
		super();
	}
	
	public ETLPluginTypeInfo(String stepid, String stepname, String category, String description, String pluginDialogClassName) {
		super();
		this.stepid = stepid;
		this.stepname = stepname;
		this.category = category;
		this.description = description;
		this.pluginDialogClassName = pluginDialogClassName;
	}
	public String getStepid() {
		return stepid;
	}
	public void setStepid(String stepid) {
		this.stepid = stepid;
	}
	public String getStepname() {
		return stepname;
	}
	public void setStepname(String stepname) {
		this.stepname = stepname;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPluginDialogClassName() {
		return pluginDialogClassName;
	}
	public void setPluginDialogClassName(String pluginDialogClassName) {
		this.pluginDialogClassName = pluginDialogClassName;
	}
}
