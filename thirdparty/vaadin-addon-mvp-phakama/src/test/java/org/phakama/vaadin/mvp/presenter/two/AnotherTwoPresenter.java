package org.phakama.vaadin.mvp.presenter.two;

import org.phakama.vaadin.mvp.annotation.event.EventListener;
import org.phakama.vaadin.mvp.event.two.AnotherTwoTestEvent;
import org.phakama.vaadin.mvp.event.two.FinallyAnotherTwoTestEvent;
import org.phakama.vaadin.mvp.event.two.TwoTestEvent;
import org.phakama.vaadin.mvp.event.two.YetAnotherTwoTestEvent;
import org.phakama.vaadin.mvp.presenter.impl.Presenter;
import org.phakama.vaadin.mvp.util.EventTrackerUtil;
import org.phakama.vaadin.mvp.view.two.TwoView;

public class AnotherTwoPresenter extends Presenter<TwoView> {
	private static final long serialVersionUID = 1457859033325707522L;
	
	private EventTrackerUtil tracker;

	public void setEventTracker(EventTrackerUtil tracker) {
		this.tracker = tracker;
	}
	
	@EventListener(event = TwoTestEvent.class)
	public void onTwoTest() {
		if (this.tracker != null)if (this.tracker != null)if (this.tracker != null)
		this.tracker.twoReceived = true;
	}
	
	@EventListener(event = AnotherTwoTestEvent.class)
	public void onAnotherTwoTest(AnotherTwoTestEvent e) {
		if (this.tracker != null)
		this.tracker.anotherTwoReceived = true;
	}
	
	@EventListener(event = YetAnotherTwoTestEvent.class)
	public void onYetAnotherTwoTest() {
		if (this.tracker != null)
		this.tracker.yetAnotherTwoReceived = true;
	}
	
	@EventListener(event = FinallyAnotherTwoTestEvent.class)
	public void onFinallyAnotherTwoTest() {
		if (this.tracker != null)
		this.tracker.finallyAnotherTwoReceived = true;
	}
}
