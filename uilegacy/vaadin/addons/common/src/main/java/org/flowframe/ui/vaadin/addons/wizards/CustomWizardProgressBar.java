package org.flowframe.ui.vaadin.addons.wizards;

import org.flowframe.ui.vaadin.addons.wizards.event.custom.CustomWizardCancelledEvent;
import org.flowframe.ui.vaadin.addons.wizards.event.custom.CustomWizardCompletedEvent;
import org.flowframe.ui.vaadin.addons.wizards.event.custom.CustomWizardProgressListener;
import org.flowframe.ui.vaadin.addons.wizards.event.custom.CustomWizardStepActivationEvent;
import org.flowframe.ui.vaadin.addons.wizards.event.custom.CustomWizardStepSetChangedEvent;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractComponent;

/**
 * WizardProgressBar displays the progress bar for a {@link Wizard}.
 */
@SuppressWarnings("serial")
@com.vaadin.ui.ClientWidget(org.flowframe.ui.vaadin.addons.wizards.client.ui.VWizardProgressBar.class)
public class CustomWizardProgressBar extends AbstractComponent implements
        CustomWizardProgressListener {

    private final CustomWizard wizard;
    private boolean completed;

    public CustomWizardProgressBar(CustomWizard wizard) {
        this.wizard = wizard;
        setWidth("100%");
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        /*-
         steps
             step
                 caption
                 completed
                 current
             step
         steps
         */
        target.startTag("steps");
        for (WizardStep step : wizard.getSteps()) {
            target.startTag("step");
            target.addAttribute("caption", step.getCaption());
            target.addAttribute("completed", wizard.isCompleted(step));
            target.addAttribute("current", wizard.isActive(step));
            target.endTag("step");
        }
        target.endTag("steps");
        target.addAttribute("complete", completed);
    }

    public void activeStepChanged(CustomWizardStepActivationEvent event) {
        requestRepaint();
    }

    public void stepSetChanged(CustomWizardStepSetChangedEvent event) {
        requestRepaint();
    }

    public void wizardCompleted(CustomWizardCompletedEvent event) {
        completed = true;
        requestRepaint();
    }

    public void wizardCancelled(CustomWizardCancelledEvent event) {
        // NOP, no need to react to cancellation
    }

}