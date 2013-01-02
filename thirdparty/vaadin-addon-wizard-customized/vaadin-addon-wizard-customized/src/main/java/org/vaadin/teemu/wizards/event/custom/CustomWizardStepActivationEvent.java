package org.vaadin.teemu.wizards.event.custom;

import org.vaadin.teemu.wizards.CustomWizard;
import org.vaadin.teemu.wizards.WizardStep;

@SuppressWarnings("serial")
public class CustomWizardStepActivationEvent extends CustomAbstractWizardEvent {

    private final WizardStep activatedStep;

    public CustomWizardStepActivationEvent(CustomWizard source, WizardStep activatedStep) {
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