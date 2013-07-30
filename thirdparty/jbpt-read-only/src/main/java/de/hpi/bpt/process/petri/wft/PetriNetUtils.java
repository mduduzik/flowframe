package de.hpi.bpt.process.petri.wft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import de.hpi.bpt.process.petri.Flow;
import de.hpi.bpt.process.petri.Node;
import de.hpi.bpt.process.petri.PetriNet;
import de.hpi.bpt.process.petri.Place;
import de.hpi.bpt.process.petri.Transition;

public class PetriNetUtils {
	
	public static void isolateTransitions(PetriNet net) {
		Collection<Transition> ts = new ArrayList<Transition>(net.getTransitions());
		Iterator<Transition> transitions = ts.iterator();
		while (transitions.hasNext()) {
			Transition transition = transitions.next();
			
			if (net.getPredecessors(transition).size() > 1) {
				Place newP = addPlace(net);
				Transition newT = addTransition(net);
				relinkIncomingArcs(net, transition, newT);

				net.addFlow(newT, newP);
				net.addFlow(newP, transition);
			}
			if (net.getSuccessors(transition).size()>1) {
				Place newP = addPlace(net);
				Transition newT = addTransition(net);
				relinkOutgoingArcs(net, transition, newT);

				net.addFlow(transition, newP);
				net.addFlow(newP, newT);
			}
		}
	}
	
	public static void splitMixedPlaces(PetriNet net) {
		// perform node splitting (places)
		Collection<Place> ps = new ArrayList<Place>(net.getPlaces());
		Iterator<Place> places = ps.iterator();
		while (places.hasNext()) {
			Place place = places.next();
			
			if (net.getPredecessors(place).size() > 1 && net.getSuccessors(place).size()>1) {
				Place newP = addPlace(net);
				Transition newT = addTransition(net);
				relinkOutgoingArcs(net, place, newP);
				net.addFlow(place, newT);
				net.addFlow(newT, newP);
			}
		}
	}
	
	/**
	 * Get random Id
	 * @return random Id
	 */
	public static String getId() {
		return UUID.randomUUID().toString();
	}
	
	public static Transition addTransition(PetriNet net) {
		Transition newT = new Transition();
		newT.setId(getId());
		net.addNode(newT);
		return newT;
	}
	
	public static Place addPlace(PetriNet net) {
		Place newP = new Place();
		newP.setId(getId());
		net.addNode(newP);
		return newP;
	}
	
	public static void relinkIncomingArcs(PetriNet net, Node from, Node to) {
		for (Flow f : net.getFlowRelation()) {
			if (f.getTarget().equals(from)) {
				net.addFlow(f.getSource(), to);
				net.removeEdge(f);
			}
		}
	}
	
	public static void relinkOutgoingArcs(PetriNet net, Node from, Node to) {
		for (Flow f : net.getFlowRelation()) {
			if (f.getSource().equals(from)) {
				net.addFlow(to, f.getTarget());
				net.removeEdge(f);
			}
		}
	}
	
}
