package org.flowframe.ui.component.domain.custom;

import javax.persistence.Transient;

import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.component.domain.custom.provider.ICustomContentProvider;

public class CustomComponent extends AbstractComponent {
	private static final long serialVersionUID = 3319015549762729408L;
	
	@Transient
	private ICustomContentProvider customComponent;

	public CustomComponent() {
		super("hardcodedcomponent");
	}
	
	public CustomComponent(ICustomContentProvider customComponent) {
		this();
		this.customComponent = customComponent;
	}

	public ICustomContentProvider getHardCodedComponent() {
		return customComponent;
	}

	public void setHardCodedComponent(ICustomContentProvider hardCodedComponent) {
		this.customComponent = hardCodedComponent;
	}

}
