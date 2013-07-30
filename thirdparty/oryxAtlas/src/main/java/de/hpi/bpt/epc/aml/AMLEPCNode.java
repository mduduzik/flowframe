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
import java.util.Collection;
import java.util.Iterator;

import org.w3c.dom.Node;

import de.hpi.bpt.epc.EPCConnector;
import de.hpi.bpt.epc.EPCCxn;
import de.hpi.bpt.epc.EPCEvent;
import de.hpi.bpt.epc.EPCFunction;
import de.hpi.bpt.epc.EPCNode;
import de.hpi.bpt.epc.aml.layout.Position;
import de.hpi.bpt.epc.aml.layout.Size;

/**
 * 
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public class AMLEPCNode extends AMLEPCObject implements EPCNode{
	
	public static String ST_POS = "ST_POS";
	
	public static String ST_FUNC = "ST_FUNC";
	
	public static String ST_EV = "ST_EV";
	
	public static String ST_PRCS_IF = "ST_PRCS_IF";
	
	public static String ST_OPR_XOR_1 = "ST_OPR_XOR_1";
	
	public static String ST_CLST = "ST_CLST";
	
	protected double absProbability;
	
	protected Collection<EPCCxn> inConnections;
	
	protected Collection<EPCCxn> outConnections;
	
	protected String createTimestamp;
	
	protected String lastUser;
	
	protected String creator;
	
	protected String lastChange;
	
	protected String name;
	
	protected String description;
	
	protected String shortDescription;
	
	protected int annualFrequency;
	
	protected String symbolNum;
	
	protected Position position;
	
	protected Size size;
	
	public AMLEPCNode(Node domOcc){
		super(domOcc);
		absProbability	= 0;
		inConnections = new ArrayList<EPCCxn>();
		outConnections = new ArrayList<EPCCxn>();
		name = "";
		symbolNum = "";
		position = new Position(0, 0);
		size = new Size(0, 0);
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public boolean isStart(){
		if (this.getInConnections().size() == 0)
			return true;
		
		return false;
	}
	
	public boolean isEnd(){
		if (this.getOutConnections().size() == 0)
			return true;
		
		return false;
	}

	public Collection<EPCCxn> getInConnections(){		
		return inConnections;
	}

	public void setInConnections(Collection<EPCCxn> inEdges){
		this.inConnections = inEdges;
	}

	public Collection<EPCCxn> getOutConnections(){
		return outConnections;
	}

	public void setOutConnections(Collection<EPCCxn> outEdges){
		this.outConnections = outEdges;
	}

	public void setAbsProbability(double absProbability){
		this.absProbability = absProbability;
	}

	public double getAbsProbability(){
		return this.absProbability;
	}

	public Collection<EPCNode> getParents(){
		Collection<EPCNode> parents = new ArrayList<EPCNode>();
		Iterator<EPCCxn> inConnections = this.getInConnections().iterator();
		while(inConnections.hasNext())
			parents.add(inConnections.next().getSource());
		
		return parents;
	}
	
	public Collection<EPCNode> getChildren(){
		Collection<EPCNode> children = new ArrayList<EPCNode>();
		Iterator<EPCCxn> outConnections = this.getOutConnections().iterator();
		while(outConnections.hasNext())
			children.add(outConnections.next().getTarget());
		
		return children;
	}
	
	public String getCreateTimestamp() {
		return createTimestamp;
	}

	public void setCreateTimestamp(String createTimestamp) {
		this.createTimestamp = createTimestamp;
	}

	public String getLastUser() {
		return lastUser;
	}

	public void setLastUser(String user) {
		lastUser = user;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getLastChange() {
		return lastChange;
	}

	public void setLastChange(String lastChange) {
		this.lastChange = lastChange;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	public static boolean equal(EPCNode n1, EPCNode n2){
		if (n1 == null && n2 == null) return true;
		if (n1 == null && n2 != null) return false;
		if (n1 != null && n2 == null) return false;
		if (n1 != null && n2 != null)
			return n1.getId().equals(n2.getId());
		
		return false;
	}
	
	public static boolean equalNames(EPCNode n1, EPCNode n2){
		if (n1 == null && n2 == null) return true;
		if (n1 == null && n2 != null) return false;
		if (n1 != null && n2 == null) return false;
		if (n1 != null && n2 != null)
			return n1.getName().equals(n2.getName());
		
		return false;
	}

	public int getAnnualFrequency() {
		return annualFrequency;
	}

	public void setAnnualFrequency(int annualFrequency) {
		this.annualFrequency = annualFrequency;
	}

	public boolean isConnector() {
		if (this instanceof EPCConnector)
			return true;
		
		return false;
	}

	public boolean isEvent() {
		if (this instanceof EPCEvent)
			return true;
		
		return false;
	}

	public boolean isFunction() {
		if (this instanceof EPCFunction)
			return true;
		
		return false;
	}

	public EPCNode getFirstChild() {
		return this.getChildren().iterator().next();
	}

	public EPCNode getFirstParent() {
		return this.getParents().iterator().next();
	}

	public boolean hasChildren() {
		if (this.getChildren().size()>0)
			return true;
		
		return false;
	}

	public boolean hasParents() {
		if (this.getParents().size()>0)
			return true;
		
		return false;
	}

	public EPCCxn getFirstInConnection() {
		return this.getInConnections().iterator().next();
	}

	public EPCCxn getFirstOutConnection() {
		return this.getOutConnections().iterator().next();
	}

	public String getSymbolNum() {
		return symbolNum;
	}

	public void setSymbolNum(String symbolNum) {
		this.symbolNum = symbolNum;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

}