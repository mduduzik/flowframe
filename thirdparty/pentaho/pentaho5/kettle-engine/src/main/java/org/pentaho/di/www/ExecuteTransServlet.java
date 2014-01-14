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

import org.apache.commons.codec.binary.Base64;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.encryption.Encr;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.*;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.RepositoryPluginType;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.repository.*;
import org.pentaho.di.trans.*;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.GZIPOutputStream;

public class ExecuteTransServlet extends BaseHttpServlet implements CartePluginInterface {

    private static Class<?> PKG = ExecuteTransServlet.class;  // i18n

    private static final long serialVersionUID = -5879219287669847357L;

    public static final String CONTEXT_PATH = "/kettle/executeTrans";

    public ExecuteTransServlet() {
    }

    public ExecuteTransServlet(TransformationMap transformationMap) {
        super(transformationMap);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isJettyMode() && !request.getContextPath().startsWith(CONTEXT_PATH)) {
            return;
        }

        if (log.isDebug())
            logDebug(BaseMessages.getString(PKG, "ExecuteTransServlet.Log.ExecuteTransRequested"));

        // Options taken from PAN
        //
        String[] knownOptions = new String[]{"rep", "user", "pass", "trans", "level",};

        String repOption = request.getParameter("rep");
        String userOption = request.getParameter("user");
        String passOption = Encr.decryptPasswordOptionallyEncrypted(request.getParameter("pass"));
        String transOption = request.getParameter("trans");
        String levelOption = request.getParameter("level");

        int startLineNr = Const.toInt(request.getParameter("from"), 0);

        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();

        try {

            final Repository repository = openRepository(repOption, userOption, passOption);
            final TransMeta transMeta = loadTransformation(repository, transOption);

            // Set the servlet parameters as variables in the transformation
            //
            String[] parameters = transMeta.listParameters();
            Enumeration<?> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameter = (String) parameterNames.nextElement();
                String[] values = request.getParameterValues(parameter);

                // Ignore the known options. set the rest as variables
                //
                if (Const.indexOfString(parameter, knownOptions) < 0) {
                    // If it's a trans parameter, set it, otherwise simply set the variable
                    //
                    if (Const.indexOfString(parameter, parameters) < 0) {
                        transMeta.setVariable(parameter, values[0]);
                    } else {
                        transMeta.setParameterValue(parameter, values[0]);
                    }
                }
            }

            TransExecutionConfiguration transExecutionConfiguration = new TransExecutionConfiguration();
            LogLevel logLevel = LogLevel.getLogLevelForCode(levelOption);
            transExecutionConfiguration.setLogLevel(logLevel);
            TransConfiguration transConfiguration = new TransConfiguration(transMeta, transExecutionConfiguration);

            String carteObjectId = UUID.randomUUID().toString();
            SimpleLoggingObject servletLoggingObject = new SimpleLoggingObject(CONTEXT_PATH, LoggingObjectType.CARTE, null);
            servletLoggingObject.setContainerObjectId(carteObjectId);
            servletLoggingObject.setLogLevel(logLevel);

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

            // Pass the servlet print writer to the transformation...
            //
            trans.setServletPrintWriter(out);
            trans.setServletReponse(response);
            trans.setServletRequest(request);

