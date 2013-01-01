package org.flowframe.ui.component.domain.form;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.flowframe.ds.domain.DataSource;

@Entity
public class CollapsibleConfirmActualsFormComponent extends FormComponent {
	private static final long serialVersionUID = 2274128688130249656L;
	
	@OneToMany(mappedBy = "form", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<ConfirmActualsFieldSetComponent> fieldSetSet = new HashSet<ConfirmActualsFieldSetComponent>();
	
	public CollapsibleConfirmActualsFormComponent() {
		super("collapsibleconfirmactualsform");
	}
	
	public CollapsibleConfirmActualsFormComponent(DataSource ds) {
		this();
		this.setDataSource(ds);
	}
	
	public CollapsibleConfirmActualsFormComponent(DataSource ds, String caption) {
		this(ds);
		this.setCaption(caption);
	}

	public Set<ConfirmActualsFieldSetComponent> getFieldSetSet() {
		return fieldSetSet;
	}

	public void setFieldSetSet(Set<ConfirmActualsFieldSetComponent> fieldSetSet) {
		this.fieldSetSet = fieldSetSet;
	}

	public ConfirmActualsFieldSetComponent getFieldSetForField(String fieldName) {
		for (ConfirmActualsFieldSetComponent fs : fieldSetSet) {
			if (fs.getFieldSetField(fieldName) != null) {
				return fs;
			}
		}
		return null;
	}
}
