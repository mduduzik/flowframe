package org.vaadin.mvp.eventbus.events;

import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.annotation.Event;
import org.vaadin.mvp.presenter.BasePresenter;


public interface FireEventBus extends EventBus {

  @Event(handlers = { BasePresenter.class })
  public void fireEvent(FiredEvent e);

}
