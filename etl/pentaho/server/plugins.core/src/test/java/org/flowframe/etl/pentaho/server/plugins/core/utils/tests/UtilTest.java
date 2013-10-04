package org.flowframe.etl.pentaho.server.plugins.core.utils.tests;

import org.codehaus.jettison.json.JSONException;
import org.flowframe.etl.pentaho.server.plugins.core.utils.RepositoryUtil;
import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
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
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/DBRepositoryWrapperImplTests-module-context.xml"})
public class UtilTest extends AbstractJUnit4SpringContextTests {
    private String modelJson = null;

    @Autowired
    private ICustomRepository repository;

	/**
	 * @throws Exception
	 */
	@Before
	public  void setUpBefore() throws Exception {
		URL jsonModelFile = UtilTest.class.getResource("/test1_model.json");
		File file = new File(jsonModelFile.toURI());
        //modelJson = FileUtils.readFileToString(file, "UTF-8");
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDownAfter() throws Exception {
	}


	@Test
	public final void testAddDraftTrans() throws KettleException, JSONException, URISyntaxException {
        URL res = UtilTest.class.getResource("/samples/trans_job1.xml");
        assertNotNull(res);

        TransMeta transMeta = new TransMeta(res.toURI().getPath());
        transMeta.setName("oryx-canvas123");

        Organization tenant = new Organization();
        tenant.setId(1L);
        String transName = RepositoryUtil.addOrReplaceTransMetaDraft(tenant, repository, transMeta);
        assertNotNull(transName);
	}
}
