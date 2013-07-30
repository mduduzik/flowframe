package de.hpi.bpt.epc.aml.util;

import java.util.Iterator;
import java.util.UUID;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.hpi.bpt.epc.aml.AMLEPCNode;
import de.hpi.bpt.epc.aml.Attribute;

public class AMLHelper {
	
	/*
	 * User which performed the abstraction
	 */
	public static final String BPT_USER = "BPT Abstractor";
	
	/*
	 * Names of the node attributes in AML
	 */
	public static final String CXN_DEF = "CxnDef.";
	public static final String CXN_DEF_ID = "CxnDef.ID";
	public static final String CXN_DEF_ID_REF = "CxnDef.IdRef";
	public static final String CXN_DEF_TYPE = "CxnDef.Type";
	public static final String CXN_OCC = "CxnOcc.";
	public static final String CXN_OCC_ID = "CxnOcc.ID";
	public static final String FF_TEXT_DEF_ID = "FFTextDef.ID";
	public static final String FF_TEXT_DEF_ID_REF = "FFTextDef.IdRef";
	public static final String GROUP_ID = "Group.ID";
	public static final String GROUP = "Group.";
	public static final String HINTS = "Hints";
	public static final String ZORDER = "Zorder";
	public static final String OBJ_DEF = "ObjDef.";
	public static final String OBJ_DEF_ID = "ObjDef.ID";
	public static final String OBJ_DEF_ID_REF = "ObjDef.IdRef";
	public static final String OBJ_OCC = "ObjOcc.";
	public static final String OBJ_OCC_ID = "ObjOcc.ID";
	public static final String MODEL = "Model.";
	public static final String MODEL_ID = "Model.ID";
	public static final String MODEL_TYPE = "Model.Type";
	public static final String SYMBOL_NUM = "SymbolNum";
	public static final String TO_CXN_DEF = "ToCxnDefs.IdRefs";
	public static final String TO_CXN_OCC = "ToCxnOccs.IdRefs";
	public static final String TO_OBJ_DEF = "ToObjDef.IdRef";
	public static final String TO_OBJ_OCC = "ToObjOcc.IdRef";
	public static final String TYPE_NUM = "TypeNum";
	
	/*
	 * Values of the node attributes in AML
	 */
	public static final String VAL_TYPE_NUM_FUNC = "ST_FUNC";
	public static final String VAL_TYPE_NUM_PIF	= "ST_PRCS_IF";
	
	public static final String VAL_ATTR_TYPE_NUM_TIME_AVG = "AT_TIME_AVG_PRCS";
	public static final String VAL_ATTR_TYPE_NUM_DESC = "AT_DESC";
	
	/*
	 * Types of attributes which object definitions and object occurrences can have
	 */
	public static final String AT_CREAT_TIME_STMP	= "AT_CREAT_TIME_STMP";
	public static final String AT_CREATOR = "AT_CREATOR";
	public static final String AT_DESCRIPTION = "AT_DESC";
	public static final String AT_FRQ_ANNO = "AT_FRQ_ANNO";
	public static final String AT_FUNC_AVG_TIME = "AT_TIME_AVG_PRCS";
	public static final String AT_ID = "AT_ID";
	public static final String AT_LAST_USER = "AT_LUSER";
	public static final String AT_LAST_CHANGE_TIME = "AT_LAST_CHNG_2";
	public static final String AT_NAME = "AT_NAME";
	public static final String AT_PROB = "AT_PROB";
	public static final String AT_REMARK = "AT_REM";
	public static final String AT_SHORT_DESC = "AT_SHORT_DESC";
	public static final String AT_USG_FACT = "AT_USG_FACT";
	
	public static final String PEN = "Pen";
	public static final String COLOR = "Color";
	public static final String STYLE = "Style";
	public static final String WIDTH = "Width";
	
	public static final String BRUSH = "Brush";
	public static final String HATCH = "Hatch";
	
