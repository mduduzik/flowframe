package org.flowframe.ui.component.domain.masterdetail;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.flowframe.ui.component.domain.AbstractComponent;

@Entity
public class LineEditorContainerComponent extends AbstractComponent {
	private static final long serialVersionUID = 1334745924025959679L;
	
	@OneToMany(mappedBy="mainComponent", fetch=FetchType.EAGER)
	private Set<LineEditorComponent> lineEditors = new HashSet<LineEditorComponent>();

	public LineEditorContainerComponent(String code, String name) {
		this();
		setCode(code);
		setName(name);
	}
	
	public LineEditorContainerComponent() {
		super("lineeditorcontainercomponent");
	}

	public Set<LineEditorComponent> getLineEditors() {
		return lineEditors;
	}

	public void setLineEditors(Set<LineEditorComponent> lineEditors) {
		this.lineEditors = lineEditors;
	}  
}
