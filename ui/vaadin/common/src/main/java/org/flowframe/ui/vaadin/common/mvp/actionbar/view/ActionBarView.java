package org.flowframe.ui.vaadin.common.mvp.actionbar.view;

import java.util.HashSet;
import java.util.TreeSet;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;

public class ActionBarView extends HorizontalLayout implements IActionBarView {
	private static final long serialVersionUID = 7098484001831L;

	private static final String ACTION_BAR_STYLE_NAME = "ff-action-bar";

	private static final String ACTION_BAR_WIDTH = "100%";
	private static final String ACTION_BAR_HEIGHT = "35px";
	private static final String ACTION_BAR_LOGO_REGION_WIDTH = "200px";
	private static final String ACTION_BAR_NAV_REGION_WIDTH = "100%";
	private static final String ACTION_BAR_SESSION_REGION_WIDTH = "200px";

	private HorizontalLayout logoRegionLayout;
	private HorizontalLayout navRegionLayout;
	private HorizontalLayout sessionRegionLayout;
	
	private TreeSet<Integer> ordinalSet;
	private HashSet<IActionBarNavigationListener> navListenerSet;

	public ActionBarView() {
		this.setWidth(ACTION_BAR_WIDTH);
		this.setHeight(ACTION_BAR_HEIGHT);
		this.setStyleName(ACTION_BAR_STYLE_NAME);

		this.logoRegionLayout = new HorizontalLayout();
		this.navRegionLayout = new HorizontalLayout();
		this.sessionRegionLayout = new HorizontalLayout();
		
		this.ordinalSet = new TreeSet<Integer>();
		this.navListenerSet = new HashSet<IActionBarNavigationListener>();
		
		this.logoRegionLayout.setWidth(ACTION_BAR_LOGO_REGION_WIDTH);
		this.logoRegionLayout.setHeight(ACTION_BAR_HEIGHT);
		this.navRegionLayout.setWidth(ACTION_BAR_NAV_REGION_WIDTH);
		this.navRegionLayout.setHeight(ACTION_BAR_HEIGHT);
		this.sessionRegionLayout.setWidth(ACTION_BAR_SESSION_REGION_WIDTH);
		this.sessionRegionLayout.setHeight(ACTION_BAR_HEIGHT);

		this.addComponent(this.logoRegionLayout);
		this.addComponent(this.navRegionLayout);
		this.addComponent(this.sessionRegionLayout);

		this.setExpandRatio(this.logoRegionLayout, 0.0f);
		this.setExpandRatio(this.navRegionLayout, 1.0f);
		this.setExpandRatio(this.sessionRegionLayout, 0.0f);
	}
	
	@Override
	public void addListener(IActionBarNavigationListener listener) {
		this.navListenerSet.add(listener);
	}
	
	private void fireNavEvent(String code) {
		for (IActionBarNavigationListener listener : this.navListenerSet) {
			listener.onNavigate(code);
		}
	}

	@Override
	public void addNavItem(int ordinal, String code, String iconUrl, String caption) {
		assert (code != null) : "The code must be non-null.";
		
		this.ordinalSet.add(ordinal);
		int index = this.ordinalSet.headSet(ordinal).size();
		this.navRegionLayout.addComponent(new ActionBarNavItemButton(code, iconUrl, caption), index);
	}
	
	private class ActionBarNavItemButton extends Button {
		private static final long serialVersionUID = 7098484001832L;

		private static final String ACTION_BAR_NAV_ITEM_BUTTON_STYLE_NAME = "ff-action-bar-nav-item-button";

		private String code;

		public ActionBarNavItemButton(String code, String iconUrl, String caption) {
			setStyleName(ACTION_BAR_NAV_ITEM_BUTTON_STYLE_NAME);
			this.code = code;
			assert (iconUrl != null || caption != null) : "Nav Item must have a caption, an icon url or both.";
			if (iconUrl != null) {
				this.setIcon(new ThemeResource(iconUrl));
			}
			if (caption != null) {
				this.setCaption(caption);
			}
			this.addListener(new ClickListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					fireNavEvent(ActionBarNavItemButton.this.code);
				}
			});
			this.setHeight(ACTION_BAR_HEIGHT);
		}
	}
	
	public interface IActionBarNavigationListener {
		public void onNavigate(String code);
	}
}
