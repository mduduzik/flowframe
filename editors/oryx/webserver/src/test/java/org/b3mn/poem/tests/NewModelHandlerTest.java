package org.b3mn.poem.tests;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.b3mn.poem.Identity;
import org.b3mn.poem.handler.NewModelHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class NewModelHandlerTest {
	
	private NewModelHandler handler;
	private HttpServletRequest  mockedRequest;
	private HttpServletResponse mockedResponse;
	private Identity user;
	private String title;
	private String type;
	private String summary;
	private String svg;
	private String data;
	private Identity newModelIdentity;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public  void setUpBefore() throws Exception {
		mockedRequest = Mockito.mock(HttpServletRequest.class);
		mockedResponse = Mockito.mock(HttpServletResponse.class);
		
		URL testfile = NewModelHandlerTest.class.getResource("/data/newmodelhandler/test1_svg.xml");
		File file = new File(testfile.toURI());		
		svg = FileUtils.readFileToString(file, "UTF-8");
		
		testfile = NewModelHandlerTest.class.getResource("/data/newmodelhandler/test1_content_json.txt");
		file = new File(testfile.toURI());		
		data = FileUtils.readFileToString(file, "UTF-8");	
		
		user = new Identity();
		user.setId(2);
		user.setUri("public");
		
		title = "Job #1";
		type = "http://b3mn.org/stencilset/reporting#";
		summary = "";
		newModelIdentity = null;
		
		handler = new NewModelHandler();
		assertNotNull(handler);
		
		handler.init();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDownAfter() throws Exception {
		handler.destroy();
		newModelIdentity.delete();
	}


	
	/**
	 * Test method for {@link org.b3mn.poem.util.HandlerInfo#getExportInfo()}.
	 */
	@Test
	public final void testSaveNewModel() {
		newModelIdentity = Identity.newModel(user, title, type, summary, svg, data);
		assertNotNull(newModelIdentity);
	}
}
