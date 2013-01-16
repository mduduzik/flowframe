package org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.builder.VaadinPageFactoryImpl;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.ext.mvp.IConfigurablePresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.section.EntityLineEditorSectionPresenter;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.view.EntityLineEditorView;
import org.flowframe.bpm.jbpm.ui.pageflow.vaadin.mvp.lineeditor.view.IEntityLineEditorView;
import org.flowframe.ui.component.domain.masterdetail.CreateNewLineEditorComponent;
import org.flowframe.ui.component.domain.masterdetail.LineEditorComponent;
import org.flowframe.ui.component.domain.masterdetail.LineEditorContainerComponent;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;

@Presenter(view = EntityLineEditorView.class)
public class EntityLineEditorPresenter extends BasePresenter<IEntityLineEditorView, EntityLineEditorEventBus> implements
		IConfigurablePresenter {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private Map<LineEditorComponent, EntityLineEditorSectionPresenter> lineEditorSectionCache;
	private Map<CreateNewLineEditorComponent, EntityLineEditorSectionPresenter> createNewLineEditorSectionCache;
	private List<LineEditorComponent> lineEditorSectionOrdinalCache;
	private List<CreateNewLineEditorComponent> createNewLineEditorSectionOrdinalCache;

	private VaadinPageFactoryImpl presenterFactory;
	private LineEditorContainerComponent componentModel;
	private Map<String, Object> config;

	@Override
	public void onConfigure(Map<String, Object> params) {
		this.componentModel = (LineEditorContainerComponent) params.get(IComponentFactory.COMPONENT_MODEL);
		this.presenterFactory = (VaadinPageFactoryImpl) params.get(IComponentFactory.VAADIN_COMPONENT_FACTORY);
		this.config = params;
		this.lineEditorSectionCache = new HashMap<LineEditorComponent, EntityLineEditorSectionPresenter>();
		this.createNewLineEditorSectionCache = new HashMap<CreateNewLineEditorComponent, EntityLineEditorSectionPresenter>();
		this.lineEditorSectionOrdinalCache = new LinkedList<LineEditorComponent>();
		this.createNewLineEditorSectionOrdinalCache = new LinkedList<CreateNewLineEditorComponent>();

		this.getView().init();
	}

	public void onSetItemDataSource(Item item, Container... containers) throws Exception {
		Set<LineEditorComponent> lecs = null;
		
		this.getView().removeAllTabs();

		if (this.lineEditorSectionOrdinalCache.size() == 0) {
			lecs = this.componentModel.getLineEditors();

			LineEditorComponent currentOrderedLineEditorSectionComponent = null;
			for (LineEditorComponent lineEditorSectionComponent : lecs) {
				if (!(lineEditorSectionComponent instanceof CreateNewLineEditorComponent)) {
					if (this.lineEditorSectionCache.get(lineEditorSectionComponent) == null) {
						if (this.lineEditorSectionOrdinalCache.size() > 0) {
							boolean isLineEditorAdded = false;
							for (int i = 0; i < this.lineEditorSectionOrdinalCache.size(); i++) {
								currentOrderedLineEditorSectionComponent = this.lineEditorSectionOrdinalCache.get(i);
								if (lineEditorSectionComponent.getOrdinal() < currentOrderedLineEditorSectionComponent.getOrdinal()) {
									this.lineEditorSectionOrdinalCache.add(i, (LineEditorComponent) lineEditorSectionComponent);
									isLineEditorAdded = true;
									break;
								}
							}
	
							if (!isLineEditorAdded) {
								this.lineEditorSectionOrdinalCache.add((LineEditorComponent) lineEditorSectionComponent);
							}
						} else {
							this.lineEditorSectionOrdinalCache.add(0, (LineEditorComponent) lineEditorSectionComponent);
						}
	
						this.lineEditorSectionCache.put((LineEditorComponent) lineEditorSectionComponent,
								(EntityLineEditorSectionPresenter) presenterFactory.createPresenter(lineEditorSectionComponent, this.config));
					}
				}
			}
		}
		
		EntityLineEditorSectionPresenter presenter = null;
		for (LineEditorComponent orderedLineEditorComponent : this.lineEditorSectionOrdinalCache) {
			presenter = this.lineEditorSectionCache.get(orderedLineEditorComponent);
			((IEntityLineEditorView) getView()).addTab((Component) presenter.getView(), orderedLineEditorComponent.getCaption());
		}

		Exception lastCaughtException = null;
		Set<LineEditorComponent> lineEditorComponents = this.lineEditorSectionCache.keySet();
		for (LineEditorComponent lineEditorComponent : lineEditorComponents) {
			try {
				((EntityLineEditorSectionPresenter) this.lineEditorSectionCache.get(lineEditorComponent)).onSetItemDataSource(item,
						containers);
			} catch (Exception e) {
				lastCaughtException = e;
				e.printStackTrace();
			}
		}

		if (lastCaughtException != null) {
			throw lastCaughtException;
		}
	}

	public void onSetNewItemDataSource(Item item, Container... containers) throws Exception {
		Set<LineEditorComponent> lecs = null;
		
		this.getView().removeAllTabs();

		if (this.createNewLineEditorSectionOrdinalCache.size() == 0) {
			lecs = this.componentModel.getLineEditors();

			LineEditorComponent currentOrderedLineEditorSectionComponent = null;

			// Find CreateNew LE
			CreateNewLineEditorComponent newNewLE = null;
			for (LineEditorComponent lineEditorSectionComponent : lecs) {
				if (lineEditorSectionComponent instanceof CreateNewLineEditorComponent) {
					newNewLE = (CreateNewLineEditorComponent) lineEditorSectionComponent;
					if (this.createNewLineEditorSectionCache.get(newNewLE) == null) {
						if (this.createNewLineEditorSectionOrdinalCache.size() > 0) {
							boolean isLineEditorAdded = false;
							for (int i = 0; i < this.createNewLineEditorSectionOrdinalCache.size(); i++) {
								currentOrderedLineEditorSectionComponent = this.createNewLineEditorSectionOrdinalCache.get(i);
								if (lineEditorSectionComponent.getOrdinal() < currentOrderedLineEditorSectionComponent.getOrdinal()) {
									this.createNewLineEditorSectionOrdinalCache.add(i, (CreateNewLineEditorComponent)lineEditorSectionComponent);
									isLineEditorAdded = true;
									break;
								}
							}

							if (!isLineEditorAdded) {
								this.createNewLineEditorSectionOrdinalCache.add((CreateNewLineEditorComponent) lineEditorSectionComponent);
							}
						} else {
							this.createNewLineEditorSectionOrdinalCache.add(0, (CreateNewLineEditorComponent) lineEditorSectionComponent);
						}

						this.createNewLineEditorSectionCache.put(newNewLE,
								(EntityLineEditorSectionPresenter) presenterFactory.createPresenter(newNewLE, this.config));
					}
				}
			}
		}
		
		EntityLineEditorSectionPresenter presenter = null;
		for (LineEditorComponent orderedLineEditorComponent : this.createNewLineEditorSectionOrdinalCache) {
			presenter = this.createNewLineEditorSectionCache.get(orderedLineEditorComponent);
			((IEntityLineEditorView) getView()).addTab((Component) presenter.getView(), orderedLineEditorComponent.getCaption());
		}		

		Exception lastCaughtException = null;
		Set<CreateNewLineEditorComponent> lineEditorComponents = this.createNewLineEditorSectionCache.keySet();
		for (LineEditorComponent lineEditorComponent : lineEditorComponents) {
			try {
				((EntityLineEditorSectionPresenter) this.createNewLineEditorSectionCache.get(lineEditorComponent)).onSetItemDataSource(item,
						containers);
			} catch (Exception e) {
				lastCaughtException = e;
				e.printStackTrace();
			}
		}

		if (lastCaughtException != null) {
			throw lastCaughtException;
		}
	}
}
