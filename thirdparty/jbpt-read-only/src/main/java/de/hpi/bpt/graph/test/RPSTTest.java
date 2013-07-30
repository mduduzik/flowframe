/**
 * Copyright (c) 2010 Artem Polyvyanyy
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
package de.hpi.bpt.graph.test;

import junit.framework.TestCase;
import de.hpi.bpt.graph.DirectedEdge;
import de.hpi.bpt.graph.DirectedGraph;
import de.hpi.bpt.graph.algo.rpst.RPST;
import de.hpi.bpt.graph.algo.rpst.RPSTNode;
import de.hpi.bpt.graph.algo.tctree.TCType;
import de.hpi.bpt.hypergraph.abs.Vertex;
import de.hpi.bpt.process.ControlFlow;
import de.hpi.bpt.process.Gateway;
import de.hpi.bpt.process.GatewayType;
import de.hpi.bpt.process.Node;
import de.hpi.bpt.process.Process;
import de.hpi.bpt.process.Task;

public class RPSTTest extends TestCase {
		
	public void testTrivialGraph() {
		System.out.println("========================================================");
		System.out.println("Trivial Graph");
		System.out.println("========================================================");
		
		DirectedGraph g = new DirectedGraph();
		
		Vertex v1 = new Vertex("1");
		Vertex v2 = new Vertex("2");
		
		g.addEdge(v1, v2);
		
		RPST<DirectedEdge,Vertex> rpst = new RPST<DirectedEdge,Vertex>(g);
		
		System.out.println(rpst);
		
		for (RPSTNode<DirectedEdge,Vertex> node : rpst.getVertices()) {
			System.out.println(node.getName() + " : " + node.getFragment());
		}
	}
	
	public void testBPM08Fig11() {
		System.out.println("========================================================");
		System.out.println("BPM08 Fig.11");
		System.out.println("========================================================");
		
		DirectedGraph g = new DirectedGraph();
		
		Vertex s = new Vertex("s");
		Vertex t = new Vertex("t");
		Vertex v1 = new Vertex("v1");
		Vertex v2 = new Vertex("v2");
		Vertex v3 = new Vertex("v3");
		Vertex v4 = new Vertex("v4");
		Vertex v5 = new Vertex("v5");
		Vertex v6 = new Vertex("v6");
		Vertex v7 = new Vertex("v7");
		
		g.addVertex(s);
		g.addVertex(t);
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);
		g.addVertex(v5);
		g.addVertex(v6);
		g.addVertex(v7);
		
		g.addEdge(s,v1);
		g.addEdge(s,v2);
		g.addEdge(v1,v3);
		g.addEdge(v1,v5);
		g.addEdge(v2,v5);
		g.addEdge(v3,v2);
		g.addEdge(v3,v4);
		g.addEdge(v4,v1);
		g.addEdge(v4,v2);
		g.addEdge(v5,v6);
		g.addEdge(v5,v7);
		g.addEdge(v6,v5);
		g.addEdge(v6,v7);
		g.addEdge(v7,v5);
		g.addEdge(v7,t);
		
		RPST<DirectedEdge,Vertex> rpst = new RPST<DirectedEdge,Vertex>(g);
		
		System.out.println(rpst);
		
		for (RPSTNode<DirectedEdge,Vertex> node : rpst.getVertices()) {
			System.out.println(node.getName() + " : " + node.getFragment());
		}
	}
	
	public void testSimpleGraph() {
		System.out.println("========================================================");
		System.out.println("Simple Graph");
		System.out.println("========================================================");
		
		DirectedGraph g = new DirectedGraph();
		
		Vertex s = new Vertex("s");
		Vertex t = new Vertex("t");
		Vertex y = new Vertex("y");
		Vertex z = new Vertex("z");
		
		g.addVertex(s);
		g.addVertex(t);
		g.addVertex(y);
		g.addVertex(z);
		
		g.addEdge(s,y);
		g.addEdge(y,z);
		g.addEdge(z,y);
		g.addEdge(z,t);
		
		System.out.println(g);
		
		RPST<DirectedEdge,Vertex> rpst = new RPST<DirectedEdge,Vertex>(g);
		
		System.out.println(rpst);
		
		for (RPSTNode<DirectedEdge,Vertex> node : rpst.getVertices()) {
			System.out.println(node.getName() + " : " + node.getFragment());
		}
	}
	
	
	public void testBondsTest() {
		
		System.out.println("========================================================");
		System.out.println("Bonds test");
		System.out.println("========================================================");
		
		//		  --- t3 --- t4 ---
		//		  |				  |
		// t1 -- s2 ------------ j5 -- t9
		//	.	  |				  |		.
		//	.	  |_ s6 ---- j7 __|		.
		// 	.		  |_ t8 _|			.
		//	............................. 
		
		Process p = new Process();
		
		Task t1 = new Task("1");
		Task t3 = new Task("3");
		Task t4 = new Task("4");
		Task t8 = new Task("8");
		Task t9 = new Task("9");
		
		Gateway s2 = new Gateway(GatewayType.XOR, "2");
		Gateway s6 = new Gateway(GatewayType.XOR, "6");
		Gateway j7 = new Gateway(GatewayType.XOR, "7");
		Gateway j5 = new Gateway(GatewayType.XOR, "5");
		
		p.addControlFlow(t1, s2);
		p.addControlFlow(s2, t3);
		p.addControlFlow(s2, s6);
		p.addControlFlow(s2, j5);
		p.addControlFlow(t3, t4);
		p.addControlFlow(t4, j5);
		p.addControlFlow(s6, j7);
		p.addControlFlow(s6, t8);
		p.addControlFlow(t8, j7);
		p.addControlFlow(j7, j5);
		p.addControlFlow(j5, t9);
		
		RPST<ControlFlow,Node> rpst = new RPST<ControlFlow,Node>(p);
		
		System.out.println(rpst);
		
		assertEquals(rpst.getVertices().size(), 17);
		assertEquals(rpst.getEdges().size(), 16);
		assertEquals(rpst.getVertices(TCType.B).size(), 2);
		assertEquals(rpst.getVertices(TCType.R).size(), 0);
		assertEquals(rpst.getVertices(TCType.P).size(), 4);
		assertEquals(rpst.getVertices(TCType.T).size(), 11);
	}
}
