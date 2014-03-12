package org.flowframe.testing.karaf.common.itesting.support;

import org.apache.felix.service.command.CommandProcessor;
import org.apache.felix.service.command.CommandSession;
import org.apache.karaf.features.BootFinished;
import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.apache.sshd.ClientChannel;
import org.apache.sshd.ClientSession;
import org.apache.sshd.SshClient;
import org.apache.sshd.client.future.ConnectFuture;
import org.junit.Assert;
import org.junit.Rule;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.ProbeBuilder;
import org.ops4j.pax.exam.TestProbeBuilder;
import org.ops4j.pax.exam.karaf.options.KarafDistributionOption;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.ops4j.pax.exam.options.MavenUrlReference;
import org.ops4j.pax.exam.options.extra.VMOption;
import org.osgi.framework.*;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.security.auth.Subject;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.Principal;
import java.security.PrivilegedExceptionAction;
import java.util.*;
import java.util.concurrent.*;

import static org.junit.Assert.assertTrue;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.vmOption;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.*;

public class FFKarafTestSupport {
    protected enum Result { OK, NOT_FOUND, NO_CREDENTIALS };

    public static final String RMI_SERVER_PORT = "44445";
    public static final String HTTP_PORT = "9081";
    public static final String RMI_REG_PORT = "1100";

    private SshClient client;
    private ClientChannel channel;
    private ClientSession session;

    static final Long COMMAND_TIMEOUT = 30000L;
    static final Long SERVICE_TIMEOUT = 30000L;

    private static Logger LOG = LoggerFactory.getLogger(FFKarafTestSupport.class);

    @Rule
    public FFKarafTestWatcher baseTestWatcher = new FFKarafTestWatcher();

    ExecutorService executor = Executors.newCachedThreadPool();

    @Inject
    protected BundleContext bundleContext;

    @Inject
    protected FeaturesService featureService;

    /**
     * To make sure the tests run only when the boot features are fully installed
     */
    @Inject
    BootFinished bootFinished;
    private HashSet<Feature> featuresBefore;

    @ProbeBuilder
    public TestProbeBuilder probeConfiguration(TestProbeBuilder probe) {
        probe.setHeader(Constants.DYNAMICIMPORT_PACKAGE, "*,org.apache.felix.service.*;status=provisional");
        return probe;
    }

    public File getConfigFile(String path) {
        return new File(this.getClass().getResource(path).getFile());
    }

