package org.phakama.vaadin.mvp.view.impl;

import org.phakama.vaadin.mvp.view.IFloatingView;

import com.vaadin.ui.Component;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

public class FloatingView extends View implements IFloatingView {
	private static final long serialVersionUID = -1236153430779028557L;
	
	public static final String DEFAULT_WIDTH = "300px";
	public static final String DEFAULT_HEIGHT = "200px";
	
	private Window window = new Window();
	private boolean visible = false;
	
	public FloatingView() {
		super();
		this.window.setContent(this);
		this.window.setHeight(DEFAULT_HEIGHT);
		this.window.setWidth(DEFAULT_WIDTH);
		
		setSizeFull();
	}
	
	@Override
	public void detach() {
		this.window.detach();
		super.detach();
	}
	
	@Override
	public void setCaption(String caption) {
		this.window.setCaption(caption);
	}

	public void setModal(boolean modal) {
		this.window.setModal(true);
	}
	
	@Override
	public void setWidth(float width, Unit unit) {
		this.window.setWidth(width, unit);
	}
	
	@Override
	public void setWidth(String width) {
		this.window.setWidth(width);
	}
	
	@Override
	public void setHeight(float height, Unit unit) {
		this.window.setHeight(height, unit);
	}
	
	@Override
	public void setHeight(String height) {
		this.window.setHeight(height);
	}
	
	@Override
	public void setSizeFull() {
		this.window.setSizeFull();
	}
	
	@Override
	public void setStyleName(String style) {
		this.window.setStyleName(style);
	}
	
	@Override
	public void addStyleName(String style) {
		this.window.addStyleName(style);
	}
	
	@Override
	public Component getComponent() {
		throw new UnsupportedOperationException("The getComponent() method is not supported by FloatingView. Please use FloatingView.show() and FloatingView.hide()"); 
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			show();
		} else {
			hide();
		}
	}
	
	@Override
	public boolean isVisible() {
		return this.visible;
	}

	@Override
	public void show() {
		if (!this.visible) {
			UI.getCurrent().addWindow(this.window);
			this.visible = true;
		}
	}

	@Override
	public void hide() {
		if (this.visible) {
			try {
				UI.getCurrent().removeWindow(this.window);
				this.visible = false;
			} catch (Exception e) {
				// Swallow this exception, its not entirely relevant
			}
		}
	}
	
}
