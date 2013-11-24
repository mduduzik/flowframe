package org.flowframe.etl.pentaho.server.plugins.core.utils.repository.doclib;

import org.flowframe.documentlibrary.remote.services.IRemoteDocumentRepository;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;

import java.net.URI;

/**
 * Created by Mduduzi on 11/22/13.
 */
public class DocLibUtil {
    public static URI getFileEntryWebDavURI(IRemoteDocumentRepository ecmService,String fileEntryId) throws Exception {
        final URI templateUri = new URI(ecmService.getFileAsURL(fileEntryId,null));

        final String scheme = templateUri.getScheme();
        final String hostname = templateUri.getHost();
        final Integer port = templateUri.getPort();
        final String authority = templateUri.getAuthority();


        StringBuilder path = new StringBuilder();
        path.append("/api/secure/webdav"+"/guest/document_library");

        final FileEntry fe = ecmService.getFileEntryById(fileEntryId);

        Folder folder = ecmService.getFolderById(Long.toString(fe.getFolderId()));
        String folderPath = getFolderPath(ecmService,folder);

        path.append(folderPath+"/"+fe.getTitle());

        return new URI(scheme/*String scheme*/,
                "test@liferay.com:test"/*String userInfo*/,
                hostname/*String host*/,
                port/*int port*/,
                path.toString()/*String path*/,
                null,
                null);
    }


    public static String getFolderPath(IRemoteDocumentRepository ecmService, Folder folder) throws Exception {
        StringBuilder folderPath = new StringBuilder();
        folderPath.append(folder.getName());

        while (folder.getParentFolderId() > 0) {
            folder = ecmService.getFolderById(Long.toString(folder.getParentFolderId()));
            folderPath.append("/" + folder.getName());
        }

        //Reverse
        final String[] tokens = folderPath.toString().split("/");
        folderPath = new StringBuilder();
        for (int i=tokens.length-1; i>=0; i--) {
            folderPath.append("/"+tokens[i]);
        }

        return folderPath.toString();
    }

    public URI getInternalFolderURI(IRemoteDocumentRepository ecmService, String folderId) throws Exception {
        String scheme = "ff";
        String authority = "repo";
        String path = "/internal";
        String query="folder";
        String fragment =folderId;
        return new URI(scheme, authority, path, query, fragment);
    }

    public static URI getInternalFileEntryURI(IRemoteDocumentRepository ecmService,String fileEntryId) throws Exception {
        String scheme = "ff";
        String authority = "repo";
        String path = "/internal";
        String query="fileentry";
        String fragment =fileEntryId;
        return new URI(scheme, authority, path, query, fragment);
    }

    public static URI getFileEntryWebDavURI(IRemoteDocumentRepository ecmService, URI internalURI) throws Exception {
        String fileEntryId =internalURI.getFragment();
        return DocLibUtil.getFileEntryWebDavURI(ecmService,fileEntryId);
    }

    public static String getFileEntryTitle(IRemoteDocumentRepository ecmService, URI internalURI) throws Exception {
        String fileEntryId =internalURI.getFragment();

        final FileEntry fe = ecmService.getFileEntryById(fileEntryId);

        return fe.getTitle();
    }
}
