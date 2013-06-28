package org.flowframe.kernel.eventmanagement.springdm.services.impl;

import java.io.IOException;
import java.util.Dictionary;

import org.eclipse.virgo.kernel.install.artifact.InstallArtifact;
import org.eclipse.virgo.kernel.install.artifact.InstallArtifactLifecycleListenerSupport;
import org.eclipse.virgo.nano.deployer.api.core.DeploymentException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class InstallArtifactLifecycleHandler extends InstallArtifactLifecycleListenerSupport {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	private ConfigurationAdmin confAdmin;
	
	private InstallArtifact dosgiArtifact = null;
	private InstallArtifact mainAppPlanArtifact = null;


	@Override
    public void onStarted(InstallArtifact installArtifact) throws DeploymentException {
		logger.info("InstallArtifactLifecycleHandler:onStarted "+installArtifact.getName());
		if ("cxf-dosgi-ri-singlebundle-distribution".equals(installArtifact.getName())) {
			logger.info("InstallArtifactLifecycleHandler:onStarted: Stopping cxf-dosgi-ri-singlebundle-distribution after STARTED");
			dosgiArtifact = installArtifact;
			if (mainAppPlanArtifact != null) {
				logger.info("InstallArtifactLifecycleHandler:onStarting: MainApp already started...restarting DOSGi");
				refreshDOSGi();
			}
		} 	
		/*
		if ("cxf-dosgi-ri-singlebundle-distribution".equals(installArtifact.getName())) {
			dosgiArtifact = installArtifact;
			if (mainAppPlanArtifact == null){
				//logger.info("InstallArtifactLifecycleHandler:onStarted: Stopping cxf-dosgi-ri-singlebundle-distribution since MainApp hasn't started");
				//dosgiArtifact.stop();
				//logger.info("InstallArtifactLifecycleHandler:onStarted: Stopped cxf-dosgi-ri-singlebundle-distribution since MainApp hasn't started");
			}
			else
			if (mainAppPlanArtifact != null)
			{
				logger.info("InstallArtifactLifecycleHandler:onStarted: Restarting cxf-dosgi-ri-singlebundle-distribution since MainApp has started");
				dosgiArtifact.refresh();
				logger.info("InstallArtifactLifecycleHandler:onStarted: Refreshed cxf-dosgi-ri-singlebundle-distribution");
			}
			logger.info("InstallArtifactLifecycleHandler:onStarted: Obtained reference to "+installArtifact.getName());
		} 
		*/
		else 
		if ("com.conx.bi.app.sprint2".equals(installArtifact.getName())) {
			logger.info("InstallArtifactLifecycleHandler:onStarted: Application Plan Started...restarting DOSGi");
			refreshDOSGi();
		}
    }
	
	@Override
	public void onStarting(InstallArtifact installArtifact) throws DeploymentException {
		if ("com.conx.bi.app.sprint2".equals(installArtifact.getName())) {
			logger.info("InstallArtifactLifecycleHandler:onStarting: com.conx.bi.app.sprint2 STARTING");
			mainAppPlanArtifact = installArtifact;
			logger.info("InstallArtifactLifecycleHandler:onStarting: Stopped com.conx.bi.app.sprint2 after STARTING");
			if (dosgiArtifact != null) {
				logger.info("InstallArtifactLifecycleHandler:onStarting: cxf-dosgi-ri-singlebundle-distribution already started...let com.conx.bi.app.sprint2 start");
				//dosgiArtifact.refresh();
				//logger.info("InstallArtifactLifecycleHandler:onStarting: cxf-dosgi-ri-singlebundle-distribution already started...restarting com.conx.bi.app.sprint2");
			}
			else
			{
				logger.info("InstallArtifactLifecycleHandler:onStarting: cxf-dosgi-ri-singlebundle-distribution not yet started. Stopping com.conx.bi.app.sprint2 after STARTING");
				mainAppPlanArtifact.stop();
				logger.info("InstallArtifactLifecycleHandler:onStarting: Stopped com.conx.bi.app.sprint2");
			}
		} 			
	}
	
	private void refreshDOSGi() {
		try {
			//-- try bundle 
/*			if (dosgiArtifact != null) {
				logger.info("InstallArtifactLifecycleHandler:onStarted: DOSGi Artifact available...starting it");
				dosgiArtifact.start();
				logger.info("InstallArtifactLifecycleHandler:onStarted: DOSGi Artifact available...started");
				return;
			}	*/
			
			//-- conf admin
			logger.info("InstallArtifactLifecycleHandler:refreshDOSGi: Refresh cxf-dosgi-ri-singlebundle-distribution with ConfAdmin");
			Configuration confAdminInstance = confAdmin.getConfiguration("org.apache.cxf.dosgi.discovery.zookeeper");
			logger.info("InstallArtifactLifecycleHandler:refreshDOSGi: confAdminInstance: "+confAdminInstance);
			if (confAdminInstance != null) {
				logger.info("InstallArtifactLifecycleHandler:refreshDOSGi: Refreshing org.apache.cxf.dosgi.discovery.zookeeper");
				Dictionary<String, String> properties = confAdminInstance.getProperties();
				confAdminInstance.update();
			}
			else
				logger.info("InstallArtifactLifecycleHandler:refreshDOSGi: org.apache.cxf.dosgi.discovery.zookeeper not found");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
