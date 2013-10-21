package org.flowframe.etl.pentaho.plugin.job.entries.docrepo.fileget;


import org.apache.commons.vfs.FileObject;
import org.flowframe.etl.pentaho.plugin.job.entries.docrepo.RepoConnection;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.pentaho.di.cluster.SlaveServer;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.ResultFile;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.encryption.Encr;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.vfs.KettleVFS;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entry.JobEntryBase;
import org.pentaho.di.job.entry.JobEntryInterface;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.w3c.dom.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.pentaho.di.job.entry.validator.AndValidator.putValidators;
import static org.pentaho.di.job.entry.validator.JobEntryValidatorUtils.*;

public class RepoFileGet extends JobEntryBase implements Cloneable, JobEntryInterface {
    private static Class<?> PKG = RepoFileGet.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$

    private String repositoryId;
    private String companyId;
    private String folderId;
    private String loginEmail;
    private String loginPassword;
    private String hostname;
    private String port;
    private String loginGroupId;

    private int timeout;


    private String fileEntryId;
    private boolean adddate;
    private boolean addtime;
    private boolean isaddresult;

    private String targetDirectory;

    static String FILE_SEPARATOR="/";
    private int NrErrors = 0;

    public RepoFileGet(String n) {
        super(n, "");

        setID(-1L);
    }

    public RepoFileGet() {
        this("");
    }

    public Object clone() {
        RepoFileGet je = (RepoFileGet) super.clone();
        return je;
    }

    public String getXML() {
        StringBuffer retval = new StringBuffer(128);

        retval.append(super.getXML());
        retval.append("      ").append(XMLHandler.addTagValue("port", port));
        retval.append("      ").append(XMLHandler.addTagValue("hostname", hostname));
        retval.append("      ").append(XMLHandler.addTagValue("loginEmail", loginEmail));
        retval.append("      ").append(XMLHandler.addTagValue("loginPassword", Encr.encryptPasswordIfNotUsingVariables(loginPassword)));
        retval.append("      ").append(XMLHandler.addTagValue("folderId", folderId));
        retval.append("      ").append(XMLHandler.addTagValue("loginGroupId", loginGroupId));
        retval.append("      ").append(XMLHandler.addTagValue("timeout", timeout));
        retval.append("      ").append(XMLHandler.addTagValue("fileEntryId", fileEntryId));
        retval.append("      ").append(XMLHandler.addTagValue("targetDirectory", targetDirectory));
        retval.append("      ").append(XMLHandler.addTagValue("adddate", adddate));
        retval.append("      ").append(XMLHandler.addTagValue("addtime", addtime));
        retval.append("      ").append(XMLHandler.addTagValue("isaddresult", isaddresult));

        return retval.toString();
    }

    public void loadXML(Node entrynode, List<DatabaseMeta> databases, List<SlaveServer> slaveServers, Repository rep) throws KettleXMLException {
        try {
            super.loadXML(entrynode, databases, slaveServers);
            port = XMLHandler.getTagValue(entrynode, "port");
            hostname = XMLHandler.getTagValue(entrynode, "hostname");
            loginEmail = XMLHandler.getTagValue(entrynode, "loginEmail");
            loginPassword = Encr.decryptPasswordOptionallyEncrypted(XMLHandler.getTagValue(entrynode, "loginPassword"));
            folderId = XMLHandler.getTagValue(entrynode, "folderId");
            targetDirectory = XMLHandler.getTagValue(entrynode, "targetDirectory");
            loginGroupId = XMLHandler.getTagValue(entrynode, "loginGroupId");
            fileEntryId = XMLHandler.getTagValue(entrynode, "fileEntryId");
            timeout = Const.toInt(XMLHandler.getTagValue(entrynode, "timeout"), 10000);

            String adddate_ = XMLHandler.getTagValue(entrynode, "adddate");
            if (Const.isEmpty(adddate_))
                adddate = true;
            else
                adddate = "Y".equalsIgnoreCase(adddate_);

            String addtime_ = XMLHandler.getTagValue(entrynode, "addtime");
            if (Const.isEmpty(addtime_))
                addtime = true;
            else
                addtime = "Y".equalsIgnoreCase(addtime_);

            String isaddresult_ = XMLHandler.getTagValue(entrynode, "isaddresult");
            if (Const.isEmpty(isaddresult_))
                isaddresult = true;
            else
                isaddresult = "Y".equalsIgnoreCase(isaddresult_);

        } catch (Exception xe) {
            throw new KettleXMLException("Unable to load job entry of type 'FTPS' from XML node", xe);
        }
    }


