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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;

import de.hpi.bpt.epc.EPC;
import de.hpi.bpt.epc.EPCApplicationSystem;
import de.hpi.bpt.epc.EPCConnector;
import de.hpi.bpt.epc.EPCCxn;
import de.hpi.bpt.epc.EPCDocument;
import de.hpi.bpt.epc.EPCFunction;
import de.hpi.bpt.epc.EPCNode;
import de.hpi.bpt.epc.EPCOrganization;
import de.hpi.bpt.epc.EPCOrganizationType;
import de.hpi.bpt.epc.EPCRole;
import de.hpi.bpt.epc.EPCSystem;
import de.hpi.bpt.epc.aml.AMLEPC;
import de.hpi.bpt.epc.aml.AMLEPCApplicationSystem;
import de.hpi.bpt.epc.aml.AMLEPCConnector;
import de.hpi.bpt.epc.aml.AMLEPCCxn;
import de.hpi.bpt.epc.aml.AMLEPCDocument;
import de.hpi.bpt.epc.aml.AMLEPCEvent;
import de.hpi.bpt.epc.aml.AMLEPCFunction;
import de.hpi.bpt.epc.aml.AMLEPCNode;
import de.hpi.bpt.epc.aml.AMLEPCOrganization;
import de.hpi.bpt.epc.aml.AMLEPCOrganizationType;
import de.hpi.bpt.epc.aml.AMLEPCRole;
import de.hpi.bpt.epc.aml.AMLEPCSystem;
import de.hpi.bpt.epc.aml.AMLEPCTextNote;
import de.hpi.bpt.epc.aml.Attribute;
import de.hpi.bpt.epc.aml.layout.Brush;
import de.hpi.bpt.epc.aml.layout.Offset;
import de.hpi.bpt.epc.aml.layout.Pen;
import de.hpi.bpt.epc.aml.layout.Position;
import de.hpi.bpt.epc.aml.layout.Size;

