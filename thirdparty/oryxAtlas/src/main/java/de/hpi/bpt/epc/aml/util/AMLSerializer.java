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

package de.hpi.bpt.epc.aml.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

import de.hpi.bpt.Util;
import de.hpi.bpt.epc.EPC;
import de.hpi.bpt.epc.EPCConnector;
import de.hpi.bpt.epc.EPCCxn;
import de.hpi.bpt.epc.EPCDocument;
import de.hpi.bpt.epc.EPCEvent;
import de.hpi.bpt.epc.EPCFunction;
import de.hpi.bpt.epc.EPCNode;
import de.hpi.bpt.epc.EPCRole;
import de.hpi.bpt.epc.EPCSystem;
import de.hpi.bpt.epc.aml.AMLEPC;
import de.hpi.bpt.epc.aml.AMLEPCCxn;
import de.hpi.bpt.epc.aml.AMLEPCDocument;
import de.hpi.bpt.epc.aml.AMLEPCFunction;
import de.hpi.bpt.epc.aml.AMLEPCNode;
import de.hpi.bpt.epc.aml.AMLEPCRole;
import de.hpi.bpt.epc.aml.AMLEPCSystem;

/**
 * 
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public class AMLSerializer {

	private Collection<EPC> epcs;

	private Document document;

	private int idCounter;
	
	private HashMap<EPC, Node> epc2group;

	private HashMap<EPC, Node> epc2model;
	
	private HashMap<EPCNode, Node> node2occ;

	private HashMap<EPCNode, Node> node2def;
	
	private Node aml;

	public AMLSerializer(EPC epc) {
		this.epcs = new HashSet<EPC>();
		this.epcs.add(epc);
		
		this.epc2group = new HashMap<EPC, Node>();
		this.epc2model = new HashMap<EPC, Node>();
		this.node2occ = new HashMap<EPCNode, Node>();
		this.node2def = new HashMap<EPCNode, Node>();		
	}

	public AMLSerializer(Collection<EPC> epcs) {
		this.epcs = epcs;
		this.epc2group = new HashMap<EPC, Node>();
		this.epc2model = new HashMap<EPC, Node>();
		this.node2occ = new HashMap<EPCNode, Node>();
		this.node2def = new HashMap<EPCNode, Node>();
	}
	
	public void parse() {
		document = new DocumentImpl();
		idCounter = 0;
		aml = document.createElement("AML");
		AMLHelper.addHeaderInfo(document, aml);
		AMLHelper.addLanguages(document, aml);
		document.appendChild(aml);
		parseGroups(aml);
		parseModel();
		Iterator<EPC> epcs = this.epcs.iterator();
		while(epcs.hasNext()){
			EPC epc = epcs.next();
			Iterator<EPCNode> startNodes = epc.getStartNodes().iterator();
			HashSet<AMLEPCNode> processed = new HashSet<AMLEPCNode>();
			while (startNodes.hasNext()) {
				AMLEPCNode node = (AMLEPCNode) startNodes.next();
				if (!processed.contains(node))
					processNode(node, processed, epc2group.get(epc), epc2model.get(epc), null, null, null, null);
			}

			NodeList nodes = document.getElementsByTagName("AttrOcc");
			for (int i = 0; i < nodes.getLength(); i++)
				if (nodes.item(i).getAttributes().getNamedItem("FontSS.IdRef") != null)
					nodes.item(i).getAttributes().removeNamedItem("FontSS.IdRef");

		}
		
		NodeList nl = aml.getChildNodes();
		Node firstGroup = null;
		for (int i = 0; i < nl.getLength(); i ++) {
			if(nl.item(i).getNodeName().equals("Group")){
				firstGroup = nl.item(i);
				continue;
			}
		}
		epcs = this.epcs.iterator();
		//NICO: disabling aml serialization of text notes!!!!
		/*while (epcs.hasNext()) {
			EPC epc = epcs.next();
			Node model = epc2model.get(epc);
			Iterator<Node> textNotices = ((AMLEPC) epc).getMetaInfo().getTextNotes().keySet().iterator();
			while (textNotices.hasNext()) {
				Node node = textNotices.next();
				node = firstGroup.getOwnerDocument().adoptNode(node);
				firstGroup.getParentNode().insertBefore(node, firstGroup);
				Node textNoticeOcc = ((AMLEPC) epc).getMetaInfo().getTextNotes().get(node); 
				textNoticeOcc = model.getOwnerDocument().adoptNode(textNoticeOcc);
				if(textNoticeOcc.getAttributes().getNamedItem("FontSS.IdRef") != null)
					textNoticeOcc.getAttributes().removeNamedItem("FontSS.IdRef");
				model.appendChild(textNoticeOcc);
			}
		}*/
	}

	//TODO fix the method, since it works poorly
	private void parseGroups(Node aml) {
		Iterator<EPC> epcs = this.epcs.iterator();
		while(epcs.hasNext()){
			EPC epc = epcs.next();
			
			List<String> groups = ((AMLEPC) epc).getGroups();
			Node parent = aml;
			for (int i = (groups.size() - 1); i > -1; i --) {
				Node group = null;
				NodeList children = parent.getChildNodes();
				for(int k = 0; k < children.getLength(); k ++){
					if(children.item(k).getNodeName().equals("Group")){
						if(!children.item(k).getAttributes().getNamedItem(AMLHelper.GROUP_ID).getNodeValue().equals(AMLHelper.ROOT_GROUP)){
							if(groups.get(i).equals(AMLHelper.getObjAttrValue(children.item(k), AMLHelper.AT_NAME))){
								group = children.item(k);
								break;								
							}
						}else {
							if(groups.get(i).equals(AMLHelper.GROUP_ID)){
								group = children.item(k);
								break;
							}
						}
					}
				}
				
				if(group == null){
					group = document.createElement("Group");

					AMLHelper.addGroupId(document, group, idCounter);
					idCounter ++;
					AMLHelper.addAtName(document, group, groups.get(i));
					parent.appendChild(group);
				}
				parent = group;
			}

			epc2group.put(epc, parent);
		}
	}

	private void parseModel() {
		Iterator<EPC> epcs = this.epcs.iterator();
		while(epcs.hasNext()){
			EPC epc = epcs.next();
			Node group = epc2group.get(epc);
			Node model = document.createElement("Model");
			AMLHelper.addAttribute(document, model, "Model.Type", "MT_EEPC");
			AMLHelper.addAttribute(document, model, "Model.ID", ((AMLEPC) epc).getModelId());
			AMLHelper.addAtName(document, model, epc.getTitle());
			group.appendChild(model);

			epc2model.put(epc, model);
		}
	}

	private void processNode(AMLEPCNode node,
			HashSet<AMLEPCNode> processedNodes, Node group, Node model,
			Node parentDef, Node parentOcc, AMLEPCNode parentNode, Node cxnOcc) {
		Node objDefNode = document.createElement("ObjDef");
		node2def.put(node, objDefNode);
		String defId = AMLHelper.addObjDefId(document, objDefNode, idCounter);
		idCounter ++;
		AMLHelper.addGuid(document, objDefNode);

		if(node instanceof EPCFunction){
			EPCFunction f = (EPCFunction) node;
			if(f.isAggregating()){
				AMLHelper.addAtName(document, objDefNode, "...");
				AMLHelper.addAtDescription(document, objDefNode, node.getName());
			}else{
				AMLHelper.addAtName(document, objDefNode, node.getName());
				if (node.getDescription() != null)
					AMLHelper.addAtDescription(document, objDefNode, node.getDescription());
			}
		}else{
			AMLHelper.addAtName(document, objDefNode, node.getName());
			if (node.getDescription() != null)
				AMLHelper.addAtDescription(document, objDefNode, node.getDescription());
		}
		
		if (node.getCreateTimestamp() != null)
			AMLHelper.addAtCreateTimeStamp(document, objDefNode, node.getCreateTimestamp());
		if (node.getCreator() != null)
			AMLHelper.addAtCreator(document, objDefNode, node.getCreator());
		if (node.getLastChange() != null)
			AMLHelper.addLChange2(document, objDefNode, Util.now("HH:mm:ss.000;MM/dd/yyyy"));
		if (node.getLastUser() != null)
			AMLHelper.addAtLUser(document, objDefNode, AMLHelper.BPT_USER);
		if (node.getShortDescription() != null)
			AMLHelper.addAtShortDesc(document, objDefNode, node.getShortDescription());
		if (node.getAnnualFrequency() != 0)
			AMLHelper.addAtAnnFreq(document, objDefNode, Integer.toString(node.getAnnualFrequency()));

		if (node.getDescription() != null)
			AMLHelper.addAtDescription(document, objDefNode, node.getDescription());

		if (node instanceof EPCFunction) {
			AMLHelper.addAttribute(document, objDefNode, "TypeNum", "OT_FUNC");
			AMLHelper.addAtTimeAvgPrcs(document, objDefNode, Util.formatNumber(((EPCFunction) node).getDuration()).concat(";").concat(AMLHelper.MINUTES));
		} else if (node instanceof EPCEvent) {
			AMLHelper.addAttribute(document, objDefNode, "TypeNum", "OT_EVT");
		} else if (node instanceof EPCConnector) {
			AMLHelper.addAttribute(document, objDefNode, "TypeNum", "OT_RULE");
			if (((EPCConnector) node).getType() == EPCConnector.XOR)
				AMLHelper.addAttribute(document, objDefNode, "SymbolNum", "ST_OPR_XOR_1");
		}

		group.insertBefore(objDefNode, model);

		Node objOccNode = document.adoptNode(node.getDomOcc().cloneNode(true));
		node2occ.put(node, objOccNode);
		if (node instanceof EPCConnector)
			if (((EPCConnector) node).getType() == EPCConnector.XOR)
				objOccNode.getAttributes().getNamedItem(AMLHelper.SYMBOL_NUM).setNodeValue("ST_OPR_XOR_1");

		Node childNode = objOccNode.getFirstChild();
		do {
			Node tmp = childNode;
			childNode = childNode.getNextSibling();
			if (tmp.getNodeName().equals("CxnOcc"))
				objOccNode.removeChild(tmp);
		} while (childNode != null);

		if (objOccNode.getAttributes().getNamedItem("ToCxnOccs.IdRefs") != null)
			objOccNode.getAttributes().removeNamedItem("ToCxnOccs.IdRefs");
		objOccNode.getAttributes().getNamedItem(AMLHelper.OBJ_OCC_ID).setNodeValue(AMLHelper.generateId(idCounter, AMLHelper.OBJ_OCC));
		objOccNode.getAttributes().getNamedItem(AMLHelper.OBJ_DEF_ID_REF).setNodeValue(defId);
		idCounter ++;
		objOccNode = document.adoptNode(objOccNode);
		model.appendChild(objOccNode);
		processedNodes.add(node);

		if (parentNode != null)
			addConnection(parentNode, parentDef, parentOcc, node, objDefNode, objOccNode, cxnOcc, defId);

		if (node instanceof EPCFunction) {
			if (((EPCFunction) node).hasRoles())
				processRoles((AMLEPCFunction) node, group, model);
			if (((EPCFunction) node).hasSystems())
				processSystems((AMLEPCFunction) node, group, model);
			if (((EPCFunction) node).usesDocuments())
				processDocuments((AMLEPCFunction) node, group, model);
		}

		Iterator<EPCNode> children = node.getChildren().iterator();
		while (children.hasNext()) {
			AMLEPCNode child = (AMLEPCNode) children.next();
			Iterator<EPCCxn> cxns = node.getOutConnections().iterator();
			Node newCxnOcc = null;
			while (cxns.hasNext()) {
				AMLEPCCxn cxn = (AMLEPCCxn) cxns.next();
				if (AMLEPCNode.equal(cxn.getTarget(), child)) {
					newCxnOcc = cxn.getDomOcc();
					break;
				}
			}
			if (!processedNodes.contains(child))
				processNode(child, processedNodes, group, model, objDefNode, objOccNode, node, newCxnOcc);
			else
				addConnection(node, objDefNode, node2occ.get(node), child, node2def.get(child), node2occ.get(child), newCxnOcc, node2occ.get(child).getAttributes().getNamedItem(AMLHelper.OBJ_DEF_ID_REF).getNodeValue());
		}
	}

	private void addConnection(EPCNode parentNode, Node parentDef, Node parentOcc, EPCNode node, Node objDefNode, Node objOccNode, Node cxnOcc, String toObjDef) {
		Node cxnDef = AMLHelper.addCxnDef(document, parentDef, idCounter);
		idCounter ++;

		if (parentNode instanceof EPCConnector) {
			if (node instanceof EPCConnector)
				// if(node.getParents().size() > 1)
				AMLHelper.addAttribute(document, cxnDef,
						AMLHelper.CXN_DEF_TYPE, "CT_LNK_2");
			// else
			// AMLHelper.addAttribute(document, cxnDef, AMLHelper.CXN_DEF_TYPE,
			// "CT_LNK_1");
			else if (node instanceof EPCFunction)
				AMLHelper.addAttribute(document, cxnDef, AMLHelper.CXN_DEF_TYPE, "CT_ACTIV_1");
			else if (node instanceof EPCEvent)
				if (parentNode.getChildren().size() > 1)
					AMLHelper.addAttribute(document, cxnDef, AMLHelper.CXN_DEF_TYPE, "CT_LEADS_TO_2");
				else
					AMLHelper.addAttribute(document, cxnDef, AMLHelper.CXN_DEF_TYPE, "CT_LEADS_TO_2");
		} else if (parentNode instanceof EPCEvent) {
			if (node instanceof EPCConnector)
				AMLHelper.addAttribute(document, cxnDef, AMLHelper.CXN_DEF_TYPE, "CT_IS_EVAL_BY_1");
			else if (node instanceof EPCFunction)
				AMLHelper.addAttribute(document, cxnDef, AMLHelper.CXN_DEF_TYPE, "CT_ACTIV_1");
			else if (node instanceof EPCEvent)
				AMLHelper.addAttribute(document, cxnDef, AMLHelper.CXN_DEF_TYPE, "CT_ACTIV_1");
		} else if (parentNode instanceof EPCFunction) {
			if (node instanceof EPCConnector)
				AMLHelper.addAttribute(document, cxnDef, AMLHelper.CXN_DEF_TYPE, "CT_LEADS_TO_1");
			else if (node instanceof EPCFunction)
				AMLHelper.addAttribute(document, cxnDef, AMLHelper.CXN_DEF_TYPE, "CT_IS_PREDEC_OF_1");
			else if (node instanceof EPCEvent)
				AMLHelper.addAttribute(document, cxnDef, AMLHelper.CXN_DEF_TYPE, "CT_CRT_1");
			else if (node instanceof EPCSystem)
				AMLHelper.addAttribute(document, cxnDef, AMLHelper.CXN_DEF_TYPE, "CT_HAS_OUT");
			else if (node instanceof EPCDocument)
				AMLHelper.addAttribute(document, cxnDef, AMLHelper.CXN_DEF_TYPE, "CT_CRT_OUT_TO");
		} else if (parentNode instanceof EPCRole) {
			if (node instanceof EPCFunction)
				AMLHelper.addAttribute(document, cxnDef, AMLHelper.CXN_DEF_TYPE, "CT_EXEC_1");
		}

		cxnOcc = document.adoptNode(cxnOcc.cloneNode(true));
		cxnOcc.getAttributes().getNamedItem(AMLHelper.CXN_DEF_ID_REF).setNodeValue(cxnDef.getAttributes().getNamedItem(AMLHelper.CXN_DEF_ID).getNodeValue());
		cxnOcc.getAttributes().getNamedItem(AMLHelper.CXN_OCC_ID).setNodeValue(AMLHelper.generateId(idCounter, AMLHelper.CXN_OCC));
		idCounter ++;
		cxnOcc.getAttributes().getNamedItem(AMLHelper.TO_OBJ_OCC).setNodeValue(objOccNode.getAttributes().getNamedItem(AMLHelper.OBJ_OCC_ID).getNodeValue());
		Node c = parentOcc.getFirstChild();
		while (c != null && !c.getNodeName().equals("AttrOcc"))
			c = c.getNextSibling();
		if (c == null)
			parentOcc.appendChild(cxnOcc);
		else
			parentOcc.insertBefore(cxnOcc, c);
		if (parentOcc.getAttributes().getNamedItem(AMLHelper.TO_CXN_OCC) == null)
			AMLHelper.addAttribute(document, parentOcc, AMLHelper.TO_CXN_OCC, cxnOcc.getAttributes().getNamedItem(AMLHelper.CXN_OCC_ID).getNodeValue());
		else
			parentOcc.getAttributes().getNamedItem(AMLHelper.TO_CXN_OCC).getNodeValue().concat(" " + cxnOcc.getAttributes().getNamedItem(AMLHelper.CXN_OCC_ID).getNodeValue());

		AMLHelper.addAttribute(document, cxnDef, AMLHelper.TO_OBJ_DEF, toObjDef);
		if (objDefNode.getAttributes().getNamedItem(AMLHelper.TO_CXN_DEF) != null)
			objDefNode.getAttributes().getNamedItem(AMLHelper.TO_CXN_DEF).getNodeValue().concat(" " + cxnDef.getAttributes().getNamedItem(AMLHelper.CXN_DEF_ID).getNodeValue());
		else
			AMLHelper.addAttribute(document, objDefNode, AMLHelper.TO_CXN_DEF, cxnDef.getAttributes().getNamedItem(AMLHelper.CXN_DEF_ID).getNodeValue());
		Iterator<EPCCxn> cxns = parentNode.getOutConnections().iterator();
		while (cxns.hasNext()) {
			EPCCxn cxn = cxns.next();
			if (AMLEPCNode.equal(cxn.getTarget(), node)) {
				if (parentNode.getChildren().size() > 1)
					AMLHelper.addAtProb(document, cxnDef, Double.toString(cxn.getRelProbability()));
				break;
			}
		}
	}

	private void processRoles(AMLEPCFunction function, Node group, Node model) {
		Iterator<EPCRole> roles = function.getRoles().iterator();
		while (roles.hasNext()) {
			AMLEPCRole role = (AMLEPCRole) roles.next();
			Node objDefNode = document.createElement("ObjDef");
			node2def.put(role, objDefNode);
			String defId = AMLHelper.addObjDefId(document, objDefNode, idCounter);
			idCounter ++;
			AMLHelper.addGuid(document, objDefNode);
			AMLHelper.addAtName(document, objDefNode, role.getName());
			if (role.getCreateTimestamp() != null)
				AMLHelper.addAtCreateTimeStamp(document, objDefNode, role.getCreateTimestamp());
			if (role.getCreator() != null)
				AMLHelper.addAtCreator(document, objDefNode, role.getCreator());
			if (role.getLastChange() != null)
				AMLHelper.addLChange2(document, objDefNode, role.getLastChange());
			if (role.getLastUser() != null)
				AMLHelper.addAtLUser(document, objDefNode, role.getLastUser());
			AMLHelper.addAttribute(document, objDefNode, "TypeNum", "OT_POS");

			group.insertBefore(objDefNode, model);

			Node objOccNode = document.adoptNode(role.getDomOcc().cloneNode(true));
			node2occ.put(role, objOccNode);

			if (objOccNode.getAttributes().getNamedItem("ToCxnOccs.IdRefs") != null)
				objOccNode.getAttributes().removeNamedItem("ToCxnOccs.IdRefs");
			objOccNode.getAttributes().getNamedItem(AMLHelper.OBJ_OCC_ID).setNodeValue(AMLHelper.generateId(idCounter, AMLHelper.OBJ_OCC));
			objOccNode.getAttributes().getNamedItem(AMLHelper.OBJ_DEF_ID_REF).setNodeValue(defId);
			idCounter ++;
			objOccNode = document.adoptNode(objOccNode);
			model.appendChild(objOccNode);
			addConnection(role, objDefNode, objOccNode, function, node2def.get(function), node2occ.get(function), role.getCxn().getDomOcc(), node2def.get(function).getAttributes().getNamedItem(AMLHelper.OBJ_DEF_ID).getNodeValue());			
		}
	}

	private void processSystems(AMLEPCFunction function, Node group, Node model) {
		Iterator<EPCSystem> systems = function.getSystems().iterator();
		while (systems.hasNext()) {
			AMLEPCSystem system = (AMLEPCSystem) systems.next();
			Node objDefNode = document.createElement("ObjDef");
			node2def.put(system, objDefNode);
			String defId = AMLHelper.addObjDefId(document, objDefNode, idCounter);
			idCounter++;
			AMLHelper.addGuid(document, objDefNode);
			AMLHelper.addAtName(document, objDefNode, system.getName());
			if (system.getCreateTimestamp() != null)
				AMLHelper.addAtCreateTimeStamp(document, objDefNode, system.getCreateTimestamp());
			if (system.getCreator() != null)
				AMLHelper.addAtCreator(document, objDefNode, system.getCreator());
			if (system.getLastChange() != null)
				AMLHelper.addLChange2(document, objDefNode, system.getLastChange());
			if (system.getLastUser() != null)
				AMLHelper.addAtLUser(document, objDefNode, system.getLastUser());
			AMLHelper.addAttribute(document, objDefNode, "TypeNum", "OT_CLST");

			group.insertBefore(objDefNode, model);

			Node objOccNode = document.adoptNode(system.getDomOcc().cloneNode(true));
			node2occ.put(system, objOccNode);

			Node childNode = objOccNode.getFirstChild();
			do {
				Node tmp = childNode;
				childNode = childNode.getNextSibling();
				if (tmp.getNodeName().equals("CxnOcc"))
					objOccNode.removeChild(tmp);
			} while (childNode != null);

			if (objOccNode.getAttributes().getNamedItem("ToCxnOccs.IdRefs") != null)
				objOccNode.getAttributes().removeNamedItem("ToCxnOccs.IdRefs");
			objOccNode.getAttributes().getNamedItem(AMLHelper.OBJ_OCC_ID).setNodeValue(
							AMLHelper.generateId(idCounter, AMLHelper.OBJ_OCC));
			objOccNode.getAttributes().getNamedItem(AMLHelper.OBJ_DEF_ID_REF).setNodeValue(defId);
			idCounter ++;
			objOccNode = document.adoptNode(objOccNode);
			model.appendChild(objOccNode);

			addConnection(function, node2def.get(function), node2occ.get(function), system, objDefNode, objOccNode,	((AMLEPCCxn) system.getCxn()).getDomOcc(), defId);
		}
	}

	private void processDocuments(AMLEPCFunction function, Node group, Node model) {
		Iterator<EPCDocument> documents = function.getDocuments().iterator();
		while (documents.hasNext()) {
			AMLEPCDocument doc = (AMLEPCDocument) documents.next();
			Node objDefNode = document.createElement("ObjDef");
			node2def.put(doc, objDefNode);
			String defId = AMLHelper.addObjDefId(document, objDefNode, idCounter);
			idCounter ++;
			AMLHelper.addGuid(document, objDefNode);
			AMLHelper.addAtName(document, objDefNode, doc.getName());
			if (doc.getCreateTimestamp() != null)
				AMLHelper.addAtCreateTimeStamp(document, objDefNode, doc.getCreateTimestamp());
			if (doc.getCreator() != null)
				AMLHelper.addAtCreator(document, objDefNode, doc.getCreator());
			if (doc.getLastChange() != null)
				AMLHelper.addLChange2(document, objDefNode, doc.getLastChange());
			if (doc.getLastUser() != null)
				AMLHelper.addAtLUser(document, objDefNode, doc.getLastUser());
			AMLHelper.addAttribute(document, objDefNode, "TypeNum", "OT_INFO_CARR");

			group.insertBefore(objDefNode, model);

			Node objOccNode = document.adoptNode(doc.getDomOcc().cloneNode(true));
			node2occ.put(doc, objOccNode);

			Node childNode = objOccNode.getFirstChild();
			do {
				Node tmp = childNode;
				childNode = childNode.getNextSibling();
				if (tmp.getNodeName().equals("CxnOcc"))
					objOccNode.removeChild(tmp);
			} while (childNode != null);

			if (objOccNode.getAttributes().getNamedItem("ToCxnOccs.IdRefs") != null)
				objOccNode.getAttributes().removeNamedItem("ToCxnOccs.IdRefs");
			objOccNode.getAttributes().getNamedItem(AMLHelper.OBJ_OCC_ID).setNodeValue(AMLHelper.generateId(idCounter, AMLHelper.OBJ_OCC));
			objOccNode.getAttributes().getNamedItem(AMLHelper.OBJ_DEF_ID_REF).setNodeValue(defId);
			idCounter ++;
			objOccNode = document.adoptNode(objOccNode);
			model.appendChild(objOccNode);

			addConnection(function, node2def.get(function), node2occ.get(function), doc, objDefNode, objOccNode, ((AMLEPCCxn) doc.getCxn()).getDomOcc(), defId);
		}
	}

	public Collection<EPC> getEpcs() {
		return epcs;
	}

	public void setEpcs(Collection<EPC> epcs) {
		this.epcs = epcs;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
}