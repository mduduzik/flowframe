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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.w3c.dom.Node;

import de.hpi.bpt.epc.EPCApplicationSystem;
import de.hpi.bpt.epc.EPCDocument;
import de.hpi.bpt.epc.EPCFunction;
import de.hpi.bpt.epc.EPCOrganization;
import de.hpi.bpt.epc.EPCOrganizationType;
import de.hpi.bpt.epc.EPCRole;
import de.hpi.bpt.epc.EPCSystem;
import de.hpi.bpt.epc.aml.util.AMLHelper;

/**
 * 
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public class AMLEPCFunction extends AMLEPCNode implements EPCFunction {
	
	private double duration;
	
	private Collection<EPCRole> roles;
	
	private Collection<EPCSystem> systems;
	
	private Collection<EPCApplicationSystem> applicationSystems;
	
	private Collection<EPCDocument> documents;
	
	private Collection<EPCOrganization> organizations;
	
	private Collection<EPCOrganizationType> organizationTypes;
	
	private boolean processInterface;
	
	private boolean aggregating;
	
	private String referencedModelId;
	
	public AMLEPCFunction(Node occ) {
		super(occ);
		this.systems = new HashSet<EPCSystem>();
		this.documents = new HashSet<EPCDocument>();
		this.organizations = new HashSet<EPCOrganization>();
		this.organizationTypes = new HashSet<EPCOrganizationType>();
		this.applicationSystems = new HashSet<EPCApplicationSystem>();
		this.roles = new HashSet<EPCRole>();
		this.processInterface = false;
		this.aggregating = false;
		this.referencedModelId = "";
	}

	public void setRoles(Collection<EPCRole> roles) {
		this.roles = roles;
	}

	public Collection<EPCRole> getRoles() {
		return this.roles;
	}
	
	public boolean addRole(EPCRole role) {
		Iterator<EPCRole> roles = this.roles.iterator();
		boolean contains = false;
		while(roles.hasNext() && !contains)
			if(roles.next().getName().equals(role.getName()))
				contains = true;
		if(!contains)
			return this.roles.add(role);
			
		return false;
	}

	public boolean removeRole(EPCRole role) {
		return roles.remove(role);		
	}
	
	public boolean hasRoles() {
		if(!roles.isEmpty())
			return true;
		
		return false;
	}

	public boolean addSystem(EPCSystem system) {
		Iterator<EPCSystem> systems = this.systems.iterator();
		boolean contains = false;
		while(systems.hasNext() && !contains)
			if(systems.next().getName().equals(system.getName()))
				contains = true;
		if(!contains)
			return this.systems.add(system);
			
		return false;
	}	
	
	public boolean removeSystem(EPCSystem system) {
		return systems.remove(system);		
	}

	public Collection<EPCSystem> getSystems() {
		return this.systems;
	}
	
	public void setSystems(Collection<EPCSystem> systems) {
		this.systems = systems;
	}
	
	public boolean hasSystems() {
		if(!systems.isEmpty())
			return true;
		
		return false;
	}

	public boolean addDocument(EPCDocument document) {
		Iterator<EPCDocument> documents = this.documents.iterator();
		boolean contains = false;
		while(documents.hasNext() && !contains)
			if(documents.next().getName().equals(document.getName()))
				contains = true;
		if(!contains)
			return this.documents.add(document);
			
		return false;
	}
	
	public boolean removeDocument(EPCDocument document) {
		return documents.remove(document);		
	}

	public Collection<EPCDocument> getDocuments() {
		return this.documents;
	}
	
	public void setDocuments(Collection<EPCDocument> documents) {
		this.documents = documents;
	}
	
	public boolean usesDocuments() {
		if(!documents.isEmpty())
			return true;
		
		return false;
	}

	public double getDuration() {
		return this.duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	public double getEffort() {
		return getDuration() * this.getAbsProbability();
	}

	public boolean isProcessInterface() {
		return processInterface;
	}

	public void setProcessInterface(boolean processInterface) {
		this.processInterface = processInterface;
		
		if(processInterface)
		{
			AMLHelper.setNodeAttributeValue(domOcc, AMLHelper.SYMBOL_NUM, AMLHelper.VAL_TYPE_NUM_PIF);
			AMLHelper.ensureOccAttributes(domOcc.getOwnerDocument(), domOcc);
		}
		else
			AMLHelper.setNodeAttributeValue(domOcc, AMLHelper.SYMBOL_NUM, AMLHelper.VAL_TYPE_NUM_FUNC);
	}

	public boolean isAggregating() {
		return aggregating;
	}

	public void setAggregating(boolean aggregating) {
		this.aggregating = aggregating;
	}

	public AMLEPCFunction clone() {
		AMLEPCFunction copy = new AMLEPCFunction(this.getDomOcc().cloneNode(true));
		copy.setAbsProbability(absProbability);
		copy.setAggregating(aggregating);
		copy.setAnnualFrequency(annualFrequency);
		copy.setCreateTimestamp(createTimestamp);
		copy.setCreator(creator);
		copy.setDescription(description);
		copy.setDocuments(documents);
		copy.setDuration(duration);
		copy.setEPC(epc);
		copy.setId(id);
		copy.setInConnections(inConnections);
		copy.setLastChange(lastChange);
		copy.setLastUser(lastUser);
		copy.setName(name);
		copy.setOutConnections(outConnections);
		copy.setProcessInterface(processInterface);
		copy.setRoles(roles);
		copy.setShortDescription(shortDescription);
		copy.setSystems(systems);
		
		return copy;
	}
	
	public String toString() {
		return this.name;
	}

	public String getReferencedModelId() {
		return referencedModelId;
	}

	public void setReferencedModelId(String referencedModelId) {
		this.referencedModelId = referencedModelId;
	}

	public boolean addOrganization(EPCOrganization organization) {
		Iterator<EPCOrganization> organizations = this.organizations.iterator();
		boolean contains = false;
		while(organizations.hasNext() && !contains)
			if(organizations.next().getName().equals(organization.getName()))
				contains = true;
		if(!contains)
			return this.organizations.add(organization);
			
		return false;
	}
	
	public boolean removeOrganization(EPCOrganization organization) {
		return organizations.remove(organization);		
	}

	public Collection<EPCOrganization> getOrganizations() {
		return this.organizations;
	}
	
	public void setOrganizations(Collection<EPCOrganization> organizations) {
		this.organizations = organizations;
	}
	
	public boolean hasOrganizations() {
		if(!organizations.isEmpty())
			return true;
		
		return false;
	}

	public boolean addOrganizationType(EPCOrganizationType organizationType) {
		Iterator<EPCOrganizationType> organizations = this.organizationTypes.iterator();
		boolean contains = false;
		while(organizations.hasNext() && !contains)
			if(organizations.next().getName().equals(organizationType.getName()))
				contains = true;
		if(!contains)
			return this.organizationTypes.add(organizationType);
			
		return false;
	}
	
	public boolean removeOrganizationType(EPCOrganizationType organizationType) {
		return organizationTypes.remove(organizationType);		
	}

	public Collection<EPCOrganizationType> getOrganizationTypes() {
		return this.organizationTypes;
	}
	
	public void setOrganizationTypes(Collection<EPCOrganizationType> organizationTypes) {
		this.organizationTypes = organizationTypes;
	}
	
	public boolean hasOrganizationTypes() {
		if(!organizationTypes.isEmpty())
			return true;
		
		return false;
	}
	
	public boolean addApplicationSystem(EPCApplicationSystem applicationSystem) {
		Iterator<EPCApplicationSystem> applicationSystems = this.applicationSystems.iterator();
		boolean contains = false;
		while(applicationSystems.hasNext() && !contains)
			if(applicationSystems.next().getName().equals(applicationSystem.getName()))
				contains = true;
		if(!contains)
			return this.applicationSystems.add(applicationSystem);
			
		return false;
	}
	
	public boolean removeApplicationSystem(EPCApplicationSystem applicationSystem) {
		return applicationSystems.remove(applicationSystem);		
	}

	public Collection<EPCApplicationSystem> getApplicationSystems() {
		return this.applicationSystems;
	}
	
	public void setApplicationSystems(Collection<EPCApplicationSystem> applicationSystems) {
		this.applicationSystems = applicationSystems;
	}
	
	public boolean hasApplicationSystems() {
		if(!applicationSystems.isEmpty())
			return true;
		
		return false;
	}
	
}