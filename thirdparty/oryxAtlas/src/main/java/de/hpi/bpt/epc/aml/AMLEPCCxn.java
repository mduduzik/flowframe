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
import java.util.List;

import org.w3c.dom.Node;

import de.hpi.bpt.epc.EPCCxn;
import de.hpi.bpt.epc.EPCNode;
import de.hpi.bpt.epc.aml.layout.Position;

/**
 * 
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public class AMLEPCCxn extends AMLEPCObject implements EPCCxn{

	private EPCNode source;
	
	private EPCNode target;
	
	private double relProbability;
	
	private int srcArrow;
	
	private int tgtArrow;
	
	private List<Position> positions;
	
	private boolean embedding;
	
	private boolean diagonal;
	
	private String type;

	public AMLEPCCxn(Node domOcc) {
		super(domOcc);
	}

	public AMLEPCCxn(Node domOcc, EPCNode source, EPCNode target) {
		this(domOcc);
		this.source = source;
		this.target = target;
		this.positions = new ArrayList<Position>();
	}

	public String getId() {
		return this.id;
	}

	public EPCNode getSource() {
		return source;
	}

	public void setSource(EPCNode source){
		this.source.getOutConnections().remove(this);
		this.source = source;
		this.source.getOutConnections().add(this);
	}

	public EPCNode getTarget() {
		return target;
	}

	public void setTarget(EPCNode target) {
		this.target.getInConnections().remove(this);
		this.target = target;
		this.target.getInConnections().add(this);
	}

	public double getRelProbability() {
		return relProbability;
	}

	public void setRelProbability(double relProbability) {
		this.relProbability = relProbability;
	}
	
	public AMLEPCCxn clone(){
		AMLEPCCxn copy = new AMLEPCCxn(this.getDomOcc().cloneNode(true));
		copy.setEPC(epc);
		copy.setId(id);
		copy.setRelProbability(relProbability);
		copy.setSource(source);
		copy.setTarget(target);
		
		return copy;
	}
	
	public String toString(){
		return this.getSource().toString() + "->" + this.getTarget().toString();
	}

	public int getSrcArrow() {
		return srcArrow;
	}

	public void setSrcArrow(int srcArrow) {
		this.srcArrow = srcArrow;
	}

	public int getTgtArrow() {
		return tgtArrow;
	}

	public void setTgtArrow(int tgtArrow) {
		this.tgtArrow = tgtArrow;
	}
	
	public boolean isEmbedding() {
		return embedding;
	}

	public void setEmbedding(boolean embedding) {
		this.embedding = embedding;
	}

	public boolean isDiagonal() {
		return diagonal;
	}

	public void setDiagonal(boolean diagonal) {
		this.diagonal = diagonal;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}
	
	public boolean addPosition(Position position){
		return positions.add(position);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}