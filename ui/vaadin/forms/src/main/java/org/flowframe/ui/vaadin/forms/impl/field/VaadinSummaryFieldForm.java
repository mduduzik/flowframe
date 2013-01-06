package org.flowframe.ui.vaadin.forms.impl.field;

import java.util.Collection;

import org.flowframe.ui.vaadin.forms.impl.VaadinFormAlertPanel;
import com.vaadin.data.Item;
import com.vaadin.event.MouseEvents;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.VerticalLayout;

public class VaadinSummaryFieldForm extends Form {
	private static final long serialVersionUID = 1L;
	
//	private VaadinFormToolStrip toolStrip;
//	private VaadinFormToolStripButton verifyButton;
//	private VaadinFormToolStripButton saveButton;
//	private VaadinFormToolStripButton resetButton;
	private VaadinFormAlertPanel alertPanel;
	private VerticalLayout layout;
	private VerticalLayout innerLayout;
//	private VaadinFormToolStripButton editButton;

	public VaadinSummaryFieldForm() {
		this.layout = new VerticalLayout();
		this.innerLayout = new VerticalLayout();
		this.alertPanel = new VaadinFormAlertPanel();

		initialize();
	}

	private void initialize() {
//		this.toolStrip = new VaadinFormToolStrip();

//		this.verifyButton = this.toolStrip.addToolStripButton(VaadinFormToolStrip.TOOLSTRIP_IMG_VERIFY_PNG);
//		this.verifyButton.setEnabled(false);
//		this.verifyButton.addListener(new ClickListener() {
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//			}
//		});
//		
//		this.editButton = this.toolStrip.addToolStripButton(VaadinFormToolStrip.TOOLSTRIP_IMG_EDIT_PNG);
//		this.editButton.setEnabled(false);
//		this.editButton.addListener(new ClickListener() {
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//				VaadinSummaryFieldForm.this.setReadOnly(false);
//				VaadinSummaryFieldForm.this.innerLayout.setSpacing(true);
//				VaadinSummaryFieldForm.this.editButton.setEnabled(false);
//			}
//		});
//
//		this.saveButton = this.toolStrip.addToolStripButton(VaadinFormToolStrip.TOOLSTRIP_IMG_SAVE_PNG);
//		this.saveButton.setEnabled(false);
//		this.saveButton.addListener(new ClickListener() {
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//			}
//		});
//
//		this.resetButton = this.toolStrip.addToolStripButton(VaadinFormToolStrip.TOOLSTRIP_IMG_RESET_PNG);
//		this.resetButton.setEnabled(false);
//		this.resetButton.addListener(new ClickListener() {
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//			}
//		});
		
		this.alertPanel.setVisible(false);
		this.alertPanel.addCloseListener(new MouseEvents.ClickListener() {
			private static final long serialVersionUID = 5815832688929242745L;

			@Override
			public void click(MouseEvents.ClickEvent event) {
				VaadinSummaryFieldForm.this.alertPanel.setVisible(false);
			}
		});

		this.innerLayout.setWidth("100%");
		this.innerLayout.setMargin(true);
		this.innerLayout.setSpacing(true);

		this.layout.setSizeFull();
		this.layout.addStyleName("conx-inner-form");
//		this.layout.addComponent(toolStrip);
		this.layout.addComponent(alertPanel);
		this.layout.addComponent(innerLayout);
		
		setSizeFull();
		setLayout(layout);
		setReadOnly(true);
	}
	
	@Override
	protected void attachField(Object propertyId, Field field) {
        if (propertyId == null || field == null) {
            return;
        }
        
        if (field instanceof AbstractComponent) {
        	((AbstractComponent) field).setImmediate(true);
        	((AbstractComponent) field).setReadOnly(true);
        }
        
        field.setWidth("100%");
        this.innerLayout.addComponent(field);
    }
	
	@Override
	public void setItemDataSource(Item newDataSource) {
		this.innerLayout.removeAllComponents();
		super.setItemDataSource(newDataSource);
	}
	
	@Override
	public void setItemDataSource(Item newDataSource, Collection<?> propertyIds) {
		this.innerLayout.removeAllComponents();
		super.setItemDataSource(newDataSource, propertyIds);
	}
}
