package org.b3mn.poem.tests;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.b3mn.poem.Identity;
import org.b3mn.poem.business.User;
import org.b3mn.poem.handler.NewModelHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

//@Ignore
public class NewModelHandlerTest {
	
	private NewModelHandler handler;
	private HttpServletRequest  mockedRequest;
	private HttpServletResponse mockedResponse;
	private User user;
	private String title;
	private String type;
	private String summary;
	private String svg;
	private String data;
	private Identity newModelIdentity;
	private SqlSessionFactory sqlSessionFactory;
	
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
		
		
		//--
		//DataDefaults.all();
		Reader reader = Resources.getResourceAsReader("org/b3mn/poem/tests/myibitis-config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		reader.close();
		
		SqlSession session = sqlSessionFactory.openSession();
		Connection conn = session.getConnection();
		reader = Resources.getResourceAsReader("org/b3mn/poem/tests/test_reset_mysql_db_schema.sql");
		ScriptRunner runner = new ScriptRunner(conn);
		runner.setErrorLogWriter(null);
		runner.runScript(reader);
		reader.close();
		
		reader = Resources.getResourceAsReader("org/b3mn/poem/tests/test_data.sql");
		runner = new ScriptRunner(conn);
		runner.setErrorLogWriter(null);
		runner.runScript(reader);
		reader.close();
		
		session.close();

		
		title = "Job #1";
		type = "http://b3mn.org/stencilset/reporting#";
		summary = "";
		newModelIdentity = null;
		user = new User("public");
		
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
		//newModelIdentity.delete();
	}

	
	/**
	 * Test method for {@link org.b3mn.poem.util.HandlerInfo#getExportInfo()}.
	 */
	@Test
	public final void testSaveNewModel() {
		newModelIdentity = Identity.newModel(user.getIdentity(), title, type, summary, svg, data);
		assertNotNull(newModelIdentity);
	}
}
