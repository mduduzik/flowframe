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

/**
 * 
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public interface EPC {
	
	/**
	 * All the nodes of this EPC.
	 * Includes events, functions and connectors 
	 * @return
	 */
	public Collection<EPCNode> getNodes();
	
	/**
	 * The starting nodes of this EPC.
	 * @return
	 */
	public Collection<EPCNode> getStartNodes();

	/**
	 * The end nodes of this EPC.
	 * @return
	 */
	public Collection<EPCNode> getEndNodes();
	
	/**
	 * The connections of this EPC.
	 * Connections between the flow objects are returned
	 * (connections between functions and roles and systems are ignored).
	 * @return
	 */
	public Collection<EPCCxn> getCxns();
	
	/**
	 * All the events of this EPC.
	 * @return
	 */
	public Collection<EPCEvent> getEvents();
	
	/**
	 * All the functions of this EPC.
	 * @return
	 */
	public Collection<EPCFunction> getFunctions();
	
	/**
	 * All the connectors of this EPC.
	 * @return
	 */
	public Collection<EPCConnector> getConnectors();
	
	/**
	 * Tries to remove the object from this EPC.
	 * The object can be a node or a connection.
	 * @param object an object to remove
	 * @return a removed object
	 */
	public EPCObject removeObject(EPCObject object);
	
	/**
	 * Tries to remove the object from this EPC.
	 * The object can be a node or a connection.
	 * @param object an object to remove
	 * @return a removed object
	 */
	public EPCObject addObject(EPCObject object);
	
	public EPCNode addNode(EPCNode node);
	
	public EPCNode removeNode(EPCNode node);
	
	public EPCCxn addCxn(EPCCxn cxn);
	
	public EPCCxn removeCxn(EPCCxn cxn);
	
	public String getTitle();
	
	public void setTitle(String title);

}