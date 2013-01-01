package org.flowframe.ui.component.domain.masterdetail;

import javax.persistence.Entity;

@Entity
public class CreateNewLineEditorComponent extends LineEditorComponent {
	private static final long serialVersionUID = 1L;
	public CreateNewLineEditorComponent(String code, String caption,LineEditorContainerComponent mainComponent, int ordinal) {
		super(code,caption,mainComponent,ordinal);
	}
	
	public CreateNewLineEditorComponent(String code, String caption,LineEditorContainerComponent mainComponent) {
		super(code,caption,mainComponent);
	}

	public CreateNewLineEditorComponent() {
		super();
	}

	public CreateNewLineEditorComponent(LineEditorContainerComponent mainComponent) {
		super(mainComponent);
	}
}