    @Configuration
    public Option[] config() {
        vmOption("-Dosgi.dev=target/classes");
        switchPlatformEncodingToUTF8();
        MavenArtifactUrlReference karafUrl = maven()
                .groupId("org.apache.karaf")
                .artifactId("apache-karaf")
                .type("tar.gz")
                .versionAsInProject();

        MavenUrlReference karafStandardRepo = maven()
                .groupId("org.apache.karaf.features")
                .artifactId("standard")
                .classifier("features")
                .type("xml")
                .versionAsInProject();

        MavenUrlReference karafSpringRepo = maven()
                .groupId("org.apache.karaf.features")
                .artifactId("spring")
                .classifier("features")
                .type("xml")
                .versionAsInProject();

        return new Option[] {
                // KarafDistributionOption.debugConfiguration("5005", true),
                karafDistributionConfiguration()
                        .frameworkUrl(karafUrl)
                        .unpackDirectory(new File("target/exam"))
                        .useDeployFolder(false),

                //keepRuntimeFolder(),
                replaceConfigurationFile("etc/org.ops4j.pax.logging.cfg", getConfigFile("/etc/org.ops4j.pax.logging.cfg")),
                replaceConfigurationFile("etc/user.properties", getConfigFile("/etc/user.properties")),
                configureSecurity().enableKarafMBeanServerBuilder(),
                editConfigurationFilePut("etc/config.properties", "karaf.framework", "equinox"),
                KarafDistributionOption.features(karafStandardRepo , "ssh"),
                KarafDistributionOption.features(karafStandardRepo , "scr"),
                KarafDistributionOption.features(karafSpringRepo , "spring-dm"),
                KarafDistributionOption.features(karafSpringRepo , "spring-dm-web"),
                KarafDistributionOption.features(karafSpringRepo , "spring-dm-web"),
                editConfigurationFilePut("etc/org.ops4j.pax.web.cfg", "org.osgi.service.http.port", HTTP_PORT),
                editConfigurationFilePut("etc/org.apache.karaf.management.cfg", "rmiRegistryPort", RMI_REG_PORT),
                editConfigurationFilePut("etc/org.apache.karaf.management.cfg", "rmiServerPort", RMI_SERVER_PORT),
                new VMOption("-Djavax.management.builder.initial=org.apache.karaf.management.boot.KarafMBeanServerBuilder"),
                editConfigurationFilePut("etc/jmx.acl.org.apache.karaf.service.cfg", "getServices()", "karaf"),
                editConfigurationFilePut("etc/jmx.acl.org.apache.karaf.service.cfg", "getServices()", "admin"),
                editConfigurationFilePut("etc/jmx.acl.org.apache.karaf.service.cfg", "getServices(boolean)", "viewer"),
                editConfigurationFilePut("etc/jmx.acl.org.apache.karaf.service.cfg", "getServices(long)", "manager"),
                editConfigurationFilePut("etc/jmx.acl.org.apache.karaf.service.cfg", "getServices(long,boolean)", "admin")
/*                mavenBundle()
                        .groupId("com.conx.bi")
                        .artifactId("com.conx.bi.testing.karaf.spring-dm-test.dosgi-sample1-interface")
                        .versionAsInProject().start() ,
                bundle("reference:file:" + PathUtils.getBaseDir() +"/esting/karaf/spring-dm-test/dosgi-sample1-impl"+ "/target/classes/")*/
        };
    }

/*    @Configuration
    public Option[] config() {
        switchPlatformEncodingToUTF8();
        String karafVersion = MavenUtils.getArtifactVersion("org.apache.karaf", "apache-karaf");
        MavenArtifactUrlReference karafUrl = maven().groupId("org.apache.karaf").artifactId("apache-karaf").version(karafVersion).type("tar.gz");
        return new Option[]{
                // KarafDistributionOption.debugConfiguration("8889", true),
                karafDistributionConfiguration().frameworkUrl(karafUrl).name("Apache Karaf").unpackDirectory(new File("target/exam")),
                // enable JMX RBAC security, thanks to the KarafMBeanServerBuilder
                configureSecurity().enableKarafMBeanServerBuilder(),
                keepRuntimeFolder(),
                replaceConfigurationFile("etc/org.ops4j.pax.logging.cfg", getConfigFile("/etc/org.ops4j.pax.logging.cfg")),
                editConfigurationFilePut("etc/org.apache.karaf.features.cfg", "featuresBoot", "config,standard,region,package,kar,management"),
                editConfigurationFilePut("etc/org.ops4j.pax.web.cfg", "org.osgi.service.http.port", HTTP_PORT),
                editConfigurationFilePut("etc/org.apache.karaf.management.cfg", "rmiRegistryPort", RMI_REG_PORT),
                editConfigurationFilePut("etc/org.apache.karaf.management.cfg", "rmiServerPort", RMI_SERVER_PORT)
        };
    }*/

    /**
     * Executes a shell command and returns output as a String.
     * Commands have a default timeout of 10 seconds.
     *
     * @param command The command to execute
     * @param principals The principals (e.g. RolePrincipal objects) to run the command under
     * @return
     */
    protected String executeCommand(final String command, Principal ... principals) {
        return executeCommand(command, COMMAND_TIMEOUT, false, principals);
    }

