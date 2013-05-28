package org.flowframe.ui.vaadin.common.mvp.actionbar.view;

import org.flowframe.ui.vaadin.common.mvp.actionbar.view.ActionBarView.IActionBarNavigationListener;

public interface IActionBarView {
	public void addNavItem(int ordinal, String code, String iconUrl, String caption);
	public void addListener(IActionBarNavigationListener listener);
}
