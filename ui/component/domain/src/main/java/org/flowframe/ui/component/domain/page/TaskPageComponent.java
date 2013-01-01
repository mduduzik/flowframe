package org.flowframe.ui.component.domain.page;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.flowframe.ui.component.domain.AbstractComponent;

@Entity
public class TaskPageComponent extends AbstractComponent {
	private static final long serialVersionUID = -89799981390933973L;
	
	@OneToOne
	private AbstractComponent content;

	public TaskPageComponent() {
		super("taskpage");
	}
	
	public TaskPageComponent(AbstractComponent content) {
		this();
		this.content = content;
	}

	public AbstractComponent getContent() {
		return content;
	}

	public void setContent(AbstractComponent content) {
		this.content = content;
	}

}
