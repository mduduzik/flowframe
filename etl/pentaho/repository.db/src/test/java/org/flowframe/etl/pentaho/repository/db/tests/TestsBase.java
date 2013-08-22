package org.flowframe.etl.pentaho.repository.db.tests;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.external.ExternalTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/22/13
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class TestsBase extends JerseyTest {
    private static final String TEST_WEBAPP_CONTEXT_PATH = "/etlrepo";
    private static final String TEST_CONTAINER_FACTORY_EXTERNAL = "org.glassfish.jersey.test.external.ExternalTestContainerFactory";

    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        return new ExternalTestContainerFactory();
    }


    @Override
    protected Application configure() {
        ResourceConfig rc = new ResourceConfig();
                //.register(SpringLifecycleListener.class)
/*                .register(RequestContextFilter.class)
                .register(DatabaseMetaResource.class)
                .register(DatabaseTypeResource.class)
                .register(RepositoryExplorerResource.class)
                .register(MultiPartFeature.class)
                .register(LoggingFilter.class);*/
        //TestUtil.registerHK2Services(rc);
        //rc.property("contextConfigLocation", "classpath:applicationContext.xml");
        //return configure(rc);
        System.setProperty("jersey.config.test.container.port","8082");
        return rc;
    }

    protected abstract ResourceConfig configure(ResourceConfig rc);

    protected abstract String getResourcePath();

    protected String getResourceFullPath() {
/*        String containerFactory = System.getProperty(TestProperties.CONTAINER_FACTORY);
        if (TEST_CONTAINER_FACTORY_EXTERNAL.equals(containerFactory)) {
            return System.getProperty(TEST_WEBAPP_CONTEXT_PATH) + getResourcePath() + "8082";
        }
        return getResourcePath();
        */
        return TEST_WEBAPP_CONTEXT_PATH  + getResourcePath();
    }

    // test singleton scoped Spring bean injection using @Inject + @Autowired
    @Test
    public void testSingletonScopedSpringService() {
        WebTarget t = target(getResourceFullPath());

        //t.path("/singleton/xyz123").request().put(Entity.entity(newBalance.toString(), MediaType.TEXT_PLAIN_TYPE));
        //BigDecimal balance = t.path("/singleton/autowired/xyz123").request().get(BigDecimal.class);
        //assertEquals(newBalance, balance);
    }
}
