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
public interface EPCNode extends EPCObject{

	public String getName();

	public void setName(String name);

	public boolean isStart();

	public boolean isEnd();
	
	public boolean hasParents();
	
	public boolean hasChildren();

	public Collection<EPCCxn> getInConnections();

	public void setInConnections(Collection<EPCCxn> inEdges);

	public Collection<EPCCxn> getOutConnections();

	public void setOutConnections(Collection<EPCCxn> outEdges);

	public Collection<EPCNode> getParents();

	public Collection<EPCNode> getChildren();
	
	public double getAbsProbability();
	
	public void setAbsProbability(double probability);
	
	public int getAnnualFrequency();

	public void setAnnualFrequency(int annualFrequency);
	
	public boolean isEvent();
	
	public boolean isFunction();
	
	public boolean isConnector();
	
	public EPCNode getFirstParent();
	
	public EPCNode getFirstChild();
	
	public EPCCxn getFirstOutConnection();
	
	public EPCCxn getFirstInConnection();

}