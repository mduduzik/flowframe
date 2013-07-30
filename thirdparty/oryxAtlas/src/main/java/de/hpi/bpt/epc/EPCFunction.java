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

package de.hpi.bpt.epc;

import java.util.Collection;

/**
 * 
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public interface EPCFunction extends EPCNode{

	public boolean isProcessInterface();
	
	public void setProcessInterface(boolean processInterface);
	
	public void setRoles(Collection<EPCRole> role);

	public Collection<EPCRole> getRoles();
	
	public boolean addRole(EPCRole role);
	
	public boolean removeRole(EPCRole role);
		
	public boolean hasRoles();
	
	public void setSystems(Collection<EPCSystem> systems);

	public Collection<EPCSystem> getSystems();

	public boolean addSystem(EPCSystem system);
	
	public boolean removeSystem(EPCSystem system);
	
	public boolean hasSystems();

	public void setDocuments(Collection<EPCDocument> documents);

	public Collection<EPCDocument> getDocuments();

	public boolean addDocument(EPCDocument document);
	
	public boolean removeDocument(EPCDocument document);
	
	public boolean usesDocuments();

	public void setOrganizations(Collection<EPCOrganization> organizations);

	public Collection<EPCOrganization> getOrganizations();

	public boolean addOrganization(EPCOrganization organization);
	
	public boolean removeOrganization(EPCOrganization organization);
	
	public boolean hasOrganizations();

	public void setOrganizationTypes(Collection<EPCOrganizationType> organizationTypes);

	public Collection<EPCOrganizationType> getOrganizationTypes();

	public boolean addOrganizationType(EPCOrganizationType organizationType);
	
	public boolean removeOrganizationType(EPCOrganizationType organizationType);
	
	public boolean hasOrganizationTypes();
	
	public void setApplicationSystems(Collection<EPCApplicationSystem> applicationSystems);

	public Collection<EPCApplicationSystem> getApplicationSystems();

	public boolean addApplicationSystem(EPCApplicationSystem applicationSystem);
	
	public boolean removeApplicationSystem(EPCApplicationSystem applicationSystem);
	
	public boolean hasApplicationSystems();
	
	public double getEffort();
	
	public double getDuration();
	
	public void setDuration(double duration);
	
	public boolean isAggregating();
	
	public void setAggregating(boolean aggregating);

}