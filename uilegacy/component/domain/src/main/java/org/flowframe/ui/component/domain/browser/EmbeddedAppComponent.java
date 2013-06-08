package org.flowframe.ui.component.domain.browser;

import org.flowframe.ui.component.domain.AbstractComponent;

public class EmbeddedAppComponent extends AbstractComponent {
	private static final long serialVersionUID = 11212009982L;
	private String url;
	private String userTokenName;
	private String userTokenValue;

	public EmbeddedAppComponent() {
		super("embeddedappcomponent");
	}
	
	public EmbeddedAppComponent(String url) {
		this();
		this.url = url;
	}
	
	public EmbeddedAppComponent(String url, String userTokenName, String userTokenValue) {
		super();
		this.url = url;
		this.userTokenName = userTokenName;
		this.userTokenValue = userTokenValue;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserTokenName() {
		return userTokenName;
	}

	public void setUserTokenName(String userTokenName) {
		this.userTokenName = userTokenName;
	}

	public String getUserTokenValue() {
		return userTokenValue;
	}

	public void setUserTokenValue(String userTokenValue) {
		this.userTokenValue = userTokenValue;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
	
	public String generateFullUrl(){
		if (url.contains("?")) {
			return url += userTokenName+"="+userTokenValue;
		}
		else
		{
			return url += "?"+userTokenName+"="+userTokenValue;
		}
	}
}
