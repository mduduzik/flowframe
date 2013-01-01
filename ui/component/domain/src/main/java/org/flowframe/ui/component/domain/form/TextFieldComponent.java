package org.flowframe.ui.component.domain.form;

import javax.persistence.Entity;

import org.flowframe.ds.domain.DataSourceField;
import org.flowframe.ui.component.domain.AbstractFieldComponent;

@Entity
public class TextFieldComponent extends AbstractTextFieldComponent {
	  /**
     * Tells if input is used to enter sensitive information that is not echoed
     * to display. Typically passwords.
     */
    private boolean secret = false;

    /**
     * Number of visible rows in a multiline TextField. Value 0 implies a
     * single-line text-editor.
     */
    private int rows = 0;

    /**
     * Tells if word-wrapping should be used in multiline mode.
     */
    private boolean wordwrap = true;

	public TextFieldComponent() {
		super("textField");
	}
}
