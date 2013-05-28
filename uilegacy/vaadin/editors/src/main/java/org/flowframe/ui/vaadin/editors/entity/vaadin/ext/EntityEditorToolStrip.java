package org.flowframe.ui.vaadin.editors.entity.vaadin.ext;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class EntityEditorToolStrip extends HorizontalLayout {
	private static final long serialVersionUID = 2452919399202554279L;
	
	public static final String TOOLSTRIP_CAPTION_DELETE = "Delete Item";
	public static final String TOOLSTRIP_CAPTION_EDIT = "Edit Item";
	public static final String TOOLSTRIP_CAPTION_CREATE = "Create New Item";
	public static final String TOOLSTRIP_CAPTION_SAVE = "Save";
	public static final String TOOLSTRIP_CAPTION_ATTACH = "Attach New Document";
	public static final String TOOLSTRIP_CAPTION_REPORT = "Create Report for Item";
	public static final String TOOLSTRIP_CAPTION_PRINT = "Print Grid";
	public static final String TOOLSTRIP_CAPTION_VERIFY = "Validate";
	public static final String TOOLSTRIP_CAPTION_RESET = "Reset";
	public static final String TOOLSTRIP_CAPTION_SEARCH = "Search";
	public static final String TOOLSTRIP_CAPTION_HIDE_FILTER = "Hide Filter";
	public static final String TOOLSTRIP_CAPTION_FILTER = "Show Filter";
	public static final String TOOLSTRIP_CAPTION_CLEAR = "Clear";
	public static final String TOOLSTRIP_CAPTION_MATCH = "Match Item";
	public static final String TOOLSTRIP_CAPTION_UNMATCH = "Unmatch Item";

	public static final String TOOLSTRIP_IMG_DELETE_PNG = "toolstrip/img/delete.png";
	public static final String TOOLSTRIP_IMG_EDIT_PNG = "toolstrip/img/edit.png";
	public static final String TOOLSTRIP_IMG_CREATE_PNG = "toolstrip/img/new.png";
	public static final String TOOLSTRIP_IMG_SAVE_PNG = "toolstrip/img/save.png";
	public static final String TOOLSTRIP_IMG_ATTACH_PNG = "toolstrip/img/attach.png";
	public static final String TOOLSTRIP_IMG_REPORT_PNG = "toolstrip/img/report.png";
	public static final String TOOLSTRIP_IMG_PRINT_PNG = "toolstrip/img/print.png";
	public static final String TOOLSTRIP_IMG_VERIFY_PNG = "toolstrip/img/verify.png";
	public static final String TOOLSTRIP_IMG_RESET_PNG = "toolstrip/img/reset.png";
	public static final String TOOLSTRIP_IMG_SEARCH_PNG = "toolstrip/img/search.png";
	public static final String TOOLSTRIP_IMG_HIDE_FILTER_PNG = "toolstrip/img/hide-filter.png";
	public static final String TOOLSTRIP_IMG_FILTER_PNG = "toolstrip/img/filter.png";
	public static final String TOOLSTRIP_IMG_CLEAR_PNG = "toolstrip/img/clear.png";
	public static final String TOOLSTRIP_IMG_MATCH_PNG = "toolstrip/img/verify.png";
	public static final String TOOLSTRIP_IMG_UNMATCH_PNG = "toolstrip/img/clear.png";

	private HorizontalLayout leftLayout;
	private Label titleLabel;
	private HorizontalLayout rightLayout;

	public EntityEditorToolStrip() {
		setStyleName("conx-entity-toolstrip");
		setHeight("40px");
		setWidth("100%");
		
		leftLayout = new HorizontalLayout();
		leftLayout.setHeight("28px");
		leftLayout.setStyleName("conx-entity-toolstrip-left");
		leftLayout.setSpacing(true);
		
		titleLabel = new Label();
		titleLabel.setStyleName("conx-entity-toolstrip-title");
		titleLabel.setWidth("150px");
		
		rightLayout = new HorizontalLayout();
		rightLayout.setHeight("28px");
		rightLayout.setStyleName("conx-entity-toolstrip-right");
		
		addComponent(leftLayout);
		addComponent(titleLabel);
		addComponent(rightLayout);
		
		setComponentAlignment(leftLayout, Alignment.MIDDLE_LEFT);
		setComponentAlignment(titleLabel, Alignment.MIDDLE_CENTER);
		setComponentAlignment(rightLayout, Alignment.MIDDLE_RIGHT);
	}
	
	public void setTitle(String title) {
		this.titleLabel.setValue(title);
	}
	
	public EntityEditorToolStripButton addToolStripButton(String iconUrl) {
		EntityEditorToolStripButton button = new EntityEditorToolStripButton(iconUrl);
		String description = provideButtonDescription(iconUrl);
		if (description != null) {
			button.setDescription(description);
		}
		leftLayout.addComponent(button);
		return button;
	}
	
	public void removeToolStripButton(EntityEditorToolStripButton button) {
		leftLayout.removeComponent(button);
	}
	
	public void addContextComponent(Component component) {
		rightLayout.addComponent(component);
		rightLayout.setComponentAlignment(component, Alignment.MIDDLE_RIGHT);
	}
	
	public void removeContextComponent(Component component) {
		rightLayout.removeComponent(component);
	}
	
	public String provideButtonDescription(String iconUrl) {
		if (TOOLSTRIP_IMG_DELETE_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_DELETE;
		} else if (TOOLSTRIP_IMG_EDIT_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_EDIT;
		} else if (TOOLSTRIP_IMG_CREATE_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_CREATE;
		} else if (TOOLSTRIP_IMG_SAVE_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_SAVE;
		} else if (TOOLSTRIP_IMG_ATTACH_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_ATTACH;
		} else if (TOOLSTRIP_IMG_REPORT_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_REPORT;
		} else if (TOOLSTRIP_IMG_PRINT_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_PRINT;
		} else if (TOOLSTRIP_IMG_VERIFY_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_VERIFY;
		} else if (TOOLSTRIP_IMG_RESET_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_RESET;
		} else if (TOOLSTRIP_IMG_SEARCH_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_SEARCH;
		} else if (TOOLSTRIP_IMG_HIDE_FILTER_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_HIDE_FILTER;
		} else if (TOOLSTRIP_IMG_FILTER_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_FILTER;
		} else if (TOOLSTRIP_IMG_CLEAR_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_CLEAR;
		} else if (TOOLSTRIP_IMG_MATCH_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_MATCH;
		} else if (TOOLSTRIP_IMG_UNMATCH_PNG.equals(iconUrl)) {
			return TOOLSTRIP_CAPTION_UNMATCH;
		}
		
		return null;
	}
	
	public class EntityEditorToolStripButton extends Button {
		private static final long serialVersionUID = -6850572740737479916L;

		public EntityEditorToolStripButton(String iconUrl) {
			setIcon(new ThemeResource(iconUrl));
			setStyleName("conx-entity-toolstrip-button");
			setHeight("28px");
		}
	}
}
