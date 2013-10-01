package org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.tests;

import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONException;
import org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.JSONStencilSet2TransformationConverter;
import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;

import static org.junit.Assert.assertNotNull;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/DBRepositoryWrapperImplTests-module-context.xml"})
public class ConverterTest extends AbstractJUnit4SpringContextTests {
    private String modelJson = null;

    @Autowired
    private ICustomRepository repository;

	/**
	 * @throws Exception
	 */
	@Before
	public  void setUpBefore() throws Exception {
		URL jsonModelFile = ConverterTest.class.getResource("/test1_model.json");
		File file = new File(jsonModelFile.toURI());
        modelJson = FileUtils.readFileToString(file, "UTF-8");
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDownAfter() throws Exception {
	}


	@Test
	public final void testConversion() throws KettleException, JSONException {
        TransMeta transMeta = JSONStencilSet2TransformationConverter.toTransMeta(repository, modelJson);
		assertNotNull(transMeta);

        String transMetaContent = transMeta.getXML();
        assertNotNull(transMetaContent);
	}
}
