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

import de.hpi.bpt.epc.EPC;
import de.hpi.bpt.epc.EPCCxn;
import de.hpi.bpt.epc.EPCFunction;
import de.hpi.bpt.epc.EPCNode;
import de.hpi.bpt.epc.EPCPath;

/**
 * 
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public class SkeletonPathBuilder {

	private EPC epc;
	private Collection<EPCPath> skeletonPaths;
	private EPCPath mdPath;
	
	public SkeletonPathBuilder(EPC epc){
		this.epc = epc;
		this.skeletonPaths = new HashSet<EPCPath>();
	}
	
	
	/**
	 * Build all the paths for the given EPC
	 */
	public void buildSkeletonPaths() {

		this.skeletonPaths.clear();
		
		Iterator<EPCNode> i = this.epc.getStartNodes().iterator();
	
		while(i.hasNext()) {
			EPCPath path = new AMLEPCPath();
			path.augment(i.next());
			augmentSkeletonPath(path);
		}
	}

	private void augmentSkeletonPath(EPCPath path) {
		EPCCxn cxn = path.getLastNode().getOutConnections().iterator().next();
		while(cxn.getTarget().getOutConnections() != null && cxn.getTarget().getOutConnections().size() == 1)
			cxn = cxn.getTarget().getOutConnections().iterator().next();
		if(cxn.getTarget().getOutConnections().isEmpty()) {
			skeletonPaths.add(path);
			return;
		}
		else {
			Iterator<EPCCxn> cxns = cxn.getTarget().getOutConnections().iterator();
			while(cxns.hasNext()) {
				EPCPath augmentedPath = new AMLEPCPath(path);
				augmentedPath.augment(cxns.next().getTarget());
				augmentSkeletonPath(augmentedPath);
			}
		}
	}

	public EPCPath getMaxDurationPath() {
		Iterator<EPCNode> i = this.epc.getStartNodes().iterator();
		while(i.hasNext()) {	
			EPCPath path = new AMLEPCPath();
			path.augment(i.next());
			findMaxDurationPath(path);
		}
		return mdPath;
	}
	
	private void findMaxDurationPath(EPCPath path) {
		EPCCxn cxn = path.getLastNode().getOutConnections().iterator().next();
		while(cxn.getTarget().getOutConnections() != null && cxn.getTarget().getOutConnections().size() == 1) {
			if(cxn.getTarget() instanceof EPCFunction)
				path.setDuration(path.getDuration() + ((EPCFunction) cxn.getTarget()).getDuration());
			cxn = cxn.getTarget().getOutConnections().iterator().next();
		}
			
		if(cxn.getTarget().getOutConnections().isEmpty()) {
			if(mdPath == null || path.getDuration() > mdPath.getDuration())
				mdPath = path;
			return;
		}
		else {
			Iterator<EPCCxn> cxns = cxn.getTarget().getOutConnections().iterator();
			while(cxns.hasNext()) {
				EPCPath augmentedPath = new AMLEPCPath(path);
				augmentedPath.augment(cxns.next().getTarget());
				findMaxDurationPath(augmentedPath);
			}
		}
	}

	public EPCPath getMaxEffortPath() {
		Iterator<EPCNode> i = this.epc.getStartNodes().iterator();
		while(i.hasNext()) {
			EPCPath path = new AMLEPCPath();
			path.augment(i.next());
			findMaxEffortPath(path);
		}
		
		return mdPath;
	}
	
	private void findMaxEffortPath(EPCPath path) {
		EPCCxn cxn = path.getLastNode().getOutConnections().iterator().next();
		while(cxn.getTarget().getOutConnections() != null && cxn.getTarget().getOutConnections().size() == 1) {
			if(cxn.getTarget() instanceof EPCFunction)
				path.setEffort(path.getEffort() + ((EPCFunction) cxn.getTarget()).getEffort());
			cxn = cxn.getTarget().getOutConnections().iterator().next();
		}
			
		if(cxn.getTarget().getOutConnections().isEmpty()) {
			if(mdPath == null || path.getEffort() > mdPath.getEffort())
				mdPath = path;
			return;
		}
		else {
			Iterator<EPCCxn> cxns = cxn.getTarget().getOutConnections().iterator();
			while(cxns.hasNext()) {
				EPCPath augmentedPath = new AMLEPCPath(path);
				augmentedPath.augment(cxns.next().getTarget());
				findMaxEffortPath(augmentedPath);
			}
		}
	}

	public static EPCPath getPathFromSkeleton(EPCPath skeleton) {
		if(!skeleton.isEmpty()){
			EPCPath path = new AMLEPCPath();
			for(int i = 0; i < skeleton.getLength(); i ++) {
				EPCNode node = skeleton.getNodeAt(i);
				while(node.getOutConnections().size() == 1) {
					path.augment(node);
					if(!node.isEnd())
						node = node.getChildren().iterator().next();
				}
				path.augment(node);
			}
			return path;
		}else
			return null;
	}
	
	public EPC getEpc() {
		return epc;
	}

	public void setEpc(EPC epc) {
		this.epc = epc;
	}

}