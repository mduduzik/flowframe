package org.vaadin.teemu.wizards.event.custom;

import org.vaadin.teemu.wizards.CustomWizard;

@SuppressWarnings("serial")
public class CustomWizardCancelledEvent extends CustomAbstractWizardEvent {

    public CustomWizardCancelledEvent(CustomWizard source) {
        super(source);
    }

}
