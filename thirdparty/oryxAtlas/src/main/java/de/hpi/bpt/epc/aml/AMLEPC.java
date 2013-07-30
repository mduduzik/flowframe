/*
 * Copyright (c) 2009 Sergey Smirnov, Artem Polyvyanyy
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE. 
 */

package de.hpi.bpt.epc.aml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.hpi.bpt.epc.EPC;
import de.hpi.bpt.epc.EPCApplicationSystem;
import de.hpi.bpt.epc.EPCConnector;
import de.hpi.bpt.epc.EPCCxn;
import de.hpi.bpt.epc.EPCDocument;
import de.hpi.bpt.epc.EPCEvent;
import de.hpi.bpt.epc.EPCFunction;
import de.hpi.bpt.epc.EPCNode;
import de.hpi.bpt.epc.EPCObject;
import de.hpi.bpt.epc.EPCOrganization;
import de.hpi.bpt.epc.EPCOrganizationType;
import de.hpi.bpt.epc.EPCRole;
import de.hpi.bpt.epc.EPCSystem;

/**
 * 
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public class AMLEPC implements EPC {
	
	/*
	 * Model title
	 */
	private String title;
	
	/*
	 * Id of the model (id of the model occurrence in the AML document)
	 */
	private String modelId;
	
	/*
	 * The hierarchy of groups to which the model belongs
	 */
	private List<String> groups;
	
	/*
	 * functions, events, connectors (flow related nodes) of an EPC
	 */
	private HashMap<String, EPCNode> nodes;
	
	/*
	 * Connections of an EPC
	 */
	private HashMap<String, EPCCxn> cxns;
	
	/*
	 * Roles
	 */
	private HashMap<String, EPCRole> roles;
	
	/*
	 * Organizations
	 */
	private HashMap<String, EPCOrganization> organizations;
	
	/*
	 * Organization types
	 */
	private HashMap<String, EPCOrganizationType> organizationTypes;
	
	/*
	 * Systems
	 */
	private HashMap<String, EPCSystem> systems;
	
	/*
	 * Systems
	 */
	private HashMap<String, EPCApplicationSystem> applicationSystems;
	
	/*
	 * Documents
	 */
	private HashMap<String, EPCDocument> documents;
	
	private AMLMetaInfo metaInfo;

	public AMLEPC() {
		nodes = new HashMap<String, EPCNode>();
		cxns = new HashMap<String, EPCCxn>();
		roles = new HashMap<String, EPCRole>();
		organizations = new HashMap<String, EPCOrganization>();
		organizationTypes = new HashMap<String, EPCOrganizationType>();
		systems = new HashMap<String, EPCSystem>();
		applicationSystems = new HashMap<String, EPCApplicationSystem>();
		documents = new HashMap<String, EPCDocument>();
		groups = new ArrayList<String>();
		metaInfo = new AMLMetaInfo();
	}

	/**
	 * Get graph start nodes
	 * 
	 * @return Collection of graph start nodes
	 */
	public Collection<EPCNode> getStartNodes() {
		ArrayList<EPCNode> res = new ArrayList<EPCNode>();

		Iterator<EPCNode> i = nodes.values().iterator();
		while (i.hasNext()) {
			EPCNode node = i.next();
			if (node.isStart())
				res.add(node);
		}

		return res;
	}

	/**
	 * Get graph end nodes
	 * 
	 * @return Collection of graph end nodes
	 */
	public Collection<EPCNode> getEndNodes() {
		ArrayList<EPCNode> res = new ArrayList<EPCNode>();

		Iterator<EPCNode> i = nodes.values().iterator();
		while (i.hasNext()) {
			EPCNode node = i.next();
			if (node.isEnd())
				res.add(node);
		}

		return res;
	}

	public Collection<EPCNode> getNodes() {
		return nodes.values();
	}

	public Collection<EPCCxn> getCxns() {
		return cxns.values();
	}

	public Collection<EPCFunction> getFunctions() {
		ArrayList<EPCFunction> funcs = new ArrayList<EPCFunction>();

		Iterator<EPCNode> i = getNodes().iterator();
		while (i.hasNext()) {
			EPCNode node = i.next();
			if (node instanceof EPCFunction)
				funcs.add((EPCFunction) node);
		}

		return funcs;
	}

	public Collection<EPCEvent> getEvents() {
		ArrayList<EPCEvent> events = new ArrayList<EPCEvent>();

		Iterator<EPCNode> i = getNodes().iterator();
		while (i.hasNext()) {
			EPCNode node = i.next();
			if (node instanceof EPCEvent)
				events.add((EPCEvent) node);
		}

		return events;
	}

	public Collection<EPCConnector> getConnectors() {
		ArrayList<EPCConnector> connectors = new ArrayList<EPCConnector>();

		Iterator<EPCNode> i = getNodes().iterator();
		while (i.hasNext()) {
			EPCNode node = i.next();
			if (node instanceof EPCConnector)
				connectors.add((EPCConnector) node);
		}

		return connectors;
	}

	public EPCObject removeObject(EPCObject object) {
		if (object instanceof EPCNode) {
			return removeNode((EPCNode) object);
		} else if (object instanceof EPCCxn) {
			return removeCxn((EPCCxn) object);
		} else if (object instanceof EPCSystem) {
			return removeSystem((EPCSystem) object);
		}

		return null;
	}

	public EPCNode removeNode(EPCNode node) {
		if (node instanceof EPCFunction) {
			EPCFunction function = (EPCFunction) node;
			
			if (function.hasRoles()) {
				Iterator<EPCRole> roles = function.getRoles().iterator();
				while (roles.hasNext())
					removeRole(roles.next());
			}
			if (function.hasSystems()) {
				Iterator<EPCSystem> systems = function.getSystems().iterator();
				while (systems.hasNext())
					removeSystem(systems.next());
			}
			if (function.usesDocuments()) {
				Iterator<EPCDocument> documents = function.getDocuments().iterator();
				while (documents.hasNext())
					removeDocument(documents.next());
			}
		}

		return nodes.remove(node.getId());
	}

	public EPCCxn removeCxn(EPCCxn cxn) {
		cxn.getSource().getOutConnections().remove(cxn);
		cxn.getTarget().getInConnections().remove(cxn);
		return cxns.remove(cxn.getId());
	}

	private EPCSystem removeSystem(EPCSystem system) {
		return systems.remove(system.getId());
	}

	private EPCDocument removeDocument(EPCDocument document) {
		return documents.remove(document.getId());
	}

	private EPCRole removeRole(EPCRole role) {
		return roles.remove(role.getId());
	}

	public EPCNode addNode(EPCNode node) {
		EPCNode node1 = nodes.put(node.getId(), node);
		if(node1 == null)
			return node;
		return node1;
	}

	public EPCCxn addCxn(EPCCxn cxn) {
		EPCCxn cxn1 = cxns.put(cxn.getId(), cxn);
		if(cxn1 == null)
			return cxn;
		return cxn1;
	}

	public EPCObject addObject(EPCObject object) {
		if(object instanceof EPCCxn)
			return addCxn((EPCCxn) object);
		if(object instanceof EPCNode)
			return addNode((EPCNode) object);
		return null;
	}
	
	/**
	 * Get EPC effort without recalculations
	 * @return
	 */
	public double getProcessAvgEffort() {
		double res = 0.0;

		Collection<EPCFunction> fs = this.getFunctions();
		Iterator<EPCFunction> i = fs.iterator();
		while (i.hasNext()) {
			EPCFunction f = i.next();
				res += f.getEffort();
		}

		return res;
	}

	public Collection<EPCRole> getRoles() {
		return roles.values();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setNodeMap(HashMap<String, EPCNode> nodes) {
		this.nodes = nodes;
	}

	public void setCxnMap(HashMap<String, EPCCxn> cxns) {
		this.cxns = cxns;
	}

	public HashMap<String, EPCCxn> getCxnMap() {
		return this.cxns;
	}

	public void setRoleMap(HashMap<String, EPCRole> roles) {
		this.roles = roles;
	}

	public HashMap<String, EPCRole> getRoleMap() {
		return this.roles;
	}

	public void setSystemMap(HashMap<String, EPCSystem> systems) {
		this.systems = systems;
	}

	public HashMap<String, EPCSystem> getSystemMap() {
		return this.systems;
	}

	public void setApplicationSystemMap(HashMap<String, EPCApplicationSystem> applicationSystems) {
		this.applicationSystems = applicationSystems;
	}

	public HashMap<String, EPCApplicationSystem> getApplicationSystemMap() {
		return this.applicationSystems;
	}

	
	public void setDocumentMap(HashMap<String, EPCDocument> documents) {
		this.documents = documents;
	}

	public HashMap<String, EPCDocument> getDocumentMap() {
		return this.documents;
	}

	public EPCNode getNode(String id) {
		return this.nodes.get(id);
	}

	public EPCCxn getCxn(String id) {
		return this.cxns.get(id);
	}

	public EPCSystem getSystem(String id) {
		return this.systems.get(id);
	}

	public EPCApplicationSystem getApplicationSystem(String id) {
		return this.applicationSystems.get(id);
	}
	
	public EPCDocument getDocument(String id) {
		return this.documents.get(id);
	}

	public EPCRole getRole(String id) {
		return this.roles.get(id);
	}

	public EPCOrganization getOrganization(String id) {
		return this.organizations.get(id);
	}

	public EPCOrganizationType getOrganizationType(String id) {
		return this.organizationTypes.get(id);
	}
	
	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public AMLMetaInfo getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(AMLMetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}

	public HashMap<String, EPCOrganization> getOrganizationMap() {
		return organizations;
	}

	public void setOrganizationMap(HashMap<String, EPCOrganization> organizations) {
		this.organizations = organizations;
	}

	public void setOrganizationTypeMap(HashMap<String, EPCOrganizationType> organizationTypes) {
		this.organizationTypes = organizationTypes;
	}

	public HashMap<String, EPCOrganizationType> getOrganizationTypeMap() {
		return organizationTypes;
	}
	
}