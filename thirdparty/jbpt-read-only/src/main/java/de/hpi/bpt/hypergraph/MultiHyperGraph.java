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
package de.hpi.bpt.hypergraph;

import java.util.Collection;

import de.hpi.bpt.hypergraph.abs.AbstractMultiHyperGraph;
import de.hpi.bpt.hypergraph.abs.Vertex;

/**
 * Multi hyper graph implementation
 * Multi hyper graph is a collection of hyper edges and vertices
 * Multiple edges are allowed, i.e., edges with same vertices
 * 
 * @author Artem Polyvyanyy
 */
public class MultiHyperGraph extends AbstractMultiHyperGraph<HyperEdge, Vertex>
{
	/*
	 * (non-Javadoc)
	 * @see de.hpi.bpt.hypergraph.abs.AbstractMultiHyperGraph#addEdge(java.util.Collection)
	 */
	@Override
	public HyperEdge addEdge(Collection<Vertex> vs) {
		HyperEdge e = new HyperEdge(this);
		e.addVertices(vs);
		
		return e;
	}

	/*
	 * (non-Javadoc)
	 * @see de.hpi.bpt.hypergraph.abs.AbstractMultiHyperGraph#addEdge(de.hpi.bpt.hypergraph.abs.IVertex)
	 */
	@Override
	public HyperEdge addEdge(Vertex v) {
		HyperEdge e = new HyperEdge(this);
		e.addVertex(v);
		
		return e;
	}
}
