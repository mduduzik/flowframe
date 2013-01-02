package org.flowframe.ui.component.domain;

import javax.persistence.Entity;

import org.flowframe.ds.domain.DataSourceField;
import org.flowframe.ui.component.domain.AbstractFieldComponent;

@Entity
public abstract class AbstractSelectComponent extends AbstractFieldComponent {
    private boolean multiSelect = false; 

	public AbstractSelectComponent(String typeId) {
		super(typeId);
	}
	
}
