package org.flowframe.etl.pentaho.server.plugins.core.model.json.jackson.mixin.steps.tests;

import junit.framework.Assert;
import org.codehaus.jackson.map.ObjectMapper;
import org.flowframe.etl.pentaho.server.plugins.core.model.json.CustomObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputMeta;

import static org.junit.Assert.assertNotNull;


public class MixInTests {
    private ObjectMapper mapper;

    /**
	 * @throws Exception
	 */
	@Before
	public  void setUpBefore() throws Exception {
        mapper = new CustomObjectMapper();
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDownAfter() throws Exception {

	}


	@Test
	public final void testTextFileInputMetaMixIn() throws Exception {
        assertNotNull(mapper);

        TextFileInputMeta meta = new TextFileInputMeta();
        meta.setDefault();
        String res = mapper.writeValueAsString(meta);
        assertNotNull(res);

        meta = mapper.readValue(res, TextFileInputMeta.class);
        Assert.assertNotNull(meta);
	}
}
