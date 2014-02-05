package org.flowframe.etl.pentaho.plugin.di.docrepo;

import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.documentlibrary.remote.services.impl.min.LiferayPortalDocumentRepositoryImpl;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;

import java.io.InputStream;

/**
 * Created by Mduduzi on 10/21/13.
 */
public class RepoConnection {
    private String repositoryId;
    private String companyId;
    private String folderId;
    private String loginEmail;
    private String loginPassword;
    private String hostname;
    private String port;
    private String loginGroupId;

    private IRemoteDocumentRepository repository = null;

    public RepoConnection(String repositoryId, String companyId, String folderId, String loginEmail, String loginPassword, String hostname, String port, String loginGroupId) {
        this.repositoryId = repositoryId;
        this.companyId = companyId;
        this.folderId = folderId;
        this.loginEmail = loginEmail;
        this.loginPassword = loginPassword;
        this.hostname = hostname;
        this.port = port;
        this.loginGroupId = loginGroupId;
    }


    public void connect() throws Exception {
        if (this.repository == null){
            repository = new LiferayPortalDocumentRepositoryImpl(repositoryId,companyId,folderId,loginEmail,loginPassword,hostname,port,loginGroupId);
            repository.init();
        }
    }

    public void disconnect() {
        this.repository = null;
    }

    /**
     *
     * File OPS
     *
     */
    public FileEntry getFileEntry(String fileEntryId) throws Exception {
        return this.repository.getFileEntryById(fileEntryId);
    }
    public InputStream getFileAsStream(String fileEntryId) throws Exception {
        FileEntry fe = this.repository.getFileEntryById(fileEntryId);
        InputStream in = this.repository.getFileAsStream(fileEntryId, null);
        return in;
    }

    /**
     *
     * Folder OPS
     *
     */
    public Folder getFolder(String folderId) throws Exception {
        return this.repository.getFolderById(folderId);
    }
}
