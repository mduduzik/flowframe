package org.flowframe.ui.vaadin.addons.common;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;

public class FlowFrameEntityToolStripButton extends Button {
	private static final long serialVersionUID = -6850572740737479916L;

	public FlowFrameEntityToolStripButton(String iconUrl) {
		setIcon(new ThemeResource(iconUrl));
		setStyleName("conx-entity-toolstrip-button");
		setHeight("28px");
	}
}
