package org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.textfileinput.tests;

import junit.framework.Assert;
import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.etl.pentaho.server.plugins.core.MainApplication;
import org.flowframe.etl.pentaho.server.plugins.core.model.json.CustomObjectMapper;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Test;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputMeta;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Application;

//@Ignore
public class TextFileInputDialogDelegateResourceTests extends JerseyTest {
    private String modelJson = null;

    @Autowired
    private IRemoteDocumentRepository repository;

    private String url;

    @Override
    protected Application configure() {
        // Enable logging.
        enable(TestProperties.LOG_TRAFFIC);
        //enable(TestProperties.DUMP_ENTITY);

        // Create an instance of MyApplication ...
        return new MainApplication()
                // ... and pass "contextConfigLocation" property to Spring integration.
                .property("contextConfigLocation", "classpath:/META-INF/TextFileInputMetaTests-module-context.xml");
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


	@Test
	public final void testOnNew() throws Exception {
        String res = target("/textfileinputmeta/onnew").request().header("userid", "test").get(String.class);
        CustomObjectMapper mapper = new CustomObjectMapper();
        TextFileInputMeta meta = mapper.readValue(res, TextFileInputMeta.class);
        Assert.assertNotNull(meta);
	}
}
