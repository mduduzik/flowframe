package org.flowframe.ui.vaadin.addons.wizards.event;

import org.flowframe.ui.vaadin.addons.wizards.Wizard;

@SuppressWarnings("serial")
public class WizardCompletedEvent extends AbstractWizardEvent {

    public WizardCompletedEvent(Wizard source) {
        super(source);
    }

}
