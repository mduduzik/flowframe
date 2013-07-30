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

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Node;

import de.hpi.bpt.epc.EPC;
import de.hpi.bpt.epc.EPCObject;
import de.hpi.bpt.epc.aml.layout.Brush;
import de.hpi.bpt.epc.aml.layout.Pen;

/**
 * 
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public abstract class AMLEPCObject implements EPCObject, AMLCompatible {

	protected EPC epc;
	
	protected String id;
	
	protected Node domOcc;
	
	protected int zorder;
	
	protected int hints;
	
	protected boolean visible;
	
	protected boolean active;
	
	protected boolean shadow;
	
	protected Pen pen;
	
	protected Brush brush;
	
	protected Set<Attribute> attributes;

	public AMLEPCObject(Node domOcc) {
		this.domOcc = domOcc;
		this.zorder = 0;
		this.hints = 0;
		this.visible = true;
		this.active = true;
		this.shadow = false;
		this.attributes = new HashSet<Attribute>();
		this.pen = null;
		this.brush = null;
	}

	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return id;
	}

	public Node getDomOcc() {
		return domOcc;
	}

	public void setDomOcc(Node domOcc) {
		this.domOcc = domOcc;
	}

	public EPC getEPC() {
		return this.epc;
	}

	public void setEPC(EPC epc) {
		this.epc = epc;
	}

	public int getZorder() {
		return zorder;
	}

	public void setZorder(int zorder) {
		this.zorder = zorder;
	}

	public int getHints() {
		return hints;
	}

	public void setHints(int hints) {
		this.hints = hints;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isShadow() {
		return shadow;
	}

	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}

	public Set<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	public boolean addAttribute(Attribute attribute) {
		return attributes.add(attribute);
	}
	
	public Pen getPen() {
		return pen;
	}

	public void setPen(Pen pen) {
		this.pen = pen;
	}

	public Brush getBrush() {
		return brush;
	}

	public void setBrush(Brush brush) {
		this.brush = brush;
	}

}