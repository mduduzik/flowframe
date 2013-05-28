package org.flowframe.ui.component.domain.layout;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.MarginInfo;

@Entity
public abstract class AbstractLayoutComponent extends AbstractComponent {
	
	@ManyToOne
	private MarginInfo margins;
	
    public AbstractLayoutComponent(String typeId) {
        super(typeId);
    }	

    public AbstractLayoutComponent() {
        super("abstractLayout");
    }
}
