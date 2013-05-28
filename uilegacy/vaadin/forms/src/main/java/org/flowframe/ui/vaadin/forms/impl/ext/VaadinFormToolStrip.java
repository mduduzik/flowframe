package org.flowframe.ui.vaadin.forms.impl.ext;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

public class VaadinFormToolStrip extends HorizontalLayout {
	private static final long serialVersionUID = 2452919399202554279L;

	public static final String TOOLSTRIP_IMG_DELETE_PNG = "toolstrip/img/delete-dark.png";
	public static final String TOOLSTRIP_IMG_EDIT_PNG = "toolstrip/img/edit-dark.png";
	public static final String TOOLSTRIP_IMG_CREATE_PNG = "toolstrip/img/new-dark.png";
	public static final String TOOLSTRIP_IMG_SAVE_PNG = "toolstrip/img/save-dark.png";
	public static final String TOOLSTRIP_IMG_VERIFY_PNG = "toolstrip/img/verify-dark.png";
	public static final String TOOLSTRIP_IMG_RESET_PNG = "toolstrip/img/reset-dark.png";

	private HorizontalLayout leftLayout;
	private HorizontalLayout rightLayout;

	public VaadinFormToolStrip() {
		setStyleName("conx-inner-form-toolstrip");
		setHeight("40px");
		setWidth("100%");
		
		leftLayout = new HorizontalLayout();
		leftLayout.setHeight("28px");
		leftLayout.setStyleName("conx-inner-form-toolstrip-left");
		leftLayout.setSpacing(true);
		
		rightLayout = new HorizontalLayout();
		rightLayout.setHeight("28px");
		rightLayout.setStyleName("conx-inner-form-toolstrip-right");
		
		addComponent(leftLayout);
		addComponent(rightLayout);
		
		setComponentAlignment(leftLayout, Alignment.MIDDLE_LEFT);
		setComponentAlignment(rightLayout, Alignment.MIDDLE_RIGHT);
	}
	
	public VaadinFormToolStripButton addToolStripButton(String iconUrl) {
		VaadinFormToolStripButton button = new VaadinFormToolStripButton(iconUrl);
		leftLayout.addComponent(button);
		return button;
	}
	
	public void removeToolStripButton(VaadinFormToolStripButton button) {
		leftLayout.removeComponent(button);
	}
	
	public void addContextComponent(Component component) {
		rightLayout.addComponent(component);
	}
	
	public void removeContextComponent(Component component) {
		rightLayout.removeComponent(component);
	}
	
	public class VaadinFormToolStripButton extends Button {
		private static final long serialVersionUID = -6850572740737479916L;

		public VaadinFormToolStripButton(String iconUrl) {
			setIcon(new ThemeResource(iconUrl));
			setStyleName("conx-inner-form-toolstrip-button");
			setHeight("28px");
		}
	}
}
