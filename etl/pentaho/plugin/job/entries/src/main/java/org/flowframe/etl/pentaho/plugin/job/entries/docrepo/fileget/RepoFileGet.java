package org.flowframe.etl.pentaho.plugin.job.entries.docrepo.fileget;


import org.apache.commons.vfs.FileObject;
import org.flowframe.etl.pentaho.plugin.job.entries.docrepo.RepoConnection;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.pentaho.di.cluster.SlaveServer;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.ResultFile;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.encryption.Encr;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleFileException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.util.UUIDUtil;
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


@org.pentaho.di.core.annotations.JobEntry(
        id="DocRepoFileGet",
        categoryDescription="File transfer",
        i18nPackageName="org.flowframe.etl.pentaho.plugin.job.entries.docrepo.fileget.RepoFileGet",
        image="copy.gif",
        name="DocRepoFileGet",
        description="Doc Repo File Get"
)
public class RepoFileGet extends JobEntryBase implements Cloneable, JobEntryInterface {
    private static Class<?> PKG = RepoFileGet.class; // for i18n purposes, needed by Translator2!!   $NON-NLS-1$

    private String repositoryId = "10180";
    private String companyId = "10180";
    private String folderId = "17603";
    private String loginEmail = "test@liferay.com";
    private String loginPassword = "test";
    private String hostname = "localhost";
    private String port = "7080";
    private String loginGroupId = "10180";

    private int timeout;


    private String fileEntryId = "12345";
    private boolean adddate;
    private boolean addtime;
    private boolean isaddresult;

    private String targetDirectory = "c:\\temp";

    static String FILE_SEPARATOR="/";
    private int NrErrors = 0;
    private String variableName;


    public RepoFileGet(String repositoryId,
                       String companyId,
                       String folderId,
                       String loginEmail,
                       String loginPassword,
                       String hostname,
                       String port,
                       String loginGroupId,
                       String fileEntryId) {
        this.repositoryId = repositoryId;
        this.companyId = companyId;
        this.folderId = folderId;
        this.loginEmail = loginEmail;
        this.loginPassword = loginPassword;
        this.hostname = hostname;
        this.port = port;
        this.loginGroupId = loginGroupId;
        this.fileEntryId = fileEntryId;
        this.isaddresult = true;
    }

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
            throw new KettleXMLException("Unable to load job entry of type 'RepoFileGet' from XML node", xe);
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
            // Create RepoFileGet client to host:port ...
            String realRepositoryId = environmentSubstitute(repositoryId);
            String realCompanyId= environmentSubstitute(companyId);
            String realFolderId= environmentSubstitute(folderId);
            String realHostname = environmentSubstitute(hostname);
            String realLoginEmail = environmentSubstitute(loginEmail);
            String realLoginGroupId = environmentSubstitute(loginGroupId);
            String reaLoginPassword = Encr.decryptPasswordOptionallyEncrypted(environmentSubstitute(loginPassword));
            String realPort = environmentSubstitute(this.port);

            connection = new RepoConnection(realRepositoryId,realCompanyId,realFolderId,realLoginEmail,reaLoginPassword,realHostname,realPort,realLoginGroupId);

            // login to RepoFileGet host ...
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
            folderId = Long.toString(fe.getFolderId());
            Folder fldr = connection.getFolder(folderId);

            InputStream fs = connection.getFileAsStream(fileEntryId);
            String localFilenamePath = targetDirectory+FILE_SEPARATOR+fe.getTitle();
            String localFilename = writeStreamToTempFile(fs,fldr.getName(),fe.getTitle(),fe.getExtension());
            addFilenameToResultFilenames(result,localFilename);
        } catch (Exception e) {
            // Update errors number
            updateErrors();
            logError(BaseMessages.getString(PKG, "JobRepoFileGet.UnexpectedError", e.toString()));
        }
    }

    protected String writeStreamToTempFile(InputStream in, String folderName, String fileName, String ext) throws IOException, KettleFileException {
        byte[] buffer = new byte[1024];
        int bytesRead;

        //FileObject tempFile = KettleVFS.createTempFile(fileName, ext, folderName);
        String filename = new StringBuffer(50).append(folderName).append('/').append(fileName).append('_').append(UUIDUtil.getUUIDAsString()).append("."+ext).toString();
        File fileOut = new File(System.getProperty("java.io.tmpdir"), filename);
        if (!fileOut.getParentFile().exists())
            fileOut.getParentFile().mkdir();

        FileOutputStream out = new FileOutputStream(fileOut);
        //read from is to buffer
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        in.close();
        //flush OutputStream to write any buffered data to file
        out.flush();
        out.close();

        return fileOut.getAbsolutePath();
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
                resultFile.setComment(BaseMessages.getString(PKG, "RepoFileGet.Downloaded", filename)); //$NON-NLS-1$
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

    public String getRepositoryId() {
        return repositoryId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getFolderId() {
        return folderId;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public String getHostname() {
        return hostname;
    }


    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getLoginGroupId() {
        return loginGroupId;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getFileEntryId() {
        return fileEntryId;
    }

    public boolean isIsaddresult() {
        return isaddresult;
    }

    public String getTargetDirectory() {
        return targetDirectory;
    }

    public String getVariableName() {
        return variableName;
    }


    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
}