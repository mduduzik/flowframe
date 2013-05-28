package org.flowframe.ui.component.domain.masterdetail;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.flowframe.ui.component.domain.AbstractComponent;

@Entity
public class LineEditorComponent extends AbstractComponent {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private LineEditorContainerComponent mainComponent;
	
	@OneToOne 
	private AbstractComponent content;
	
	private int ordinal;
	
	public LineEditorComponent(String code, String caption,LineEditorContainerComponent mainComponent, int ordinal) {
		super("lineeditorcomponent");
		this.ordinal = ordinal;
		setCode(code);
		setName(caption);
		setCaption(caption);
		this.mainComponent = mainComponent;
	}
	
	public LineEditorComponent(String code, String caption,LineEditorContainerComponent mainComponent) {
		this();
		setCode(code);
		setName(caption);
		setCaption(caption);
		this.mainComponent = mainComponent;
	}

	public LineEditorComponent() {
		super("lineeditorcomponent");
		this.ordinal = -1;
	}

	public LineEditorComponent(LineEditorContainerComponent mainComponent) {
		this();
		this.mainComponent = mainComponent;
	}

	public LineEditorContainerComponent getMainComponent() {
		return mainComponent;
	}

	public void setMainComponent(LineEditorContainerComponent mainComponent) {
		this.mainComponent = mainComponent;
	}

	public AbstractComponent getContent() {
		return content;
	}

	public void setContent(AbstractComponent content) {
		this.content = content;
	}
	
	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}
}
