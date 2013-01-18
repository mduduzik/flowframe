package org.flowframe.kernel.common.mdm.domain.application;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.BaseEntity;

@Entity
@Table(name = "ffsysapplication")
public class Application extends BaseEntity {
	private static final long serialVersionUID = 100984377L;

	private String themeIconPath;

	@OneToMany(targetEntity = Feature.class, mappedBy = "parentApplication", cascade = CascadeType.ALL)
	private Set<Feature> features = new HashSet<Feature>();

	public Application() {
	}

	public Application(String ddAppPrefix)// e.g. APP.WHSE
	{
		setCode(ddAppPrefix);
	}

	public String getThemeIconPath() {
		return themeIconPath;
	}

	public void setThemeIconPath(String themeIconPath) {
		this.themeIconPath = themeIconPath;
	}

	public Set<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(Set<Feature> features) {
		this.features = features;
	}
}
