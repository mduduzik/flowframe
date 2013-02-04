package org.flowframe.ui.vaadin.addons.wizards.event;

import org.flowframe.ui.vaadin.addons.wizards.Wizard;
import org.flowframe.ui.vaadin.addons.wizards.WizardStep;

@SuppressWarnings("serial")
public class WizardStepActivationEvent extends AbstractWizardEvent {

    private final WizardStep activatedStep;

    public WizardStepActivationEvent(Wizard source, WizardStep activatedStep) {
        super(source);
        this.activatedStep = activatedStep;
    }

    /**
     * Returns the {@link WizardStep} that was the activated.
     * 
     * @return the activated {@link WizardStep}.
     */
    public WizardStep getActivatedStep() {
        return activatedStep;
    }

}