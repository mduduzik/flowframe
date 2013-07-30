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

package de.hpi.bpt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.xml.sax.SAXException;

import de.hpi.bpt.epc.EPC;
import de.hpi.bpt.epc.aml.util.AMLParser;
import de.hpi.bpt.epc.aml.util.AMLSerializer;
import de.hpi.bpt.epc.aml.util.Serializer;

/**
 * Example of how the parsing and serializing functionality can be used.
 * 
 * @author Sergey Smirnov, Artem Polyvyanyy
 * @version 1.0
 */
public class HelloWorld {
	
	public static void main(String[] args) throws Exception {
		String inFileName = getInFileName(args);

		String fileName = (new File(inFileName)).getName();
		fileName = fileName.substring(0, fileName.length() - 4);
		
		File currentDir = new File(".");
		File f1 = new File("ARIS-Export.dtd");
		f1.createNewFile();
		String dirName = currentDir.getCanonicalPath() + "\\out\\";
		File directory = new File(dirName);
		directory.mkdir();

		Collection<EPC> epcs = parseEPCs(inFileName);
		serializeEPCs(epcs, dirName, getOutFileName(args));
		f1.delete();
		System.exit(0);
	}
	
	private static Collection<EPC> parseEPCs(String inFileName) throws IOException, SAXException{
		AMLParser parser = new AMLParser(inFileName);
		parser.parse();
		Collection<EPC> epcs = new HashSet<EPC>();
		for(Iterator<String> modelIds = parser.getModelIds().iterator(); modelIds.hasNext();)
			epcs.add(parser.getEPC(modelIds.next()));
		
		return epcs;
	}
	
	private static void serializeEPCs(Collection<EPC> epcs, String dirName, String outFileName) throws FileNotFoundException, IOException {
		AMLSerializer serializer = new AMLSerializer(epcs);
		serializer.parse();
		Serializer.serializeAML(serializer.getDocument(), dirName + outFileName);
	}
	
	private static String getInFileName(String[] args){
		String inFileName = null;
		for(int i = 0; i < args.length; i ++)
			if(args[i].equals("-i") && (i + 1 < args.length))
				inFileName = args[i + 1];
		
		return inFileName;
	}

	private static String getOutFileName(String[] args){
		String outFileName = null;
		for(int i = 0; i < args.length; i ++)
			if(args[i].equals("-o") && (i + 1 < args.length))
				outFileName = args[i + 1];
		
		return outFileName;
	}
}