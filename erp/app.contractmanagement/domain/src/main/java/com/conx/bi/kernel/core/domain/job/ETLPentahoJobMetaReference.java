package com.conx.bi.kernel.core.domain.job;

import javax.persistence.Entity;

@Entity
public class ETLPentahoJobMetaReference extends ETLJobMetaReference {
	private String pentahoRepoDir;

	public String getPentahoRepoDir() {
		return pentahoRepoDir;
	}

	public void setPentahoRepoDir(String pentahoRepoDir) {
		this.pentahoRepoDir = pentahoRepoDir;
	}
}