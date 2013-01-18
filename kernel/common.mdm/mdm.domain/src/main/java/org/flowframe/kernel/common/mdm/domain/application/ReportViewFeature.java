package org.flowframe.kernel.common.mdm.domain.application;

import javax.persistence.Entity;

@Entity
public class ReportViewFeature extends Feature {
	private String reportUrl;

	public ReportViewFeature() {
	}

	public ReportViewFeature(String reportUrl) {
		super();
		this.setReportUrl(reportUrl);
		setName("Report");
	}
	
	public ReportViewFeature(String reportUrl, String caption) {
		super();
		this.setReportUrl(reportUrl);
		setName(caption);
	}

	public String getReportUrl() {
		return reportUrl;
	}

	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}
}
