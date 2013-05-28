package org.flowframe.ui.vaadin.addons.wizards.event.custom;

import org.flowframe.ui.vaadin.addons.wizards.CustomWizard;

@SuppressWarnings("serial")
public class CustomWizardStepSetChangedEvent extends CustomAbstractWizardEvent {

    public CustomWizardStepSetChangedEvent(CustomWizard source) {
        super(source);
    }

}
