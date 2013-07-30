/**
 * Copyright (c) 2008 Artem Polyvyanyy
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.hpi.bpt.process.fpg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import de.hpi.bpt.process.petri.PetriNet;
import de.hpi.bpt.process.petri.Place;
import de.hpi.bpt.process.petri.Transition;

public class FPGtoPetriNet {
	public static PetriNet transform(FPG fpg) {
		int p = 0;
		PetriNet pn = new PetriNet();
		
		Collection<Edge> es = fpg.getEdges();
		Collection<Edge> ess = fpg.getEdgesWithSources(new ArrayList<Activity>());
		es.removeAll(ess);
		
		// map start edges
		Iterator<Edge> i = ess.iterator();
		while (i.hasNext())
			p = transformStartEdge(pn,i.next(),p);
		
		// map edges
		i = es.iterator();
		while (i.hasNext())
			p = transformEdge(pn, i.next(),p);
		
		// put end places
		Iterator<Transition> it = pn.getTransitions().iterator();
		while (it.hasNext()) {
			Transition t = it.next();
			if (pn.getSuccessors(t).size()==0) {
				Place place = new Place("P" + (++p));
				//pn.addFlow(new FlowRelation(t,place));
			}
		}
		
		return pn;
	}
	
	public static int transformStartEdge(PetriNet pn, Edge e, int p) {
		Collection<Activity> as = e.getTargetVertices();
		Iterator<Activity> j = as.iterator();
		
		switch (e.getType()) {
			case XOR:
				Place place = new Place("P" + (++p));
				pn.putToken(place);
				while (j.hasNext()) {
					Activity a = j.next();
					
					Transition t = new Transition(a.getName(),a.getDescription());
					//pn.addFlow(new FlowRelation(place,t));
				}
				break;
			case AND:
				while (j.hasNext()) {
					Activity a = j.next();
					
					Transition t = new Transition(a.getName(),a.getDescription());
					Place place2 = new Place("P" + (++p));
					pn.putToken(place2);
					
					//pn.addFlow(new FlowRelation(place2,t));
				}
				break;
		}
		
		return p;
	}
	
	public static int transformEdge(PetriNet pn, Edge e, int p) {
		Collection<Transition> ts = new ArrayList<Transition>();
		Collection<Transition> tt = new ArrayList<Transition>();
			
		Collection<Activity> as = e.getSourceVertices();
		Collection<Activity> at = e.getTargetVertices();
			
		Iterator<Activity> j = as.iterator();
		while (j.hasNext()) {
			Activity a = j.next();
			Transition t = new Transition(a.getName(),a.getDescription());
			ts.add(t);
		}
			
		j = at.iterator();
		while (j.hasNext()) {
			Activity a = j.next();
			Transition t = new Transition(a.getName(),a.getDescription());
			tt.add(t);
		}
		
		switch (e.getType()) {
			case XOR:
				Iterator<Transition> j1 = ts.iterator();
				while (j1.hasNext()) {
						Transition t1 = j1.next();
						Iterator<Transition> j2 = tt.iterator();
						Place place = new Place("P" + (++p));
						//pn.addFlow(new FlowRelation(t1,place));
						while (j2.hasNext()) {
							Transition t2 = j2.next();
							//pn.addFlow(new FlowRelation(place,t2));
							
							/*Set<Node> ps = pn.getPredecessors(t2);
							Iterator<Node> j3 = ps.iterator();
							while (j3.hasNext()) {
								Node n = j3.next();
								if (n instanceof Place) {
									Place pp = (Place) n;
									pn.addFlow(new FlowRelation(t2,pp));
								}
							}*/
						}
					}
			break;
			case AND:
				Iterator<Transition> i1 = ts.iterator();
				while (i1.hasNext()) {
						Transition t1 = i1.next();
						Iterator<Transition> i2 = tt.iterator();
						while (i2.hasNext()) {
							Transition t2 = i2.next();
							Place place = new Place("P" + (++p));
							
							//pn.addFlow(new FlowRelation(t1,place));
							//pn.addFlow(new FlowRelation(place,t2));
							
							// TODO!!!
							/*Set<Node> ps = pn.getPredecessors(t2);
							Iterator<Node> i3 = ps.iterator();
							while (i3.hasNext()) {
								Node n = i3.next();
								if (n instanceof Place) {
									Place pp = (Place) n;
									pn.addFlow(new FlowRelation(t1,pp));
								}
							}*/
						}
					}	
			break;
		}
		
		return p;
	}
}
