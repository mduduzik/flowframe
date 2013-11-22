package org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.excelinput;

import junit.framework.Assert;
import org.codehaus.jackson.JsonNode;
import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.etl.pentaho.server.plugins.core.MainApplication;
import org.flowframe.etl.pentaho.server.plugins.core.model.json.CustomObjectMapper;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.pentaho.di.trans.steps.excelinput.ExcelInputMeta;
import org.pentaho.di.trans.steps.excelinput.SpreadSheetType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class ExcelInputDialogDelegateResourceTests extends JerseyTest {
    private String modelJson = null;

    @Autowired
    private IRemoteDocumentRepository repository;

    private String url;

    @Override
    protected Application configure() {
        // Enable logging.
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        // Create an instance of MyApplication ...
        return new MainApplication()
                // ... and pass "contextConfigLocation" property to Spring integration.
                .property("contextConfigLocation", "classpath:/META-INF/ExcelInputMetaTests-module-context.xml");
    }

/*    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        return new TestContainerFactory() {
            @Override
            public TestContainer create(final URI baseUri, final ApplicationHandler application) throws IllegalArgumentException {
                return new TestContainer() {
                    private HttpServer server;

                    @Override
                    public ClientConfig getClientConfig() {
                        return null;
                    }

                    @Override
                    public URI getBaseUri() {
                        return baseUri;
                    }

                    @Override
                    public void start() {
                        try {
                            this.server = GrizzlyWebContainerFactory.create(
                                    baseUri, Collections.singletonMap("jersey.config.server.provider.packages", "org.glassfish.jersey.media.multipart,org.glassfish.jersey.jackson,org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.textfileinput")
                            );
                        } catch (ProcessingException e) {
                            throw new TestContainerException(e);
                        } catch (IOException e) {
                            throw new TestContainerException(e);
                        }
                    }

                    @Override
                    public void stop() {
                        this.server.stop();
                    }
                };

            }
        };
    }*/


	/**
	 * @throws Exception
	 */
	@After
	public void tearDownAfter() throws Exception {

	}


    @Ignore
	@Test
	public final void testOnNew() throws Exception {
        //onnew
        String res = target("/excelinputmeta/onnew").request().header("userid", "test").get(String.class);
        CustomObjectMapper mapper = new CustomObjectMapper();
        ExcelInputMeta meta = mapper.readValue(res, ExcelInputMeta.class);
        Assert.assertNotNull(meta);
	}

    @Test
    public final void testCreateMetadata() throws Exception {
        //onnew
        String res = target("/excelinputmeta/onnew").request().header("userid", "test").get(String.class);
        CustomObjectMapper mapper = new CustomObjectMapper();
        ExcelInputMeta meta = mapper.readValue(res, ExcelInputMeta.class);
        Assert.assertNotNull(meta);

        //ongetsheets
        String file = "ff://repo/internal?fileentry#28352";
        meta.setFileName(new String[]{file});
        meta.setSpreadSheetType(SpreadSheetType.POI);
        res = mapper.writeValueAsString(meta);

        Response resp = target("/excelinputmeta/ongetsheets").request().header("userid", "test").post(Entity.entity(res, MediaType.APPLICATION_JSON_TYPE));
        String response = resp.readEntity(String.class);
        Assert.assertNotNull(resp);

        JsonNode root = mapper.readTree(response);
        JsonNode fieldsNode = root.get("rows");
        Map sheets = mapper.readValue(fieldsNode.toString(),HashMap.class);
        //meta.setField(fields);
        Assert.assertNotNull(sheets);

        //onpreview
/*        res = mapper.writeValueAsString(meta);
        resp = target("/excelinputmeta/previewdata").request().header("userid", "test").header("start", 1).header("pageSize", 12).post(Entity.entity(res, MediaType.APPLICATION_JSON_TYPE));
        response = resp.readEntity(String.class);
        Assert.assertNotNull(resp);*/
    }
}
