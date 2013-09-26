/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.hpi.nunet;

import de.hpi.petrinet.FlowRelationship;
import de.hpi.petrinet.NodeImpl;

import java.util.ArrayList;
import java.util.List;

public class Node extends NodeImpl {

	private String id;
	private ArrayList<FlowRelationship> incomingFlowRelationships;
	private List<FlowRelationship> outgoingFlowRelationships;
	private ProcessModel processModel;
	private String label;

	public String getId() {
		return id;
	}

	public void setId(String label) {
		this.id = label;
	}

	public List<FlowRelationship> getIncomingFlowRelationships() {
		if (incomingFlowRelationships == null)
			incomingFlowRelationships = new ArrayList<FlowRelationship>();
		return incomingFlowRelationships;
	}

	public List<FlowRelationship> getOutgoingFlowRelationships() {
		if (outgoingFlowRelationships == null)
			outgoingFlowRelationships = new ArrayList<FlowRelationship>();
		return outgoingFlowRelationships;
	}

	public ProcessModel getProcessModel() {
		return processModel;
	}

	public void setProcessModel(ProcessModel value) {
		processModel = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String value) {
		label = value;
	}
	
	public String toString() {
		return getLabel();
	}

} // Node