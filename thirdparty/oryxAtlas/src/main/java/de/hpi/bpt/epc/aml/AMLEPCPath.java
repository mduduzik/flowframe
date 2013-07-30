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
import java.util.Iterator;
import java.util.List;

import de.hpi.bpt.epc.EPCFunction;
import de.hpi.bpt.epc.EPCNode;
import de.hpi.bpt.epc.EPCPath;

/**
 * 
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public class AMLEPCPath implements EPCPath {

	private List<EPCNode> nodes;
	
	private double effort;
	
	private double duration;
	
	public AMLEPCPath() {
		nodes = new ArrayList<EPCNode>();
		effort = 0;
		duration = 0;
	}

	public AMLEPCPath(EPCPath path) {
		nodes = new ArrayList<EPCNode>(path.getNodes());
		duration = path.getDuration();
	}
	
	public boolean augment(EPCNode node) {
		if(node instanceof EPCFunction) {
			duration += ((EPCFunction) node).getDuration();
			effort += ((EPCFunction) node).getEffort();
		}
			
		return nodes.add(node);
	}

	public double getDuration() {
		return this.duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	public List<EPCNode> getNodes(){
		return this.nodes;
	}

	public EPCNode getNodeAt(int i) {
		if(this.nodes == null)
			return null;
		else
			return nodes.get(i);
	}

	public EPCNode getFirstNode() {
		if(this.nodes == null)
			return null;
		else
			return nodes.get(0);
	}

	public EPCNode getLastNode() {
		if(this.nodes == null)
			return null;
		else
			return nodes.get(nodes.size() - 1);
	}

	public int getLength() {
		if(nodes == null)
			return 0;
		return nodes.size();
	}

	public boolean isEmpty() {
		if(nodes == null)
			return true;
		return nodes.isEmpty(); 
	}

	public double getEffort() {
		return this.effort;
	}

	public void setEffort(double effort) {
		this.effort = effort;	
	}

	public List<EPCFunction> getFunctions() {
		List<EPCFunction> functions = new ArrayList<EPCFunction>();
		Iterator<EPCNode> pathNodes = nodes.iterator();
		while(pathNodes.hasNext()) {
			EPCNode node = pathNodes.next();
			if(node instanceof EPCFunction)
				functions.add((EPCFunction)node);
		}
		
		return functions;
	}

	public boolean contains(EPCNode node) {
		return nodes.contains(node);
	}

}