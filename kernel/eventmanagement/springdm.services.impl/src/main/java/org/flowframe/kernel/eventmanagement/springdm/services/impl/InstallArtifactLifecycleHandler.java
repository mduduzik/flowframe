package org.flowframe.kernel.eventmanagement.springdm.services.impl;

import org.eclipse.virgo.kernel.install.artifact.InstallArtifact;
import org.eclipse.virgo.kernel.install.artifact.InstallArtifactLifecycleListenerSupport;
import org.eclipse.virgo.nano.deployer.api.core.DeploymentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InstallArtifactLifecycleHandler extends InstallArtifactLifecycleListenerSupport {
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	private InstallArtifact dosgiArtifact = null;
	private InstallArtifact mainAppPlanArtifact = null;

	@Override
    public void onStarted(InstallArtifact installArtifact) throws DeploymentException {
		logger.info("InstallArtifactLifecycleHandler:onStarted "+installArtifact.getName());
		if ("cxf-dosgi-ri-singlebundle-distribution".equals(installArtifact.getName())) {
			dosgiArtifact = installArtifact;
			logger.info("InstallArtifactLifecycleHandler:onStarted: Obtained reference to "+installArtifact.getName());
		} 
		else if ("com.conx.bi.app.sprint2".equals(installArtifact.getName())) {
			logger.info("InstallArtifactLifecycleHandler:onStarted: Application Plan Started...restarting DOSGi");
			if (dosgiArtifact != null) {
				logger.info("InstallArtifactLifecycleHandler:onStarted: DOSGi Artifact available...restarting it");
				dosgiArtifact.refresh();
			}
			else {
				mainAppPlanArtifact = installArtifact;
				logger.info("InstallArtifactLifecycleHandler:onStarted: DOSGi Artifact not available...will keep trying");
			}
		}
    }
	
	@Override
	public void onInstalled(InstallArtifact installArtifact) throws DeploymentException {
		if ("cxf-dosgi-ri-singlebundle-distribution".equals(installArtifact.getName())) {
			dosgiArtifact = installArtifact;
			logger.info("InstallArtifactLifecycleHandler:onInstalled: Obtained reference to "+installArtifact.getName());
			if (mainAppPlanArtifact != null) {
				logger.info("InstallArtifactLifecycleHandler:onInstalled: MainApp already started...restarting DOSGi");
				dosgiArtifact.refresh();
			}
		} 		
	}
}
