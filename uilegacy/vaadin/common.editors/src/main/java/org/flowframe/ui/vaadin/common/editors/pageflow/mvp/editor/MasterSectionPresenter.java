package org.flowframe.ui.vaadin.common.editors.pageflow.mvp.editor;

import java.util.HashMap;
import java.util.Map;

import org.flowframe.ui.pageflow.services.IPageFactory;
import org.flowframe.ui.vaadin.common.editors.pageflow.builder.VaadinPageDataBuilder;
import org.flowframe.ui.vaadin.common.editors.pageflow.ext.grid.VaadinMatchGrid;
import org.flowframe.ui.vaadin.common.editors.pageflow.ext.mvp.IConfigurablePresenter;
import org.flowframe.ui.vaadin.common.editors.pageflow.ext.mvp.IContainerItemPresenter;
import org.flowframe.ui.vaadin.common.editors.pageflow.ext.mvp.ILocalizedEventSubscriber;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.editor.multilevel.MultiLevelEditorEventBus;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.editor.view.IMasterSectionView;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.editor.view.MasterSectionView;
import org.flowframe.ui.vaadin.common.editors.pageflow.mvp.lineeditor.EntityLineEditorEventBus;
import org.flowframe.ui.component.domain.AbstractComponent;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.flowframe.ui.vaadin.common.editors.entity.ext.EntityEditorToolStrip.EntityEditorToolStripButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.eventbus.EventBus;
import org.vaadin.mvp.eventbus.EventBusManager;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.IPresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;

/**
 * This presenter is responsible for the primary (top) section of a Master
 * Detail Layout. It manages its header and content presenters and uses the
 * factory to create them.
 * 
 * @author Sandile
 */
