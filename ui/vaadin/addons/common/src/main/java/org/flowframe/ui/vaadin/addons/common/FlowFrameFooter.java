package org.flowframe.ui.vaadin.addons.common;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class FlowFrameFooter extends HorizontalLayout {
	private static final long serialVersionUID = 8044130622970466177L;
	
	private Label customer;
	
	public FlowFrameFooter() {
		setWidth("100%");
		setHeight("30px");
		setStyleName("conx-footer");
		setMargin(false, true, false, true);
		
		initialize();
	}
	
	private void initialize() {
		Label poweredBy = new Label("Powered By ConX");
		poweredBy.setWidth("150px");
		addComponent(poweredBy);
		setComponentAlignment(poweredBy, Alignment.MIDDLE_LEFT);
		
		Label copyright = new Label("&copy; Business Convergence LLC 2012. All Rights Reserved.");
		copyright.setContentMode(Label.CONTENT_XHTML);
		copyright.addStyleName("conx-footer-middle");
		addComponent(copyright);
		setComponentAlignment(copyright, Alignment.MIDDLE_CENTER);
		setExpandRatio(copyright, 1.0f);
		
		customer = new Label();
		customer.setWidth("150px");
		customer.setContentMode(Label.CONTENT_XHTML);
		customer.addStyleName("conx-footer-right");
		setCustomerName("OHL");
		
		addComponent(customer);
		setComponentAlignment(customer, Alignment.MIDDLE_RIGHT);
	}
	
	public void setCustomerName(String customerName) {
		customer.setCaption("Licenced by " + customerName);
	}
}
