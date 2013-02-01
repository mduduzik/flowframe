package org.flowframe.ui.vaadin.addons.wizards.event;

import org.flowframe.ui.vaadin.addons.wizards.Wizard;

@SuppressWarnings("serial")
public class WizardStepSetChangedEvent extends AbstractWizardEvent {

    public WizardStepSetChangedEvent(Wizard source) {
        super(source);
    }

}
