package de.hpi.bpel2bpmn.mapping.basic;

import org.w3c.dom.Node;

import de.hpi.bpel2bpmn.mapping.MappingContext;
import de.hpi.bpel2bpmn.util.BPEL2BPMNMappingUtil;
import de.hpi.bpmn.IntermediateErrorEvent;

/**
 * Please note that the throw activity always maps to an intermediate
 * event first. However, it might this event might later be replaced
 * by an end event in the course of a post processing of the model.
 * 
 * @author matthias.weidlich
 *
 */
public class ThrowMapping extends BasicActivityMapping {
	
	static private ThrowMapping instance = null;
	
	static public ThrowMapping getInstance() {
		if(null == instance) {
			instance = new ThrowMapping();
		}
		return instance;
	}
	
	public void mapElement(Node node, MappingContext mappingContext) {

		String name = BPEL2BPMNMappingUtil.getRealNameOfNode(node);
		
		IntermediateErrorEvent event = mappingContext.getFactory().createIntermediateErrorEvent();
		event.setLabel(name);
		
		Node errorNode = node.getAttributes().getNamedItem("faultName");
		if (errorNode != null) {
			event.setErrorCode(errorNode.getTextContent());
		}

		setConnectionPointsWithControlLinks(node, event, event, null, mappingContext);
		
		mappingContext.addMappingElementToSet(node,event);

	}

}
