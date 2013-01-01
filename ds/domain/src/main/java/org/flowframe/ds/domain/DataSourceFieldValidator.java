package org.flowframe.ds.domain;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.flowframe.kernel.common.mdm.domain.MultitenantBaseEntity;

@Entity
@Table(name = "ffsysdsfieldvalidator")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class DataSourceFieldValidator extends MultitenantBaseEntity {
	private static final long serialVersionUID = 2947385360166519486L;

	private String validationExpression;
	
	private String errorMessage;
	
	@ManyToOne
	private DataSourceField field;
	
	public DataSourceFieldValidator() {
	}
	
	public DataSourceFieldValidator(String validationExpression) {
		this.validationExpression = validationExpression;
	}

	public String getValidationExpression() {
		return validationExpression;
	}

	public void setValidationExpression(String validationExpression) {
		this.validationExpression = validationExpression;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public DataSourceField getField() {
		return field;
	}

	public void setField(DataSourceField field) {
		this.field = field;
	}
}
