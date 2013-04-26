package com.conx.bi.kernel.core.domain.etl.ref.trans.steps;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

@Entity(name = "StepMetaRef")
@Table(name = "bietlstepmetaref")
public class StepMetaRef extends MultitenantBaseEntity {

	protected String stepId;

	protected String stepType;
	
	protected String category;

	@Column(columnDefinition = "longtext")
	protected String xmlContent;

	protected String repoDirectory;
	
	protected String repoTransformationFilename;

	protected String repoTransformationFilenameVersion;
	
	public StepMetaRef(){
		super();
	}

	public StepMetaRef(String stepId, String stepType, String category, String description, String xmlContent, String repoDirectory, String repoFilename, String repoFilenameVersion) {
		super();
		this.stepId = stepId;
		super.setCode(this.stepId);
		super.setName(this.stepId);

		this.stepType = stepType;
		this.category = category;
		super.setDescription(description);
		this.xmlContent = xmlContent;
		this.repoDirectory = repoDirectory;
		this.repoTransformationFilename = repoFilename;
		this.repoTransformationFilenameVersion = repoFilenameVersion;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}

	public String getXmlContent() {
		return xmlContent;
	}

	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}

	public String getRepoDirectory() {
		return repoDirectory;
	}

	public void setRepoDirectory(String repoDirectory) {
		this.repoDirectory = repoDirectory;
	}

	public String getRepoTransformationFilename() {
		return repoTransformationFilename;
	}

	public void setRepoTransformationFilename(String repoFilename) {
		this.repoTransformationFilename = repoFilename;
	}

	public String getRepoTransformationFilenameVersion() {
		return repoTransformationFilenameVersion;
	}

	public void setRepoTransformationFilenameVersion(String repoFilenameVersion) {
		this.repoTransformationFilenameVersion = repoFilenameVersion;
	}

	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
