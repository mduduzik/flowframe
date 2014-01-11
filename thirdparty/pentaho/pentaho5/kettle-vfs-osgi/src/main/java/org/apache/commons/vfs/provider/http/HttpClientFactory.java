/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.vfs.provider.http;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.UserAuthenticationData;
import org.apache.commons.vfs.UserAuthenticator;
import org.apache.commons.vfs.util.UserAuthenticatorUtils;

/**
 * Create a HttpClient instance.
 *
 * @author <a href="mailto:imario@apache.org">Mario Ivankovits</a>
 * @version $Revision: 833480 $ $Date: 2009-11-06 12:37:05 -0500 (Fri, 06 Nov 2009) $
 */
public class HttpClientFactory
{
    private HttpClientFactory()
    {
    }

    public static HttpClient createConnection(String scheme, String hostname, int port, String username,
                                              String password, FileSystemOptions fileSystemOptions)
            throws FileSystemException
    {
        return createConnection(HttpFileSystemConfigBuilder.getInstance(), scheme, hostname, port,
            username, password, fileSystemOptions);
    }

    /**
     * Creates a new connection to the server.
     * @param builder The HttpFileSystemConfigBuilder.
     * @param scheme The protocol.
     * @param hostname The hostname.
     * @param username The username.
     * @param password The password
     * @param fileSystemOptions The file system options.
     * @return a new HttpClient connection.
     * @throws FileSystemException if an error occurs.
     */
    public static HttpClient createConnection(HttpFileSystemConfigBuilder builder, String scheme,
                                              String hostname, int port, String username,
                                              String password, FileSystemOptions fileSystemOptions)
            throws FileSystemException
    {
        HttpClient client;
        try
        {
            HttpConnectionManager mgr = new MultiThreadedHttpConnectionManager();
            HttpConnectionManagerParams connectionMgrParams = mgr.getParams();

            client = new HttpClient(mgr);

            final HostConfiguration config = new HostConfiguration();
            config.setHost(hostname, port, scheme);

            if (fileSystemOptions != null)
            {
                String proxyHost = builder.getProxyHost(fileSystemOptions);
                int proxyPort = builder.getProxyPort(fileSystemOptions);

                if (proxyHost != null && proxyHost.length() > 0 && proxyPort > 0)
                {
                    config.setProxy(proxyHost, proxyPort);
                }

                UserAuthenticator proxyAuth = builder.getProxyAuthenticator(fileSystemOptions);
                if (proxyAuth != null)
                {
                    UserAuthenticationData authData = UserAuthenticatorUtils.authenticate(proxyAuth,
                        new UserAuthenticationData.Type[]
                        {
                            UserAuthenticationData.USERNAME,
                            UserAuthenticationData.PASSWORD
                        });

                    if (authData != null)
                    {
                        final UsernamePasswordCredentials proxyCreds =
                            new UsernamePasswordCredentials(
                                UserAuthenticatorUtils.toString(UserAuthenticatorUtils.getData(authData,
                                    UserAuthenticationData.USERNAME, null)),
                                UserAuthenticatorUtils.toString(UserAuthenticatorUtils.getData(authData,
                                    UserAuthenticationData.PASSWORD, null)));

                        client.getState().setProxyCredentials(null, proxyHost, proxyCreds);
                    }
                }

                Cookie[] cookies = builder.getCookies(fileSystemOptions);
                if (cookies != null)
                {
                    client.getState().addCookies(cookies);
                }
            }
            /**
             * ConnectionManager set methodsmust be called after the host & port and proxy host & port
             * are set in the HostConfiguration. They are all used as part of the key when HttpConnectionManagerParams
             * tries to locate the host configuration.
             */
            connectionMgrParams.setMaxConnectionsPerHost(config, builder.getMaxConnectionsPerHost(fileSystemOptions));
            connectionMgrParams.setMaxTotalConnections(builder.getMaxTotalConnections(fileSystemOptions));

            client.setHostConfiguration(config);

            if (username != null)
            {
                final UsernamePasswordCredentials creds =
                    new UsernamePasswordCredentials(username, password);
                client.getState().setCredentials(null, hostname, creds);
            }

            client.executeMethod(new HeadMethod());
        }
        catch (final Exception exc)
        {
            throw new FileSystemException("vfs.provider.http/connect.error", new Object[]{hostname}, exc);
        }

        return client;
    }
}
