package org.flowframe.etl.pentaho.repository.db.resource.etl.trans.steps.csvinput.tests;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/22/13
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class AddCsvInputMetadataTests {//}   extends TestsBase {
    private Client client = null;

    @Before
    public void setUp() {
        client = ClientBuilder.newBuilder()
                .register(MultiPartFeature.class)
                .build();
    }

    @After
    public void shutDown() {
        client.close();
    }

    protected String getResourcePath() {
        return "http://localhost:8082/etlrepo/csvinput";
    }

    @Test
    public void testResourceScope() throws Exception {
        WebTarget t = client.target(getResourcePath());

        //-- 1. New Meta
        Invocation.Builder builder = t.path("onnew").request().accept(MediaType.APPLICATION_JSON_TYPE).header("userid", "test");
        String newMeta = builder.get(String.class);
        JSONObject newMetaJson = new JSONObject(newMeta);

        //-- 2. Post sample csv
        URL csv = AddCsvInputMetadataTests.class.getResource("/testfiles/sales_data.csv");
        File file = new File(csv.toURI());
        final FileDataBodyPart filePart = new FileDataBodyPart("ext-gen12345", file);
        final FormDataMultiPart multipart = (FormDataMultiPart) new FormDataMultiPart()
                .field("MAX_FILE_SIZE", "50000")
                .bodyPart(filePart);
        builder = t.path("uploadsample").request();
        final Response response_ = builder.post(Entity.entity(multipart, multipart.getMediaType()));
        String response = response_.readEntity(String.class);
        System.out.println("getFileEntry Res:[" + response + "]");
        JSONObject res = new JSONObject(response);
        String feId = res.getString("fileentryid");
        newMetaJson.put("filename", feId);

        //-- 4. Guess metadata
        builder = t.path("ongetmetadata").request().accept(MediaType.APPLICATION_JSON_TYPE).header("userid", "test");
        newMetaJson.remove("class");
        newMeta = newMetaJson.toString();
        JSONObject newMetaJSON = new JSONObject(newMeta);
        JSONObject paramJson = (JSONObject)newMetaJSON.get("data");
        paramJson.put("fileEntryId", feId);
        paramJson.remove("class");

        Response analyzedMetadataResponse = builder.post(Entity.entity(paramJson.toString(), MediaType.APPLICATION_JSON_TYPE));
        response = analyzedMetadataResponse.readEntity(String.class);
        System.out.println("getFileEntry Res:[" + response + "]");
        newMetaJSON = new JSONObject(response);
        JSONArray fieldsJSONArray = (JSONArray) newMetaJSON.get("rows");
        for (int i=0; i<fieldsJSONArray.length(); i++){
            ((JSONObject)fieldsJSONArray.get(i)).remove("class");
        }
        paramJson.put("inputFields",fieldsJSONArray);

        //-- 5. Preview data
        builder = t.path("previewdata").request().accept(MediaType.APPLICATION_JSON_TYPE).header("userid", "test");
        //newMeta = meta.toString();
        Response previewResponse = builder.post(Entity.entity(paramJson.toString(), MediaType.APPLICATION_JSON_TYPE));
        response = previewResponse.readEntity(String.class);
        System.out.println("getFileEntry Res:[" + response + "]");
        //JSONDeserializer<CsvInputMetaDTO> deserializer = new JSONDeserializer<CsvInputMetaDTO>();
        //CsvInputMetaDTO meta = deserializer.deserialize(response);

/*        //-- 3. Check delegate
        builder = t.path("getcurrentlyedited").request().accept(MediaType.APPLICATION_JSON_TYPE).header("userid","test");
        String cacheMeta = builder.get(String.class);
        Assert.assertEquals(newMeta,cacheMeta);
*/

    }


}