    public void loadRep(Repository rep, ObjectId id_jobentry, List<DatabaseMeta> databases, List<SlaveServer> slaveServers) throws KettleException {
        try {
            port = rep.getJobEntryAttributeString(id_jobentry, "port");
            hostname = rep.getJobEntryAttributeString(id_jobentry, "hostname");
            loginEmail = rep.getJobEntryAttributeString(id_jobentry, "loginEmail");
            loginPassword = Encr.decryptPasswordOptionallyEncrypted(rep.getJobEntryAttributeString(id_jobentry, "loginPassword"));
            folderId = rep.getJobEntryAttributeString(id_jobentry, "folderId");
            targetDirectory = rep.getJobEntryAttributeString(id_jobentry, "targetDirectory");
            loginGroupId = rep.getJobEntryAttributeString(id_jobentry, "loginGroupId");
            fileEntryId = rep.getJobEntryAttributeString(id_jobentry, "fileEntryId");
            timeout = (int) rep.getJobEntryAttributeInteger(id_jobentry, "timeout");
            adddate = (boolean) rep.getJobEntryAttributeBoolean(id_jobentry, "adddate");
            addtime = (boolean) rep.getJobEntryAttributeBoolean(id_jobentry, "addtime");
            isaddresult = (boolean) rep.getJobEntryAttributeBoolean(id_jobentry, "isaddresult");
        } catch (KettleException dbe) {
            throw new KettleException("Unable to load job entry of type 'RepoFileGet' from the repository for id_jobentry=" + id_jobentry, dbe);
        }
    }

    public void saveRep(Repository rep, ObjectId id_job) throws KettleException {
        try {
            rep.saveJobEntryAttribute(id_job, getObjectId(), "port", port);
            rep.saveJobEntryAttribute(id_job, getObjectId(), "hostname", hostname);
            rep.saveJobEntryAttribute(id_job, getObjectId(), "loginEmail", loginEmail);
            rep.saveJobEntryAttribute(id_job, getObjectId(), "loginPassword", Encr.encryptPasswordIfNotUsingVariables(loginPassword));
            rep.saveJobEntryAttribute(id_job, getObjectId(), "folderId", folderId);
            rep.saveJobEntryAttribute(id_job, getObjectId(), "targetDirectory", targetDirectory);
            rep.saveJobEntryAttribute(id_job, getObjectId(), "loginGroupId", loginGroupId);
            rep.saveJobEntryAttribute(id_job, getObjectId(), "fileEntryId", fileEntryId);
            rep.saveJobEntryAttribute(id_job, getObjectId(), "timeout", timeout);
            rep.saveJobEntryAttribute(id_job, getObjectId(), "adddate", adddate);
            rep.saveJobEntryAttribute(id_job, getObjectId(), "addtime", addtime);
            rep.saveJobEntryAttribute(id_job, getObjectId(), "isaddresult", isaddresult);
        } catch (KettleDatabaseException dbe) {
            throw new KettleException("Unable to save job entry of type 'RepoFileGet' to the repository for id_job=" + id_job, dbe);
        }
    }


    public Result execute(Result previousResult, int nr) throws KettleException {
        //LogWriter log = LogWriter.getInstance();
        logBasic(BaseMessages.getString(PKG, "JobEntryRepoFileGet.Started", hostname)); //$NON-NLS-1$

        Result result = previousResult;
        result.setNrErrors(1);
        result.setResult(false);
        NrErrors = 0;
        boolean exitjobentry = false;


        String localFolder = environmentSubstitute(targetDirectory);

        if (isDetailed()) logDetailed(BaseMessages.getString(PKG, "RepoFileGet.Start")); //$NON-NLS-1$

        RepoConnection connection = null;

        try {
            // Create FTPS client to host:port ...
            String realRepositoryId = environmentSubstitute(repositoryId);
            String realCompanyId= environmentSubstitute(companyId);
            String realFolderId= environmentSubstitute(folderId);
            String realHostname = environmentSubstitute(hostname);
            String realLoginEmail = environmentSubstitute(loginEmail);
            String realLoginGroupId = environmentSubstitute(loginGroupId);
            String reaLoginPassword = Encr.decryptPasswordOptionallyEncrypted(environmentSubstitute(loginPassword));
            String realPort = environmentSubstitute(this.port);

            connection = new RepoConnection(realRepositoryId,realCompanyId,realFolderId,realLoginEmail,reaLoginPassword,realHostname,realPort,realLoginGroupId);

            // login to FTPS host ...
            connection.connect();
            if (isDetailed()) {
                logDetailed(BaseMessages.getString(PKG, "RepoFileGet.LoggedIn", realLoginEmail));
            }

            if (!exitjobentry) {
                // Get all the files in the current directory...
                downloadFile(connection, folderId, fileEntryId, result);
            }

        } catch (Exception e) {
            if (!exitjobentry) updateErrors();
            logError(BaseMessages.getString(PKG, "RepoFileGet.ErrorGetting", e.getMessage())); //$NON-NLS-1$
        } finally {
            if (connection != null) {
                try {
                    connection.disconnect();
                } catch (Exception e) {
                    logError(BaseMessages.getString(PKG, "RepoFileGet.ErrorQuitting", e.getMessage())); //$NON-NLS-1$
                }
            }
        }

        result.setNrErrors(NrErrors);
        if (getSuccessStatus()) result.setResult(true);
        if (exitjobentry) result.setResult(false);

        displayResults();

        return result;
    }

