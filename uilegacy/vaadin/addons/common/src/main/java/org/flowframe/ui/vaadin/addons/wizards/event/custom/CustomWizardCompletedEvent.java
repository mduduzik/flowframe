package org.flowframe.ui.vaadin.addons.wizards.event.custom;

import org.flowframe.ui.vaadin.addons.wizards.CustomWizard;

@SuppressWarnings("serial")
public class CustomWizardCompletedEvent extends CustomAbstractWizardEvent {

    public CustomWizardCompletedEvent(CustomWizard source) {
        super(source);
    }

}
