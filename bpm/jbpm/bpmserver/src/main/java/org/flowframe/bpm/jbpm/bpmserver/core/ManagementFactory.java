package org.flowframe.bpm.jbpm.bpmserver.core;

import org.flowframe.bpm.jbpm.bpmserver.BPMServerImpl;

public class ManagementFactory extends org.jboss.bpm.console.server.integration.ManagementFactory {
	
	private BPMServerImpl bpmService;
	
	public ManagementFactory(BPMServerImpl bpmService)
	{
		this.bpmService = bpmService;
	}

	public ProcessManagement createProcessManagement() {
		return new ProcessManagement(this.bpmService);
	}

	public TaskManagement createTaskManagement() {
		return new TaskManagement(this.bpmService);
	}

	public UserManagement createUserManagement() {
		return new UserManagement();
	}
	
	public HumanTaskService createHumanTaskService() {
		return new HumanTaskService(this.bpmService);
	}

}
