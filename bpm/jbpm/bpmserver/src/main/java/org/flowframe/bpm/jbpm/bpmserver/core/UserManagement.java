package org.flowframe.bpm.jbpm.bpmserver.core;

import java.util.ArrayList;
import java.util.List;

public class UserManagement implements org.jboss.bpm.console.server.integration.UserManagement {

	public List<String> getActorsForGroup(String groupName) {
		// TODO: fixme
		List<String> result = new ArrayList<String>();
		
		result.add("admin");
		return result;
	}

	public List<String> getGroupsForActor(String actorId) {
		// TODO: fixme
		List<String> result = new ArrayList<String>();
		result.add("admins");
		return result;
	}

}
