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
package de.hpi.bpt.graph.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for de.hpi.bpt.graph.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(ProcessTest.class);
		suite.addTestSuite(EPCTest.class);
		suite.addTestSuite(ERDFTest.class);
		suite.addTestSuite(CombinationGeneratorTest.class);
		suite.addTestSuite(MultiHyperGraphTest.class);
		suite.addTestSuite(DirectedGraphTest.class);
		suite.addTestSuite(MultiDirectedHyperGraphTest.class);
		suite.addTestSuite(DirectedFragmentsTest.class);
		suite.addTestSuite(HyperGraphTest.class);
		suite.addTestSuite(TCTreeTest.class);
		suite.addTestSuite(DirectedHyperGraphTest.class);
		suite.addTestSuite(GraphAlgorithmsTest_isConnected.class);
		suite.addTestSuite(BiconnectivityCheckTest.class);
		//$JUnit-END$
		return suite;
	}

}
