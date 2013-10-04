package org.flowframe.etl.pentaho.server.carte.standalone.impl.utils.tests;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.flowframe.etl.pentaho.server.carte.standalone.impl.utils.XML2JSONTransformer;
import org.flowframe.kernel.common.utils.StringUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.www.SlaveServerTransStatus;
import org.springframework.util.Assert;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

//@Ignore
public class UtilsTest {
    private String modelJson = null;

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
	public final void testXML2JSONTransformer() throws KettleException, JSONException, IOException, TransformerException, URISyntaxException {
        InputStream is = UtilsTest.class.getResourceAsStream("/samples/trans_status_response.xml");
        Assert.notNull(is);
        String xml = StringUtil.read(is);
        SlaveServerTransStatus resp = SlaveServerTransStatus.fromXML(xml);
        if (resp.getErrorDescription() == null)
            resp.setErrorDescription("null");
        if (resp.getLoggingString() == null)
            resp.setLoggingString("null");

        String logText = resp.getLoggingString();
        Assert.notNull(xml);

        xml = StringUtil.replace(resp.getXML(),"<log_text/>","");
        xml = StringUtil.replace(xml,"<logging_string/>","");

        JSONArray json = XML2JSONTransformer.transform(xml);
        Assert.notNull(json);
	}

    @Test
    public final void testXML2JSONTransformerWithError() throws KettleException, JSONException, IOException, TransformerException, URISyntaxException {
        InputStream is = UtilsTest.class.getResourceAsStream("/samples/trans_status_response_error1.xml");
        Assert.notNull(is);
        String xml = StringUtil.read(is);
        SlaveServerTransStatus resp = SlaveServerTransStatus.fromXML(xml);

        String logText = resp.getLoggingString();
        Assert.notNull(xml);

/*        xml = StringUtil.replace(resp.getXML(),"<log_text/>","");
        xml = StringUtil.replace(xml,"<logging_string/>","");*/

        JSONArray json = XML2JSONTransformer.transform(xml);
        Assert.notNull(json);
    }
}
