package org.flowframe.ui.component.domain.form;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.flowframe.ds.domain.DataSource;

@Entity
public class CollapsiblePhysicalAttributeConfirmActualsFormComponent extends FormComponent {
	private static final long serialVersionUID = 2274128688130249656L;
	
	@OneToMany(mappedBy = "form", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<PhysicalAttributeConfirmActualsFieldSetComponent> fieldSetSet = new HashSet<PhysicalAttributeConfirmActualsFieldSetComponent>();
	
	public CollapsiblePhysicalAttributeConfirmActualsFormComponent() {
		super("collapsiblephysicalattributeconfirmactualsform");
	}
	
	public CollapsiblePhysicalAttributeConfirmActualsFormComponent(DataSource ds) {
		this();
		this.setDataSource(ds);
	}
	
	public CollapsiblePhysicalAttributeConfirmActualsFormComponent(DataSource ds, String caption) {
		this(ds);
		this.setCaption(caption);
	}

	public Set<PhysicalAttributeConfirmActualsFieldSetComponent> getFieldSetSet() {
		return fieldSetSet;
	}

	public void setFieldSetSet(Set<PhysicalAttributeConfirmActualsFieldSetComponent> fieldSetSet) {
		this.fieldSetSet = fieldSetSet;
	}

	public PhysicalAttributeConfirmActualsFieldSetComponent getFieldSetForField(String fieldName) {
		for (PhysicalAttributeConfirmActualsFieldSetComponent fs : fieldSetSet) {
			if (fs.getFieldSetField(fieldName) != null) {
				return fs;
			}
		}
		return null;
	}
}
