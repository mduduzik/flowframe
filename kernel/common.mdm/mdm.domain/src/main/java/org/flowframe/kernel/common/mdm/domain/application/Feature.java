package org.flowframe.kernel.common.mdm.domain.application;

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

import org.flowframe.kernel.common.mdm.domain.BaseEntity;

import org.flowframe.kernel.common.utils.Validator;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "sysfeature")
public class Feature extends BaseEntity {
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

	protected boolean featureSet = false;

	protected boolean taskFeature;
	
	protected String taskId;

	protected String componentModelCode;

	protected Long entityId;// In case of inline editor launch

	private String caption;

	private String iconUrl;

	public Feature() {
	}

	public Feature(Application parentApplication, Feature parentFeature, String featureCode) {
		setParentApplication(parentApplication);
		setParentFeature(parentFeature);
		if (Validator.isNotNull(parentFeature))
			setCode(parentFeature.getCode() + "." + featureCode);
		else {
			setCode(parentApplication.getCode() + "." + featureCode);
			setFeatureSet(true);
		}
	}

	public Feature(Application parentApplication, Feature parentFeature, String featureCode, boolean isFeatuteset) {
		setParentApplication(parentApplication);
		setParentFeature(parentFeature);
		setCode(parentFeature.getCode() + "." + featureCode);
		this.featureSet = isFeatuteset;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Feature getParentFeature() {
		return parentFeature;
	}

	public void setParentFeature(Feature parentFeature) {
		this.parentFeature = parentFeature;
	}

	public Application getParentApplication() {
		return parentApplication;
	}

	public void setParentApplication(Application parentApplication) {
		this.parentApplication = parentApplication;
	}

	public Set<Feature> getChildFeatures() {
		return childFeatures;
	}

	public void setChildFeatures(Set<Feature> childFeatures) {
		this.childFeatures = childFeatures;
	}

	public boolean isFeatureSet() {
		return featureSet;
	}

	public void setFeatureSet(boolean featureSet) {
		this.featureSet = featureSet;
	}

	public boolean isTaskFeature() {
		return taskFeature;
	}

	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

	public String getComponentModelCode() {
		return componentModelCode;
	}

	public void setComponentModelCode(String componentModelCode) {
		this.componentModelCode = componentModelCode;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
}