            try {
                // Execute the transformation...
                //
                executeTrans(trans);


                //gather status
                CarteObjectEntry entry = new CarteObjectEntry(transMeta.getName(), carteObjectId);
                String status = trans.getStatus();
                int lastLineNr = KettleLogStore.getLastBufferLineNr();
                List<KettleLoggingEvent> logEvents = KettleLogStore.getAppender().getLogBufferFromTo(trans.getLogChannel().getLogChannelId(), false, startLineNr, lastLineNr);
                StringBuffer stringBuffer = new StringBuffer(10000);
                int index = startLineNr;
                String record;
                for (KettleLoggingEvent event : logEvents) {
                    if (logEvents.size() == 1 || stringBuffer.length() == 0)
                        stringBuffer.append("[" + (index++) + ",\"" + encodeJson(new Date(event.getTimeStamp()).toString()) + "\",\"" + encodeJson(event.getMessage().toString()) + "\"]");
                    else
                        stringBuffer.append(",[" + (index++) + ",\"" + encodeJson(new Date(event.getTimeStamp()).toString()) + "\",\"" + encodeJson(event.getMessage().toString()) + "\"]");
                }
                String logText = "{\"records\":[" + stringBuffer.toString() + "]}";

                response.setContentType("text/xml");
                response.setCharacterEncoding(Const.XML_ENCODING);
                out.print(XMLHandler.getXMLHeader(Const.XML_ENCODING));

                SlaveServerTransStatus transStatus = new SlaveServerTransStatus(transMeta.getName(), entry.getId(), status);
                transStatus.setFirstLoggingLineNr(startLineNr);
                transStatus.setLastLoggingLineNr(lastLineNr);

                for (int i = 0; i < trans.nrSteps(); i++) {
                    StepInterface baseStep = trans.getRunThread(i);
                    if ((baseStep.isRunning()) || baseStep.getStatus() != BaseStepData.StepExecutionStatus.STATUS_EMPTY) {
                        StepStatus stepStatus = new StepStatus(baseStep);
                        transStatus.getStepStatusList().add(stepStatus);
                    }
                }

                // The log can be quite large at times, we are going to put a base64 encoding around a compressed stream
                // of bytes to handle this one.

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                GZIPOutputStream gzos = new GZIPOutputStream(baos);
                gzos.write(logText.getBytes());
                gzos.close();

                String loggingString = new String(Base64.encodeBase64(baos.toByteArray()));
                transStatus.setLoggingString(loggingString);

                // Also set the result object...
                //
                transStatus.setResult(trans.getResult());

                // Is the transformation paused?
                //
                transStatus.setPaused(trans.isPaused());

                // Send the result back as XML
                //
                try {
                    out.println(transStatus.getXML());
                } catch (KettleException e) {
                    throw new ServletException("Unable to get the transformation status in XML format", e);
                }
                out.flush();

            } catch (Exception executionException) {
                String logging = KettleLogStore.getAppender().getBuffer(trans.getLogChannelId(), false).toString();
                throw new KettleException("Error executing transformation: " + logging, executionException);
            }
        } catch (Exception ex) {

            out.println(new WebResult(WebResult.STRING_ERROR, BaseMessages.getString(PKG, "ExecuteTransServlet.Error.UnexpectedError", Const.CR + Const.getStackTracker(ex))));
        }
    }

    private TransMeta loadTransformation(Repository repository, String trans) throws KettleException {

        if (repository == null) {

            // Without a repository it's a filename --> file:///foo/bar/trans.ktr
            //
            TransMeta transMeta = new TransMeta(trans);
            return transMeta;

        } else {

            // With a repository we need to load it from /foo/bar/Transformation
            // We need to extract the folder name from the path in front of the name...
            //
            String directoryPath;
            String name;
            int lastSlash = trans.lastIndexOf(RepositoryDirectory.DIRECTORY_SEPARATOR);
            if (lastSlash < 0) {
                directoryPath = "/";
                name = trans;
            } else {
                directoryPath = trans.substring(0, lastSlash);
                name = trans.substring(lastSlash + 1);
            }
            RepositoryDirectoryInterface directory = repository.loadRepositoryDirectoryTree().findDirectory(directoryPath);
            if (directory == null) {
                throw new KettleException("Unable to find directory path '" + directoryPath + "' in the repository");
            }

            ObjectId transformationID = repository.getTransformationID(name, directory);
            if (transformationID == null) {
                throw new KettleException("Unable to find transformation '" + name + "' in directory :" + directory);
            }
            TransMeta transMeta = repository.loadTransformation(transformationID, null);
            return transMeta;
        }
    }

    private Repository openRepository(String repositoryName, String user, String pass) throws KettleException {

        if (Const.isEmpty(repositoryName)) return null;

        RepositoriesMeta repositoriesMeta = new RepositoriesMeta();
        repositoriesMeta.readData();
        RepositoryMeta repositoryMeta = repositoriesMeta.findRepository(repositoryName);
        if (repositoryMeta == null) {
            throw new KettleException("Unable to find repository: " + repositoryName);
        }
        PluginRegistry registry = PluginRegistry.getInstance();
        Repository repository = registry.loadClass(
                RepositoryPluginType.class,
                repositoryMeta,
                Repository.class
        );
        repository.init(repositoryMeta);
        repository.connect(user, pass);
        return repository;
    }

    public String toString() {
        return "Start transformation";
    }

    public String getService() {
        return CONTEXT_PATH + " (" + toString() + ")";
    }

    protected void executeTrans(Trans trans) throws KettleException {
        trans.prepareExecution(null);
        trans.startThreads();
        trans.waitUntilFinished();
    }

    public String getContextPath() {
        return CONTEXT_PATH;
    }

    private String encodeJson(String message) {
        message = message.replaceAll("\\[", "%5B");
        message = message.replaceAll("\\]", "%5D");
        message = message.replaceAll("\\{", "%7B");
        message = message.replaceAll("\\}", "%7D");
        message = message.replaceAll(":", "%3A");
        message = message.replaceAll(";", "%3B");
        message = message.replaceAll(",", "%2C");
        message = message.replaceAll("\"", "%22");
        message = message.replaceAll("'", "%27");
        message = message.replaceAll("\\\\", "%5C");
        return message;
    }

}