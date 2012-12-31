package org.flowframe.ds.domain.validators;

import javax.persistence.Entity;

import org.flowframe.ds.domain.DataSourceField;
import org.flowframe.ds.domain.DataSourceFieldValidator;

@Entity
public class IsGreaterNumberFieldValidator extends DataSourceFieldValidator {
	private static final long serialVersionUID = -7496310890917438800L;

	public IsGreaterNumberFieldValidator(DataSourceField field, DataSourceField comparisonField) {
		String fieldValue = "#form.getField('" + getPropertyId(field) + "').getValue()";
		String comparisonFieldValue = "#form.getField('" + getPropertyId(comparisonField) + "').getValue()";
		this.setValidationExpression("#isNumber(" + fieldValue + ") AND #isNumber(" + comparisonFieldValue + ") AND (#toNumber(" + fieldValue + ") > #toNumber(" + comparisonFieldValue + "))");
		this.setErrorMessage(fieldNameToTitle(field.getName()) + " must be greater than " + fieldNameToTitle(comparisonField.getName()) + ".");
		this.setField(field);
	}
	
	private String getPropertyId(DataSourceField dsField) {
		if (dsField.getJPAPath() != null) {
			return dsField.getJPAPath();
		} else {
			return dsField.getName();
		}
	}
	
	private static String fieldNameToTitle(String field) {
		String simpleName = field, title = "";
		String[] sections = simpleName.split("(?=\\p{Upper})");
		boolean isFirst = true;
		for (String section : sections) {
			if (!isFirst) {
				if (section.length() > 1) {
					title += " ";
				}
			}
			section = section.trim();
			if (section.length() == 1) {
				section = section.toUpperCase();
			} else if (section.length() > 1) {
				section = Character.toUpperCase(section.charAt(0)) + section.substring(1);
			}
			
			title += section;
			if (!"".equals(section)) {
				isFirst = false;
			}
		}
		return title;
	}
}
