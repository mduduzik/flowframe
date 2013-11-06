package org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.tests;

import flexjson.JSONDeserializer;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.textfileinput.TextFileInputDialogDelegateResource;
import org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.textfileinput.dto.TextFileInputMetaDTO;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/META-INF/TextFileInputMetaTests-module-context.xml"})
public class TextFileInputMetaTests extends AbstractJUnit4SpringContextTests {
    private String modelJson = null;

    @Autowired
    private IRemoteDocumentRepository repository;

    @Autowired
    private TextFileInputDialogDelegateResource resource;

    private String url;

    /**
	 * @throws Exception
	 */
	@Before
	public  void setUpBefore() throws Exception {
        FileEntry fe = repository.getFileEntryById("28601");
        assertNotNull(fe);

        url = repository.getFileAsURL("28701",null);//http://test%40.liferay.com:test@localhost:7080/documents/10180/28185/sales_data.csv/c619e370-d31d-4fbe-842f-2a6c72984bad
        //url = StringUtil.replace(url,"://","://test%40.liferay.com:test@");
        url = "http://test%40liferay.com:test@localhost:7080/api/secure/webdav/guest/document_library/Organization-1/sales_data.csv";
        assertNotNull(url);
	}

	/**
	 * @throws Exception
	 */
	@After
	public void tearDownAfter() throws Exception {

	}


	@Test
	public final void testBasic() throws Exception {
        assertNotNull(resource);

        String dtoJson = resource.onNew("test");
        JSONDeserializer metaDeserializer = new JSONDeserializer();
        TextFileInputMetaDTO dto = (TextFileInputMetaDTO)metaDeserializer.deserialize(dtoJson, TextFileInputMetaDTO.class);
        dto.setName("Test");
        dto.setFileName(new String[]{url});


        String res = resource.onGetMetadata("test", dto);
        JSONObject resObj = new JSONObject(res);
        JSONObject param = new JSONObject();
        param.put("inputFields",resObj.get("rows"));
        param.put("fileName",new JSONArray("[\"http://test%40liferay.com:test@localhost:7080/api/secure/webdav/guest/document_library/Organization-1/sales_data.csv\"]"));
        param.put("includeSubFolders",new JSONArray("[null]"));

        dto = (TextFileInputMetaDTO)metaDeserializer.deserialize(param.toString(), TextFileInputMetaDTO.class);
        res = resource.onPreviewData("test",dto);
	}
}
