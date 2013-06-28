package org.phakama.vaadin.mvp.view.three;

import org.phakama.vaadin.mvp.event.IEvent;

import com.vaadin.ui.Component;

public class ThreeViewImpl implements ThreeView {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getComponent() {
		return null;
	}

	@Override
	public void onBind() {
	}

	@Override
	public void onUnbind() {
	}

	@Override
	public int dispatch(IEvent event) {
		return -1;
	}

}
