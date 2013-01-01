package org.flowframe.ui.component.domain.form;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class DetailFormComponent extends FormComponent {
	private static final long serialVersionUID = -3105118712425596679L;

	@OneToMany(mappedBy = "form", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<FieldSetComponent> fieldSetSet = new HashSet<FieldSetComponent>();

	public DetailFormComponent() {
		super("detailForm");
	}
	
	public DetailFormComponent(Set<FieldSetComponent> fieldSetSet) {
		super("detailForm");
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
}
