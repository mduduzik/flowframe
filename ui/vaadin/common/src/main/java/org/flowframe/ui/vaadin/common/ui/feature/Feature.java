package org.flowframe.ui.vaadin.common.ui.feature;

import java.io.Serializable;

public class Feature implements Serializable {
	private static final long serialVersionUID = 6565632381L;

	public static final Object PROPERTY_ICON = "Icon";
	public static final Object PROPERTY_NAME = "Name";
	public static final Object PROPERTY_DESCRIPTION = "Description";

	private String name;
	private String description;
	private String code;
	private String icon;

	public Feature() {
	}

	public Feature(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}
	
	public Feature(String code, String name, String description, String icon) {
		this(code, name, description);
		this.setIcon(icon);
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "[ Name = "+getName()+", Code = "+getCode()+", Description = "+getDescription()+" ]";
	}
}