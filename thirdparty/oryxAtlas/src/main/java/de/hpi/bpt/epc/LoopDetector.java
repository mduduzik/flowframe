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

package de.hpi.bpt.epc;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public class LoopDetector {
	
	public static boolean containsLoop(EPC epc){
		Collection<EPCNode> white = new HashSet<EPCNode>();
		Collection<EPCNode> grey = new HashSet<EPCNode>();
		Collection<EPCNode> black = new HashSet<EPCNode>();
		Iterator<EPCNode> vertices = epc.getNodes().iterator();
		while(vertices.hasNext())
			markWhite(vertices.next(), white, grey, black);
		vertices = epc.getNodes().iterator();
		while(vertices.hasNext()){
			EPCNode vertex = vertices.next();
			if(white.contains(vertex))
				if(visit(epc, vertex, white, grey, black))
					return true;
		}
		
		return false;
	}
	
	private static boolean visit(EPC epc, EPCNode vertex, Collection<EPCNode> white, Collection<EPCNode> grey, Collection<EPCNode> black){
		markGrey(vertex, white, grey, black);
		Iterator<EPCNode> children = vertex.getChildren().iterator();
		
		while(children.hasNext()){
			EPCNode child = children.next();
			if(grey.contains(child))
				return true;
			else if (white.contains(child))
				if(visit(epc, child, white, grey, black))
					return true;	
		}
		markBlack(vertex, white, grey, black);
		return false;
	}
	
	private static boolean markBlack(EPCNode vertex, Collection<EPCNode> white, Collection<EPCNode> grey, Collection<EPCNode> black){
		white.remove(vertex);
		grey.remove(vertex);
		return black.add(vertex);
	}

	private static boolean markWhite(EPCNode vertex, Collection<EPCNode> white, Collection<EPCNode> grey, Collection<EPCNode> black){
		black.remove(vertex);
		grey.remove(vertex);
		return white.add(vertex);
	}
	
	private static boolean markGrey(EPCNode vertex, Collection<EPCNode> white, Collection<EPCNode> grey, Collection<EPCNode> black){
		white.remove(vertex);
		black.remove(vertex);
		return grey.add(vertex);
	}	
}