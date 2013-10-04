/*******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2012 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.www;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.logging.LoggingObjectType;
import org.pentaho.di.core.logging.SimpleLoggingObject;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.UUID;

public class AddTransServlet extends BaseHttpServlet implements CarteServletInterface {
    private static final long serialVersionUID = -6850701762586992604L;

    public static final String CONTEXT_PATH = "/kettle/addTrans";

    public AddTransServlet() {
    }

    public AddTransServlet(TransformationMap transformationMap, SocketRepository socketRepository) {
        super(transformationMap, socketRepository);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logMinimal("Calling ..."+CONTEXT_PATH);
        if (isJettyMode() && !request.getRequestURI().startsWith(CONTEXT_PATH))
            return;

        logMinimal("Encoding: " + request.getCharacterEncoding());
        if (log.isDebug())
            logMinimal("Addition of transformation requested");

        boolean useXML = "Y".equalsIgnoreCase(request.getParameter("xml"));
        long contentLength = Long.valueOf(request.getParameter("length"));

        PrintWriter out = response.getWriter();

        //BufferedReader in = request.getReader();
        if (log.isDetailed())
            logDetailed("Encoding: " + request.getCharacterEncoding());

        if (useXML) {
            response.setContentType("text/xml");
            out.print(XMLHandler.getXMLHeader());
        } else {
            response.setContentType("text/html");
            out.println("<HTML>");
            out.println("<HEAD><TITLE>Add transformation</TITLE></HEAD>");
            out.println("<BODY>");
        }

        response.setStatus(HttpServletResponse.SC_OK);

        try {
            // First read the complete transformation in memory from the request
            //
            BufferedReader in = request.getReader();
            StringBuilder xml = new StringBuilder((int)contentLength);
            int c;
            while ((c = in.read()) != -1) {
                xml.append((char) c);
            }
/*            Enumeration headerNames = request.getHeaderNames();
            while(headerNames.hasMoreElements()) {
                String headerName = (String)headerNames.nextElement();
                logMinimal("" + headerName);
                logMinimal("" + request.getHeader(headerName));
            }
            logMinimal("Content-Length: " + request.getContentLength());
            logMinimal("Reading InputStream...");
            InputStreamReader is = new InputStreamReader(new BufferedInputStream(request.getInputStream()));
            StringBuilder sb=new StringBuilder();
            BufferedReader br = new BufferedReader(is);
            String read = br.readLine();
            while (read != null) {
                sb.append(read);
                read =br.readLine();
            }*/

            // Parse the XML, create a transformation configuration
            //
            logMinimal("XML Trans:\n:" + xml.toString());
            TransConfiguration transConfiguration = TransConfiguration.fromXML(xml.toString());
            TransMeta transMeta = transConfiguration.getTransMeta();
            TransExecutionConfiguration transExecutionConfiguration = transConfiguration.getTransExecutionConfiguration();
            transMeta.setLogLevel(transExecutionConfiguration.getLogLevel());
            if (log.isDetailed()) {
                logDetailed("Logging level set to " + log.getLogLevel().getDescription());
            }
            transMeta.injectVariables(transExecutionConfiguration.getVariables());

            // Also copy the parameters over...
            //
            Map<String, String> params = transExecutionConfiguration.getParams();
            for (String param : params.keySet()) {
                String value = params.get(param);
                transMeta.setParameterValue(param, value);
            }

            // If there was a repository, we know about it at this point in time.
            //
            TransExecutionConfiguration executionConfiguration = transConfiguration.getTransExecutionConfiguration();
            final Repository repository = transConfiguration.getTransExecutionConfiguration().getRepository();

            String carteObjectId = UUID.randomUUID().toString();
            SimpleLoggingObject servletLoggingObject = new SimpleLoggingObject(CONTEXT_PATH, LoggingObjectType.CARTE, null);
            servletLoggingObject.setContainerObjectId(carteObjectId);
            servletLoggingObject.setLogLevel(executionConfiguration.getLogLevel());

            // Create the transformation and store in the list...
            //
            final Trans trans = new Trans(transMeta, servletLoggingObject);

            trans.setRepository(repository);
            trans.setSocketRepository(getSocketRepository());

            getTransformationMap().addTransformation(transMeta.getName(), carteObjectId, trans, transConfiguration);
            trans.setContainerObjectId(carteObjectId);

            if (repository != null) {
                // The repository connection is open: make sure we disconnect from the repository once we
                // are done with this transformation.
                //
                trans.addTransListener(new TransAdapter() {
                    public void transFinished(Trans trans) {
                        repository.disconnect();
                    }
                });
            }

            String message = "Transformation '" + trans.getName() + "' was added to Carte with id "+carteObjectId;

            if (useXML) {
                // Return the log channel id as well
                //
                out.println(new WebResult(WebResult.STRING_OK, message, carteObjectId));
            } else {
                out.println("<H1>" + message + "</H1>");
                out.println("<p><a href=\"" + convertContextPath(GetTransStatusServlet.CONTEXT_PATH) + "?name=" + trans.getName()
                        + "&id="+carteObjectId+"\">Go to the transformation status page</a><p>");
            }
        } catch (Exception ex) {
            if (useXML) {
                out.println(new WebResult(WebResult.STRING_ERROR, Const.getStackTracker(ex)));
            } else {
                out.println("<p>");
                out.println("<pre>");
                ex.printStackTrace(out);
                out.println("</pre>");
            }
        }

        if (!useXML) {
            out.println("<p>");
            out.println("</BODY>");
            out.println("</HTML>");
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logMinimal("Check: " + (isJettyMode() && !request.getRequestURI().startsWith(CONTEXT_PATH)));
        if (isJettyMode() && !request.getRequestURI().startsWith(CONTEXT_PATH))
            return;

        logMinimal("Encoding: " + request.getCharacterEncoding());

        boolean useXML = "Y".equalsIgnoreCase(request.getParameter("xml"));

        PrintWriter out = response.getWriter();

        //BufferedReader in = request.getReader();
        if (log.isDetailed())
            logDetailed("Encoding: " + request.getCharacterEncoding());

        if (useXML) {
            response.setContentType("text/xml");
            out.print(XMLHandler.getXMLHeader());
        } else {
            response.setContentType("text/html");
            out.println("<HTML>");
            out.println("<HEAD><TITLE>Add transformation</TITLE></HEAD>");
            out.println("<BODY>");
        }

        response.setStatus(HttpServletResponse.SC_OK);

        try {
            // First read the complete transformation in memory from the request
            //
/*            StringBuilder xml = new StringBuilder(request.getContentLength());
            int c;
            while ((c = in.read()) != -1) {
                xml.append((char) c);
            }*/

            logMinimal("Reading InputStream...");
            BufferedReader in = request.getReader();
            StringBuilder xml = new StringBuilder(request.getContentLength());
            int c;
            while ((c = in.read()) != -1) {
                xml.append((char) c);
            }

            // Parse the XML, create a transformation configuration
            //
            logMinimal("XML Trans:\n:"+xml.toString());
            TransConfiguration transConfiguration = TransConfiguration.fromXML(xml.toString());
            TransMeta transMeta = transConfiguration.getTransMeta();
            TransExecutionConfiguration transExecutionConfiguration = transConfiguration.getTransExecutionConfiguration();
            transMeta.setLogLevel(transExecutionConfiguration.getLogLevel());
            if (log.isDetailed()) {
                logDetailed("Logging level set to " + log.getLogLevel().getDescription());
            }
            transMeta.injectVariables(transExecutionConfiguration.getVariables());

            // Also copy the parameters over...
            //
            Map<String, String> params = transExecutionConfiguration.getParams();
            for (String param : params.keySet()) {
                String value = params.get(param);
                transMeta.setParameterValue(param, value);
            }

            // If there was a repository, we know about it at this point in time.
            //
            TransExecutionConfiguration executionConfiguration = transConfiguration.getTransExecutionConfiguration();
            final Repository repository = transConfiguration.getTransExecutionConfiguration().getRepository();

            String carteObjectId = UUID.randomUUID().toString();
            SimpleLoggingObject servletLoggingObject = new SimpleLoggingObject(CONTEXT_PATH, LoggingObjectType.CARTE, null);
            servletLoggingObject.setContainerObjectId(carteObjectId);
            servletLoggingObject.setLogLevel(executionConfiguration.getLogLevel());

            // Create the transformation and store in the list...
            //
            final Trans trans = new Trans(transMeta, servletLoggingObject);

            trans.setRepository(repository);
            trans.setSocketRepository(getSocketRepository());

            getTransformationMap().addTransformation(transMeta.getName(), carteObjectId, trans, transConfiguration);
            trans.setContainerObjectId(carteObjectId);

            if (repository != null) {
                // The repository connection is open: make sure we disconnect from the repository once we
                // are done with this transformation.
                //
                trans.addTransListener(new TransAdapter() {
                    public void transFinished(Trans trans) {
                        repository.disconnect();
                    }
                });
            }

            String message = "Transformation '" + trans.getName() + "' was added to Carte with id "+carteObjectId;
            logMinimal(message);
            if (useXML) {
                // Return the log channel id as well
                //
                out.println(new WebResult(WebResult.STRING_OK, message, carteObjectId));
            } else {
                out.println("<H1>" + message + "</H1>");
                out.println("<p><a href=\"" + convertContextPath(GetTransStatusServlet.CONTEXT_PATH) + "?name=" + trans.getName()
                        + "&id="+carteObjectId+"\">Go to the transformation status page</a><p>");
            }
        } catch (Exception ex) {
            if (useXML) {
                out.println(new WebResult(WebResult.STRING_ERROR, Const.getStackTracker(ex)));
            } else {
                out.println("<p>");
                out.println("<pre>");
                ex.printStackTrace(out);
                out.println("</pre>");
            }
        }

        if (!useXML) {
            out.println("<p>");
            out.println("</BODY>");
            out.println("</HTML>");
        }
    }

    public String toString() {
        return "Add Transformation";
    }

    public String getService() {
        return CONTEXT_PATH + " (" + toString() + ")";
    }
    public class BufferedInputStreamReader {

        private InputStream in;
        private int timeout;

        public BufferedInputStreamReader(InputStream in) throws IOException {
            this(in, -1);
        }

        public BufferedInputStreamReader(InputStream in, int timeout)
                throws IOException {
            this.in = in;
            this.timeout = timeout;
        }

        public int read(byte[] b, int off, int len) throws IOException {
            return in.read(b, off, len);
        }

        public int read(byte[] b) throws IOException {
            return in.read(b);
        }

        public String readLine(boolean waitFor) throws IOException {
            long startTime = System.currentTimeMillis();
            String res = null;
            int ch = -1;
            boolean virgin = true;
            do {
                if (ch != -1) {
                    if (ch != '\r' && ch != '\n') {
                        if (virgin) {
                            res = "";
                        }
                        virgin = false;
                        res += (char) ch;
                    } else if (ch == '\n') {
                        return res == null ? "" : res;
                    }
                }
                ch = in.read();
            } while ((waitFor || in.available() > 0)
                    && (timeout < 0 || startTime + timeout > System
                    .currentTimeMillis()));
            return res;
        }

        public String readLine() throws IOException {
            return readLine(true);
        }

        public void close() throws IOException {
            in.close();
        }
    }}
