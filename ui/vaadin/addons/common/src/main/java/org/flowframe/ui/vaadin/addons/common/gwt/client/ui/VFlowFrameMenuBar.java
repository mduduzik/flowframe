package org.flowframe.ui.vaadin.addons.common.gwt.client.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.BrowserInfo;
import com.vaadin.terminal.gwt.client.ContainerResizedListener;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.TooltipInfo;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.Util;
import com.vaadin.terminal.gwt.client.VTooltip;
import com.vaadin.terminal.gwt.client.ui.Icon;
import com.vaadin.terminal.gwt.client.ui.SimpleFocusablePanel;
import com.vaadin.terminal.gwt.client.ui.SubPartAware;
import com.vaadin.terminal.gwt.client.ui.VLazyExecutor;

public class VFlowFrameMenuBar extends SimpleFocusablePanel implements Paintable,
        CloseHandler<PopupPanel>, ContainerResizedListener, KeyPressHandler,
        KeyDownHandler, FocusHandler, SubPartAware {

    // The hierarchy of VMenuBar is a bit weird as VMenuBar is the Paintable,
    // used for the root menu but also used for the sub menus.

    /** Set the CSS class name to allow styling. */
    public static final String CLASSNAME = "v-menubar";

    /** For server connections **/
    protected String uidlId;
    protected ApplicationConnection client;

    protected final VFlowFrameMenuBar hostReference = this;
    protected String submenuIcon = null;
    protected CustomMenuItem moreItem = null;

    // Only used by the root menu bar
    protected VFlowFrameMenuBar collapsedRootItems;

    // Construct an empty command to be used when the item has no command
    // associated
    protected static final Command emptyCommand = null;

    public static final String OPEN_ROOT_MENU_ON_HOWER = "ormoh";

    public static final String ATTRIBUTE_CHECKED = "checked";

    public static final String HTML_CONTENT_ALLOWED = "usehtml";

    /** Widget fields **/
    protected boolean subMenu;
    protected ArrayList<CustomMenuItem> items;
    protected Element containerElement;
    protected VFlowFrameMenuOverlay popup;
    protected VFlowFrameMenuBar visibleChildMenu;
    protected boolean menuVisible = false;
    protected VFlowFrameMenuBar parentMenu;
    protected CustomMenuItem selected;

    private boolean enabled = true;

    private String width = "notinited";

    private VLazyExecutor iconLoadedExecutioner = new VLazyExecutor(100,
            new ScheduledCommand() {

                public void execute() {
                    iLayout(true);
                }
            });

    private boolean openRootOnHover;

    private boolean htmlContentAllowed;

    public VFlowFrameMenuBar() {
        // Create an empty horizontal menubar
        this(false, null);

        // Navigation is only handled by the root bar
        addFocusHandler(this);

        /*
         * Firefox auto-repeat works correctly only if we use a key press
         * handler, other browsers handle it correctly when using a key down
         * handler
         */
        if (BrowserInfo.get().isGecko()) {
            addKeyPressHandler(this);
        } else {
            addKeyDownHandler(this);
        }
    }

    public VFlowFrameMenuBar(boolean subMenu, VFlowFrameMenuBar parentMenu) {

        items = new ArrayList<CustomMenuItem>();
        popup = null;
        visibleChildMenu = null;

        containerElement = getElement();

        if (!subMenu) {
            setStyleName(CLASSNAME);
        } else {
            setStyleName(CLASSNAME + "-submenu");
            this.parentMenu = parentMenu;
        }
        this.subMenu = subMenu;

        sinkEvents(Event.ONCLICK | Event.ONMOUSEOVER | Event.ONMOUSEOUT
                | Event.ONLOAD);

        sinkEvents(VTooltip.TOOLTIP_EVENTS);
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        if (!subMenu) {
            setSelected(null);
            hideChildren();
            menuVisible = false;
        }
    }

    @Override
    public void setWidth(String width) {
        if (Util.equals(this.width, width)) {
            return;
        }

        this.width = width;
        if (BrowserInfo.get().isIE6() && width.endsWith("px")) {
            // IE6 sometimes measures wrong using
            // Util.setWidthExcludingPaddingAndBorder so this is extracted to a
            // special case that uses another method. Really should fix the
            // Util.setWidthExcludingPaddingAndBorder method but that will
            // probably break additional cases
            int requestedPixelWidth = Integer.parseInt(width.substring(0,
                    width.length() - 2));
            int paddingBorder = Util.measureHorizontalPaddingAndBorder(
                    getElement(), 0);
            int w = requestedPixelWidth - paddingBorder;
            if (w < 0) {
                w = 0;
            }
            getElement().getStyle().setWidth(w, Unit.PX);
        } else {
            Util.setWidthExcludingPaddingAndBorder(this, width, 0);
        }
        if (!subMenu) {
            // Only needed for root level menu
            hideChildren();
            setSelected(null);
            menuVisible = false;
        }
    }

    /**
     * This method must be implemented to update the client-side component from
     * UIDL data received from server.
     * 
     * This method is called when the page is loaded for the first time, and
     * every time UI changes in the component are received from the server.
     */
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        // This call should be made first. Ensure correct implementation,
        // and let the containing layout manage caption, etc.
        if (client.updateComponent(this, uidl, true)) {
            return;
        }

        htmlContentAllowed = uidl.hasAttribute(HTML_CONTENT_ALLOWED);

        openRootOnHover = uidl.getBooleanAttribute(OPEN_ROOT_MENU_ON_HOWER);

        enabled = !uidl.getBooleanAttribute("disabled");

        // For future connections
        this.client = client;
        uidlId = uidl.getId();

        // Empty the menu every time it receives new information
        if (!getItems().isEmpty()) {
            clearItems();
        }

        UIDL options = uidl.getChildUIDL(0);

        // FIXME remove in version 7
        if (options.hasAttribute("submenuIcon")) {
            submenuIcon = client.translateVaadinUri(uidl.getChildUIDL(0)
                    .getStringAttribute("submenuIcon"));
        } else {
            submenuIcon = null;
        }

        if (uidl.hasAttribute("width")) {
            UIDL moreItemUIDL = options.getChildUIDL(0);
            StringBuffer itemHTML = new StringBuffer();

            if (moreItemUIDL.hasAttribute("icon")) {
                itemHTML.append("<img src=\""
                        + Util.escapeAttribute(client
                                .translateVaadinUri(moreItemUIDL
                                        .getStringAttribute("icon")))
                        + "\" class=\"" + Icon.CLASSNAME + "\" alt=\"\" />");
            }

            String moreItemText = moreItemUIDL.getStringAttribute("text");
            if ("".equals(moreItemText)) {
                moreItemText = "&#x25BA;";
            }
            itemHTML.append(moreItemText);

            moreItem = GWT.create(CustomMenuItem.class);
            moreItem.setHTML(itemHTML.toString());
            moreItem.setCommand(emptyCommand);

            collapsedRootItems = new VFlowFrameMenuBar(true,
                    (VFlowFrameMenuBar) client.getPaintable(uidlId));
            moreItem.setSubMenu(collapsedRootItems);
            moreItem.addStyleName(CLASSNAME + "-more-menuitem");
        }

        UIDL uidlItems = uidl.getChildUIDL(1);
        Iterator<Object> itr = uidlItems.getChildIterator();
        Stack<Iterator<Object>> iteratorStack = new Stack<Iterator<Object>>();
        Stack<VFlowFrameMenuBar> menuStack = new Stack<VFlowFrameMenuBar>();
        VFlowFrameMenuBar currentMenu = this;

        while (itr.hasNext()) {
            UIDL item = (UIDL) itr.next();
            CustomMenuItem currentItem = null;

            final int itemId = item.getIntAttribute("id");

            boolean itemHasCommand = item.hasAttribute("command");
            boolean itemIsCheckable = item.hasAttribute(ATTRIBUTE_CHECKED);

            String itemHTML = buildItemHTML(item);

            Command cmd = null;
            if (!item.hasAttribute("separator")) {
                if (itemHasCommand || itemIsCheckable) {
                    // Construct a command that fires onMenuClick(int) with the
                    // item's id-number
                    cmd = new Command() {
                        public void execute() {
                            hostReference.onMenuClick(itemId);
                        }
                    };
                }
            }

            currentItem = currentMenu.addItem(itemHTML.toString(), cmd);
            currentItem.updateFromUIDL(item, client);

            if (item.getChildCount() > 0) {
                menuStack.push(currentMenu);
                iteratorStack.push(itr);
                itr = item.getChildIterator();
                currentMenu = new VFlowFrameMenuBar(true, currentMenu);
                if (uidl.hasAttribute("style")) {
                    for (String style : uidl.getStringAttribute("style").split(
                            " ")) {
                        currentMenu.addStyleDependentName(style);
                    }
                }
                currentItem.setSubMenu(currentMenu);
            }

            while (!itr.hasNext() && !iteratorStack.empty()) {
                boolean hasCheckableItem = false;
                for (CustomMenuItem menuItem : currentMenu.getItems()) {
                    hasCheckableItem = hasCheckableItem
                            || menuItem.isCheckable();
                }
                if (hasCheckableItem) {
                    currentMenu.addStyleDependentName("check-column");
                } else {
                    currentMenu.removeStyleDependentName("check-column");
                }

                itr = iteratorStack.pop();
                currentMenu = menuStack.pop();
            }
        }// while

        iLayout(false);

    }// updateFromUIDL

    /**
     * Build the HTML content for a menu item.
     * 
     * @param item
     * @return
     */
    protected String buildItemHTML(UIDL item) {
        // Construct html from the text and the optional icon
        StringBuffer itemHTML = new StringBuffer();
        if (item.hasAttribute("separator")) {
            itemHTML.append("<span>---</span>");
        } else {
            // Add submenu indicator
            if (item.getChildCount() > 0) {
                // FIXME For compatibility reasons: remove in version 7
                String bgStyle = "";
                if (submenuIcon != null) {
                    bgStyle = " style=\"background-image: url("
                            + Util.escapeAttribute(submenuIcon)
                            + "); text-indent: -999px; width: 1em;\"";
                }
                itemHTML.append("<span class=\"" + CLASSNAME
                        + "-submenu-indicator\"" + bgStyle + ">&#x25BA;</span>");
            }

            itemHTML.append("<span class=\"" + CLASSNAME
                    + "-menuitem-caption\">");
            if (item.hasAttribute("icon")) {
                itemHTML.append("<img src=\""
                        + Util.escapeAttribute(client.translateVaadinUri(item
                                .getStringAttribute("icon"))) + "\" class=\""
                        + Icon.CLASSNAME + "\" alt=\"\" />");
            }
            String itemText = item.getStringAttribute("text");
            if (!htmlContentAllowed) {
                itemText = Util.escapeHTML(itemText);
            }
            itemHTML.append(itemText);
            itemHTML.append("</span>");
        }
        return itemHTML.toString();
    }

    /**
     * This is called by the items in the menu and it communicates the
     * information to the server
     * 
     * @param clickedItemId
     *            id of the item that was clicked
     */
    public void onMenuClick(int clickedItemId) {
        // Updating the state to the server can not be done before
        // the server connection is known, i.e., before updateFromUIDL()
        // has been called.
        if (uidlId != null && client != null) {
            // Communicate the user interaction parameters to server. This call
            // will initiate an AJAX request to the server.
            client.updateVariable(uidlId, "clickedId", clickedItemId, true);
        }
    }

    /** Widget methods **/

    /**
     * Returns a list of items in this menu
     */
    public List<CustomMenuItem> getItems() {
        return items;
    }

    /**
     * Remove all the items in this menu
     */
    public void clearItems() {
        Element e = getContainerElement();
        while (DOM.getChildCount(e) > 0) {
            DOM.removeChild(e, DOM.getChild(e, 0));
        }
        items.clear();
    }

    /**
     * Returns the containing element of the menu
     * 
     * @return
     */
    @Override
    public Element getContainerElement() {
        return containerElement;
    }

    /**
     * Add a new item to this menu
     * 
     * @param html
     *            items text
     * @param cmd
     *            items command
     * @return the item created
     */
    public CustomMenuItem addItem(String html, Command cmd) {
        CustomMenuItem item = GWT.create(CustomMenuItem.class);
        item.setHTML(html);
        item.setCommand(cmd);

        addItem(item);
        return item;
    }

    /**
     * Add a new item to this menu
     * 
     * @param item
     */
    public void addItem(CustomMenuItem item) {
        if (items.contains(item)) {
            return;
        }
        DOM.appendChild(getContainerElement(), item.getElement());
        item.setParentMenu(this);
        item.setSelected(false);
        items.add(item);
    }

    public void addItem(CustomMenuItem item, int index) {
        if (items.contains(item)) {
            return;
        }
        DOM.insertChild(getContainerElement(), item.getElement(), index);
        item.setParentMenu(this);
        item.setSelected(false);
        items.add(index, item);
    }

    /**
     * Remove the given item from this menu
     * 
     * @param item
     */
    public void removeItem(CustomMenuItem item) {
        if (items.contains(item)) {
            int index = items.indexOf(item);

            DOM.removeChild(getContainerElement(),
                    DOM.getChild(getContainerElement(), index));
            items.remove(index);
        }
    }

    /*
     * @see
     * com.google.gwt.user.client.ui.Widget#onBrowserEvent(com.google.gwt.user
     * .client.Event)
     */
    @Override
    public void onBrowserEvent(Event e) {
        super.onBrowserEvent(e);

        // Handle onload events (icon loaded, size changes)
        if (DOM.eventGetType(e) == Event.ONLOAD) {
            if (BrowserInfo.get().isIE6()) {
                Util.doIE6PngFix((Element) Element.as(e.getEventTarget()));
            }
            VFlowFrameMenuBar parent = getParentMenu();
            if (parent != null) {
                // The onload event for an image in a popup should be sent to
                // the parent, which owns the popup
                parent.iconLoaded();
            } else {
                // Onload events for images in the root menu are handled by the
                // root menu itself
                iconLoaded();
            }
            return;
        }

        Element targetElement = DOM.eventGetTarget(e);
        CustomMenuItem targetItem = null;
        for (int i = 0; i < items.size(); i++) {
            CustomMenuItem item = items.get(i);
            if (DOM.isOrHasChild(item.getElement(), targetElement)) {
                targetItem = item;
            }
        }

        // Handle tooltips
        if (targetItem == null && client != null) {
            // Handle root menubar tooltips
            client.handleTooltipEvent(e, this);
        } else if (targetItem != null) {
            // Handle item tooltips
            targetItem.onBrowserEvent(e);
        }

        if (targetItem != null) {
            switch (DOM.eventGetType(e)) {

            case Event.ONCLICK:
                if (isEnabled() && targetItem.isEnabled()) {
                    itemClick(targetItem);
                }
                if (subMenu) {
                    // Prevent moving keyboard focus to child menus
                    VFlowFrameMenuBar parent = parentMenu;
                    while (parent.getParentMenu() != null) {
                        parent = parent.getParentMenu();
                    }
                    parent.setFocus(true);
                }

                break;

            case Event.ONMOUSEOVER:
                LazyCloser.cancelClosing();

                if (isEnabled() && targetItem.isEnabled()) {
                    itemOver(targetItem);
                }
                break;

            case Event.ONMOUSEOUT:
                itemOut(targetItem);
                LazyCloser.schedule();
                break;
            }
        } else if (subMenu && DOM.eventGetType(e) == Event.ONCLICK && subMenu) {
            // Prevent moving keyboard focus to child menus
            VFlowFrameMenuBar parent = parentMenu;
            while (parent.getParentMenu() != null) {
                parent = parent.getParentMenu();
            }
            parent.setFocus(true);
        }
    }

    private boolean isEnabled() {
        return enabled;
    }

    private void iconLoaded() {
        iconLoadedExecutioner.trigger();
    }

    /**
     * When an item is clicked
     * 
     * @param item
     */
    public void itemClick(CustomMenuItem item) {
        if (item.getCommand() != null) {
            setSelected(null);

            if (visibleChildMenu != null) {
                visibleChildMenu.hideChildren();
            }

            hideParents(true);
            menuVisible = false;
            Scheduler.get().scheduleDeferred(item.getCommand());

        } else {
            if (item.getSubMenu() != null
                    && item.getSubMenu() != visibleChildMenu) {
                setSelected(item);
                showChildMenu(item);
                menuVisible = true;
            } else if (!subMenu) {
                setSelected(null);
                hideChildren();
                menuVisible = false;
            }
        }
    }

    /**
     * When the user hovers the mouse over the item
     * 
     * @param item
     */
    public void itemOver(CustomMenuItem item) {
        if ((openRootOnHover || subMenu || menuVisible) && !item.isSeparator()) {
            setSelected(item);
            if (!subMenu && openRootOnHover && !menuVisible) {
                menuVisible = true; // start opening menus
                LazyCloser.prepare(this);
            }
        }

        if (menuVisible && visibleChildMenu != item.getSubMenu()
                && popup != null) {
            popup.hide();
        }

        if (menuVisible && item.getSubMenu() != null
                && visibleChildMenu != item.getSubMenu()) {
            showChildMenu(item);
        }
    }

    /**
     * When the mouse is moved away from an item
     * 
     * @param item
     */
    public void itemOut(CustomMenuItem item) {
        if (visibleChildMenu != item.getSubMenu()) {
            hideChildMenu(item);
            setSelected(null);
        } else if (visibleChildMenu == null) {
            setSelected(null);
        }
    }

    /**
     * Used to autoclose submenus when they the menu is in a mode which opens
     * root menus on mouse hover.
     */
    private static class LazyCloser extends Timer {
        static LazyCloser INSTANCE;
        private VFlowFrameMenuBar activeRoot;

        @Override
        public void run() {
            activeRoot.hideChildren();
            activeRoot.setSelected(null);
            activeRoot.menuVisible = false;
            activeRoot = null;
        }

        public static void cancelClosing() {
            if (INSTANCE != null) {
                INSTANCE.cancel();
            }
        }

        public static void prepare(VFlowFrameMenuBar vMenuBar) {
            if (INSTANCE == null) {
                INSTANCE = new LazyCloser();
            }
            if (INSTANCE.activeRoot == vMenuBar) {
                INSTANCE.cancel();
            } else if (INSTANCE.activeRoot != null) {
                INSTANCE.cancel();
                INSTANCE.run();
            }
            INSTANCE.activeRoot = vMenuBar;
        }

        public static void schedule() {
            if (INSTANCE != null && INSTANCE.activeRoot != null) {
                INSTANCE.schedule(750);
            }
        }

    }

    /**
     * Shows the child menu of an item. The caller must ensure that the item has
     * a submenu.
     * 
     * @param item
     */
    public void showChildMenu(CustomMenuItem item) {

        int left = 0;
        int top = 0;
        if (subMenu) {
            left = item.getParentMenu().getAbsoluteLeft()
                    + item.getParentMenu().getOffsetWidth();
            top = item.getAbsoluteTop();
        } else {
            left = item.getAbsoluteLeft();
            top = item.getParentMenu().getAbsoluteTop()
                    + item.getParentMenu().getOffsetHeight();
        }
        showChildMenuAt(item, top, left);
    }

    protected void showChildMenuAt(CustomMenuItem item, int top, int left) {
        final int shadowSpace = 10;

        popup = new VFlowFrameMenuOverlay(true, false, true);
        popup.setStyleName(CLASSNAME + "-popup");
        popup.setWidget(item.getSubMenu());
        popup.addCloseHandler(this);
        popup.addAutoHidePartner(item.getElement());

        // at 0,0 because otherwise IE7 add extra scrollbars (#5547)
        popup.setPopupPosition(0, 0);

        item.getSubMenu().onShow();
        visibleChildMenu = item.getSubMenu();
        item.getSubMenu().setParentMenu(this);

        popup.show();

        if (left + popup.getOffsetWidth() >= RootPanel.getBodyElement()
                .getOffsetWidth() - shadowSpace) {
            if (subMenu) {
                left = item.getParentMenu().getAbsoluteLeft()
                        - popup.getOffsetWidth() - shadowSpace;
            } else {
                left = RootPanel.getBodyElement().getOffsetWidth()
                        - popup.getOffsetWidth() - shadowSpace;
            }
            // Accommodate space for shadow
            if (left < shadowSpace) {
                left = shadowSpace;
            }
        }

        top = adjustPopupHeight(top, shadowSpace);

        popup.setPopupPosition(left, top);

        // IE7 really tests one's patience sometimes
        // Part of a fix to correct #3850
        if (BrowserInfo.get().isIE7()) {
            popup.getElement().getStyle().setProperty("zoom", "");
            Scheduler.get().scheduleDeferred(new Command() {
                public void execute() {
                    if (popup == null) {
                        // The child menu can be hidden before this command is
                        // run.
                        return;
                    }

                    if (popup.getElement().getStyle().getProperty("width") == null
                            || popup.getElement().getStyle()
                                    .getProperty("width") == "") {
                        popup.setWidth(popup.getOffsetWidth() + "px");
                    }
                    popup.getElement().getStyle().setProperty("zoom", "1");
                }
            });
        }
    }

    private int adjustPopupHeight(int top, final int shadowSpace) {
        // Check that the popup will fit the screen
        int availableHeight = RootPanel.getBodyElement().getOffsetHeight()
                - top - shadowSpace;
        int missingHeight = popup.getOffsetHeight() - availableHeight;
        if (missingHeight > 0) {
            // First move the top of the popup to get more space
            // Don't move above top of screen, don't move more than needed
            int moveUpBy = Math.min(top - shadowSpace, missingHeight);

            // Update state
            top -= moveUpBy;
            missingHeight -= moveUpBy;
            availableHeight += moveUpBy;

            if (missingHeight > 0) {
                int contentWidth = visibleChildMenu.getOffsetWidth();

                // If there's still not enough room, limit height to fit and add
                // a scroll bar
                Style style = popup.getElement().getStyle();
                style.setHeight(availableHeight, Unit.PX);
                style.setOverflowY(Overflow.SCROLL);

                // Make room for the scroll bar
                if (BrowserInfo.get().isIE6()) {
                    // IE6 renders the sub menu arrow icons on the scroll bar
                    // unless we add some padding
                    style.setPaddingRight(Util.getNativeScrollbarSize(),
                            Unit.PX);
                } else {
                    // For other browsers, adjusting the width of the popup is
                    // enough
                    style.setWidth(
                            contentWidth + Util.getNativeScrollbarSize(),
                            Unit.PX);
                }
                popup.updateMenuOverlayShadowSizeAndPosition();
            }
        }
        return top;
    }

    /**
     * Hides the submenu of an item
     * 
     * @param item
     */
    public void hideChildMenu(CustomMenuItem item) {
        if (visibleChildMenu != null
                && !(visibleChildMenu == item.getSubMenu())) {
            popup.hide();
        }
    }

    /**
     * When the menu is shown.
     */
    public void onShow() {
        // remove possible previous selection
        if (selected != null) {
            selected.setSelected(false);
            selected = null;
        }
        menuVisible = true;
    }

    /**
     * Listener method, fired when this menu is closed
     */
    public void onClose(CloseEvent<PopupPanel> event) {
        hideChildren();
        if (event.isAutoClosed()) {
            hideParents(true);
            menuVisible = false;
        }
        visibleChildMenu = null;
        popup = null;
    }

    /**
     * Recursively hide all child menus
     */
    public void hideChildren() {
        if (visibleChildMenu != null) {
            visibleChildMenu.hideChildren();
            popup.hide();
        }
    }

    /**
     * Recursively hide all parent menus
     */
    public void hideParents(boolean autoClosed) {
        if (visibleChildMenu != null) {
            popup.hide();
            setSelected(null);
            menuVisible = !autoClosed;
        }

        if (getParentMenu() != null) {
            getParentMenu().hideParents(autoClosed);
        }
    }

    /**
     * Returns the parent menu of this menu, or null if this is the top-level
     * menu
     * 
     * @return
     */
    public VFlowFrameMenuBar getParentMenu() {
        return parentMenu;
    }

    /**
     * Set the parent menu of this menu
     * 
     * @param parent
     */
    public void setParentMenu(VFlowFrameMenuBar parent) {
        parentMenu = parent;
    }

    /**
     * Returns the currently selected item of this menu, or null if nothing is
     * selected
     * 
     * @return
     */
    public CustomMenuItem getSelected() {
        return selected;
    }

    /**
     * Set the currently selected item of this menu
     * 
     * @param item
     */
    public void setSelected(CustomMenuItem item) {
        // If we had something selected, unselect
        if (item != selected && selected != null) {
            selected.setSelected(false);
        }
        // If we have a valid selection, select it
        if (item != null) {
            item.setSelected(true);
        }

        selected = item;
    }

    /**
     * 
     * A class to hold information on menu items
     * 
     */
    protected static class CustomMenuItem extends Widget implements HasHTML {

        private ApplicationConnection client;

        protected String html = null;
        protected Command command = null;
        protected VFlowFrameMenuBar subMenu = null;
        protected VFlowFrameMenuBar parentMenu = null;
        protected boolean enabled = true;
        protected boolean isSeparator = false;
        protected boolean checkable = false;
        protected boolean checked = false;

        /**
         * Default menu item {@link Widget} constructor for GWT.create().
         * 
         * Use {@link #setHTML(String)} and {@link #setCommand(Command)} after
         * constructing a menu item.
         */
        public CustomMenuItem() {
            this("", null);
        }

        /**
         * Creates a menu item {@link Widget}.
         * 
         * @param html
         * @param cmd
         * @deprecated use the default constructor and {@link #setHTML(String)}
         *             and {@link #setCommand(Command)} instead
         */
        @Deprecated
        public CustomMenuItem(String html, Command cmd) {
            // We need spans to allow inline-block in IE
            setElement(DOM.createSpan());

            setHTML(html);
            setCommand(cmd);
            setSelected(false);
            setStyleName(CLASSNAME + "-menuitem");

            sinkEvents(VTooltip.TOOLTIP_EVENTS);
        }

        public void setSelected(boolean selected) {
            if (selected && isSelectable()) {
                addStyleDependentName("selected");
                // needed for IE6 to have a single style name to match for an
                // element
                if (checkable) {
                    if (checked) {
                        removeStyleDependentName("selected-unchecked");
                        addStyleDependentName("selected-checked");
                    } else {
                        removeStyleDependentName("selected-checked");
                        addStyleDependentName("selected-unchecked");
                    }
                }
            } else {
                removeStyleDependentName("selected");
                // needed for IE6 to have a single style name to match for an
                // element
                removeStyleDependentName("selected-checked");
                removeStyleDependentName("selected-unchecked");
            }
        }

        public void setChecked(boolean checked) {
            if (checkable && !isSeparator) {
                this.checked = checked;

                if (checked) {
                    addStyleDependentName("checked");
                    removeStyleDependentName("unchecked");
                } else {
                    addStyleDependentName("unchecked");
                    removeStyleDependentName("checked");
                }
            } else {
                this.checked = false;
            }
        }

        public boolean isChecked() {
            return checked;
        }

        public void setCheckable(boolean checkable) {
            if (checkable && !isSeparator) {
                this.checkable = true;
            } else {
                setChecked(false);
                this.checkable = false;
            }
        }

        public boolean isCheckable() {
            return checkable;
        }

        /*
         * setters and getters for the fields
         */

        public void setSubMenu(VFlowFrameMenuBar subMenu) {
            this.subMenu = subMenu;
        }

        public VFlowFrameMenuBar getSubMenu() {
            return subMenu;
        }

        public void setParentMenu(VFlowFrameMenuBar parentMenu) {
            this.parentMenu = parentMenu;
        }

        public VFlowFrameMenuBar getParentMenu() {
            return parentMenu;
        }

        public void setCommand(Command command) {
            this.command = command;
        }

        public Command getCommand() {
            return command;
        }

        public String getHTML() {
            return html;
        }

        public void setHTML(String html) {
            this.html = html;
            DOM.setInnerHTML(getElement(), html);

            // Sink the onload event for any icons. The onload
            // events are handled by the parent VMenuBar.
            Util.sinkOnloadForImages(getElement());
        }

        public String getText() {
            return html;
        }

        public void setText(String text) {
            setHTML(Util.escapeHTML(text));
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
            if (enabled) {
                removeStyleDependentName("disabled");
            } else {
                addStyleDependentName("disabled");
            }
        }

        public boolean isEnabled() {
            return enabled;
        }

        private void setSeparator(boolean separator) {
            isSeparator = separator;
            if (separator) {
                setStyleName(CLASSNAME + "-separator");
            } else {
                setStyleName(CLASSNAME + "-menuitem");
                setEnabled(enabled);
            }
        }

        public boolean isSeparator() {
            return isSeparator;
        }

        public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
            this.client = client;
            setSeparator(uidl.hasAttribute("separator"));
            setEnabled(!uidl.hasAttribute("disabled"));

            if (!isSeparator() && uidl.hasAttribute(ATTRIBUTE_CHECKED)) {
                // if the selected attribute is present (either true or false),
                // the item is selectable
                setCheckable(true);
                setChecked(uidl.getBooleanAttribute(ATTRIBUTE_CHECKED));
            } else {
                setCheckable(false);
            }

            if (uidl.hasAttribute("style")) {
                String itemStyle = uidl.getStringAttribute("style");
                addStyleDependentName(itemStyle);
            }

            if (uidl.hasAttribute("description")) {
                String description = uidl.getStringAttribute("description");
                TooltipInfo info = new TooltipInfo(description);

                VFlowFrameMenuBar root = findRootMenu();
                client.registerTooltip(root, this, info);
            }
        }

        @Override
        public void onBrowserEvent(Event event) {
            super.onBrowserEvent(event);
            if (client != null) {
                client.handleTooltipEvent(event, findRootMenu(), this);
            }
        }

        private VFlowFrameMenuBar findRootMenu() {
            VFlowFrameMenuBar menubar = getParentMenu();

            // Traverse up until root menu is found
            while (menubar.getParentMenu() != null) {
                menubar = menubar.getParentMenu();
            }

            return menubar;
        }

        /**
         * Checks if the item can be selected.
         * 
         * @return true if it is possible to select this item, false otherwise
         */
        public boolean isSelectable() {
            return !isSeparator() && isEnabled();
        }

    }

    /**
     * @author Jouni Koivuviita / Vaadin Ltd.
     */
    private int paddingWidth = -1;

    public void iLayout() {
        iLayout(false);
    }

    public void iLayout(boolean iconLoadEvent) {
        // Only collapse if there is more than one item in the root menu and the
        // menu has an explicit size
        if ((getItems().size() > 1 || (collapsedRootItems != null && collapsedRootItems
                .getItems().size() > 0))
                && getElement().getStyle().getProperty("width") != null
                && moreItem != null) {

            // Measure the width of the "more" item
            final boolean morePresent = getItems().contains(moreItem);
            addItem(moreItem);
            final int moreItemWidth = moreItem.getOffsetWidth();
            if (!morePresent) {
                removeItem(moreItem);
            }

            // Measure available space
            if (paddingWidth == -1) {
                int widthBefore = getElement().getClientWidth();
                getElement().getStyle().setProperty("padding", "0");
                paddingWidth = widthBefore - getElement().getClientWidth();
                getElement().getStyle().setProperty("padding", "");
            }
            String overflow = "";
            if (BrowserInfo.get().isIE6()) {
                // IE6 cannot measure available width correctly without
                // overflow:hidden
                overflow = getElement().getStyle().getProperty("overflow");
                getElement().getStyle().setProperty("overflow", "hidden");
            }

            int availableWidth = getElement().getClientWidth() - paddingWidth;

            if (BrowserInfo.get().isIE6()) {
                // IE6 cannot measure available width correctly without
                // overflow:hidden
                getElement().getStyle().setProperty("overflow", overflow);
            }

            // Used width includes the "more" item if present
            int usedWidth = getConsumedWidth();
            int diff = availableWidth - usedWidth;
            removeItem(moreItem);

            if (diff < 0) {
                // Too many items: collapse last items from root menu
                int widthNeeded = usedWidth - availableWidth;
                if (!morePresent) {
                    widthNeeded += moreItemWidth;
                }
                int widthReduced = 0;

                while (widthReduced < widthNeeded && getItems().size() > 0) {
                    // Move last root menu item to collapsed menu
                    CustomMenuItem collapse = getItems().get(
                            getItems().size() - 1);
                    widthReduced += collapse.getOffsetWidth();
                    removeItem(collapse);
                    collapsedRootItems.addItem(collapse, 0);
                }
            } else if (collapsedRootItems.getItems().size() > 0) {
                // Space available for items: expand first items from collapsed
                // menu
                int widthAvailable = diff + moreItemWidth;
                int widthGrowth = 0;

                while (widthAvailable > widthGrowth
                        && collapsedRootItems.getItems().size() > 0) {
                    // Move first item from collapsed menu to the root menu
                    CustomMenuItem expand = collapsedRootItems.getItems()
                            .get(0);
                    collapsedRootItems.removeItem(expand);
                    addItem(expand);
                    widthGrowth += expand.getOffsetWidth();
                    if (collapsedRootItems.getItems().size() > 0) {
                        widthAvailable -= moreItemWidth;
                    }
                    if (widthGrowth > widthAvailable) {
                        removeItem(expand);
                        collapsedRootItems.addItem(expand, 0);
                    } else {
                        widthAvailable = diff;
                    }

                    if (BrowserInfo.get().isIE6()) {
                        /*
                         * Handle transparency for IE6 here as we cannot
                         * implement it in CustomMenuItem.onAttach because
                         * onAttach is never called for CustomMenuItem due to an
                         * invalid component hierarchy (#6203)...
                         */
                        reloadImages(expand.getElement());
                    }
                }
            }
            if (collapsedRootItems.getItems().size() > 0) {
                addItem(moreItem);
            }
        }

        // If a popup is open we might need to adjust the shadow as well if an
        // icon shown in that popup was loaded
        if (popup != null) {
            // Forces a recalculation of the shadow size
            popup.show();
        }
        if (iconLoadEvent) {
            // Size have changed if the width is undefined
            Util.notifyParentOfSizeChange(this, false);
        }
    }

    private int getConsumedWidth() {
        int w = 0;
        for (CustomMenuItem item : getItems()) {
            if (!collapsedRootItems.getItems().contains(item)) {
                w += item.getOffsetWidth();
            }
        }
        return w;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gwt.event.dom.client.KeyPressHandler#onKeyPress(com.google
     * .gwt.event.dom.client.KeyPressEvent)
     */
    public void onKeyPress(KeyPressEvent event) {
        if (handleNavigation(event.getNativeEvent().getKeyCode(),
                event.isControlKeyDown() || event.isMetaKeyDown(),
                event.isShiftKeyDown())) {
            event.preventDefault();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gwt.event.dom.client.KeyDownHandler#onKeyDown(com.google.gwt
     * .event.dom.client.KeyDownEvent)
     */
    public void onKeyDown(KeyDownEvent event) {
        if (handleNavigation(event.getNativeEvent().getKeyCode(),
                event.isControlKeyDown() || event.isMetaKeyDown(),
                event.isShiftKeyDown())) {
            event.preventDefault();
        }
    }

    /**
     * Get the key that moves the selection upwards. By default it is the up
     * arrow key but by overriding this you can change the key to whatever you
     * want.
     * 
     * @return The keycode of the key
     */
    protected int getNavigationUpKey() {
        return KeyCodes.KEY_UP;
    }

    /**
     * Get the key that moves the selection downwards. By default it is the down
     * arrow key but by overriding this you can change the key to whatever you
     * want.
     * 
     * @return The keycode of the key
     */
    protected int getNavigationDownKey() {
        return KeyCodes.KEY_DOWN;
    }

    /**
     * Get the key that moves the selection left. By default it is the left
     * arrow key but by overriding this you can change the key to whatever you
     * want.
     * 
     * @return The keycode of the key
     */
    protected int getNavigationLeftKey() {
        return KeyCodes.KEY_LEFT;
    }

    /**
     * Get the key that moves the selection right. By default it is the right
     * arrow key but by overriding this you can change the key to whatever you
     * want.
     * 
     * @return The keycode of the key
     */
    protected int getNavigationRightKey() {
        return KeyCodes.KEY_RIGHT;
    }

    /**
     * Get the key that selects a menu item. By default it is the Enter key but
     * by overriding this you can change the key to whatever you want.
     * 
     * @return
     */
    protected int getNavigationSelectKey() {
        return KeyCodes.KEY_ENTER;
    }

    /**
     * Get the key that closes the menu. By default it is the escape key but by
     * overriding this yoy can change the key to whatever you want.
     * 
     * @return
     */
    protected int getCloseMenuKey() {
        return KeyCodes.KEY_ESCAPE;
    }

    /**
     * Handles the keyboard events handled by the MenuBar
     * 
     * @param event
     *            The keyboard event received
     * @return true iff the navigation event was handled
     */
    public boolean handleNavigation(int keycode, boolean ctrl, boolean shift) {

        // If tab or shift+tab close menus
        if (keycode == KeyCodes.KEY_TAB) {
            setSelected(null);
            hideChildren();
            menuVisible = false;
            return false;
        }

        if (ctrl || shift || !isEnabled()) {
            // Do not handle tab key, nor ctrl keys
            return false;
        }

        if (keycode == getNavigationLeftKey()) {
            if (getSelected() == null) {
                // If nothing is selected then select the last item
                setSelected(items.get(items.size() - 1));
                if (!getSelected().isSelectable()) {
                    handleNavigation(keycode, ctrl, shift);
                }
            } else if (visibleChildMenu == null && getParentMenu() == null) {
                // If this is the root menu then move to the left
                int idx = items.indexOf(getSelected());
                if (idx > 0) {
                    setSelected(items.get(idx - 1));
                } else {
                    setSelected(items.get(items.size() - 1));
                }

                if (!getSelected().isSelectable()) {
                    handleNavigation(keycode, ctrl, shift);
                }
            } else if (visibleChildMenu != null) {
                // Redirect all navigation to the submenu
                visibleChildMenu.handleNavigation(keycode, ctrl, shift);

            } else if (getParentMenu().getParentMenu() == null) {
                // Inside a sub menu, whose parent is a root menu item
                VFlowFrameMenuBar root = getParentMenu();

                root.getSelected().getSubMenu().setSelected(null);
                root.hideChildren();

                // Get the root menus items and select the previous one
                int idx = root.getItems().indexOf(root.getSelected());
                idx = idx > 0 ? idx : root.getItems().size();
                CustomMenuItem selected = root.getItems().get(--idx);

                while (selected.isSeparator() || !selected.isEnabled()) {
                    idx = idx > 0 ? idx : root.getItems().size();
                    selected = root.getItems().get(--idx);
                }

                root.setSelected(selected);
                openMenuAndFocusFirstIfPossible(selected);
            } else {
                getParentMenu().getSelected().getSubMenu().setSelected(null);
                getParentMenu().hideChildren();
            }

            return true;

        } else if (keycode == getNavigationRightKey()) {

            if (getSelected() == null) {
                // If nothing is selected then select the first item
                setSelected(items.get(0));
                if (!getSelected().isSelectable()) {
                    handleNavigation(keycode, ctrl, shift);
                }
            } else if (visibleChildMenu == null && getParentMenu() == null) {
                // If this is the root menu then move to the right
                int idx = items.indexOf(getSelected());

                if (idx < items.size() - 1) {
                    setSelected(items.get(idx + 1));
                } else {
                    setSelected(items.get(0));
                }

                if (!getSelected().isSelectable()) {
                    handleNavigation(keycode, ctrl, shift);
                }
            } else if (visibleChildMenu == null
                    && getSelected().getSubMenu() != null) {
                // If the item has a submenu then show it and move the selection
                // there
                showChildMenu(getSelected());
                menuVisible = true;
                visibleChildMenu.handleNavigation(keycode, ctrl, shift);
            } else if (visibleChildMenu == null) {

                // Get the root menu
                VFlowFrameMenuBar root = getParentMenu();
                while (root.getParentMenu() != null) {
                    root = root.getParentMenu();
                }

                // Hide the submenu
                root.hideChildren();

                // Get the root menus items and select the next one
                int idx = root.getItems().indexOf(root.getSelected());
                idx = idx < root.getItems().size() - 1 ? idx : -1;
                CustomMenuItem selected = root.getItems().get(++idx);

                while (selected.isSeparator() || !selected.isEnabled()) {
                    idx = idx < root.getItems().size() - 1 ? idx : -1;
                    selected = root.getItems().get(++idx);
                }

                root.setSelected(selected);
                openMenuAndFocusFirstIfPossible(selected);
            } else if (visibleChildMenu != null) {
                // Redirect all navigation to the submenu
                visibleChildMenu.handleNavigation(keycode, ctrl, shift);
            }

            return true;

        } else if (keycode == getNavigationUpKey()) {

            if (getSelected() == null) {
                // If nothing is selected then select the last item
                setSelected(items.get(items.size() - 1));
                if (!getSelected().isSelectable()) {
                    handleNavigation(keycode, ctrl, shift);
                }
            } else if (visibleChildMenu != null) {
                // Redirect all navigation to the submenu
                visibleChildMenu.handleNavigation(keycode, ctrl, shift);
            } else {
                // Select the previous item if possible or loop to the last item
                int idx = items.indexOf(getSelected());
                if (idx > 0) {
                    setSelected(items.get(idx - 1));
                } else {
                    setSelected(items.get(items.size() - 1));
                }

                if (!getSelected().isSelectable()) {
                    handleNavigation(keycode, ctrl, shift);
                }
            }

            return true;

        } else if (keycode == getNavigationDownKey()) {

            if (getSelected() == null) {
                // If nothing is selected then select the first item
                selectFirstItem();
            } else if (visibleChildMenu == null && getParentMenu() == null) {
                // If this is the root menu the show the child menu with arrow
                // down, if there is a child menu
                openMenuAndFocusFirstIfPossible(getSelected());
            } else if (visibleChildMenu != null) {
                // Redirect all navigation to the submenu
                visibleChildMenu.handleNavigation(keycode, ctrl, shift);
            } else {
                // Select the next item if possible or loop to the first item
                int idx = items.indexOf(getSelected());
                if (idx < items.size() - 1) {
                    setSelected(items.get(idx + 1));
                } else {
                    setSelected(items.get(0));
                }

                if (!getSelected().isSelectable()) {
                    handleNavigation(keycode, ctrl, shift);
                }
            }
            return true;

        } else if (keycode == getCloseMenuKey()) {
            setSelected(null);
            hideChildren();
            menuVisible = false;

        } else if (keycode == getNavigationSelectKey()) {
            if (getSelected() == null) {
                // If nothing is selected then select the first item
                selectFirstItem();
            } else if (visibleChildMenu != null) {
                // Redirect all navigation to the submenu
                visibleChildMenu.handleNavigation(keycode, ctrl, shift);
                menuVisible = false;
            } else if (visibleChildMenu == null
                    && getSelected().getSubMenu() != null) {
                // If the item has a sub menu then show it and move the
                // selection there
                openMenuAndFocusFirstIfPossible(getSelected());
            } else {
                Command command = getSelected().getCommand();
                if (command != null) {
                    command.execute();
                }

                setSelected(null);
                hideParents(true);
            }
        }

        return false;
    }

    private void selectFirstItem() {
        for (int i = 0; i < items.size(); i++) {
            CustomMenuItem item = items.get(i);
            if (item.isSelectable()) {
                setSelected(item);
                break;
            }
        }
    }

    private void openMenuAndFocusFirstIfPossible(CustomMenuItem menuItem) {
        VFlowFrameMenuBar subMenu = menuItem.getSubMenu();
        if (subMenu == null) {
            // No child menu? Nothing to do
            return;
        }

        VFlowFrameMenuBar parentMenu = menuItem.getParentMenu();
        parentMenu.showChildMenu(menuItem);

        menuVisible = true;
        // Select the first item in the newly open submenu
        subMenu.selectFirstItem();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.gwt.event.dom.client.FocusHandler#onFocus(com.google.gwt.event
     * .dom.client.FocusEvent)
     */
    public void onFocus(FocusEvent event) {

    }

    private final String SUBPART_PREFIX = "item";

    public Element getSubPartElement(String subPart) {
        int index = Integer
                .parseInt(subPart.substring(SUBPART_PREFIX.length()));
        CustomMenuItem item = getItems().get(index);

        return item.getElement();
    }

    public String getSubPartName(Element subElement) {
        if (!getElement().isOrHasChild(subElement)) {
            return null;
        }

        Element menuItemRoot = subElement;
        while (menuItemRoot != null && menuItemRoot.getParentElement() != null
                && menuItemRoot.getParentElement() != getElement()) {
            menuItemRoot = menuItemRoot.getParentElement().cast();
        }
        // "menuItemRoot" is now the root of the menu item

        final int itemCount = getItems().size();
        for (int i = 0; i < itemCount; i++) {
            if (getItems().get(i).getElement() == menuItemRoot) {
                String name = SUBPART_PREFIX + i;
                return name;
            }
        }
        return null;
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        if (BrowserInfo.get().isIE6()) {
            reloadImages(getElement());
        }
    }

    /**
     * Force a new onload event for all images. Used only for IE6 to deal with
     * PNG transparency.
     */
    private void reloadImages(Element root) {

        NodeList<com.google.gwt.dom.client.Element> imgElements = root
                .getElementsByTagName("img");
        for (int i = 0; i < imgElements.getLength(); i++) {
            Element e = (Element) imgElements.getItem(i);

            // IE6 fires onload events for the icons before the listener
            // is attached (or never). Updating the src force another
            // onload event
            String src = e.getAttribute("src");
            e.setAttribute("src", src);
        }
    }

}

