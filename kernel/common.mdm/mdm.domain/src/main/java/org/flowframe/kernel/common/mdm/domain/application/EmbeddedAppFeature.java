package org.flowframe.kernel.common.mdm.domain.application;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.utils.StringUtil;


@Entity
public class EmbeddedAppFeature extends Feature {

	private String baseUrl;
	private Map<String,String> urlParams = new HashMap<String,String>();
 
    public EmbeddedAppFeature()
    {
    }

	public EmbeddedAppFeature(String baseUrl) {
		super();
		this.baseUrl = baseUrl;
	}
	
	public EmbeddedAppFeature(String baseUrl, String code, String name, Feature[] childFeatures) {
		super(code, name, childFeatures);
		this.baseUrl = baseUrl;
	}
	
	public EmbeddedAppFeature(String baseUrl, String code, String name, String iconUrl) {
		super(code, name, iconUrl);
		this.baseUrl = baseUrl;
	}	

	public void addParam(String name, String value){
		urlParams.put(name, value);
	}
	
	public void addParams(Map<String,String> urlParams){
		urlParams.putAll(urlParams);
	}
	
	public String generateUrl(){
		String url = baseUrl+"?";
		for (String param : urlParams.keySet()) {
			url += param+"="+urlParams.get(param)+";";
		}
		
		url = StringUtil.replaceLast(url, ';',' ');
		
		return url;
	}
}
