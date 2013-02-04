package org.flowframe.ui.vaadin.addons.wizards.event.custom;

import org.flowframe.ui.vaadin.addons.wizards.CustomWizard;

import com.vaadin.ui.Component;

@SuppressWarnings("serial")
public class CustomAbstractWizardEvent extends Component.Event {

    protected CustomAbstractWizardEvent(CustomWizard source) {
        super(source);
    }

    /**
     * Returns the {@link CustomWizard} component that was the source of this event.
     * 
     * @return the source {@link CustomWizard} of this event.
     */
    public CustomWizard getWizard() {
        return (CustomWizard) getSource();
    }
}
