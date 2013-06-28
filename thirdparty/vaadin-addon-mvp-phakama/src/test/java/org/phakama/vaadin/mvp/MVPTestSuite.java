package org.phakama.vaadin.mvp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.phakama.vaadin.mvp.event.EventTestSuite;
import org.phakama.vaadin.mvp.presenter.PresenterTestSuite;
import org.phakama.vaadin.mvp.view.ViewTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ ViewTestSuite.class, PresenterTestSuite.class, EventTestSuite.class })
public class MVPTestSuite {
}