	public static final String POSITION = "Position";
	public static final String POSITION_X = "Pos.X";
	public static final String POSITION_Y = "Pos.Y";
	public static final String SIZE = "Size";
	public static final String SIZE_X = "Size.dX";
	public static final String SIZE_Y = "Size.dY";
	
	public static final String ARROW_SRC = "SrcArrow";
	public static final String ARROW_TGT = "TgtArrow";
	
	public static final String ACTIVE = "Active";
	public static final String SHADOW = "Shadow";
	public static final String VISIBLE = "Visible";
	public static final String DIAGONAL = "Diagonal";
	public static final String EMBEDDING = "Embedding";
	
	public static final String ATTR_OCC = "AttrOcc";
	public static final String ATTR_TYPE_NUM = "AttrTypeNum";
	public static final String ATTR_PORT = "Port";
	public static final String ATTR_ORDER_NUM = "OrderNum";
	public static final String ATTR_ALIGNMENT = "Alignment";
	public static final String ATTR_SYMBOL_FLAG = "SymbolFlag";
	public static final String ATTR_FONT = "FontSS.IdRef";
	public static final String ATTR_OFFSET_X = "OffsetX";
	public static final String ATTR_OFFSET_Y = "OffsetY";
	
	public static final String YES = "YES";
	public static final String NO = "NO";
	
	public static final String ROOT_GROUP = "Group.Root";
	
	/*
	 * The supported version of ARIX AML format 
	 */
	public static final String ARIS_CURRENT_VERSION = "70";
	
	/*
	 * List of locales
	 */
	public static final String GERMAN_LOCALE = "1031";
	public static final String GERMAN_CODEPAGE = "1252";

	/*
	 * Codes for time intervals naming in AML and time conversion constants
	 */
	public static final String SECONDS = "43";
	public static final String MINUTES = "44";
	public static final String HOURS = "45";
	public static final String DAYS = "46";
	public static final String MONTHS = "47";
	public static final String YEARS = "48";
	
	private static final double secInMin = 60;
	private static final double minInHour = 60;
	private static final double hourInDay = 8;
	private static final double dayInMnth = 30;
	private static final double mnthInYear = 12;
	
	public static void addAttribute(Document document, Node node, String name, String value) {
		Attr attr = document.createAttribute(name);
		attr.setNodeValue(value);
		node.getAttributes().setNamedItem(attr);
	}

	public static String addObjDefId(Document document, Node node, int counter) {
		String id = generateId(counter, OBJ_DEF);
		addAttribute(document, node, OBJ_DEF_ID, id);
		return id;
	}

	public static String addGroupId(Document document, Node node, int counter) {
		String id = generateId(counter, GROUP);
		addAttribute(document, node, GROUP_ID, id);
		return id;
	}

	
	public static String addObjOccId(Document document, Node node, int counter) {
		String id = generateId(counter, OBJ_OCC);
		addAttribute(document, node, OBJ_OCC_ID, generateId(counter, OBJ_OCC));
		return id;
	}
	
	public static String generateId(int counter, String type) {
		String objDefId = Integer.toString(counter, 36);
		while(objDefId.length() < 6)
			objDefId = objDefId.concat("-");
		
		return type.concat(objDefId).concat("r-----p--0--");
	}
	
	public static void addGuid(Document document, Node node) {
		Node guid = document.createElement("GUID");
		guid.setTextContent((new UUID(System.nanoTime(), (long) Math.random())).toString());
		node.appendChild(guid);
	}
	
	public static Node addCxnDef(Document document, Node node, int counter) {
		Node cxnDef = document.createElement("CxnDef");
		addAttribute(document, cxnDef, CXN_DEF_ID, generateId(counter, CXN_DEF));
		addGuid(document, cxnDef);
		node.appendChild(cxnDef);
		
		return cxnDef;
	}
	
