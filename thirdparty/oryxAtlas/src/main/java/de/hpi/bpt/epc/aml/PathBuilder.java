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

import de.hpi.bpt.epc.EPC;
import de.hpi.bpt.epc.EPCCxn;
import de.hpi.bpt.epc.EPCFunction;
import de.hpi.bpt.epc.EPCNode;
import de.hpi.bpt.epc.EPCPath;

/**
 * TODO Only XOR processes so far
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public class PathBuilder {
	
	private EPC epc;
	
	private List<List <EPCNode>> paths;
	
	private List<Double> pathsAbsEfforts;
	
	private List<Double> pathsDurations;
	
	private List<Double> pathMinAbsProbWidth;
	
	private EPCPath mdPath;
	
	public PathBuilder(EPC epc) {
		this.epc = epc;
		this.paths = new ArrayList<List <EPCNode>>();
		this.pathsDurations = new ArrayList<Double>();
		this.pathsAbsEfforts = new ArrayList<Double>();
		this.pathMinAbsProbWidth = new ArrayList<Double>();
	}
	
	public void buildAllPaths() {
		this.buildAllPaths(false);
	}
	
	/**
	 * Build all the paths for the given EPC
	 */
	public void buildAllPaths(boolean calculateStatistics) {
		this.paths.clear();
		this.pathsDurations.clear();
		this.pathsAbsEfforts.clear();
		this.pathMinAbsProbWidth.clear();
		
		Iterator<EPCNode> i = this.epc.getStartNodes().iterator();
		while(i.hasNext()) {
			List<EPCNode> path = new ArrayList<EPCNode>();
			path.add(i.next());
			augmentPath(path);
		}
		
		if(calculateStatistics){
			this.findPathsDurations();
			this.findPathsAbsEfforts();
			this.findPathMinAbsProbWidth();
		}
	}
	
	private void augmentPath(List<EPCNode> path) {
		int pathLength = path.size();
		
		if (path.get(pathLength - 1).isEnd()) {
			paths.add(path);
			return;
		}
		else {
			Iterator<EPCCxn> i = path.get(pathLength - 1).getOutConnections().iterator();
			
			while(i.hasNext()) {
				EPCCxn cxn = i.next();
				List<EPCNode> augmentedPath = new ArrayList<EPCNode>(path);
				augmentedPath.add(cxn.getTarget());
				augmentPath(augmentedPath);
			}
		}
	}

	public EPCPath getMDPath(boolean calculateStatistics) {
		
		Iterator<EPCNode> i = this.epc.getStartNodes().iterator();
		mdPath = null;
		while(i.hasNext()) {
			EPCPath path = new AMLEPCPath();
			path.augment(i.next());
			findMDPath(path);
		}
		if(calculateStatistics) {
			this.findPathsDurations();
			this.findPathsAbsEfforts();
			this.findPathMinAbsProbWidth();
		}

		return mdPath;
	}
	
	private void findMDPath(EPCPath path) {
		int pathLength = path.getNodes().size();
		
		if (path.getNodes().get(pathLength - 1).isEnd()) {
			if(mdPath == null || path.getDuration() > mdPath.getDuration())
				mdPath = path;

			return;
		} else {
			Iterator<EPCCxn> i = path.getNodes().get(pathLength - 1).getOutConnections().iterator();
			while(i.hasNext()) {
				EPCCxn cxn = i.next();
				EPCPath augmentedPath = new AMLEPCPath(path);
				augmentedPath.augment(cxn.getTarget());
				findMDPath(augmentedPath);
			}
		}
	}
	
	/**
	 * Update path absolute efforts
	 */
	public void findPathsAbsEfforts() {
		pathsAbsEfforts.clear();
		
		for (int i = 0; i < paths.size(); i ++) {
			double absEffort = 0.0;
			for (int j = 0; j < paths.get(i).size(); j ++)
				if(paths.get(i).get(j) instanceof EPCFunction)
					absEffort += ((EPCFunction) paths.get(i).get(j)).getEffort();
			
			pathsAbsEfforts.add(absEffort);
		}
	}
	
	/**
	 * Update path absolute efforts
	 */
	public void findPathsDurations() {
		pathsDurations.clear();
		
		for (int i = 0; i < paths.size(); i ++) {
			double duration = 0.0;
			for (int j = 0; j < paths.get(i).size(); j ++)
				if(paths.get(i).get(j) instanceof EPCFunction)
					duration += ((EPCFunction) paths.get(i).get(j)).getDuration();

			pathsDurations.add(duration);
		}
	}
	
	public void findPathMinAbsProbWidth() {
		pathMinAbsProbWidth.clear();
		
		for (int i = 0; i < paths.size(); i ++) {
			double minWidth = Double.MAX_VALUE;
			for (int j = 0; j < paths.get(i).size(); j ++) {
				double absP = ((EPCNode) paths.get(i).get(j)).getAbsProbability();
				if (absP < minWidth)
					minWidth = absP;
			}
			
			System.out.println(minWidth);
			pathMinAbsProbWidth.add(minWidth);
		}
	}
	
	public EPC getEpc() {
		return epc;
	}

	public void setEpc(EPC epc) {
		this.epc = epc;
	}

	public List<List<EPCNode>> getPaths() {
		return paths;
	}

	public void setPaths(List<List<EPCNode>> paths) {
		this.paths = paths;
	}

	public List<Double> getPathsAbsEfforts() {
		return pathsAbsEfforts;
	}

	public void setPathsAbsEfforts(List<Double> pathsAbsEfforts) {
		this.pathsAbsEfforts = pathsAbsEfforts;
	}

	public List<Double> getPathsDurations() {
		return pathsDurations;
	}

	public void setPathsDurations(List<Double> pathsDurations) {
		this.pathsDurations = pathsDurations;
	}

	public List<Double> getPathMinAbsProbWidth() {
		return pathMinAbsProbWidth;
	}

	public void setPathMinAbsProbWidth(List<Double> pathMinAbsProbWidth) {
		this.pathMinAbsProbWidth = pathMinAbsProbWidth;
	}

}