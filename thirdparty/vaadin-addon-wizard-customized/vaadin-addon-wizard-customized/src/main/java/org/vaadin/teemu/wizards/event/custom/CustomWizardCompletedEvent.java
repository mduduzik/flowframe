package org.vaadin.teemu.wizards.event.custom;

import org.vaadin.teemu.wizards.CustomWizard;

@SuppressWarnings("serial")
public class CustomWizardCompletedEvent extends CustomAbstractWizardEvent {

    public CustomWizardCompletedEvent(CustomWizard source) {
        super(source);
    }

}