@Presenter(view = MasterSectionView.class)
public class MasterSectionPresenter extends BasePresenter<IMasterSectionView, MasterSectionEventBus> implements IConfigurablePresenter,
		IContainerItemPresenter {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private IPresenter<?, ? extends EventBus> headerPresenter;
	private IPresenter<?, ? extends EventBus> contentPresenter;
	private AbstractComponent componentModel;
	private IPageFactory factory;
	private EventBusManager sectionEventBusManager;
	private Map<String, Object> config;
	private VaadinPageDataBuilder pageDataBuilder;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void onAddNewBeanItem(Object newBean) throws Exception {
		if (this.contentPresenter == null) {
			if (this.getView().getContent() instanceof VaadinMatchGrid) {
				Container container = ((VaadinMatchGrid) this.getView().getContent()).getMatchedGrid().getContainerDataSource();
				if (container instanceof BeanItemContainer) {
					Item item = ((BeanItemContainer) container).addBean(newBean);
					if (((VaadinMatchGrid) this.getView().getContent()).getUnmatchedGrid().getContainerDataSource() instanceof JPAContainer<?>) {
						((JPAContainer<?>) ((VaadinMatchGrid) this.getView().getContent()).getUnmatchedGrid().getContainerDataSource())
								.refresh();
					}
					EntityLineEditorEventBus lineEditorEventBus = this.factory.getPresenterFactory().getEventBusManager()
							.getEventBus(EntityLineEditorEventBus.class);
					if (lineEditorEventBus != null) {
						lineEditorEventBus.setItemDataSource(item, container);
					} else {
						throw new Exception("EntityLineEditorEventBus could not be fetched from the event bus manager.");
					}
				}
			}
		}
	}

	@SuppressWarnings("serial")
	@Override
	public void onConfigure(Map<String, Object> params) throws Exception {
		this.config = params;
		this.componentModel = (AbstractComponent) params.get(IComponentFactory.COMPONENT_MODEL);
		this.factory = (IPageFactory) params.get(IComponentFactory.VAADIN_COMPONENT_FACTORY);
		// This map is used for shallow copying the parameters for child
		// components
		HashMap<String, Object> childParams = null;

		if (this.componentModel != null) {
			// A private ebm that creates a walled garden for events fired by
			// children
			this.sectionEventBusManager = new EventBusManager();
			this.sectionEventBusManager.register(MasterSectionEventBus.class, this);
			// Add the MLE Event Bus if it exists, we need it to switch editors
			EventBus mleEventBus = this.factory.getPresenterFactory().getEventBusManager().getEventBus(MultiLevelEditorEventBus.class);
			if (mleEventBus != null) {
				this.sectionEventBusManager.register(MultiLevelEditorEventBus.class, mleEventBus);
			}
			// Create the header and content presenter
			childParams = new HashMap<String, Object>(params);
			this.headerPresenter = this.factory.createMasterSectionHeaderPresenter(this.componentModel);
			this.contentPresenter = this.factory.createMasterSectionContentPresenter(this.componentModel, childParams);
			// If the header and content presenter have support for subscribing
			// to our private ebm, make sure they do
			if (this.headerPresenter instanceof ILocalizedEventSubscriber) {
				((ILocalizedEventSubscriber) this.headerPresenter).subscribe(this.sectionEventBusManager);
			}
			if (this.contentPresenter instanceof ILocalizedEventSubscriber) {
				((ILocalizedEventSubscriber) this.contentPresenter).subscribe(this.sectionEventBusManager);
			}
			// Provided they aren't respectively null, add our header and
			// content to our view
			if (this.headerPresenter != null) {
				this.getView().setHeader((Component) this.headerPresenter.getView());
			}
			if (this.contentPresenter != null) {
				this.getView().setContent((Component) this.contentPresenter.getView());
			} else {
				final Component contentComponent = this.factory.createComponent(this.componentModel);
				if (contentComponent instanceof VaadinMatchGrid) {
					if (((VaadinMatchGrid) contentComponent).getComponentModel().isDynamic()) {
						EntityEditorToolStripButton newEntityButton = ((VaadinMatchGrid) contentComponent).getNewMatchedItemButton();
						newEntityButton.addListener(new ClickListener() {

							@SuppressWarnings({ "unchecked", "rawtypes" })
							@Override
							public void buttonClick(ClickEvent event) {
								assert (MasterSectionPresenter.this.factory != null);
								assert (MasterSectionPresenter.this.factory.getPresenterFactory() != null);
								assert (MasterSectionPresenter.this.factory.getPresenterFactory().getEventBusManager() != null);

								try {
									BeanItemContainer container = new BeanItemContainer(((VaadinMatchGrid) contentComponent)
											.getMatchedContainerType());
									Object bean = ((VaadinMatchGrid) contentComponent).getMatchedContainerType().newInstance();
									Item item = container.addBean(bean);
									EntityLineEditorEventBus eventBus = MasterSectionPresenter.this.factory.getPresenterFactory()
											.getEventBusManager().getEventBus(EntityLineEditorEventBus.class);
									if (eventBus != null) {
										eventBus.setNewItemDataSource(item, container);
									}
								} catch (IllegalArgumentException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (InstantiationException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
					}
				}
				this.getView().setContent(contentComponent);
			}
		}
		
		this.pageDataBuilder = new VaadinPageDataBuilder();
	}

	@Override
	public void onSetItemDataSource(Item item, Container... containers) throws Exception {
		if (this.contentPresenter != null) {
			// If we can set the item datasource of the content presenter, lets
			// make it happen
			if (IContainerItemPresenter.class.isAssignableFrom(this.contentPresenter.getClass())) {
				((IContainerItemPresenter) this.contentPresenter).onSetItemDataSource(item, containers);
			}
		} else {
			if (this.getView().getContent() != null) {
				if (containers.length == 1) {
					this.pageDataBuilder.applyItemDataSource(this.getView().getContent(), containers[0], item,
							this.factory.getPresenterFactory(), this.config);
				} else {
					throw new Exception("One container is needed to set item datasource with a content presenter.");
				}
			} else {
				throw new Exception("Could not set the item datasource. The content presenter was null.");
			}
		}
	}
}