/**
 * 
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public class AMLParser {

	private String fileName;
	
	private Document document;
	
	private HashMap<String, Node> models;
	
	private HashMap<String, Node> allObjDefs;
	
	private HashMap<String, Node> textNoticeDefs;
	
	private HashMap<String, String> linkedModels;
	
	private HashMap<String, String> cxnDefId2objDefId;
	
	private HashMap<String, String> model2group;

	private HashMap<String, String> group2group;
	
	private HashMap<String, String> groupId2groupName;
	
	public AMLParser(String fileName) {
		this.fileName = fileName;
		this.models = new HashMap<String, Node>();
		this.allObjDefs = new HashMap<String, Node>();
		this.linkedModels = new HashMap<String, String>();
		this.cxnDefId2objDefId = new HashMap<String, String>();
		this.model2group = new HashMap<String, String>();
		this.group2group = new HashMap<String, String>();
		this.groupId2groupName = new HashMap<String, String>();
		this.textNoticeDefs = new HashMap<String, Node>();
	}
		
	public void parse() throws SAXException, IOException {
		allObjDefs.clear();
		models.clear();
		DOMParser parser = new DOMParser();
		String url = "file:" + fileName;
		parser.parse(url);
		document = parser.getDocument();
		parseAllObjectDefinitions();
		parseTextNoticeDefinitions();
		parseAllModels();
		parseAllGroups();
		NodeList modelNodes = document.getElementsByTagName("Model");
		for(int i = 0; i < modelNodes.getLength(); i ++)
			if(modelNodes.item(i).getAttributes().getNamedItem(AMLHelper.MODEL_TYPE).getNodeValue().equals("MT_EEPC"))
				models.put(modelNodes.item(i).getAttributes().getNamedItem(AMLHelper.MODEL_ID).getNodeValue(), modelNodes.item(i));
		
	}
	
	public EPC getEPC(String id) {
		return parseProcessModel(id);
	}
			
	private EPC parseProcessModel(String id) {
		AMLEPC epc = new AMLEPC();
		NodeList model = models.get(id).getChildNodes();
		HashMap<String, Node> cxnDefId2cxnDef = new HashMap<String, Node>();
		HashMap<String, Node> objDefs = new HashMap<String, Node>();
		parseObjectDefinitions(epc, document, cxnDefId2cxnDef, objDefs);
		epc.setModelId(id);
		parseModelTitle(epc, model);
		parseGroup(epc, models.get(id));
		HashMap<String, Node> objOccs = new HashMap<String, Node>();
		parseObjectOccs(epc, model, objOccs, objDefs);
		parseCxns(epc, model, cxnDefId2cxnDef, objOccs);
		
		return epc;
	}
	
	private void parseObjectDefinitions(AMLEPC epc, Document doc, HashMap<String, Node> cxnDefId2cxnDef, HashMap<String, Node> objDefs) {
		NodeList objDefNodes =  doc.getElementsByTagName("ObjDef");
		for (int i = 0; i < objDefNodes.getLength(); i ++) {
			Node node = objDefNodes.item(i);
			String objDefId = node.getAttributes().getNamedItem(AMLHelper.OBJ_DEF_ID).getNodeValue();
			objDefs.put(objDefId, node);
			NodeList nodeList = node.getChildNodes();
			for (int k = 0; k < nodeList.getLength(); k ++)
				if(nodeList.item(k).getNodeName().equals("CxnDef")) {
					cxnDefId2cxnDef.put(nodeList.item(k).getAttributes().getNamedItem(AMLHelper.CXN_DEF_ID).getNodeValue(), nodeList.item(k));
					cxnDefId2objDefId.put(nodeList.item(k).getAttributes().getNamedItem(AMLHelper.CXN_DEF_ID).getNodeValue(), objDefId);
				}
		}
	}
	
	private void parseTextNoticeDefinitions() {
		NodeList textNoticeDefNodes =  document.getElementsByTagName("FFTextDef");
		for (int i = 0; i < textNoticeDefNodes.getLength(); i ++) {
			Node node = textNoticeDefNodes.item(i);
			textNoticeDefs.put(node.getAttributes().getNamedItem(AMLHelper.FF_TEXT_DEF_ID).getNodeValue(), node.cloneNode(true));
		}
	}
	
	private void parseModelTitle(AMLEPC epc, NodeList model) {
		for(int i = 0; i < model.getLength(); i ++) {
			if(model.item(i).getNodeName().equals("AttrDef") && model.item(i).getAttributes().getNamedItem("AttrDef.Type").getNodeValue().equals(AMLHelper.AT_NAME)) {
				Node attrValue = model.item(i).getFirstChild();
				while(attrValue.getNodeType() == Node.TEXT_NODE)
					attrValue = attrValue.getNextSibling(); 
				epc.setTitle(attrValue.getTextContent());
			}
		}
	}
	
	private void parseGroup(AMLEPC epc, Node model) {
		List<String> groupIds = new ArrayList<String>();
		String groupId = model2group.get(model.getAttributes().getNamedItem(AMLHelper.MODEL_ID).getNodeValue());
		groupIds.add(groupId2groupName.get(groupId));
		groupId = group2group.get(groupId);
		while(!groupId.equals("")) {
			groupIds.add(groupId2groupName.get(groupId));
			groupId = group2group.get(groupId);	
		}
		epc.setGroups(groupIds);
	}
	
	/**
	 * @param epc
	 * @param model
	 * @param objOccs
	 * @param objDefs
	 */
	private void parseObjectOccs(AMLEPC epc, NodeList model, HashMap<String, Node> objOccs, HashMap<String, Node> objDefs) {
		HashMap<String, EPCNode> nodes = new HashMap<String, EPCNode>();
		HashMap<String, EPCRole> roles = new HashMap<String, EPCRole>();
		HashMap<String, EPCSystem> systems = new HashMap<String, EPCSystem>();
		HashMap<String, EPCDocument> documents = new HashMap<String, EPCDocument>();
		HashMap<String, EPCOrganization> organizations = new HashMap<String, EPCOrganization>();
		HashMap<String, EPCOrganizationType> organizationTypes = new HashMap<String, EPCOrganizationType>();
		HashMap<String, EPCApplicationSystem> applicationSystems = new HashMap<String, EPCApplicationSystem>();
		HashMap<String, String> occId2defId = new HashMap<String, String>();
		for(int i = 0; i < model.getLength(); i ++) {
			if(model.item(i).getNodeName().equals("ObjOcc")) {
				Node objOcc = model.item(i).cloneNode(true);
				
				String objOccId = objOcc.getAttributes().getNamedItem(AMLHelper.OBJ_OCC_ID).getNodeValue();
				String objDefId = objOcc.getAttributes().getNamedItem(AMLHelper.OBJ_DEF_ID_REF).getNodeValue();
								
				Node objDefNode = objDefs.get(objDefId);
				String type = objDefNode.getAttributes().getNamedItem(AMLHelper.TYPE_NUM).getNodeValue();
				
				AMLEPCNode n = null;
				if (type.equals("OT_FUNC")) {
					/*
					 * Node is a function
					 */
					n = new AMLEPCFunction(objOcc);
					((AMLEPCFunction) n).setDuration(AMLHelper.getDuration(objDefNode));
					n.setAnnualFrequency(AMLHelper.getAnnualFrequency(objDefNode));
					String symbolNum = objOcc.getAttributes().getNamedItem("SymbolNum").getNodeValue();
					if(symbolNum != null && symbolNum.equals("ST_PRCS_IF"))
						((EPCFunction) n).setProcessInterface(true);
					n.setSymbolNum(symbolNum);
					if(objDefNode.getAttributes().getNamedItem("LinkedModels.IdRefs") != null)
						((AMLEPCFunction) n).setReferencedModelId(objDefNode.getAttributes().getNamedItem("LinkedModels.IdRefs").getNodeValue());
					nodes.put(objOccId, n);
				}else if (type.equals("OT_EVT")){
					/*
					 * Node is an event
					 */
					n = new AMLEPCEvent(objOcc);
					n.setAnnualFrequency(AMLHelper.getAnnualFrequency(objDefNode));
					n.setSymbolNum(objOcc.getAttributes().getNamedItem(AMLHelper.SYMBOL_NUM).getNodeValue());
					nodes.put(objOccId, n);
				}else if (type.equals("OT_RULE")){
					/*
					 * Node is a connector
					 */
					n = new AMLEPCConnector(objOcc);
					String symbolNum = objOcc.getAttributes().getNamedItem(AMLHelper.SYMBOL_NUM).getNodeValue();
					if (symbolNum != null)
						if (symbolNum.contains("_XOR_"))
							((EPCConnector) n).setType(EPCConnector.XOR);
						else if (symbolNum.contains("_AND_"))
							((EPCConnector) n).setType(EPCConnector.AND);
						else if (symbolNum.contains("_OR_"))
							((EPCConnector) n).setType(EPCConnector.OR);
						else
							((EPCConnector) n).setType(EPCConnector.UNDEFINED);
					n.setAnnualFrequency(AMLHelper.getAnnualFrequency(objDefNode));
					n.setSymbolNum(symbolNum);
					nodes.put(objOccId, n);
				}else if (type.equals("OT_POS")){
					/*
					 * Node is a role
					 */
					n = new AMLEPCRole(objOcc);
					n.setSymbolNum(objOcc.getAttributes().getNamedItem(AMLHelper.SYMBOL_NUM).getNodeValue());
					roles.put(objOccId, (AMLEPCRole) n);
				}else if (type.equals("OT_CLST")){
					/*
					 * Node is a system
					 */
					n = new AMLEPCSystem(objOcc);
					n.setSymbolNum(objOcc.getAttributes().getNamedItem(AMLHelper.SYMBOL_NUM).getNodeValue());
					systems.put(objOccId, (AMLEPCSystem) n);
				}else if (type.equals("OT_INFO_CARR")){
					/*
					 * Node is a document
					 */
					n = new AMLEPCDocument(objOcc);
					n.setSymbolNum(objOcc.getAttributes().getNamedItem(AMLHelper.SYMBOL_NUM).getNodeValue());
					documents.put(objOccId, (AMLEPCDocument) n);
				}else if (type.equals("OT_ORG_UNIT")){
					/*
					 * Node is an organization
					 */
					n = new AMLEPCOrganization(objOcc);
					n.setSymbolNum(objOcc.getAttributes().getNamedItem(AMLHelper.SYMBOL_NUM).getNodeValue());
					organizations.put(objOccId, (AMLEPCOrganization) n);
				}else if (type.equals("OT_ORG_UNIT_TYPE")){
					/*
					 * Node is an organization
					 */
					n = new AMLEPCOrganizationType(objOcc);
					n.setSymbolNum(objOcc.getAttributes().getNamedItem(AMLHelper.SYMBOL_NUM).getNodeValue());
					organizationTypes.put(objOccId, (AMLEPCOrganizationType) n);
				}else if (type.equals("OT_APPL_SYS_TYPE")){
					/*
					 * Node is an organization
					 */
					n = new AMLEPCApplicationSystem(objOcc);
					n.setSymbolNum(objOcc.getAttributes().getNamedItem(AMLHelper.SYMBOL_NUM).getNodeValue());
					applicationSystems.put(objOccId, (AMLEPCApplicationSystem) n);
				}
				
				if (n != null){
					n.setEPC(epc);
					n.setId(objOccId);
					if(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_NAME) == null)
						n.setName("");
					else
						n.setName(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_NAME));
					n.setDescription(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_DESCRIPTION));
					n.setShortDescription(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_SHORT_DESC));
					n.setCreateTimestamp(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_CREAT_TIME_STMP));
					n.setCreator(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_CREATOR));
					n.setLastChange(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_LAST_CHANGE_TIME));
					n.setLastUser(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_LAST_USER));
					parseNodeLayout(n, objOcc);
					try{
						n.setHints(Integer.parseInt(objOcc.getAttributes().getNamedItem(AMLHelper.HINTS).getNodeValue()));	
					}catch(NumberFormatException e){
						n.setHints(0);
					}
					try{
						n.setZorder(Integer.parseInt(objOcc.getAttributes().getNamedItem(AMLHelper.ZORDER).getNodeValue()));	
					}catch(NumberFormatException e){
						n.setZorder(0);
					}


					objOccs.put(objOccId, objOcc);
					occId2defId.put(objOccId, objDefId);
				}				
			}
			if(model.item(i).getNodeName().equals("FFTextOcc")){
				Node objOcc = model.item(i).cloneNode(true);
				
				String objDefId = objOcc.getAttributes().getNamedItem(AMLHelper.FF_TEXT_DEF_ID_REF).getNodeValue();
				
				Node objDefNode = textNoticeDefs.get(objDefId);
				
				AMLEPCNode n = new AMLEPCTextNote(objOcc);
				parseNodeLayout(n, objOcc);
				
				n.setEPC(epc);
				n.setId(objDefId);
				if(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_NAME) == null)
					n.setName("");
				else
					n.setName(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_NAME));
				n.setDescription(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_DESCRIPTION));
				n.setShortDescription(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_SHORT_DESC));
				n.setCreateTimestamp(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_CREAT_TIME_STMP));
				n.setCreator(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_CREATOR));
				n.setLastChange(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_LAST_CHANGE_TIME));
				n.setLastUser(AMLHelper.getObjAttrValue(objDefNode, AMLHelper.AT_LAST_USER));
				parseNodeLayout(n, objOcc);
				try{
					n.setHints(Integer.parseInt(objOcc.getAttributes().getNamedItem(AMLHelper.HINTS).getNodeValue()));	
				}catch(NumberFormatException e){
					n.setHints(0);
				}catch(NullPointerException e){
					n.setHints(0);
				}
				try{
					n.setZorder(Integer.parseInt(objOcc.getAttributes().getNamedItem(AMLHelper.ZORDER).getNodeValue()));	
				}catch(NumberFormatException e){
					n.setZorder(0);
				}
				
				epc.getMetaInfo().getTextNotes().put(objDefId, (AMLEPCTextNote)n);
			}
		}
		epc.setNodeMap(nodes);
		epc.setRoleMap(roles);
		epc.setSystemMap(systems);
		epc.setDocumentMap(documents);
		epc.setOrganizationMap(organizations);
		epc.setOrganizationTypeMap(organizationTypes);
		epc.setApplicationSystemMap(applicationSystems);
	}
	
	private void parseCxns(AMLEPC epc, NodeList model, HashMap<String, Node> cxnDefId2cxnDef, HashMap<String, Node> objOccs){
		HashMap<String, EPCCxn> cxns = new HashMap<String, EPCCxn>();		
		Iterator<Entry<String,Node>> i = objOccs.entrySet().iterator();
		while (i.hasNext()){
			Entry<String, Node> e = i.next(); 
			String objOccId = e.getKey();
			Node objOccNode = e.getValue();
			
			NodeList nodeList = objOccNode.getChildNodes();
			
			for (int k = 0; k < nodeList.getLength(); k++){
				if(nodeList.item(k).getNodeName().equals("CxnOcc")){
					Node cxnOcc = nodeList.item(k);
					Node cxnDef = cxnDefId2cxnDef.get(cxnOcc.getAttributes().getNamedItem(AMLHelper.CXN_DEF_ID_REF).getNodeValue());
					EPCNode source = epc.getNode(objOccId);
					EPCNode target = epc.getNode(cxnOcc.getAttributes().getNamedItem(AMLHelper.TO_OBJ_OCC).getNodeValue());
					
					//type
					String type = cxnDef.getAttributes().getNamedItem(AMLHelper.CXN_DEF_TYPE).getNodeValue();
					
					if (source != null && target != null){
						AMLEPCCxn connection = new AMLEPCCxn(cxnOcc, source, target);
						connection.setType(type);
						parseCxnLayout(connection, cxnOcc);
						String probability = AMLHelper.getObjAttrValue(cxnDef, AMLHelper.AT_PROB);
						double relProbability = 1.0;
						if (probability != null) {
							try {
								relProbability = Double.parseDouble(probability);
							} catch (Exception ex) {
								relProbability = 0.0;
							}
						}
						connection.setRelProbability(relProbability);
						if(connection != null)
							cxns.put(connection.getId(), connection);
						
						source.getOutConnections().add(connection);
						target.getInConnections().add(connection);
					} else if(source == null && (epc.getRole(objOccId) != null)){
						//TODO check this!! It can the error source.
						EPCRole role = epc.getRole(objOccId);
						AMLEPCCxn connection = new AMLEPCCxn(cxnOcc, role, target);
						connection.setType(type);
						parseCxnLayout(connection, cxnOcc);
						((AMLEPCRole) role).setCxn(connection);
						((EPCFunction) target).addRole(role);
					}else if(source == null && epc.getOrganization(objOccId) != null){
						EPCOrganization organization = epc.getOrganization(objOccId);
						AMLEPCCxn connection = new AMLEPCCxn(cxnOcc, organization, target);
						connection.setType(type);
						parseCxnLayout(connection, cxnOcc);
						((AMLEPCOrganization) organization).setCxn(connection);
						((EPCFunction) target).addOrganization(organization);
					}else if(source == null && epc.getOrganizationType(objOccId) != null){
						EPCOrganizationType organizationType = epc.getOrganizationType(objOccId);
						AMLEPCCxn connection = new AMLEPCCxn(cxnOcc, organizationType, target);
						connection.setType(type);
						parseCxnLayout(connection, cxnOcc);
						((AMLEPCOrganizationType) organizationType).setCxn(connection);
						((EPCFunction) target).addOrganizationType(organizationType);
					}else if(source == null && epc.getDocument(objOccId) != null){
						EPCDocument doc = epc.getDocument(objOccId);
						AMLEPCCxn connection = new AMLEPCCxn(cxnOcc, doc, target);
						connection.setType(type);
						parseCxnLayout(connection, cxnOcc);
						((AMLEPCDocument) doc).setCxn(connection);
						((EPCFunction) target).addDocument(doc);
					}else if(source == null && epc.getSystem(objOccId) != null){
						EPCSystem system = epc.getSystem(objOccId);
						AMLEPCCxn connection = new AMLEPCCxn(cxnOcc, system, target);
						connection.setType(type);
						parseCxnLayout(connection, cxnOcc);
						((AMLEPCSystem) system).setCxn(connection);
						((EPCFunction) target).addSystem(system);
					}else if(source == null && epc.getApplicationSystem(objOccId) != null){
						EPCApplicationSystem applicationSystem = epc.getApplicationSystem(objOccId);
						AMLEPCCxn connection = new AMLEPCCxn(cxnOcc, applicationSystem, target);
						connection.setType(type);
						parseCxnLayout(connection, cxnOcc);
						((AMLEPCApplicationSystem) applicationSystem).setCxn(connection);
						((EPCFunction) target).addApplicationSystem(applicationSystem);
					}else if (source != null && target == null){
						if(epc.getSystem(cxnOcc.getAttributes().getNamedItem(AMLHelper.TO_OBJ_OCC).getNodeValue()) != null){
							EPCSystem system = epc.getSystem(cxnOcc.getAttributes().getNamedItem(AMLHelper.TO_OBJ_OCC).getNodeValue());
							if (system != null && (source instanceof AMLEPCFunction)){
								AMLEPCCxn systemCxn = new AMLEPCCxn(cxnOcc, source, system);
								systemCxn.setType(type);
								parseCxnLayout(systemCxn, cxnOcc);
								system.setCxn(systemCxn);
								((EPCFunction) source).addSystem(system);
							}							
						}else if(epc.getDocument(cxnOcc.getAttributes().getNamedItem(AMLHelper.TO_OBJ_OCC).getNodeValue()) != null){
							EPCDocument document = epc.getDocument(cxnOcc.getAttributes().getNamedItem(AMLHelper.TO_OBJ_OCC).getNodeValue());
							if (document != null && (source instanceof AMLEPCFunction)){
								AMLEPCCxn documentCxn = new AMLEPCCxn(cxnOcc, source, document);
								documentCxn.setType(type);
								parseCxnLayout(documentCxn, cxnOcc);
								document.setCxn(documentCxn);
								((EPCFunction) source).addDocument(document);
							}
						}else if(epc.getApplicationSystem(cxnOcc.getAttributes().getNamedItem(AMLHelper.TO_OBJ_OCC).getNodeValue()) != null){
							EPCApplicationSystem applicationSystem = epc.getApplicationSystem(cxnOcc.getAttributes().getNamedItem(AMLHelper.TO_OBJ_OCC).getNodeValue());
							if (applicationSystem != null && (source instanceof AMLEPCFunction)){
								AMLEPCCxn documentCxn = new AMLEPCCxn(cxnOcc, source, applicationSystem);
								documentCxn.setType(type);
								parseCxnLayout(documentCxn, cxnOcc);
								applicationSystem.setCxn(documentCxn);
								((EPCFunction) source).addApplicationSystem(applicationSystem);
							}							
						}
					}
					cxnOcc.getParentNode().removeChild(cxnOcc);
				}
			}
		}
		
		epc.setCxnMap(cxns);
	}
	
	private void parseAllObjectDefinitions(){
		NodeList objDefs = document.getElementsByTagName("ObjDef");
		for(int i = 0; i < objDefs.getLength(); i ++){
			allObjDefs.put(objDefs.item(i).getAttributes().getNamedItem(AMLHelper.OBJ_DEF_ID).getNodeValue(), objDefs.item(i));
			if(objDefs.item(i).getAttributes().getNamedItem("LinkedModels.IdRefs") != null)
				linkedModels.put(objDefs.item(i).getAttributes().getNamedItem(AMLHelper.OBJ_DEF_ID).getNodeValue(), objDefs.item(i).getAttributes().getNamedItem("LinkedModels.IdRefs").getNodeValue());
		}
	}

	private void parseAllModels(){
		NodeList model = document.getElementsByTagName("Model");
		for(int i = 0; i < model.getLength(); i ++)
			model2group.put(model.item(i).getAttributes().getNamedItem(AMLHelper.MODEL_ID).getNodeValue(), model.item(i).getParentNode().getAttributes().getNamedItem(AMLHelper.GROUP_ID).getNodeValue());
	}

	private void parseAllGroups(){
		NodeList groups = document.getElementsByTagName("Group");
		for(int i = 0; i < groups.getLength(); i ++){
			if(groups.item(i).getParentNode().getNodeName().equals("AML")){
				group2group.put(groups.item(i).getAttributes().getNamedItem(AMLHelper.GROUP_ID).getNodeValue(), "");
				if(!groups.item(i).getAttributes().getNamedItem(AMLHelper.GROUP_ID).getNodeValue().equals(AMLHelper.ROOT_GROUP)){
					groupId2groupName.put(groups.item(i).getAttributes().getNamedItem(AMLHelper.GROUP_ID).getNodeValue(), AMLHelper.getObjAttrValue(groups.item(i), AMLHelper.AT_NAME));
				}else{
					groupId2groupName.put(AMLHelper.ROOT_GROUP, AMLHelper.ROOT_GROUP);
				}
			}
			else{
				group2group.put(groups.item(i).getAttributes().getNamedItem(AMLHelper.GROUP_ID).getNodeValue(), groups.item(i).getParentNode().getAttributes().getNamedItem(AMLHelper.GROUP_ID).getNodeValue());
				if(!groups.item(i).getAttributes().getNamedItem(AMLHelper.GROUP_ID).getNodeValue().equals(AMLHelper.ROOT_GROUP)){
					groupId2groupName.put(groups.item(i).getAttributes().getNamedItem(AMLHelper.GROUP_ID).getNodeValue(), AMLHelper.getObjAttrValue(groups.item(i), AMLHelper.AT_NAME));
				}else{
					groupId2groupName.put(AMLHelper.ROOT_GROUP, AMLHelper.ROOT_GROUP);
				}
			}
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Collection<String> getModelIds() {
		return this.models.keySet();
	}
	
	private void parseNodeLayout(AMLEPCNode node, Node objOcc){
		NodeList children = objOcc.getChildNodes();

		if(objOcc.getAttributes().getNamedItem(AMLHelper.ACTIVE) != null)
			if(objOcc.getAttributes().getNamedItem(AMLHelper.ACTIVE).getNodeValue().equals("YES"))
				node.setActive(true);
			else
				node.setActive(false);
		
		if(objOcc.getAttributes().getNamedItem(AMLHelper.SHADOW) != null)
			if(objOcc.getAttributes().getNamedItem(AMLHelper.SHADOW).getNodeValue().equals("YES"))
				node.setShadow(true);
			else
				node.setShadow(false);

		if(objOcc.getAttributes().getNamedItem(AMLHelper.VISIBLE) != null)
			if(objOcc.getAttributes().getNamedItem(AMLHelper.VISIBLE).getNodeValue().equals("YES"))
				node.setVisible(true);
			else
				node.setVisible(false);
		
		for(int i = 0; i < children.getLength(); i ++){
			Node child = children.item(i);
			if(child.getNodeName().equals(AMLHelper.POSITION))
				try {
					node.setPosition(new Position(Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.POSITION_X).getNodeValue()), Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.POSITION_Y).getNodeValue())));	
				} catch (NumberFormatException e) {
					node.setPosition(new Position(0, 0));
				}
			if(child.getNodeName().equals(AMLHelper.SIZE))
				try {
					node.setSize(new Size(Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.SIZE_X).getNodeValue()), Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.SIZE_Y).getNodeValue())));	
				} catch (NumberFormatException e) {
					node.setSize(new Size(0, 0));
				}
			if(child.getNodeName().equals(AMLHelper.ATTR_OCC)){
					Attribute attribute = new Attribute(child.getAttributes().getNamedItem(AMLHelper.ATTR_TYPE_NUM).getNodeValue());
					if(child.getAttributes().getNamedItem(AMLHelper.ATTR_ALIGNMENT) != null)
						attribute.setAlignment(child.getAttributes().getNamedItem(AMLHelper.ATTR_ALIGNMENT).getNodeValue());
					if(child.getAttributes().getNamedItem(AMLHelper.ATTR_FONT) != null)
						attribute.setFont(child.getAttributes().getNamedItem(AMLHelper.ATTR_FONT).getNodeValue());
					if(child.getAttributes().getNamedItem(AMLHelper.ATTR_PORT) != null)
						attribute.setPort(child.getAttributes().getNamedItem(AMLHelper.ATTR_PORT).getNodeValue());
					if(child.getAttributes().getNamedItem(AMLHelper.ATTR_SYMBOL_FLAG) != null)
						attribute.setSymbolFlag(child.getAttributes().getNamedItem(AMLHelper.ATTR_SYMBOL_FLAG).getNodeValue());
					try {
						if(child.getAttributes().getNamedItem(AMLHelper.ATTR_OFFSET_X) != null && child.getAttributes().getNamedItem(AMLHelper.ATTR_OFFSET_Y) != null)
							attribute.setOffset(new Offset(Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.ATTR_OFFSET_X).getNodeValue()), Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.ATTR_OFFSET_Y).getNodeValue())));
					} catch (NumberFormatException e) {
						attribute.setOffset(new Offset(0, 0));
					}
					try {
						if(child.getAttributes().getNamedItem(AMLHelper.ATTR_ORDER_NUM) != null)
							attribute.setOrderNum(Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.ATTR_ORDER_NUM).getNodeValue()));
					} catch (NumberFormatException e) {
						attribute.setOrderNum(0);
					}				
					node.addAttribute(attribute);
			}
			if(child.getNodeName().equals(AMLHelper.PEN))
				try {
					node.setPen(new Pen(child.getAttributes().getNamedItem(AMLHelper.COLOR).getNodeValue(), Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.STYLE).getNodeValue()), Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.WIDTH).getNodeValue())));
				} catch (NumberFormatException e) {
					node.setPen(new Pen("0", 0, 0));
				}
			if(child.getNodeName().equals(AMLHelper.BRUSH))
				try {
					node.setBrush(new Brush(child.getAttributes().getNamedItem(AMLHelper.COLOR).getNodeValue(), Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.STYLE).getNodeValue()), Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.HATCH).getNodeValue())));
				} catch (NumberFormatException e) {
					node.setBrush(new Brush("0", 0, 0));
				}
		}
	}
	
	private void parseCxnLayout(AMLEPCCxn cxn, Node objOcc){
		
		if(objOcc.getAttributes().getNamedItem(AMLHelper.CXN_OCC_ID) != null)
			cxn.setId(objOcc.getAttributes().getNamedItem(AMLHelper.CXN_OCC_ID).getNodeValue());
		
		if(objOcc.getAttributes().getNamedItem(AMLHelper.HINTS) != null)
			try {
				cxn.setHints(Integer.parseInt(objOcc.getAttributes().getNamedItem(AMLHelper.HINTS).getNodeValue()));
			} catch (NumberFormatException e) {
				cxn.setHints(0);
			}
			
		if(objOcc.getAttributes().getNamedItem(AMLHelper.ZORDER) != null)
			try {
				cxn.setZorder(Integer.parseInt(objOcc.getAttributes().getNamedItem(AMLHelper.ZORDER).getNodeValue()));				
			} catch (NumberFormatException e) {
				cxn.setZorder(0);
			}
			
		if(objOcc.getAttributes().getNamedItem(AMLHelper.ARROW_SRC) != null)
			try {
				cxn.setSrcArrow(Integer.parseInt(objOcc.getAttributes().getNamedItem(AMLHelper.ARROW_SRC).getNodeValue()));				
			} catch (NumberFormatException e) {
				cxn.setSrcArrow(0);
			}
			
		if(objOcc.getAttributes().getNamedItem(AMLHelper.ARROW_TGT) != null)
			try {
				cxn.setTgtArrow(Integer.parseInt(objOcc.getAttributes().getNamedItem(AMLHelper.ARROW_TGT).getNodeValue()));
			} catch (NumberFormatException e) {
				cxn.setTgtArrow(0);
			}
		
		if(objOcc.getAttributes().getNamedItem(AMLHelper.ACTIVE) != null)
			if(objOcc.getAttributes().getNamedItem(AMLHelper.ACTIVE).getNodeValue().equals("YES"))
				cxn.setActive(true);
			else
				cxn.setActive(false);
		
		if(objOcc.getAttributes().getNamedItem(AMLHelper.SHADOW) != null)
			if(objOcc.getAttributes().getNamedItem(AMLHelper.SHADOW).getNodeValue().equals("YES"))
				cxn.setShadow(true);
			else
				cxn.setShadow(false);

		if(objOcc.getAttributes().getNamedItem(AMLHelper.VISIBLE) != null)
			if(objOcc.getAttributes().getNamedItem(AMLHelper.VISIBLE).getNodeValue().equals("YES"))
				cxn.setVisible(true);
			else
				cxn.setVisible(false);

		if(objOcc.getAttributes().getNamedItem(AMLHelper.DIAGONAL) != null)
			if(objOcc.getAttributes().getNamedItem(AMLHelper.DIAGONAL).getNodeValue().equals("YES"))
				cxn.setDiagonal(true);
			else
				cxn.setDiagonal(false);
		
		if(objOcc.getAttributes().getNamedItem(AMLHelper.EMBEDDING) != null)
			if(objOcc.getAttributes().getNamedItem(AMLHelper.EMBEDDING).getNodeValue().equals("YES"))
				cxn.setEmbedding(true);
			else
				cxn.setEmbedding(false);
		
		NodeList children = objOcc.getChildNodes();
		try {
			cxn.setSrcArrow(Integer.parseInt(objOcc.getAttributes().getNamedItem(AMLHelper.ARROW_SRC).getNodeValue()));	
		} catch (NumberFormatException e) {
			cxn.setSrcArrow(0);
		}
		
		try {
			cxn.setTgtArrow(Integer.parseInt(objOcc.getAttributes().getNamedItem(AMLHelper.ARROW_TGT).getNodeValue()));	
		} catch (NumberFormatException e) {
			cxn.setTgtArrow(0);
		}		
		for(int i = 0; i < children.getLength(); i ++){
			Node child = children.item(i);
			if(child.getNodeName().equals(AMLHelper.POSITION))
				try {
					cxn.addPosition(new Position(Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.POSITION_X).getNodeValue()), Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.POSITION_Y).getNodeValue())));
				} catch (NumberFormatException e) {
					cxn.addPosition(new Position(0, 0));				
				}
			if(child.getNodeName().equals(AMLHelper.PEN))
				try {
					cxn.setPen(new Pen(child.getAttributes().getNamedItem(AMLHelper.COLOR).getNodeValue(), Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.STYLE).getNodeValue()), Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.WIDTH).getNodeValue())));
				} catch (NumberFormatException e) {
					cxn.setPen(new Pen("0", 0, 0));
				}
			if(child.getNodeName().equals(AMLHelper.BRUSH))
				try {
					cxn.setBrush(new Brush(child.getAttributes().getNamedItem(AMLHelper.COLOR).getNodeValue(), Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.STYLE).getNodeValue()), Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.HATCH).getNodeValue())));
				} catch (NumberFormatException e) {
					cxn.setBrush(new Brush("0", 0, 0));
				}
				
			if(child.getNodeName().equals(AMLHelper.ATTR_OCC)){
				Attribute attribute = new Attribute(child.getAttributes().getNamedItem(AMLHelper.ATTR_TYPE_NUM).getNodeValue());
				if(child.getAttributes().getNamedItem(AMLHelper.ATTR_ALIGNMENT) != null)
					attribute.setAlignment(child.getAttributes().getNamedItem(AMLHelper.ATTR_ALIGNMENT).getNodeValue());
				if(child.getAttributes().getNamedItem(AMLHelper.ATTR_FONT) != null)
					attribute.setFont(child.getAttributes().getNamedItem(AMLHelper.ATTR_FONT).getNodeValue());
				if(child.getAttributes().getNamedItem(AMLHelper.ATTR_PORT) != null)
					attribute.setPort(child.getAttributes().getNamedItem(AMLHelper.ATTR_PORT).getNodeValue());
				if(child.getAttributes().getNamedItem(AMLHelper.ATTR_SYMBOL_FLAG) != null)
					attribute.setSymbolFlag(child.getAttributes().getNamedItem(AMLHelper.ATTR_SYMBOL_FLAG).getNodeValue());
				try {
					if(child.getAttributes().getNamedItem(AMLHelper.ATTR_OFFSET_X) != null && child.getAttributes().getNamedItem(AMLHelper.ATTR_OFFSET_Y) != null)
						attribute.setOffset(new Offset(Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.ATTR_OFFSET_X).getNodeValue()), Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.ATTR_OFFSET_Y).getNodeValue())));
				} catch (NumberFormatException e) {
					attribute.setOffset(new Offset(0, 0));
				}
				try {
					if(child.getAttributes().getNamedItem(AMLHelper.ATTR_ORDER_NUM) != null)
						attribute.setOrderNum(Integer.parseInt(child.getAttributes().getNamedItem(AMLHelper.ATTR_ORDER_NUM).getNodeValue()));
				} catch (NumberFormatException e) {
					attribute.setOrderNum(0);
				}				
				cxn.addAttribute(attribute);
			}
		}
	}
}