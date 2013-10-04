package org.flowframe.etl.pentaho.server.carte.standalone.impl.tests;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.flowframe.etl.pentaho.server.carte.services.ICarteJobService;
import org.flowframe.etl.pentaho.server.carte.standalone.impl.utils.XML2JSONTransformer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mortbay.jetty.testing.HttpTester;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.www.GetStatusServlet;
import org.pentaho.di.www.SlaveServerStatus;
import org.pentaho.di.www.SlaveServerTransStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.URISyntaxException;
import java.util.zip.GZIPInputStream;

import static org.junit.Assert.assertNotNull;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/BasicTests-module-context.xml"})
public class BasicTest extends AbstractJUnit4SpringContextTests {
    private String modelJson = null;

    @Autowired
    private ICarteJobService jobService;

	/**
	 * @throws Exception
	 */
	@Before
	public  void setUpBefore() throws Exception {
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDownAfter() throws Exception {
	}


    @Ignore
    @Test
    public final void testGetRootServlet() {
        HttpTester request = new HttpTester();
        HttpTester response = new HttpTester();
        request.setMethod("GET");
        request.setHeader("Host", "tester");
        request.setURI(GetStatusServlet.CONTEXT_PATH);
        request.setVersion("HTTP/1.0");
        try {
            String res = response.parse(request.generate());
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }
    }

    @Ignore
    @Test
    public final void testAddTransJob() throws KettleException, JSONException, IOException, TransformerException, URISyntaxException {
        InputStream is = BasicTest.class.getResourceAsStream("/samples/trans_job1.xml");
        org.springframework.util.Assert.notNull(is);
        Document doc = XMLHandler.loadXMLFile(is, null, false, false);

        TransMeta transMeta = new TransMeta(doc, null);
        transMeta.setName("oryx-canvas123");

        SlaveServerTransStatus res = jobService.addTransformationJob(transMeta);
        assertNotNull(res);
    }


    @Test
    public final void testExecuteTransFromRepo() throws KettleException, JSONException, IOException, TransformerException, URISyntaxException {
/*
        InputStream is = BasicTest.class.getResourceAsStream("/samples/trans_job1.xml");
        org.springframework.util.Assert.notNull(is);
        Document doc = XMLHandler.loadXMLFile(is, null, false, false);

        TransMeta transMeta = new TransMeta(doc, null);
        transMeta.setName("oryx-canvas123");


*/
        SlaveServerTransStatus res = jobService.executeTransformationJob("/conxbi/tenant/Organization-1/Trans/Steps/Transforms/Drafts/oryx-canvas123","detailed");
        assertNotNull(res);
    }




    @Ignore
    @Test
    public final void testStartTransJob() throws KettleException, JSONException, IOException, TransformerException, URISyntaxException {
        testAddTransJob();

        SlaveServerTransStatus res = jobService.startTransformationJob("oryx-canvas123");

        assertNotNull(res);
    }



    @Ignore
	@Test
	public final void testGetStatus() throws KettleException, JSONException, IOException, TransformerException, URISyntaxException {
        SlaveServerStatus res = jobService.getCarteStatus();
        assertNotNull(res);

        SlaveServerTransStatus resp = jobService.startTransformationJob("Row generator test");
        assertNotNull(resp);

        resp = jobService.getTransformationJobStatus("Row generator test");
        String xml = resp.getXML();
        JSONArray json = XML2JSONTransformer.transform(xml);
        assertNotNull(resp);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resp = jobService.getTransformationJobStatus("Row generator test",resp.getId());
        xml = resp.getXML();
        json = XML2JSONTransformer.transform(xml);
        assertNotNull(resp);
	}

    private String uncompress(String gzippedAndEncodedLogText) throws IOException {
        Reader reader = null;
        StringWriter writer = null;
        String log = null;
        String charset = "UTF-8"; // You should determine it based on response header.

        try {
            gzippedAndEncodedLogText = new String(Base64.decodeBase64(gzippedAndEncodedLogText.getBytes()));

            InputStream gzippedText = new ByteArrayInputStream(gzippedAndEncodedLogText.getBytes());
            InputStream unzippedText = new GZIPInputStream(gzippedText);
            reader = new InputStreamReader(unzippedText, charset);
            writer = new StringWriter();

            char[] buffer = new char[10240];
            for (int length = 0; (length = reader.read(buffer)) > 0;) {
                writer.write(buffer, 0, length);
            }

            String body = writer.toString();
        } finally {
            if (reader != null)
                reader.close();

            if (writer != null)
                writer.close();
        }

        return log;
    }
}
