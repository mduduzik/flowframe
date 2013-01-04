package org.vaadin.teemu.wizards.event.custom;

import org.vaadin.teemu.wizards.CustomWizard;

@SuppressWarnings("serial")
public class CustomWizardStepSetChangedEvent extends CustomAbstractWizardEvent {

    public CustomWizardStepSetChangedEvent(CustomWizard source) {
        super(source);
    }

}
