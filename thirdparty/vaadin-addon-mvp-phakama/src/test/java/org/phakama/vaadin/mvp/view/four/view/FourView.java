package org.phakama.vaadin.mvp.view.four.view;

import org.phakama.vaadin.mvp.event.IEvent;
import org.phakama.vaadin.mvp.view.four.IFourView;

import com.vaadin.ui.Component;

public class FourView implements IFourView {
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
