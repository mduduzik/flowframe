package de.hpi.bpt.process.petri.bp.construct;

import java.util.Collection;

import de.hpi.bpt.process.petri.Node;
import de.hpi.bpt.process.petri.PetriNet;
import de.hpi.bpt.process.petri.bp.BehaviouralProfile;
import de.hpi.bpt.process.petri.bp.BehaviouralProfile.CharacteristicRelationType;
import de.hpi.bpt.process.petri.util.ConcurrencyRelation;

/**
 * Computation of the behavioural profile for a given collection of 
 * nodes (or all nodes) of a sound free-choice WF-net.
 * 
 * Soundness assumption is currently not checked!
 * 
 * Implemented as a singleton, use <code>getInstance()</code>.
 * 
 * @author matthias.weidlich
 *
 */
public class BPCreatorNet extends AbstractBPCreator {
	
	private static BPCreatorNet eInstance;
	
	public static BPCreatorNet getInstance() {
		if (eInstance == null)
			eInstance  = new BPCreatorNet();
		return eInstance;
	}
	
	private BPCreatorNet() {
		
	}

	public BehaviouralProfile deriveBehaviouralProfile(PetriNet pn) {
		return deriveBehaviouralProfile(pn, pn.getNodes());
	}
	
	public BehaviouralProfile deriveBehaviouralProfile(PetriNet pn, Collection<Node> nodes) {
		
		/*
		 * Check some of the assumptions.
		 */
		if (!pn.isExtendedFreeChoice()) throw new IllegalArgumentException();
		if (!pn.isWFNet()) throw new IllegalArgumentException();

		BehaviouralProfile profile = new BehaviouralProfile(pn,nodes);
		CharacteristicRelationType[][] matrix = profile.getMatrix();
		
		ConcurrencyRelation concurrencyRelation = new ConcurrencyRelation(pn);
		
		for(Node n1 : profile.getNodes()) {
			int index1 = profile.getNodes().indexOf(n1);
			for(Node n2 : profile.getNodes()) {
				int index2 = profile.getNodes().indexOf(n2);
				/*
				 * The matrix is symmetric. Therefore, we need to traverse only 
				 * half of the entries.
				 */
				if (index2 > index1)
					continue;
				/*
				 * What about the relation of a node to itself?
				 */
				if (index1 == index2) {
					if (pn.hasPath(n1,n2))
						matrix[index1][index1] = CharacteristicRelationType.InterleavingOrder;
					else
						matrix[index1][index1] = CharacteristicRelationType.Exclusive;
				}
				/*
				 * Check all cases for two distinct nodes of the net
				 */
				else if (pn.hasPath(n1,n2) && pn.hasPath(n2,n1)) {
					super.setMatrixEntry(matrix,index1,index2,CharacteristicRelationType.InterleavingOrder);
				}
				else if (concurrencyRelation.areConcurrent(index1,index2)) {
					super.setMatrixEntry(matrix,index1,index2,CharacteristicRelationType.InterleavingOrder);
				}
				else if (!concurrencyRelation.areConcurrent(index1,index2) && !pn.hasPath(n1,n2) && !pn.hasPath(n2,n1)) {
					super.setMatrixEntry(matrix,index1,index2,CharacteristicRelationType.Exclusive);
				}
				else if (pn.hasPath(n1,n2) && !pn.hasPath(n2,n1)) {
					super.setMatrixEntryOrder(matrix,index1,index2);
				}
				else if (pn.hasPath(n2,n1) && !pn.hasPath(n1,n2)) {
					super.setMatrixEntryOrder(matrix,index2,index1);
				}
			}
		}
		
		return profile;
	}
}
