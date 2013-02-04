package org.flowframe.ui.vaadin.addons.wizards.event.custom;

import org.flowframe.ui.vaadin.addons.wizards.CustomWizard;
import org.flowframe.ui.vaadin.addons.wizards.WizardStep;

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