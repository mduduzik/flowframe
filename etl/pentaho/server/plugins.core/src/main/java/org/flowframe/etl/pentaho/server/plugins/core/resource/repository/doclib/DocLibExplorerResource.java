package org.flowframe.etl.pentaho.server.plugins.core.resource.repository.doclib;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.server.plugins.core.resource.BaseDelegateResource;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.glassfish.jersey.media.multipart.*;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("docexplorer")
@Component
public class DocLibExplorerResource extends BaseDelegateResource {
    public static String REPOSITORY_ITEM_TYPE = "itemtype";
    public static String REPOSITORY_ITEM_TYPE_DATABASE = "database";
    public static String REPOSITORY_ITEM_TYPE_CSVMETA = "csvmeta";
    public static String REPOSITORY_ITEM_TYPE_EXCELMETA = "excelmeta";
    public static String REPOSITORY_ITEM_PARENTFOLDER_OBJID = "folderObjectId";


    public static String REPOSITORY_ITEMCONTAINER_TYPE = "itemcontainertype";
    public static String REPOSITORY_ITEMCONTAINER_TYPE_REPOFOLDER = "repofolder";
    public static String REPOSITORY_ITEMCONTAINER_TYPE_DOCREPOFOLDER = "docrepofolder";
    public static String REPOSITORY_ITEMCONTAINER_TYPE_DOCREPOFILEITEM = "docrepofileentry";
    public static String REPOSITORY_ITEMCONTAINER_TYPE_DYNAMIC = "dynamic";
    public static String REPOSITORY_REPOFOLDER_OBJID = "folderObjectId";

    public static String REPOSITORY_UI_TREE_LOADING_TYPE = "loadingtype";
    public static String REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND = "ondemand";
    public static String REPOSITORY_UI_TREE_LOADING_TYPE_ONTIME = "ontime";
    public static String REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME = "menugroup";
    public static String REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME = "ddenabled";
    private static final String REPOSITORY_UI_TREE_NODE_CAN_DELETE = "candelete";
    private static final String REPOSITORY_UI_TREE_NODE_CAN_UPLOAD = "canupload";
    private static final String REPOSITORY_UI_TREE_NODE_CAN_DOWNLOAD = "candownload";


    public static String REPOSITORY_FOLDER_INBOX = "INBOX";
    public static String REPOSITORY_FOLDER_OUTBOX = "OUTBOX";
    public static String REPOSITORY_FOLDER_SAMPLES = "SAMPLES";

    private String docRepositoryId;
    private String docRepositoryCompanyId;
    private String docRepositoryRootFolderId;
    private String docRepositoryLoginEmail;
    private String docRepositoryLoginPassword;
    private String docRepositoryHostname;
    private String docRepositoryPort;
    private String docRepositoryLoginGroupId;

    @GET
    @Path("/getfile")
    //@Produces("application/octet-stream")
    public Response getfile(@HeaderParam("userid") String userid,
                            @QueryParam("path") String path,
                            @QueryParam("pathId") String pathId) throws Exception {
        FileEntry fe = ecmService.getFileEntryById(pathId);
        InputStream reportStream = ecmService.getFileAsStream(pathId, null);

        ContentDisposition contentDisposition = ContentDisposition.type("attachment")
                .fileName(fe.getTitle()).creationDate(fe.getCreateDate()).build();
        BodyPart sourcePart = new StreamDataBodyPart(fe.getTitle(), reportStream);

        if (StringUtils.contains(fe.getMimeType(),'/')) {
            String mimeTypeParts[] = StringUtils.split(fe.getMimeType(),'/');
            sourcePart.setMediaType(new MediaType(mimeTypeParts[0],mimeTypeParts[1]));
        }
        else {
            sourcePart.setMediaType(MediaType.APPLICATION_OCTET_STREAM_TYPE);
        }


        MultiPart multiPart = new MultiPart().
                bodyPart(sourcePart);

        //return Response.ok(multiPart).header("Content-Disposition",contentDisposition).build();
        return Response.ok(reportStream).type(fe.getMimeType()).header("Content-Disposition",contentDisposition).build();//;header("Content-Disposition",contentDisposition).build();
    }

