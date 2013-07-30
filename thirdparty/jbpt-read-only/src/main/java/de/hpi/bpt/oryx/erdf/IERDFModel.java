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
package de.hpi.bpt.oryx.erdf;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;


/**
 * Interface to an eRDF model
 * 
 * @author Artem Polyvyanyy
 */
public interface IERDFModel<E extends ERDFEdge<V>, V extends ERDFNode> {
	
	/**
	 * Parse eRDF model form eRDF string
	 * @param erdfString String containing eRDF encoding
	 */
	public void parseERDF(String erdfString) throws SAXException, IOException, ParserConfigurationException;
	
	/**
	 * Parse eRDF model form eRDF file
	 * @param erdfFile File containing eRDF encoding
	 */
	public void parseERDFFile(String erdfFile) throws SAXException, IOException, ParserConfigurationException;

	/**
	 * Get eRDF model serialization string
	 * @return eRDF serialization string of the model
	 */
	public String serializeERDF();
	
	public V createNode(String type);
	
	public E createEdge(String type, V s, V t);
}
