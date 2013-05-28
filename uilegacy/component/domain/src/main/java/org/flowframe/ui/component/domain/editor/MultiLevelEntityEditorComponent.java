package org.flowframe.ui.component.domain.editor;

import org.flowframe.ui.component.domain.masterdetail.MasterDetailComponent;

public class MultiLevelEntityEditorComponent extends EntityEditorComponent {
	private static final long serialVersionUID = 1L;
	
	private MasterDetailComponent content;

	public MultiLevelEntityEditorComponent() {
		super("multilevelentityeditor");
	}

	public MasterDetailComponent getContent() {
		return content;
	}

	public void setContent(MasterDetailComponent content) {
		this.content = content;
	}
}
