package org.flowframe.ui.vaadin.addons.wizards.event.custom;

import org.flowframe.ui.vaadin.addons.wizards.CustomWizard;

@SuppressWarnings("serial")
public class CustomWizardCancelledEvent extends CustomAbstractWizardEvent {

    public CustomWizardCancelledEvent(CustomWizard source) {
        super(source);
    }

}
