package org.flowframe.ui.vaadin.forms.impl;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class VaadinFormAlertPanel extends HorizontalLayout {
	private static final long serialVersionUID = 7061452677402665549L;
	
	private Embedded icon;
	private Label message;
	private Embedded closeButton;

	public VaadinFormAlertPanel() {
		this.icon = new Embedded();
		this.message = new Label();
		this.closeButton = new Embedded();
		
		initialize();
	}

	private void initialize() {
		this.icon.setStyleName("conx-entity-alert-panel-error-icon");
		this.icon.setHeight("14px");
		this.icon.setWidth("16px");
		
		this.message.setStyleName("conx-entity-alert-panel-message");
		this.message.setWidth("100%");
		
		this.closeButton.setStyleName("conx-entity-alert-panel-close");
		this.closeButton.setHeight("20px");
		this.closeButton.setWidth("20px");
		
		setStyleName("conx-entity-alert-panel-error");
		setWidth("100%");
		setHeight("33px");
		setSpacing(true);
		setMargin(false, true, false, true);
		
		addComponent(icon);
		addComponent(message);
		addComponent(closeButton);
		setComponentAlignment(icon, Alignment.MIDDLE_LEFT);
		setComponentAlignment(message, Alignment.TOP_LEFT);
		setComponentAlignment(closeButton, Alignment.MIDDLE_RIGHT);
		setExpandRatio(message, 1.0f);
	}
	
	public void setMessage(String msg) {
		this.message.setValue(msg);
	}
	
	public void addCloseListener(com.vaadin.event.MouseEvents.ClickListener listener) {
		this.closeButton.addListener(listener);
	}
	
	public void setAlertType(AlertType type) {
		switch (type) {
		case ERROR:
			this.icon.setStyleName("conx-entity-alert-panel-error-icon");
			setStyleName("conx-entity-alert-panel-error");
			break;
		case WARNING:
			this.icon.setStyleName("conx-entity-alert-panel-warning-icon");
			setStyleName("conx-entity-alert-panel-warning");
			break;
		case SUCCESS:
			this.icon.setStyleName("conx-entity-alert-panel-success-icon");
			setStyleName("conx-entity-alert-panel-success");
			break;
		case INFO:
			this.icon.setStyleName("conx-entity-alert-panel-info-icon");
			setStyleName("conx-entity-alert-panel-info");
			break;
		case STATUS:
			this.icon.setStyleName("conx-entity-alert-panel-status-icon");
			setStyleName("conx-entity-alert-panel-status");
			break;
		}
	}
	
	public void setCloseable(boolean closeable) {
		this.closeButton.setVisible(closeable);
	}
	
	public enum AlertType {
		ERROR,
		WARNING,
		INFO,
		STATUS,
		SUCCESS
	}
}
