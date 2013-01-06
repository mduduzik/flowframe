package org.flowframe.ui.vaadin.editors.entity.vaadin.ext.pager;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class EntityEditorPager extends HorizontalLayout {
	private static final long serialVersionUID = 8900784706665666615L;
	
	private static final String PAGER_FIRST_PNG = "pager/img/conx-header-first.png";
	private static final String PAGER_PREVIOUS_PNG = "pager/img/conx-header-previous.png";
	private static final String PAGER_NEXT_PNG = "pager/img/conx-header-next.png";
	private static final String PAGER_LAST_PNG = "pager/img/conx-header-last.png";
	
	private Label caption;
	private Button first, previous, next, last;

	public EntityEditorPager() {
		this.caption = new Label();
		this.first = new Button();
		this.previous = new Button();
		this.next = new Button();
		this.last = new Button();
		
		initialize();
	}
	
	private void initialize() {
		this.first.setStyleName("conx-entity-pager-button");
		this.first.setIcon(new ThemeResource(PAGER_FIRST_PNG));
		this.first.setHeight("14px");
		
		this.previous.setStyleName("conx-entity-pager-button");
		this.previous.setIcon(new ThemeResource(PAGER_PREVIOUS_PNG));
		this.previous.setHeight("14px");
		
		this.next.setStyleName("conx-entity-pager-button");
		this.next.setIcon(new ThemeResource(PAGER_NEXT_PNG));
		this.previous.setHeight("14px");
		
		this.last.setStyleName("conx-entity-pager-button");
		this.last.setIcon(new ThemeResource(PAGER_LAST_PNG));
		this.previous.setHeight("14px");
		
		setStyleName("conx-entity-pager");
		setSpacing(true);
		setHeight("14px");
		addComponent(this.first);
		addComponent(this.previous);
		addComponent(this.caption);
		addComponent(this.next);
		addComponent(this.last);
	}
	
	public void addFirstListener(ClickListener listener) {
		this.first.addListener(listener);
	}
	
	public void setFirstEnabled(boolean enabled) {
		this.first.setEnabled(enabled);
	}
	
	public void addPreviousListener(ClickListener listener) {
		this.previous.addListener(listener);
	}
	
	public void setPreviousEnabled(boolean enabled) {
		this.previous.setEnabled(enabled);
	}
	
	public void addNextListener(ClickListener listener) {
		this.next.addListener(listener);
	}
	
	public void setNextEnabled(boolean enabled) {
		this.next.setEnabled(enabled);
	}
	
	public void addLastListener(ClickListener listener) {
		this.last.addListener(listener);
	}
	
	public void setLastEnabled(boolean enabled) {
		this.last.setEnabled(enabled);
	}
	
	@Override
	public void setCaption(String string) {
		this.caption.setValue(string);
	}
}