    /**
     * Executes a shell command and returns output as a String.
     * Commands have a default timeout of 10 seconds.
     *
     * @param command    The command to execute.
     * @param timeout    The amount of time in millis to wait for the command to execute.
     * @param silent     Specifies if the command should be displayed in the screen.
     * @param principals The principals (e.g. RolePrincipal objects) to run the command under
     * @return
     */
    protected String executeCommand(final String command, final Long timeout, final Boolean silent, final Principal ... principals) {
        waitForCommandService(command);

        String response;
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(byteArrayOutputStream);
        final CommandProcessor commandProcessor = getOsgiService(CommandProcessor.class);
        final CommandSession commandSession = commandProcessor.createSession(System.in, printStream, System.err);

        final Callable<String> commandCallable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    if (!silent) {
                        System.err.println(command);
                    }
                    commandSession.execute(command);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
                printStream.flush();
                return byteArrayOutputStream.toString();
            }
        };

        FutureTask<String> commandFuture;
        if (principals.length == 0) {
            commandFuture = new FutureTask<String>(commandCallable);
        } else {
            // If principals are defined, run the command callable via Subject.doAs()
            commandFuture = new FutureTask<String>(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Subject subject = new Subject();
                    subject.getPrincipals().addAll(Arrays.asList(principals));
                    return Subject.doAs(subject, new PrivilegedExceptionAction<String>() {
                        @Override
                        public String run() throws Exception {
                            return commandCallable.call();
                        }
                    });
                }
            });
        }

        try {
            executor.submit(commandFuture);
            response = commandFuture.get(timeout, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace(System.err);
            response = "SHELL COMMAND TIMED OUT: ";
        } catch (ExecutionException e) {
            Throwable cause = e.getCause().getCause();
            throw new RuntimeException(cause.getMessage(), cause);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return response;
    }


    protected <T> T getOsgiService(Class<T> type, long timeout) {
        return getOsgiService(type, null, timeout);
    }

    protected <T> T getOsgiService(Class<T> type) {
        return getOsgiService(type, null, SERVICE_TIMEOUT);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected <T> T getOsgiService(Class<T> type, String filter, long timeout) {
        ServiceTracker tracker = null;
        try {
            String flt;
            if (filter != null) {
                if (filter.startsWith("(")) {
                    flt = "(&(" + Constants.OBJECTCLASS + "=" + type.getName() + ")" + filter + ")";
                } else {
                    flt = "(&(" + Constants.OBJECTCLASS + "=" + type.getName() + ")(" + filter + "))";
                }
            } else {
                flt = "(" + Constants.OBJECTCLASS + "=" + type.getName() + ")";
            }
            Filter osgiFilter = FrameworkUtil.createFilter(flt);
            tracker = new ServiceTracker(bundleContext, osgiFilter, null);
            tracker.open(true);
            // Note that the tracker is not closed to keep the reference
            // This is buggy, as the service reference may change i think
            Object svc = type.cast(tracker.waitForService(timeout));
            if (svc == null) {
                Dictionary dic = bundleContext.getBundle().getHeaders();
                System.err.println("Test bundle headers: " + explode(dic));

                for (ServiceReference ref : asCollection(bundleContext.getAllServiceReferences(null, null))) {
                    System.err.println("ServiceReference: " + ref);
                }

                for (ServiceReference ref : asCollection(bundleContext.getAllServiceReferences(null, flt))) {
                    System.err.println("Filtered ServiceReference: " + ref);
                }

                throw new RuntimeException("Gave up waiting for service " + flt);
            }
            return type.cast(svc);
        } catch (InvalidSyntaxException e) {
            throw new IllegalArgumentException("Invalid filter", e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitForCommandService(String command) {
        // the commands are represented by services. Due to the asynchronous nature of services they may not be
        // immediately available. This code waits the services to be available, in their secured form. It
        // means that the code waits for the command service to appear with the roles defined.

        if (command == null || command.length() == 0) {
            return;
        }

        int spaceIdx = command.indexOf(' ');
        if (spaceIdx > 0) {
            command = command.substring(0, spaceIdx);
        }
        int colonIndx = command.indexOf(':');

        try {
            if (colonIndx > 0) {
                String scope = command.substring(0, colonIndx);
                String function = command.substring(colonIndx + 1);
                waitForService("(&(osgi.command.scope=" + scope + ")(osgi.command.function=" + function + "))", SERVICE_TIMEOUT);
            } else {
                waitForService("(osgi.command.function=" + command + ")", SERVICE_TIMEOUT);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void waitForService(String filter, long timeout) throws InvalidSyntaxException, InterruptedException {
        ServiceTracker st = new ServiceTracker(bundleContext, bundleContext.createFilter(filter), null);
        try {
            st.open();
            st.waitForService(timeout);
        } finally {
            st.close();
        }
    }

    /*
    * Explode the dictionary into a ,-delimited list of key=value pairs
    */
    @SuppressWarnings("rawtypes")
    private static String explode(Dictionary dictionary) {
        Enumeration keys = dictionary.keys();
        StringBuffer result = new StringBuffer();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            result.append(String.format("%s=%s", key, dictionary.get(key)));
            if (keys.hasMoreElements()) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    /**
     * Provides an iterable collection of references, even if the original array is null
     */
    @SuppressWarnings("rawtypes")
    private static Collection<ServiceReference> asCollection(ServiceReference[] references) {
        return references != null ? Arrays.asList(references) : Collections.<ServiceReference>emptyList();
    }

    public JMXConnector getJMXConnector() throws Exception {
        return getJMXConnector("karaf", "karaf");
    }

    public JMXConnector getJMXConnector(String userName, String passWord) throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + RMI_REG_PORT+ "/karaf-root");
        Hashtable<String, Object> env = new Hashtable<String, Object>();
        String[] credentials = new String[]{ userName, passWord };
        env.put("jmx.remote.credentials", credentials);
        JMXConnector connector = JMXConnectorFactory.connect(url, env);
        return connector;
    }

    public void assertFeatureInstalled(String featureName) {
        Feature[] features = featureService.listInstalledFeatures();
        for (Feature feature : features) {
            if (featureName.equals(feature.getName())) {
                return;
            }
        }
        Assert.fail("Feature " + featureName + " should be installed but is not");
    }

    public void assertFeaturesInstalled(String ... expectedFeatures) {
        Set<String> expectedFeaturesSet = new HashSet<String>(Arrays.asList(expectedFeatures));
        Feature[] features = featureService.listInstalledFeatures();
        Set<String> installedFeatures = new HashSet<String>();
        for (Feature feature : features) {
            installedFeatures.add(feature.getName());
        }
        String msg = "Expecting the following features to be installed : " + expectedFeaturesSet + " but found " + installedFeatures;
        Assert.assertTrue(msg, installedFeatures.containsAll(expectedFeaturesSet));
    }

    public void assertContains(String expectedPart, String actual) {
        assertTrue("Should contain '" + expectedPart + "' but was : " + actual, actual.contains(expectedPart));
    }

    public void assertContainsNot(String expectedPart, String actual) {
        Assert.assertFalse("Should not contain '" + expectedPart + "' but was : " + actual, actual.contains(expectedPart));
    }

    protected void assertBundleInstalled(String name) {
        Assert.assertNotNull("Bundle " + name + " should be installed", findBundleByName(name));
    }

    protected void assertBundleNotInstalled(String name) {
        Assert.assertNull("Bundle " + name + " should not be installed", findBundleByName(name));
    }

    protected Bundle findBundleByName(String symbolicName) {
        for (Bundle bundle : bundleContext.getBundles()) {
            if (bundle.getSymbolicName().equals(symbolicName)) {
                return bundle;
            }
        }
        return null;
    }

    protected void installAndAssertFeature(String feature) throws Exception {
        featureService.installFeature(feature);
        assertFeatureInstalled(feature);
    }

    protected void installAssertAndUninstallFeature(String... feature) throws Exception {
        Set<Feature> featuresBefore = new HashSet<Feature>(Arrays.asList(featureService.listInstalledFeatures()));
        try {
            for (String curFeature : feature) {
                featureService.installFeature(curFeature);
                assertFeatureInstalled(curFeature);
            }
        } finally {
            uninstallNewFeatures(featuresBefore);
        }
    }

    /**
     * The feature service does not uninstall feature dependencies when uninstalling a single feature.
     * So we need to make sure we uninstall all features that were newly installed.
     *
     * @param featuresBefore
     * @throws Exception
     */
    protected void uninstallNewFeatures(Set<Feature> featuresBefore)
            throws Exception {
        Feature[] features = featureService.listInstalledFeatures();
        for (Feature curFeature : features) {
            if (!featuresBefore.contains(curFeature)) {
                try {
                    System.out.println("Uninstalling " + curFeature.getName());
                    featureService.uninstallFeature(curFeature.getName(), curFeature.getVersion());
                } catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }

    protected void close(Closeable closeAble) {
        if (closeAble != null) {
            try {
                closeAble.close();
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    protected OutputStream openSshChannel(String username, String password, OutputStream ... outputs) throws Exception {
        client = SshClient.setUpDefaultClient();
        client.start();
        ConnectFuture future = client.connect("localhost", 8101).await();
        session = future.getSession();

        int ret = ClientSession.WAIT_AUTH;
        while ((ret & ClientSession.WAIT_AUTH) != 0) {
            session.authPassword(username, password);
            ret = session.waitFor(ClientSession.WAIT_AUTH | ClientSession.CLOSED | ClientSession.AUTHED, 0);
        }
        if ((ret & ClientSession.CLOSED) != 0) {
            throw new Exception("Could not open SSH channel");
        }
        channel = session.createChannel("shell");
        PipedOutputStream pipe = new PipedOutputStream();
        channel.setIn(new PipedInputStream(pipe));

        OutputStream out;
        if (outputs.length >= 1) {
            out = outputs[0];
        } else {
            out = new ByteArrayOutputStream();
        }
        channel.setOut(out);

        OutputStream err;
        if (outputs.length >= 2) {
            err = outputs[1];
        } else {
            err = new ByteArrayOutputStream();
        }
        channel.setErr(err);
        channel.open();

        return pipe;
    }

    public void closeSshChannel(OutputStream pipe) throws IOException {
        pipe.write("logout\n".getBytes());
        pipe.flush();

        channel.waitFor(ClientChannel.CLOSED, 0);
        session.close(true);
        client.stop();

        client = null;
        channel = null;
        session = null;
    }

    void addUsers(String manageruser, String vieweruser) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStream pipe = openSshChannel("karaf", "karaf", out);
        pipe.write(("jaas:realm-manage --realm=karaf"
                + ";jaas:user-add " + manageruser + " " + manageruser
                + ";jaas:role-add " + manageruser + " manager"
                + ";jaas:role-add " + manageruser + " viewer"
                + ";jaas:user-add " + vieweruser + " " + vieweruser
                + ";jaas:role-add " + vieweruser + " viewer"
                + ";jaas:update;jaas:realm-manage --realm=karaf;jaas:user-list\n").getBytes());
        pipe.flush();
        closeSshChannel(pipe);
        System.out.println(new String(out.toByteArray()));
    }

    protected String assertCommand(String user, String command, Result result) throws Exception, IOException {
        if (!command.endsWith("\n"))
            command += "\n";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        OutputStream pipe = openSshChannel(user, user, out, out);
        pipe.write(command.getBytes());
        pipe.flush();

        closeSshChannel(pipe);
        String output = new String(out.toByteArray());

        switch(result) {
            case OK:
                Assert.assertFalse("Should not contain 'Insufficient credentials' or 'Command not found': " + output,
                        output.contains("Insufficient credentials") || output.contains("Command not found"));
                break;
            case NOT_FOUND:
                Assert.assertTrue("Should contain 'Command not found': " + output,
                        output.contains("Command not found"));
                break;
            case NO_CREDENTIALS:
                Assert.assertTrue("Should contain 'Insufficient credentials': " + output,
                        output.contains("Insufficient credentials"));
                break;
            default:
                Assert.fail("Unexpected enum value: " + result);
        }
        return output;
    }

    protected void switchPlatformEncodingToUTF8() {
        try {
            System.setProperty("file.encoding","UTF-8");
            Field charset = Charset.class.getDeclaredField("defaultCharset");
            charset.setAccessible(true);
            charset.set(null,null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}