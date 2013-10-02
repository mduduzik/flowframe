package org.flowframe.etl.pentaho.server.carte.standalone.impl.tests;

import org.codehaus.jettison.json.JSONException;
import org.flowframe.etl.pentaho.server.carte.services.ICarteJobService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.www.SlaveServerStatus;
import org.pentaho.di.www.SlaveServerTransStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.net.URL;

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


	@Test
	public final void testGetStatus() throws KettleException, JSONException {
        SlaveServerStatus res = jobService.getCarteStatus();
        assertNotNull(res);

        SlaveServerTransStatus resp = jobService.startTransformationJob("Row generator test");
        assertNotNull(resp);

        resp = jobService.getTransformationJobStatus("Row generator test");
        assertNotNull(resp);
	}
}
