package org.flowframe.ds.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

@Entity
@Table(name = "sysdsfielddependenceexpression")
public class DataSourceFieldDependenceExpression extends MultitenantBaseEntity {
	private static final long serialVersionUID = 7764591627340689123L;

	private String expression;
	
	private Class<?> evaluationType;
	
	@ManyToOne
	private DataSourceField field;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<DataSourceField> dependees = new HashSet<DataSourceField>();

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public DataSourceField getField() {
		return field;
	}

	public void setField(DataSourceField field) {
		this.field = field;
	}

	public Set<DataSourceField> getDependees() {
		return dependees;
	}

	public void setDependees(Set<DataSourceField> dependees) {
		this.dependees = dependees;
	}

	public Class<?> getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(Class<?> evaluationType) {
		this.evaluationType = evaluationType;
	}
}
