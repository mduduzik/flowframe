/**
 * Copyright (c) 2008 Artem Polyvyanyy
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.hpi.bpt.process.petri;

import de.hpi.bpt.hypergraph.abs.Vertex;

/**
 * A Petri net abstract node (vertex), might be place or transition 
 * @author artem.polyvyanyy
 *
 */
public abstract class Node extends Vertex {
	/**
	 * Empty constructor
	 */
	public Node() {
		super();
	}
	
	/**
	 * Constructor with node name parameter
	 * @param name Node name
	 */
	public Node(String name) {
		super(name);
	}
	
	/**
	 * Constructor with node name and description parameters
	 * @param name Node name
	 * @param desc Node description
	 */
	public Node(String name, String desc) {
		super(name,desc);
	}
	
	@Override
	public String toString() {
		return (getName()==null) ? "" : getName();
	}
}