	public static void addAttrDef(Document document, Node node, String name, String value) {
		Node attrDefNode = document.createElement("AttrDef");
		
		Attr attribute = document.createAttribute("AttrDef.Type");
		attribute.setNodeValue(name);
		attrDefNode.getAttributes().setNamedItem(attribute);
		
		Node attrValueNode = document.createElement("AttrValue");
		attrValueNode.setTextContent(value);
		attribute = document.createAttribute("LocaleId");
		attribute.setNodeValue(GERMAN_LOCALE);
		attrValueNode.getAttributes().setNamedItem(attribute);
		attrValueNode.setNodeValue(value);

		attrDefNode.appendChild(attrValueNode);
		node.appendChild(attrDefNode);
	}

	public static void addAtName(Document document, Node node, String value) {
		addAttrDef(document, node, AT_NAME, value);
	}
	
	public static void addAtAnnFreq(Document document, Node node, String value) {
		addAttrDef(document, node, AT_FRQ_ANNO, value);
	}

	public static void addAtLUser(Document document, Node node, String value) {
		addAttrDef(document, node, AT_LAST_USER, value);
	}
	
	public static void addLChange2(Document document, Node node, String value) {
		addAttrDef(document, node, AT_LAST_CHANGE_TIME, value);
	}
	
	public static void addAtProb(Document document, Node node, String value) {
		addAttrDef(document, node, AT_PROB, value);
	}
	
	public static void addAtId(Document document, Node node, String value) {
		addAttrDef(document, node, AT_ID, value);
	}
	
	public static void addUsgFact(Document document, Node node, String value) {
		addAttrDef(document, node, AT_USG_FACT, value);
	}
	
	public static void addAtTimeAvgPrcs(Document document, Node node, String value) {
		addAttrDef(document, node, AT_FUNC_AVG_TIME, value);
	}
	
	public static void addAtShortDesc(Document document, Node node, String value) {
		addAttrDef(document, node, AT_SHORT_DESC, value);
	}
	
	public static void addAtDescription(Document document, Node node, String value) {
		addAttrDef(document, node, AT_DESCRIPTION, value);
	}
	
	public static void addAtCreator(Document document, Node node, String value) {
		addAttrDef(document, node, AT_CREATOR, value);
	}
	
	public static void addAtRem(Document document, Node node, String value) {
		addAttrDef(document, node, AT_REMARK, value);
	}

	public static void addAtCreateTimeStamp(Document document, Node node, String value) {
		addAttrDef(document, node, AT_CREAT_TIME_STMP, value);
	}
	
	public static void addHeaderInfo(Document document, Node node) {
		Node headerInfo = document.createElement("Header-Info");
		addAttribute(document, headerInfo, "ArisExeVersion", ARIS_CURRENT_VERSION);
		node.appendChild(headerInfo);
	}
	
	public static void addLanguages(Document document, Node node) {
		Node language = document.createElement("Language");
		addAttribute(document, language, "LocaleId", GERMAN_LOCALE);
		addAttribute(document, language, "Codepage", GERMAN_CODEPAGE);
		node.appendChild(language);
	}
	
