package org.flowframe.ui.pageflow.vaadin.mvp.embedded;

import java.net.URL;
import java.util.Map;

import org.flowframe.kernel.common.mdm.domain.user.User;
import org.flowframe.ui.component.domain.browser.EmbeddedAppComponent;
import org.flowframe.ui.pageflow.vaadin.ext.mvp.IConfigurablePresenter;
import org.flowframe.ui.pageflow.vaadin.mvp.embedded.view.EmbeddedAppView;
import org.flowframe.ui.pageflow.vaadin.mvp.embedded.view.IEmbeddedAppView;
import org.flowframe.ui.services.factory.IComponentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.mvp.presenter.BasePresenter;
import org.vaadin.mvp.presenter.annotation.Presenter;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Embedded;

@Presenter(view = EmbeddedAppView.class)
public class EmbeddedAppPresenter extends BasePresenter<IEmbeddedAppView, EmbeddedAppEventBus> implements IConfigurablePresenter{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	private User currentUser;
	private EmbeddedAppComponent component;
	private String bpmnUrl;
	
	
	public void onLaunch(User currentUser) {
		this.currentUser = currentUser;
    	try {
    		
    		String userparam = this.component.getUserTokenName();
    		URL aURL = new URL(bpmnUrl);
    		String ref = aURL.getRef();
    		String query = aURL.getQuery();
    		if (query != null){
    			query += "&"+userparam+"="+currentUser.getScreenName();
    			bpmnUrl = aURL.getProtocol()+"://"+aURL.getHost()+":"+aURL.getPort()+aURL.getPath()+"?"+query+"#"+aURL.getRef();
    		}
    		else {
    			bpmnUrl += userparam+"="+currentUser.getScreenName();
    			bpmnUrl = aURL.getProtocol()+"://"+aURL.getHost()+":"+aURL.getPort()+aURL.getPath()+"#"+aURL.getRef();
    		}
    		
			ExternalResource eress = new ExternalResource(bpmnUrl, "application/html"); 
			
			Embedded embeddedApp = new Embedded(null,eress);   
			
			embeddedApp.setType(Embedded.TYPE_BROWSER);
			embeddedApp.setMimeType("application/html"); 
			embeddedApp.setSizeFull();	
			embeddedApp.setHeight("800px");
			getView().getMainLayout().addComponent(embeddedApp);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    	finally
    	{
    		
    	}
	}


	@Override
	public void onConfigure(Map<String, Object> params) throws Exception {
		this.component = (EmbeddedAppComponent) params.get(IComponentFactory.FACTORY_PARAM_MVP_COMPONENT_MODEL);
		this.bpmnUrl = this.component.getUrl();
	}
}
