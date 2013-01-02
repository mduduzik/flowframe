package org.flowframe.ui.vaadin.addons.common.gwt.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VLink;

@SuppressWarnings("deprecation")
public class VActiveLink extends VLink {

    String id;
    ApplicationConnection client;
    boolean listening = false;

    public VActiveLink() {
        addMouseListener(new MouseListener() {
            public void onMouseDown(Widget sender, int x, int y) {
            }

            public void onMouseEnter(Widget sender) {
            }

            public void onMouseLeave(Widget sender) {
            }

            public void onMouseMove(Widget sender, int x, int y) {
            }

            public void onMouseUp(Widget sender, int x, int y) {
                Event e = DOM.eventGetCurrentEvent();
                if (e.getButton() == Event.BUTTON_MIDDLE) {
                    sendVariables();
                }
            }
        });
    }

    /**
     * Sends variables, returns true if default handler should be called (i.e if
     * server is listening and the link was claimed to be opened by the client)
     * 
     * @return
     */
    private boolean sendVariables() {
        Event e = DOM.eventGetCurrentEvent();
        boolean opened = (e.getCtrlKey() || e.getAltKey() || e.getShiftKey()
                || e.getMetaKey() || e.getButton() == Event.BUTTON_MIDDLE);

        // Works as VLink if no-one is listening
        if (listening) {
            if (opened) {
                // VLink will open, notify server
                client.updateVariable(id, "opened", true, false);
            } else {
                e.preventDefault();
            }
            client.updateVariable(id, "activated", true, true);
        }
        return !listening || opened;
    }

    @Override
    public void onClick(ClickEvent event) {

        if (sendVariables()) {
            // run default if not listening, or we claimed link was opened
            super.onClick(event);
        }
    }

    @Override
    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        // Ensure correct implementation,
        // but don't let container manage caption etc.
        if (client.updateComponent(this, uidl, false)) {
            return;
        }

        // Save details
        this.client = client;
        id = uidl.getId();
        listening = uidl.hasVariable("activated");

        super.updateFromUIDL(uidl, client);
    }

}
