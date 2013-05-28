package org.flowframe.ui.vaadin.addons.wizards.event.custom;

public interface CustomWizardProgressListener {

    /**
     * Called when the currently active {@link WizardStep} is changed and this
     * {@code WizardProgressListener} is expected to update itself accordingly.
     * 
     * @param event
     *            {@link WizardStepActivationEvent} object containing details
     *            about the event
     */
    void activeStepChanged(CustomWizardStepActivationEvent event);

    /**
     * Called when collection {@link WizardStep}s is changed (i.e. a step is
     * added or removed) and this {@code WizardProgressListener} is expected to
     * update itself accordingly.
     * 
     * @param event
     *            {@link CustomWizardStepSetChangedEvent} object containing details
     *            about the event
     */
    void stepSetChanged(CustomWizardStepSetChangedEvent event);

    /**
     * Called when a {@link Wizard} is completed.
     * 
     * @param event
     *            {@link WizardCompletedEvent} object containing details about
     *            the event
     */
    void wizardCompleted(CustomWizardCompletedEvent event);

    /**
     * Called when a {@link Wizard} is cancelled by the user.
     * 
     * @param event
     *            {@link WizardCancelledEvent} object containing details about
     *            the event
     */
    void wizardCancelled(CustomWizardCancelledEvent event);

}