    @POST
    @Path("/adddir")
    @Produces(MediaType.APPLICATION_JSON)
    public String adddir(@HeaderParam("userid") String userid,
                               @QueryParam("callback") String callback,
                               @FormParam("dir") String path,
                               @FormParam("parentPathId") String parentPathId) throws Exception {
        final JSONObject res = new JSONObject();
        res.put("success",true);

        try {
            String[] tokens = StringUtils.split(path, '/');
            String name = tokens[tokens.length-1];

            Folder fldr = ecmService.addFolder(parentPathId,name,name);
            res.put("id",fldr.getFolderId());
            res.put("allowDrag", false);
            res.put("allowDrop", false);
            res.put("text", fldr.getName());
            res.put("title", fldr.getName());
            updateAttributes(fldr, res, path);
            res.put("leaf", false);
            res.put("hasChildren", true);
            res.put("singleClickExpand", true);
            res.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
            res.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "true");
            res.put(REPOSITORY_ITEM_TYPE,REPOSITORY_ITEMCONTAINER_TYPE_DOCREPOFOLDER);
        } catch (Exception e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("errorMessage",e.getMessage());
        }

        return res.toString();
    }

    @POST
    @Path("/deletedir")
    @Produces(MediaType.APPLICATION_JSON)
    public String deletedir(@HeaderParam("userid") String userid,
                              @FormParam("isleaf") Boolean isleaf,
                              @FormParam("path") String path,
                              @FormParam("pathId") String pathId,
                              @FormParam("parentPathId") String parentPathId) throws Exception {

        final JSONObject res = new JSONObject();
        res.put("success",true);

        try {
            if (isleaf) {  //File
                ecmService.deleteFileEntryById(parentPathId,pathId);
            }
            else { //Dir
                ecmService.deleteFolderById(pathId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("errorMessage",e.getMessage());
        }

        return res.toString();
    }

    @Path("/upload")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadFile(@FormDataParam("cmd") String command,
                                   @FormDataParam("APC_UPLOAD_PROGRESS") String progress,
                                   @FormDataParam("UPLOAD_IDENTIFIER") String identifier,
                                   @FormDataParam("MAX_FILE_SIZE") String fileSizeStr,

                                   @FormDataParam("path") String path,
                                   @FormDataParam("pathId") String pathId,
                                   @FormDataParam("dir") String dir,
                                   FormDataMultiPart mpFileUpload) throws Exception {
        final JSONObject res = new JSONObject();
        res.put("success",true);

        try {
            //Get file handle
            FormDataBodyPart fileBP = getFileBodyPart("ext-gen", mpFileUpload);//from form field of name:file
            String fileName = fileBP.getContentDisposition().getFileName();
            //Long fileSize = Long.valueOf(fileSizeStr);
            String mediaType = fileBP.getMediaType().toString();
            InputStream sampleCSVInputStream = fileBP.getValueAs(InputStream.class);

            //Save sample to ECM
            FileEntry fe = addOrUpdateDocLibFile(pathId,sampleCSVInputStream, fileName, mediaType);

            //Result
            res.put("fileName",fileName);
            res.put("fileentryid",fe.getFileEntryId());
        } catch (Exception e) {
            e.printStackTrace();
            res.put("success", false);
            res.put("errorMessage",e.getMessage());
            return res.toString();
        }

        return res.toString();

    }



    @POST
    @Path("/getnode")
    @Produces(MediaType.TEXT_PLAIN)
    public String getnode(@QueryParam("userid") String userid,
                          @QueryParam("callback") String callback,
                          @QueryParam("itemtype") String itemtype,
                          @FormParam("node") String nodeId,
                          @FormParam("path") String path) throws Exception {
        Organization tenant = new Organization();
        tenant.setId(1L);

        Folder fldr = null;
        if (nodeId == null || !NumberUtils.isNumber(nodeId))//Root
        {
            fldr = provideTenantFolder();
            ensureInboxFolder(fldr);
            ensureOutboxFolder(fldr);
            ensureSamplesFolder(fldr);
        }
        else {
            fldr = ecmService.getFolderById(nodeId);
        }
        JSONObject json = generateFolderChildrenJSON(fldr,path);
        final String res = json.getJSONArray("children").toString();

        if (callback != null)
            return callback + "(" + res + ");";
        else
            return res;
    }

    private void ensureInboxFolder(Folder parentFldr) throws Exception {
       boolean exists = ecmService.folderExists(Long.toString(parentFldr.getFolderId()),REPOSITORY_FOLDER_INBOX);
        if (!exists) {
            ecmService.addFolder(Long.toString(parentFldr.getFolderId()),REPOSITORY_FOLDER_INBOX,"All incoming files");
        }
    }

    private void ensureOutboxFolder(Folder parentFldr) throws Exception {
        boolean exists = ecmService.folderExists(Long.toString(parentFldr.getFolderId()),REPOSITORY_FOLDER_OUTBOX);
        if (!exists) {
            ecmService.addFolder(Long.toString(parentFldr.getFolderId()),REPOSITORY_FOLDER_OUTBOX,"All outgoing files");
        }
    }

    private void ensureSamplesFolder(Folder parentFldr) throws Exception {
        boolean exists = ecmService.folderExists(Long.toString(parentFldr.getFolderId()),REPOSITORY_FOLDER_SAMPLES);
        if (!exists) {
            ecmService.addFolder(Long.toString(parentFldr.getFolderId()),REPOSITORY_FOLDER_SAMPLES,"All sample files");
        }
    }

    private JSONObject generateFolderChildrenJSON(Folder folder, String path) throws Exception {
        boolean hasChildren = false;
        JSONObject fldr = new JSONObject();

        try {
            // Populate
            fldr.put("id", folder.getFolderId());
            fldr.put("allowDrag", false);
            fldr.put("allowDrop", false);
            fldr.put("text", folder.getName());
            fldr.put("title", folder.getName());
            updateAttributes(folder, fldr, path);
            fldr.put("leaf", false);
            fldr.put("hasChildren", false);
            fldr.put("singleClickExpand", false);

            JSONArray children = new JSONArray();
            fldr.put("children", children);

            //Get sub dirs
            List<Folder> fldrs = ecmService.getSubFolders(Long.toString(folder.getFolderId()));
            if (fldrs.size() > 0) {
                fldr.put("hasChildren", true);
                fldr.put("singleClickExpand", true);
            }

            for (Folder subFldr : fldrs) {
                JSONObject subFldrObj = new JSONObject();
                subFldrObj.put("id", subFldr.getFolderId());
                subFldrObj.put("allowDrag", false);
                subFldrObj.put("allowDrop", false);
                subFldrObj.put("text", subFldr.getName());
                subFldrObj.put("title", subFldr.getName());
                updateAttributes(subFldr, subFldrObj, path);
                subFldrObj.put("leaf", false);
                subFldrObj.put("hasChildren", true);
                subFldrObj.put("singleClickExpand", true);
                subFldrObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                subFldrObj.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "true");
                subFldrObj.put(REPOSITORY_ITEM_TYPE,REPOSITORY_ITEMCONTAINER_TYPE_DOCREPOFOLDER);

                children.put(subFldrObj);
            }

            //Files
            List<FileEntry> fes = ecmService.getFileEntries(Long.toString(folder.getFolderId()));
            for (FileEntry fe : fes) {
                JSONObject feObj = new JSONObject();
                feObj.put("id", fe.getFileEntryId());
                feObj.put("allowDrag", false);
                feObj.put("allowDrop", false);
                feObj.put("text", fe.getTitle());
                feObj.put("title", fe.getTitle());
                feObj.put(REPOSITORY_UI_TREE_NODE_CAN_UPLOAD, true);
                feObj.put(REPOSITORY_UI_TREE_NODE_CAN_DOWNLOAD, true);
                feObj.put(REPOSITORY_UI_TREE_NODE_CAN_DELETE, true);
                if (StringUtils.contains(path,REPOSITORY_FOLDER_OUTBOX)) {
                    feObj.put(REPOSITORY_UI_TREE_NODE_CAN_DELETE, false);
                }
                feObj.put("iconCls", "file-"+fe.getExtension());
                feObj.put("leaf", true);
                feObj.put("hasChildren", false);
                feObj.put("singleClickExpand", false);
                feObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                feObj.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "true");
                feObj.put(REPOSITORY_ITEM_TYPE,REPOSITORY_ITEMCONTAINER_TYPE_DOCREPOFILEITEM);


                //DocRepo metadata attrs
                feObj.put("docrepofileentry.fileentryid",fe.getFileEntryId());
                feObj.put("docrepofileentry.repositoryid",getDocRepositoryId());
                feObj.put("docrepofileentry.companyid",getDocRepositoryCompanyId());
                feObj.put("docrepofileentry.folderid",getDocRepositoryRootFolderId());
                feObj.put("docrepofileentry.loginemail",getDocRepositoryLoginEmail());
                feObj.put("docrepofileentry.loginpassword",getDocRepositoryLoginPassword());
                feObj.put("docrepofileentry.hostname",getDocRepositoryHostname());
                feObj.put("docrepofileentry.port",getDocRepositoryPort());
                feObj.put("docrepofileentry.logingroupid",getDocRepositoryLoginGroupId());

                children.put(feObj);
            }


        } catch (KettleDatabaseException e) {
            throw e;
        } catch (JSONException e) {
            throw e;
        }


        return fldr;
    }

    private void updateAttributes(Folder fldr, JSONObject fldrJson, String path) throws JSONException {
        fldrJson.put(REPOSITORY_UI_TREE_NODE_CAN_UPLOAD, true);
        fldrJson.put(REPOSITORY_UI_TREE_NODE_CAN_DOWNLOAD, true);

        // Icon and Delete?
        if (REPOSITORY_FOLDER_INBOX.equals(fldr.getName())) {
            fldrJson.put("icon", "/etl/images/conxbi/folder-inbox.png");
            fldrJson.put(REPOSITORY_UI_TREE_NODE_CAN_DELETE, false);
        }
        else if (REPOSITORY_FOLDER_OUTBOX.equals(fldr.getName())) {
            fldrJson.put("icon", "/etl/images/conxbi/folder-outbox.png");
            fldrJson.put(REPOSITORY_UI_TREE_NODE_CAN_DELETE, false);
            fldrJson.put(REPOSITORY_UI_TREE_NODE_CAN_UPLOAD, false);
        }
        else if (REPOSITORY_FOLDER_SAMPLES.equals(fldr.getName())) {
            fldrJson.put("icon", "/etl/images/folder_page.png");
            fldrJson.put(REPOSITORY_UI_TREE_NODE_CAN_DELETE, false);
        }
        else {
            fldrJson.put("icon", "/etl/images/conxbi/etl/folder_close.png");
            fldrJson.put(REPOSITORY_UI_TREE_NODE_CAN_DELETE, true);
        }
    }

    private FormDataBodyPart getFileBodyPart(String startsWidth_, FormDataMultiPart multiPart) {
        List<FormDataBodyPart> fileBPList = null;
        Map<String, List<FormDataBodyPart>> fields = multiPart.getFields();
        for (String key_ : fields.keySet()) {
            //handleInputStream(field.getValueAs(InputStream.class));
            List<FormDataBodyPart> list = fields.get(key_);
            if (key_.indexOf(startsWidth_) >= 0) {
                fileBPList = list;
                break;
            }
        }

        FormDataBodyPart fileBP = fileBPList.get(0);

        return fileBP;
    }


    public void setDocRepositoryId(String docRepositoryId) {
        this.docRepositoryId = docRepositoryId;
    }

    public String getDocRepositoryId() {
        return docRepositoryId;
    }

    public void setDocRepositoryCompanyId(String docRepositoryCompanyId) {
        this.docRepositoryCompanyId = docRepositoryCompanyId;
    }

    public String getDocRepositoryCompanyId() {
        return docRepositoryCompanyId;
    }

    public void setDocRepositoryRootFolderId(String docRepositoryRootFolderId) {
        this.docRepositoryRootFolderId = docRepositoryRootFolderId;
    }

    public String getDocRepositoryRootFolderId() {
        return docRepositoryRootFolderId;
    }

    public void setDocRepositoryLoginEmail(String docRepositoryLoginEmail) {
        this.docRepositoryLoginEmail = docRepositoryLoginEmail;
    }

    public String getDocRepositoryLoginEmail() {
        return docRepositoryLoginEmail;
    }

    public void setDocRepositoryLoginPassword(String docRepositoryLoginPassword) {
        this.docRepositoryLoginPassword = docRepositoryLoginPassword;
    }

    public String getDocRepositoryLoginPassword() {
        return docRepositoryLoginPassword;
    }

    public void setDocRepositoryHostname(String docRepositoryHostname) {
        this.docRepositoryHostname = docRepositoryHostname;
    }

    public String getDocRepositoryHostname() {
        return docRepositoryHostname;
    }

    public void setDocRepositoryPort(String docRepositoryPort) {
        this.docRepositoryPort = docRepositoryPort;
    }

    public String getDocRepositoryPort() {
        return docRepositoryPort;
    }

    public void setDocRepositoryLoginGroupId(String docRepositoryLoginGroupId) {
        this.docRepositoryLoginGroupId = docRepositoryLoginGroupId;
    }

    public String getDocRepositoryLoginGroupId() {
        return docRepositoryLoginGroupId;
    }
}
