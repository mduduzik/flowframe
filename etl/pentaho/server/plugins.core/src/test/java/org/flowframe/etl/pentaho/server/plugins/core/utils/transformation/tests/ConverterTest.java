package org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.tests;


import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.etl.pentaho.server.plugins.core.exception.TransConversionException;
import org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.JSONStencilSet2TransMetaConverter;
import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/DBRepositoryWrapperImplTests-module-context.xml"})
public class ConverterTest extends AbstractJUnit4SpringContextTests {
    private String modelJson = null;

    @Autowired
    private ICustomRepository repository;

    @Autowired
    private IRemoteDocumentRepository docRepository;

	/**
	 * @throws Exception
	 */
	@Before
	public  void setUpBefore() throws Exception {
        URL url = ConverterTest.class.getResource("/json/samples/excelinputmeta_for_add.json");
        StringWriter writer = new StringWriter();
        IOUtils.copy(new FileInputStream(new File(url.toURI().getPath())), writer, "UTF-8");
        modelJson = writer.toString();
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDownAfter() throws Exception {
	}


	@Test
	public final void testConversion() throws KettleException, JSONException, IOException, TransConversionException {
        TransMeta transMeta = JSONStencilSet2TransMetaConverter.toTransMeta(getOptions(), modelJson,true);
		assertNotNull(transMeta);

        String transMetaContent = transMeta.getXML();
        assertNotNull(transMetaContent);
	}

    protected Map<String,Object> getOptions() {
        final HashMap<String, Object> options = new HashMap<String, Object>() {{
            put("etlRepository", repository);
            put("docRepositoryService", docRepository);
        }};

        return options;
    }
}
