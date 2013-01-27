package org.flowframe.kernel.common.mdm.domain.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "ffsysfeature")
public class Feature extends BaseEntity {
	private static final long serialVersionUID = 8632457324542651L;
	
	@ManyToOne(targetEntity = Application.class)
	@JoinColumn
	protected Application parentApplication;

	@ManyToOne(targetEntity = Feature.class)
	@JoinColumn
	protected Feature parentFeature;

	@OneToOne(targetEntity = Feature.class)
	@JoinColumn
	protected Feature onCompletionFeature;

	@OneToMany(targetEntity = Feature.class, mappedBy = "parentFeature", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Feature> childFeatures = new java.util.HashSet<Feature>();

	protected boolean taskFeature;

	private String iconUrl;
	
	@Transient
	private Map<String,Object> params = new HashMap<String, Object>();

	public Feature() {
	}
	
	public Feature(String code, String name) {
		this.setCode(code);
		this.setName(name);
	}
	
	public Feature(String code, String name, String iconUrl) {
		this(code, name);
		this.iconUrl = iconUrl;
	}
	
	public Feature(String code, String name, Feature[] childFeatures) {
		this(code, name);
		
		for (Feature childFeature : childFeatures) {
			this.childFeatures.add(childFeature);
		}
	}
	
	public Feature(String code, String name, Feature[] childFeatures, String iconUrl) {
		this(code, name, childFeatures);
		this.iconUrl = iconUrl;
	}

	public Feature getParentFeature() {
		return parentFeature;
	}

	public void setParentFeature(Feature parentFeature) {
		this.parentFeature = parentFeature;
	}

	public Set<Feature> getChildFeatures() {
		return childFeatures;
	}

	public void setChildFeatures(Set<Feature> childFeatures) {
		this.childFeatures = childFeatures;
	}

	public boolean isTaskFeature() {
		return taskFeature;
	}

	public void setTaskFeature(boolean taskFeature) {
		this.taskFeature = taskFeature;
	}

	public Feature getOnCompletionFeature() {
		return onCompletionFeature;
	}

	public void setOnCompletionFeature(Feature onCompletionFeature) {
		this.onCompletionFeature = onCompletionFeature;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Application getParentApplication() {
		return parentApplication;
	}

	public void setParentApplication(Application parentApplication) {
		this.parentApplication = parentApplication;
	}

	@Transient
	public boolean isFeatureSet() {
		return this.childFeatures.size() > 0;
	}
	
	public void addParam(String paramKey, Object paramValue) {
		this.params.put(paramKey, paramValue);
	}
	
	public Object getParam(String paramKey) {
		return this.params.get(paramKey);
	}	
}