	/**
	 * TODO - Optimize! Get Object Node attribute value
	 * 
	 * @param n
	 *            XML DOM Node to look in for attribute
	 * @param attr
	 *            Attribute name
	 * @return Attribute value, null is no attribute
	 */
	public static String getObjAttrValue(Node n, String attr) {
		NodeList list = n.getChildNodes();

		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeName().equals("AttrDef")
					&& list.item(i).getAttributes()
							.getNamedItem("AttrDef.Type").getNodeValue()
							.equals(attr)) {
				for (int m = 0; m < list.item(i).getChildNodes().getLength(); m ++) {
					if (list.item(i).getChildNodes().item(m).getNodeName()
							.equals("AttrValue")) {
						String value = list.item(i).getChildNodes().item(m)
								.getTextContent().trim();
						return value;
					}
				}
			}
		}

		return null;
	}
	
	public static double getDuration(Node node) {
		String functionDuration = getObjAttrValue(node, AT_FUNC_AVG_TIME);
		  
		if (functionDuration != null){
			String[] substrings = functionDuration.split(";");
			if(substrings[1].equals(SECONDS)) {
			  Double d = Double.parseDouble(substrings[0]);
			  if(d != null)
				  return d / secInMin;
			}else if(substrings[1].equals(MINUTES)) {
			  Double d = Double.parseDouble(substrings[0]);
			  if(d != null)
				  return d;  
			}else if(substrings[1].equals(HOURS)) {
			  Double d = Double.parseDouble(substrings[0]);
			  if(d != null)
				  return d * minInHour;  
			}else if(substrings[1].equals(DAYS)) {
				Double d = Double.parseDouble(substrings[0]);
				if(d != null)
					return d * minInHour * hourInDay;
			}else if(substrings[1].equals(MONTHS)) {
				Double d = Double.parseDouble(substrings[0]);
				if(d != null)
					return d * minInHour * hourInDay * dayInMnth;
			}else if(substrings[1].equals(YEARS)) {
				Double d = Double.parseDouble(substrings[0]);
				if(d != null)
					return d * minInHour * hourInDay * dayInMnth * mnthInYear;
			}
		}
		  
		return 0.0;
	}
	
	public static int getAnnualFrequency(Node node){
		String functionFrequency = getObjAttrValue(node, AT_FRQ_ANNO);
		if (functionFrequency != null)
			return Integer.parseInt(functionFrequency);
		  
		return 0;
	}
	
	public static void setNodeAttributeValue(Node node, String attr, String value){
		((Element) node).setAttribute(attr, value);
	}
	
	public static void ensureOccAttributes(Document document, Node node){
		NodeList nodes = ((Element)node).getElementsByTagName("AttrOcc");
		
		boolean hasDesc = false;
		boolean hasTimeAvg = false;
		
		for (int i = 0; i<nodes.getLength(); i++){
			if (nodes.item(i).getAttributes().getNamedItem("AttrTypeNum").getNodeValue().equals(VAL_ATTR_TYPE_NUM_TIME_AVG)) hasTimeAvg = true;
			if (nodes.item(i).getAttributes().getNamedItem("AttrTypeNum").getNodeValue().equals(VAL_ATTR_TYPE_NUM_DESC)) hasDesc = true;
		}
		
		if (!hasDesc){
			Node attrOccNode = document.createElement("AttrOcc");
			Element e = (Element) attrOccNode;
			
			e.setAttribute("Alignment", "LEFT");
			e.setAttribute("AttrTypeNum", VAL_ATTR_TYPE_NUM_DESC);
			e.setAttribute("OffsetX", "0");
			e.setAttribute("OffsetY", "0");
			e.setAttribute("SymbolFlag", "TEXT");
			e.setAttribute("Port", "SE");
			e.setAttribute("OrderNum", "0");
			
			node.appendChild(attrOccNode);
		}
		
		if (!hasTimeAvg){
			Node attrOccNode = document.createElement("AttrOcc");
			Element e = (Element) attrOccNode;
			
			e.setAttribute("Alignment", "LEFT");
			e.setAttribute("AttrTypeNum", VAL_ATTR_TYPE_NUM_TIME_AVG);
			e.setAttribute("OffsetX", "0");
			e.setAttribute("OffsetY", "0");
			e.setAttribute("SymbolFlag", "TEXT");
			e.setAttribute("Port", "NE");
			e.setAttribute("OrderNum", "0");
			
			node.appendChild(attrOccNode);
		}
		
	}
	
	public Node createObjOcc(AMLEPCNode node, Document document, String defId){
		Node objOcc = document.createElement("ObjOcc");

		Attr attr = document.createAttribute(OBJ_OCC_ID);
		attr.setNodeValue(node.getId());
		objOcc.getAttributes().setNamedItem(attr);

		attr = document.createAttribute(OBJ_DEF_ID_REF);
		attr.setNodeValue(defId);
		objOcc.getAttributes().setNamedItem(attr);
		
		attr = document.createAttribute(SYMBOL_NUM);
		attr.setNodeValue(node.getSymbolNum());
		objOcc.getAttributes().setNamedItem(attr);
		
		attr = document.createAttribute(ACTIVE);
		attr.setNodeValue((node.isActive())?"YES":"NO");
		objOcc.getAttributes().setNamedItem(attr);

		attr = document.createAttribute(SHADOW);
		attr.setNodeValue((node.isShadow())?"YES":"NO");
		objOcc.getAttributes().setNamedItem(attr);

		attr = document.createAttribute(VISIBLE);
		attr.setNodeValue((node.isVisible())?"YES":"NO");
		objOcc.getAttributes().setNamedItem(attr);

		attr = document.createAttribute(ZORDER);
		attr.setNodeValue(Integer.toString(node.getZorder()));
		objOcc.getAttributes().setNamedItem(attr);
		
		attr = document.createAttribute(HINTS);
		attr.setNodeValue(Integer.toString(node.getHints()));
		objOcc.getAttributes().setNamedItem(attr);
		
		Node position = document.createElement(POSITION);

		attr = document.createAttribute(POSITION_X);
		attr.setNodeValue(Integer.toString(node.getPosition().getX()));
		position.getAttributes().setNamedItem(attr);
		
		attr = document.createAttribute(POSITION_Y);
		attr.setNodeValue(Integer.toString(node.getPosition().getY()));
		position.getAttributes().setNamedItem(attr);
		
		objOcc.appendChild(position);
		
		Node size = document.createElement(SIZE);

		attr = document.createAttribute(SIZE_X);
		attr.setNodeValue(Integer.toString(node.getSize().getX()));
		size.getAttributes().setNamedItem(attr);
		
		attr = document.createAttribute(SIZE_Y);
		attr.setNodeValue(Integer.toString(node.getSize().getY()));
		size.getAttributes().setNamedItem(attr);
		
		objOcc.appendChild(size);
		
		Iterator<Attribute> attributes = node.getAttributes().iterator();
		while(attributes.hasNext()){
			Attribute attribute = attributes.next();
			Node attrOcc = document.createElement(ATTR_OCC);

			Attr a = document.createAttribute(ATTR_TYPE_NUM);
			a.setNodeValue(attribute.getTypeName());
			attrOcc.getAttributes().setNamedItem(a);

			a = document.createAttribute(ATTR_PORT);
			a.setNodeValue(attribute.getPort());
			attrOcc.getAttributes().setNamedItem(a);
			
			a = document.createAttribute(ATTR_ORDER_NUM);
			a.setNodeValue(Integer.toString(attribute.getOrderNum()));
			attrOcc.getAttributes().setNamedItem(a);

			a = document.createAttribute(ATTR_ALIGNMENT);
			a.setNodeValue(attribute.getAlignment());
			attrOcc.getAttributes().setNamedItem(a);

			a = document.createAttribute(ATTR_SYMBOL_FLAG);
			a.setNodeValue(attribute.getSymbolFlag());
			attrOcc.getAttributes().setNamedItem(a);

			a = document.createAttribute(ATTR_FONT);
			a.setNodeValue(attribute.getFont());
			attrOcc.getAttributes().setNamedItem(a);
			
			a = document.createAttribute(ATTR_OFFSET_X);
			a.setNodeValue(Integer.toString(attribute.getOffset().getX()));
			attrOcc.getAttributes().setNamedItem(a);
			
			a = document.createAttribute(ATTR_OFFSET_Y);
			a.setNodeValue(Integer.toString(attribute.getOffset().getY()));
			attrOcc.getAttributes().setNamedItem(a);
			
			objOcc.appendChild(attrOcc);
		}
		
		return objOcc;
	}
}