    private void downloadFile(RepoConnection connection, String folderId, String fileEntryId, Result result) throws KettleException {
        if (isDetailed())
            logDetailed("RepoFileGet.FileEntryId "+fileEntryId); //$NON-NLS-1$

        if (parentJob.isStopped()) {
            throw new KettleException(BaseMessages.getString(PKG, "RepoFileGet.JobStopped"));
        }

        try {
            FileEntry fe = connection.getFileEntry(fileEntryId);
            InputStream fs = connection.getFileAsStream(fileEntryId);
            String localFilenamePath = targetDirectory+FILE_SEPARATOR+fe.getTitle();
            File localFile = writeStreamToTempFile(fs, fe.getTitle());
            addFilenameToResultFilenames(result,localFile.getAbsolutePath());
        } catch (Exception e) {
            // Update errors number
            updateErrors();
            logError(BaseMessages.getString(PKG, "JobFTPS.UnexpectedError", e.toString()));
        }
    }

    protected File writeStreamToTempFile(InputStream in, String fileName) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        File fileOut = new File(System.getProperty("java.io.tmpdir"), fileName+"."+System.currentTimeMillis());
        FileOutputStream out = new FileOutputStream(fileOut);
        //read from is to buffer
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        in.close();
        //flush OutputStream to write any buffered data to file
        out.flush();
        out.close();

        return fileOut;
    }

    /**
     * normalize / to \ and remove trailing slashes from a path
     *
     * @param path
     * @return normalized path
     * @throws Exception
     */
    public String normalizePath(String path) throws Exception {

        String normalizedPath = path.replaceAll("\\\\", FILE_SEPARATOR);
        while (normalizedPath.endsWith("\\") || normalizedPath.endsWith(FILE_SEPARATOR)) {
            normalizedPath = normalizedPath.substring(0, normalizedPath.length() - 1);
        }

        return normalizedPath;
    }

    private void addFilenameToResultFilenames(Result result, String filename) throws KettleException {
        if (isaddresult) {
            FileObject targetFile = null;
            try {
                targetFile = KettleVFS.getFileObject(filename);

                // Add to the result files...
                ResultFile resultFile = new ResultFile(ResultFile.FILE_TYPE_GENERAL, targetFile, parentJob.getJobname(), toString());
                resultFile.setComment(BaseMessages.getString(PKG, "RepoFileGet.Downloaded", this.hostname)); //$NON-NLS-1$
                result.getResultFiles().put(resultFile.getFile().toString(), resultFile);

                if (isDetailed())
                    logDetailed(BaseMessages.getString(PKG, "RepoFileGet.FileAddedToResult", filename)); //$NON-NLS-1$
            } catch (Exception e) {
                throw new KettleException(e);
            } finally {
                try {
                    targetFile.close();
                    targetFile = null;
                } catch (Exception e) {
                }
            }
        }
    }

    private void displayResults() {
        if (isDetailed()) {
            logDetailed("=======================================");
            logDetailed(BaseMessages.getString(PKG, "RepoFileGet.Log.Info.FileRetrieved", "!!!"));
            logDetailed("=======================================");
        }
    }

    private boolean getSuccessStatus() {
        boolean retval = true;
        return retval;
    }

    private void updateErrors() {
        NrErrors++;
    }



    public boolean evaluates() {
        return true;
    }



    @Override
    public void check(List<CheckResultInterface> remarks, JobMeta jobMeta) {
        andValidator().validate(this, "serverName", remarks, putValidators(notBlankValidator())); //$NON-NLS-1$
        andValidator()
                .validate(this, "localDirectory", remarks, putValidators(notBlankValidator(), fileExistsValidator())); //$NON-NLS-1$
        andValidator().validate(this, "userName", remarks, putValidators(notBlankValidator())); //$NON-NLS-1$
        andValidator().validate(this, "password", remarks, putValidators(notNullValidator())); //$NON-NLS-1$
        andValidator().validate(this, "serverPort", remarks, putValidators(integerValidator())); //$NON-NLS-1$
    }

}