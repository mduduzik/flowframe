package org.flowframe.ui.component.domain.form;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.flowframe.ds.domain.DataSource;

@Entity
public class CollapseableSectionFormComponent extends FormComponent {
	private static final long serialVersionUID = 7927975246835400006L;

	@OneToMany(mappedBy = "form", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<FieldSetComponent> fieldSetSet = new HashSet<FieldSetComponent>();

	private int columnsLimit;

	public CollapseableSectionFormComponent() {
		super("collapseableSectionForm");
	}

	public CollapseableSectionFormComponent(DataSource ds) {
		super("collapseableSectionForm", ds);
	}

	public CollapseableSectionFormComponent(DataSource ds, Set<FieldSetComponent> fieldSetSet) {
		super("collapseableSectionForm", ds);
		this.fieldSetSet = fieldSetSet;
	}

	public Set<FieldSetComponent> getFieldSetSet() {
		return fieldSetSet;
	}

	public void setFieldSetSet(Set<FieldSetComponent> fieldSetSet) {
		this.fieldSetSet = fieldSetSet;
	}

	public FieldSetComponent getFieldSetForField(String fieldName) {
		for (FieldSetComponent fs : fieldSetSet) {
			if (fs.getFieldSetField(fieldName) != null) {
				return fs;
			}
		}
		return null;
	}

	/**
	 * Gets the maximum columns in the grid layout of the collapsible form.
	 * 
	 * @return column limit
	 */
	public int getColumnsLimit() {
		return columnsLimit;
	}

	/**
	 * Sets the maximum columns in the grid layout of the collapsible form.
	 * 
	 * @param columnsLimit limit to how many columns there are
	 */
	public void setColumnsLimit(int columnsLimit) {
		this.columnsLimit = columnsLimit;
	}
}
