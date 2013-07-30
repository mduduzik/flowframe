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

import org.w3c.dom.Node;

import de.hpi.bpt.epc.EPCRole;
import de.hpi.bpt.epc.aml.AMLEPCNode;

/**
 * 
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public class AMLEPCRole extends AMLEPCNode implements EPCRole {
	
	private AMLEPCCxn cxn;
	
	public AMLEPCRole(Node occ) {
		super(occ);
	}
	
	public AMLEPCCxn getCxn() {
		return cxn;
	}

	public void setCxn(AMLEPCCxn cxn) {
		this.cxn = cxn;
	}

	public AMLEPCRole clone() {
		AMLEPCRole copy = new AMLEPCRole(this.getDomOcc().cloneNode(true));
		copy.setAbsProbability(absProbability);
		copy.setAnnualFrequency(annualFrequency);
		copy.setCreateTimestamp(createTimestamp);
		copy.setCreator(creator);
		copy.setCxn(cxn);
		copy.setDescription(description);
		copy.setEPC(epc);
		copy.setId(id);
		copy.setInConnections(inConnections);
		copy.setLastChange(lastChange);
		copy.setLastUser(lastUser);
		copy.setName(name);
		copy.setOutConnections(outConnections);
		copy.setShortDescription(shortDescription);
		
		return copy;
	}
}