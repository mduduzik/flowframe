package org.flowframe.ui.vaadin.common.editors.entity.ext.header;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;

public class EntityEditorToolStripButton extends Button {
	private static final long serialVersionUID = -6850572740737479916L;

	public EntityEditorToolStripButton(String iconUrl) {
		setIcon(new ThemeResource(iconUrl));
		setStyleName("conx-entity-toolstrip-button");
		setHeight("28px");
	}
}
