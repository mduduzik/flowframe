package org.flowframe.ui.component.domain.tabbed;

import org.flowframe.ui.component.domain.AbstractComponent;

public interface ITabComponent {
    /**
     * Returns the visible status for the tab. An invisible tab is not shown
     * in the tab bar and cannot be selected.
     * 
     * @return true for visible, false for hidden
     */
    public boolean isVisible();

    /**
     * Sets the visible status for the tab. An invisible tab is not shown in
     * the tab bar and cannot be selected, selection is changed
     * automatically when there is an attempt to select an invisible tab.
     * 
     * @param visible
     *            true for visible, false for hidden
     */
    public void setVisible(boolean visible);

    /**
     * Returns the closability status for the tab.
     * 
     * @return true if the tab is allowed to be closed by the end user,
     *         false for not allowing closing
     */
    public boolean isClosable();

    /**
     * Sets the closability status for the tab. A closable tab can be closed
     * by the user through the user interface. This also controls if a close
     * button is shown to the user or not.
     * <p>
     * Note! Currently only supported by TabSheet, not Accordion.
     * </p>
     * 
     * @param visible
     *            true if the end user is allowed to close the tab, false
     *            for not allowing to close. Should default to false.
     */
    public void setClosable(boolean closable);

    /**
     * Returns the enabled status for the tab. A disabled tab is shown as
     * such in the tab bar and cannot be selected.
     * 
     * @return true for enabled, false for disabled
     */
    public boolean isEnabled();

    /**
     * Sets the enabled status for the tab. A disabled tab is shown as such
     * in the tab bar and cannot be selected.
     * 
     * @param enabled
     *            true for enabled, false for disabled
     */
    public void setEnabled(boolean enabled);

    /**
     * Sets the caption for the tab.
     * 
     * @param caption
     *            the caption to set
     */
    public void setCaption(String caption);

    /**
     * Gets the caption for the tab.
     */
    public String getCaption();

    /**
     * Gets the icon for the tab.
     */
    public String getIconPath();

    /**
     * Sets the icon for the tab.
     * 
     * @param icon
     *            the icon to set
     */
    public void setIconPath(String icon);

    /**
     * Gets the description for the tab. The description can be used to
     * briefly describe the state of the tab to the user, and is typically
     * shown as a tooltip when hovering over the tab.
     * 
     * @return the description for the tab
     */
    public String getDescription();

    /**
     * Sets the description for the tab. The description can be used to
     * briefly describe the state of the tab to the user, and is typically
     * shown as a tooltip when hovering over the tab.
     * 
     * @param description
     *            the new description string for the tab.
     */
    public void setDescription(String description);

    /**
     * Get the component related to the Tab
     */
    public AbstractComponent getComponent();

    /**
     * Sets a style name for the tab. The style name will be rendered as a
     * HTML class name, which can be used in a CSS definition.
     * 
     * <pre class='code'>
     * Tab tab = tabsheet.addTab(tabContent, &quot;Tab text&quot;);
     * tab.setStyleName(&quot;mystyle&quot;);
     * </pre>
     * <p>
     * The used style name will be prefixed with "
     * {@code v-tabsheet-tabitemcell-}". For example, if you give a tab the
     * style "{@code mystyle}", the tab will get a "
     * {@code v-tabsheet-tabitemcell-mystyle}" style. You could then style
     * the component with:
     * </p>
     * 
     * <pre class='code'>
     * .v-tabsheet-tabitemcell-mystyle {font-style: italic;}
     * </pre>
     * 
     * <p>
     * This method will trigger a
     * {@link com.vaadin.terminal.Paintable.RepaintRequestEvent
     * RepaintRequestEvent} on the TabSheet to which the Tab belongs.
     * </p>
     * 
     * @param styleName
     *            the new style to be set for tab
     * @see #getStyleName()
     */
    public void setStyleName(String styleName);

    /**
     * Gets the user-defined CSS style name of the tab. Built-in style names
     * defined in Vaadin or GWT are not returned.
     * 
     * @return the style name or of the tab
     * @see #setStyleName(String)
     */
    public String getStyleName();
}
