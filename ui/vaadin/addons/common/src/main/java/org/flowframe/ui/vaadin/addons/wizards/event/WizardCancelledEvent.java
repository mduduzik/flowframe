package org.flowframe.ui.vaadin.addons.wizards.event;

import org.flowframe.ui.vaadin.addons.wizards.Wizard;

@SuppressWarnings("serial")
public class WizardCancelledEvent extends AbstractWizardEvent {

    public WizardCancelledEvent(Wizard source) {
        super(source);
    }

}
