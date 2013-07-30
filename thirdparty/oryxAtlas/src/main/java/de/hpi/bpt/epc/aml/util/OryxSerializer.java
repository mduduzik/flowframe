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
import java.util.HashSet;
import java.util.Iterator;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

import de.hpi.bpt.epc.EPC;
import de.hpi.bpt.epc.EPCApplicationSystem;
import de.hpi.bpt.epc.EPCConnector;
import de.hpi.bpt.epc.EPCCxn;
import de.hpi.bpt.epc.EPCDocument;
import de.hpi.bpt.epc.EPCNode;
import de.hpi.bpt.epc.EPCObject;
import de.hpi.bpt.epc.EPCOrganization;
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
import de.hpi.bpt.epc.aml.AMLEPCRole;
import de.hpi.bpt.epc.aml.AMLEPCSystem;
import de.hpi.bpt.epc.aml.AMLEPCTextNote;
import de.hpi.bpt.epc.aml.layout.Brush;
import de.hpi.bpt.epc.aml.layout.Position;
import de.hpi.bpt.epc.aml.layout.Size;

/**
 * 
 * 
 * @author Nicolas Peters, Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public class OryxSerializer {

	private final String EPCSSNAMESPACE = "http://b3mn.org/stencilset/epc#";
	
	private final String EPCSSURL = "/stencilsets/epc/epc.json";
	
	private final double CS_CORRECTION_FACTOR = 0.33;
	
	private final String[] DIRECTED_CXN_TYPES = {"CT_PROV_INP_FOR",
												 "CT_HAS_OUT",
												 "CT_IS_INP_FOR"};
	
	private String oryxBaseUrl;
	
	private Collection<EPC> epcs;

	private Document document;
	
	private String modelId;
	
	private Element epcsDiv, processDataDiv, canvasDiv;

	public OryxSerializer(EPC epc, String oryxBaseUrl) {
		this.epcs = new HashSet<EPC>();
		this.epcs.add(epc);
		this.oryxBaseUrl = oryxBaseUrl;
	}

	public OryxSerializer(Collection<EPC> epcs, String oryxBaseUrl) {
		this.epcs = epcs;
		this.oryxBaseUrl = oryxBaseUrl;
	}
	
	public void parse() throws Exception {
		document = new DocumentImpl();
		
		//create outermost div
		epcsDiv = this.createDiv("epcs", null);
		document.appendChild(epcsDiv);

		Iterator<EPC> epcs = this.epcs.iterator();

		while(epcs.hasNext())
		{
			AMLEPC epc = (AMLEPC)(epcs.next());
			
			modelId = makeId(epc.getModelId());
			
			//create processdata div
			processDataDiv = this.createDiv("processdata", null);
			Attr attr = document.createAttribute("style");
			attr.setValue("display: none;");
			processDataDiv.setAttributeNode(attr);
			epcsDiv.appendChild(processDataDiv);

			//create canvasDiv
			processDataDiv.appendChild(createCanvasDiv(epc));
			
			//iterate nodes
			Iterator<EPCNode> nodes = epc.getNodes().iterator();

			while (nodes.hasNext()) {
				AMLEPCNode node = (AMLEPCNode) nodes.next();
				processNode(node);
			}
			
			//iterate control flows
			Iterator<EPCCxn> connections = epc.getCxns().iterator();
			
			while (connections.hasNext()) {
				AMLEPCCxn con = (AMLEPCCxn) connections.next();
				processControlFlow(con);
			}
			
			//iterate text notes
			Iterator<AMLEPCTextNote> textNotes = epc.getMetaInfo().getTextNotes().values().iterator();
			
			while(textNotes.hasNext()) {
				AMLEPCTextNote textNote = textNotes.next();
				
				//workaround for missing size information of text notes
				Size size = textNote.getSize();
				String text = textNote.getName();
				String[] lines = text.split("\n");
				
				if(size.getX() == 0) {
					String longestLine = "";
					for (int i = 0; i < lines.length; i++) {
						if(longestLine.length() < lines[i].length()) {
							longestLine = lines[i];
						}
					}
					size.setX((int)((longestLine.length()*7)/CS_CORRECTION_FACTOR));
				}
				if(size.getY() == 0) {
					size.setY((int)((lines.length*12 + 20)/CS_CORRECTION_FACTOR));
				}
				
				//workaround, because in AML position is the center of the text note
				Position pos = textNote.getPosition();
				pos.setX(pos.getX() - size.getX()/2);
				pos.setY(pos.getY() - size.getY()/2);
				
				processNode(textNote);
			}
		}
	}
	
	
	//processing methods
	private Element processNode(AMLEPCNode node) throws Exception {
		
		//create render span in canvas div
		canvasDiv.appendChild(createA("oryx-render", "#" + makeId(node)));
		
		//create resource div
		Element elem = createDiv(null, makeId(node));
		processDataDiv.appendChild(elem);
		
		//parent
		elem.appendChild(createA("raziel-parent", "#" + modelId));	
		
		//outgoing connections
		Iterator<EPCCxn> connections = node.getOutConnections().iterator();
		while(connections.hasNext()) {
			EPCCxn connection = connections.next();
			elem.appendChild(createA("raziel-outgoing", "#" + makeId(connection)));
		}
		
		//bounds
		String bounds = node.getPosition().getX()*CS_CORRECTION_FACTOR + "," + node.getPosition().getY()*CS_CORRECTION_FACTOR;
		bounds += "," + (node.getPosition().getX()*CS_CORRECTION_FACTOR + node.getSize().getX()*CS_CORRECTION_FACTOR);
		bounds += "," + (node.getPosition().getY()*CS_CORRECTION_FACTOR + node.getSize().getY()*CS_CORRECTION_FACTOR);
		elem.appendChild(createSpan("oryx-bounds", null, bounds));
		
		//color
		Brush brush = node.getBrush();
		if(brush != null) {
			String color = brush.getColor();
			if(color != null && color.trim() != "") {
				elem.appendChild(createStencilColor(node.getBrush().getColor()));
			}
			
		}
		
		//title
		elem.appendChild(createSpan("oryx-title", null, node.getName()));
		
		//parse node specific information
		if(node instanceof AMLEPCFunction) {
			
			AMLEPCFunction function = (AMLEPCFunction) node;
			processFunction(function, elem);
			
		} else if (node instanceof AMLEPCEvent) {
			
			AMLEPCEvent event = (AMLEPCEvent) node;
			processEvent(event, elem);
			
		} else if (node instanceof AMLEPCConnector) {
			
			AMLEPCConnector con = (AMLEPCConnector) node;
			processConnector(con, elem);
			
		} else if (node instanceof AMLEPCTextNote) {
			AMLEPCTextNote textNote = (AMLEPCTextNote) node;
			processTextNote(textNote, elem);
		} else {
			//error
			//System.err.println(node.toString());
		}
		
		return elem;
	}
	
	private void processTextNote(AMLEPCTextNote textNote, Element elem) {
		elem.appendChild(createStencilType("TextNote"));
	}

	private void processFunction(AMLEPCFunction function, Element elem) throws Exception {
		
		//stencil type
		if(function.isProcessInterface()) {
			elem.appendChild(createStencilType("ProcessInterface"));
		} else {
			elem.appendChild(createStencilType("Function"));
		}
		
		String refId = function.getReferencedModelId();
		
		if(refId != null && refId != "") {
			elem.appendChild(createSpan("oryx-refuri", null, makeId(refId)));
		}
		
		//roles
		processRoles((AMLEPCFunction)function);
		Iterator<EPCRole> roles = function.getRoles().iterator();
		while(roles.hasNext()) {
			AMLEPCRole role = (AMLEPCRole)(roles.next());
			
			AMLEPCCxn con = role.getCxn();
			
			if(con.getSource() == function)
				elem.appendChild(createA("raziel-outgoing", "#" + makeId(con)));
			
			processRelation(con);
		}
		
		//systems
		processSystems((AMLEPCFunction)function);
		Iterator<EPCSystem> systems = function.getSystems().iterator();
		while(systems.hasNext()) {
			AMLEPCSystem system = (AMLEPCSystem)(systems.next());
			
			AMLEPCCxn con = (AMLEPCCxn)(system.getCxn());
			
			if(con.getSource() == function)
				elem.appendChild(createA("raziel-outgoing", "#" + makeId(con)));
			
			processRelation(con);
		}
		
		//application systems
		processApplicationSystems((AMLEPCFunction)function);
		Iterator<EPCApplicationSystem> appSystems = function.getApplicationSystems().iterator();
		while(appSystems.hasNext()) {
			AMLEPCApplicationSystem appSystem = (AMLEPCApplicationSystem)(appSystems.next());
			
			AMLEPCCxn con = (AMLEPCCxn)(appSystem.getCxn());
			
			if(con.getSource() == function)
				elem.appendChild(createA("raziel-outgoing", "#" + makeId(con)));
			
			processRelation(con);
		}
		
		//documents
		processDocuments((AMLEPCFunction)function);
		Iterator<EPCDocument> documents = function.getDocuments().iterator();
		while(documents.hasNext()) {
			AMLEPCDocument document = (AMLEPCDocument)(documents.next());
			
			AMLEPCCxn con = (AMLEPCCxn)(document.getCxn());
			
			if(con.getSource() == function)
				elem.appendChild(createA("raziel-outgoing", "#" + makeId(con)));
			
			processRelation(con);
		}
		
		//organizations
		processOrganizations((AMLEPCFunction)function);
		Iterator<EPCOrganization> organizations = function.getOrganizations().iterator();
		while(organizations.hasNext()) {
			AMLEPCOrganization organization = (AMLEPCOrganization)(organizations.next());
			
			AMLEPCCxn con = (AMLEPCCxn)(organization.getCxn());
			
			if(con.getSource() == function)
				elem.appendChild(createA("raziel-outgoing", "#" + makeId(con)));
			
			processRelation(con);
		}
	}
	
	private void processEvent(AMLEPCEvent event, Element elem) {
		elem.appendChild(createStencilType("Event"));
	}
	
	private void processConnector(AMLEPCConnector con, Element elem) {
		if(con.getType() == EPCConnector.AND) {
			elem.appendChild(createStencilType("AndConnector"));
		} else if(con.getType() == EPCConnector.OR) {
			elem.appendChild(createStencilType("OrConnector"));
		} else if(con.getType() == EPCConnector.XOR) {
			elem.appendChild(createStencilType("XorConnector"));
		} else {
			//error
			System.err.println("Undefined connector: " + con.toString());
		}
	}
	
	private void processRelation(AMLEPCCxn connection) throws Exception {
		
		AMLEPCNode source = (AMLEPCNode)(connection.getSource());
		AMLEPCNode target = (AMLEPCNode)(connection.getTarget());
		Element elem = processConnection(connection, "Relation", source, target);
		
		if(isCxnDirected(connection.getType())) {
			elem.appendChild(createSpan("oryx-informationflow", null, "True"));
		} else {
			elem.appendChild(createSpan("oryx-informationflow", null, "False"));
		}
	}
	
	private void processControlFlow(AMLEPCCxn connection) throws Exception {
		
		AMLEPCNode source = (AMLEPCNode)(connection.getSource());
		AMLEPCNode target = (AMLEPCNode)(connection.getTarget());
		processConnection(connection, "ControlFlow", source, target);

	}

	private Element processConnection(AMLEPCCxn connection, String stencilType, AMLEPCNode source, AMLEPCNode target) throws Exception {
		//create render span in canvas div
		canvasDiv.appendChild(createA("oryx-render", "#" + makeId(connection)));
		
		//create resource div
		Element elem = createDiv(null, makeId(connection));
		processDataDiv.appendChild(elem);
		
		elem.appendChild(createStencilType(stencilType));
		
		elem.appendChild(createA("raziel-outgoing", "#" + makeId(connection.getTarget())));
		elem.appendChild(createA("raziel-target", "#" + makeId(connection.getTarget())));
		elem.appendChild(createA("raziel-parent", "#" + modelId));	
		
		//dockers
		Iterator<Position> positions = connection.getPositions().iterator();
		
		String dockers = "";
		
		Position pos = null;
		
		if (positions.hasNext()) {
			pos = positions.next();
			dockers += (pos.getX() - source.getPosition().getX())*CS_CORRECTION_FACTOR + " " + (pos.getY() - source.getPosition().getY())*CS_CORRECTION_FACTOR + " ";
		} else {
			dockers = source.getSize().getX()*CS_CORRECTION_FACTOR/2.0 + " " + source.getSize().getY()*CS_CORRECTION_FACTOR/2.0 + " ";
		}
		
		
		while(positions.hasNext()) {
			pos = positions.next();
			
			//skip last
			if(positions.hasNext()) {
				dockers += pos.getX()*CS_CORRECTION_FACTOR + " " + pos.getY()*CS_CORRECTION_FACTOR + " ";
			}
		}
		
		if(pos != null) {
			dockers += (pos.getX() - target.getPosition().getX())*CS_CORRECTION_FACTOR + " " + (pos.getY() - target.getPosition().getY())*CS_CORRECTION_FACTOR;
		} else {
			dockers += target.getSize().getX()*CS_CORRECTION_FACTOR/2.0 + " " + target.getSize().getY()*CS_CORRECTION_FACTOR/2.0 + " # ";
		}
		
		elem.appendChild(createSpan("oryx-dockers", null, dockers));
		
		return elem;
	}

	private void processRoles(AMLEPCFunction function) throws Exception {
		Iterator<EPCRole> roles = function.getRoles().iterator();
		
		while(roles.hasNext()) {
			AMLEPCRole role = (AMLEPCRole)roles.next();
			
			Element elem = processNode(role);
				
			//stencil type
			elem.appendChild(createStencilType("Position"));
			
			//outgoing
			AMLEPCCxn con = role.getCxn();
			
			if(con.getSource() == role)
				elem.appendChild(createA("raziel-outgoing", "#" + makeId(con)));
		}
	}

	private void processSystems(AMLEPCFunction function) throws Exception {
		Iterator<EPCSystem> systems = function.getSystems().iterator();
		
		while(systems.hasNext()) {
			AMLEPCSystem system = (AMLEPCSystem)systems.next();
			
			Element elem = processNode(system);
			
			//stencil type
			elem.appendChild(createStencilType("System"));

			//outgoing
			AMLEPCCxn con = (AMLEPCCxn)(system.getCxn());
			
			if(con.getSource() == system)
				elem.appendChild(createA("raziel-outgoing", "#" + makeId(con)));
		}
	}

	private void processApplicationSystems(AMLEPCFunction function) throws Exception {
		Iterator<EPCApplicationSystem> systems = function.getApplicationSystems().iterator();
		
		while(systems.hasNext()) {
			AMLEPCApplicationSystem system = (AMLEPCApplicationSystem)systems.next();
			
			Element elem = processNode(system);
	
			//stencil type
			elem.appendChild(createStencilType("System"));
			
			//outgoing
			AMLEPCCxn con = (AMLEPCCxn)(system.getCxn());
			
			if(con.getSource() == system)
				elem.appendChild(createA("raziel-outgoing", "#" + makeId(con)));
		}
	}

	private void processDocuments(AMLEPCFunction function) throws Exception {
		Iterator<EPCDocument> documents = function.getDocuments().iterator();
		
		while(documents.hasNext()) {
			AMLEPCDocument document = (AMLEPCDocument)documents.next();
			
			Element elem = processNode(document);
	
			//stencil type
			elem.appendChild(createStencilType("Data"));

			//outgoing
			AMLEPCCxn con = (AMLEPCCxn)(document.getCxn());
			
			if(con.getSource() == document)
				elem.appendChild(createA("raziel-outgoing", "#" + makeId(con)));
		}
	}
	
	private void processOrganizations(AMLEPCFunction function) throws Exception {
		Iterator<EPCOrganization> organisations = function.getOrganizations().iterator();
		
		while(organisations.hasNext()) {
			AMLEPCOrganization organisation = (AMLEPCOrganization)organisations.next();
			
			Element elem = processNode(organisation);

			//stencil type
			elem.appendChild(createStencilType("Organization"));

			//outgoing
			AMLEPCCxn con = (AMLEPCCxn)(organisation.getCxn());
			
			if(con.getSource() == organisation)
				elem.appendChild(createA("raziel-outgoing", "#" + makeId(con)));
		}
	}
	
	
	
	//helper methods
	private Element createCanvasDiv(AMLEPC amlepc) throws Exception {
		canvasDiv = createDiv("-oryx-canvas", makeId(amlepc.getModelId()));
		canvasDiv.appendChild(createStencilType("Diagram"));
		canvasDiv.appendChild(createSpan("oryx-mode", null, "writable"));
		canvasDiv.appendChild(createSpan("oryx-mode", null, "fullscreen"));
		canvasDiv.appendChild(createSpan("oryx-title", null, amlepc.getTitle()));
		canvasDiv.appendChild(createA("oryx-stencilset", this.oryxBaseUrl + EPCSSURL));
		return canvasDiv;
	}
	
	private Element createStencilType(String id) {
		return createSpan("oryx-type", null, EPCSSNAMESPACE + id);
	}
	
	private Element createStencilColor(String color) {
		if(color.length() == 6) {
			color = color.substring(4, 6) + color.substring(2, 4) + color.substring(0, 2);
		}
		
		
		return createSpan("oryx-bgcolor", null, "#" + color);
	}
	
	private Element createNode(String type, String zclass, String id, String rel, String href, String content) {
		Element elem = document.createElement(type);
		Attr attr;
		
		if(zclass != null) {
			attr = document.createAttribute("class");
			attr.setValue(zclass);
			elem.setAttributeNode(attr);
		}
		
		if(id != null) {
			attr = document.createAttribute("id");
			attr.setValue(id);
			elem.setAttributeNode(attr);
		}
		
		if(rel != null) {
			attr = document.createAttribute("rel");
			attr.setValue(rel);
			elem.setAttributeNode(attr);
		}
		
		if(href != null) {
			attr = document.createAttribute("href");
			attr.setValue(href);
			elem.setAttributeNode(attr);
		}
		
		elem.setTextContent(content);
		
		return elem;
	}
	
	private Element createDiv(String zclass, String id) {
		return this.createNode("div", zclass, id, null, null, null);
	}
	
	private Element createSpan(String zclass, String id, String content) {
		return this.createNode("span", zclass, id, null, null, content);
	}
	
	private Element createA(String rel, String href) {
		return this.createNode("a", null, null, rel, href, null);
	}

	private String makeId(EPCObject epco) throws Exception {
		String badId = epco.getId();
		if(badId == null || badId == "")
			throw new Exception("No id defined in object " + epco.toString());
		
		return badId.replaceAll("[.-]", "");
	}
	
	private String makeId(String badId) throws Exception {
		if(badId == null || badId == "")
			throw new Exception("Id is null or empty.");
		
		return badId.replaceAll("[.-]", "");
	}
	
	private boolean isCxnDirected(String type) {
		for(int i = 0; i < DIRECTED_CXN_TYPES.length; i++) {
			if(DIRECTED_CXN_TYPES[i].equalsIgnoreCase(type))
				return true;
		}
		
		return false;
	}
	
	
	//getter/setter
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