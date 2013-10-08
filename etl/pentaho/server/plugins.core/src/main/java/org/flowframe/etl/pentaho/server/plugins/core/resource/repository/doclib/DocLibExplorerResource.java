package org.flowframe.etl.pentaho.server.plugins.core.resource.repository.doclib;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.etl.pentaho.server.plugins.core.model.DirectoryDTO;
import org.flowframe.etl.pentaho.server.plugins.core.resource.BaseDelegateResource;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.FileEntry;
import org.flowframe.kernel.common.mdm.domain.documentlibrary.Folder;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("explorer")
@Component
public class DocLibExplorerResource extends BaseDelegateResource {
    public static String REPOSITORY_ITEM_TYPE = "itemtype";
    public static String REPOSITORY_ITEM_TYPE_DATABASE = "database";
    public static String REPOSITORY_ITEM_TYPE_CSVMETA = "csvmeta";
    public static String REPOSITORY_ITEM_TYPE_EXCELMETA = "excelmeta";
    public static String REPOSITORY_ITEM_PARENTFOLDER_OBJID = "folderObjectId";

    public static String REPOSITORY_ITEMCONTAINER_TYPE = "itemcontainertype";
    public static String REPOSITORY_ITEMCONTAINER_TYPE_REPOFOLDER = "repofolder";
    public static String REPOSITORY_ITEMCONTAINER_TYPE_DYNAMIC = "dynamic";
    public static String REPOSITORY_REPOFOLDER_OBJID = "folderObjectId";

    public static String REPOSITORY_UI_TREE_LOADING_TYPE = "loadingtype";
    public static String REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND = "ondemand";
    public static String REPOSITORY_UI_TREE_LOADING_TYPE_ONTIME = "ontime";
    public static String REPOSITORY_UI_TREE_NODE_MENUGROUP_NAME = "menugroup";
    public static String REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME = "ddenabled";

    @POST
    @Path("/adddir")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DirectoryDTO adddir(@HeaderParam("userid") String userid, DirectoryDTO record) throws Exception {
        DirectoryDTO parent = record.getParent();
        Folder fldr = null;
        if (parent != null) {
            fldr = ecmService.getFolderById(Long.toString(parent.getDirObjectId()));
        }
        else
        {
            fldr = provideTenantFolder();
        }
        fldr =  ecmService.addFolder(Long.toString(fldr.getFolderId()),record.getName(),record.getName());
        return new DirectoryDTO(fldr.getName(), fldr.getFolderId());
    }

    @DELETE
    @Path("/deletedir")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletedir(@HeaderParam("userid") String userid,
                              @HeaderParam("itemtype") String itemtype,
                              @HeaderParam("folderObjectId") String folderObjectId) throws Exception {
        ecmService.deleteFolderById(folderObjectId);
        return Response.ok("Folder " + folderObjectId + " deleted successfully", MediaType.TEXT_PLAIN).build();
    }


    @GET
    @Path("/getnode")
    @Produces(MediaType.TEXT_PLAIN)
    public String getnode(@QueryParam("userid") String userid,
                          @QueryParam("callback") String callback,
                          @QueryParam("itemtype") String itemtype,
                          @QueryParam("node") String nodeId,
                          @QueryParam("folderObjectId") String folderObjectId) throws Exception {
        Organization tenant = new Organization();
        tenant.setId(1L);

        Folder fldr = null;
        if (folderObjectId == null)//Root
        {
            fldr = provideTenantFolder();
        }
        JSONObject json = generateFolderChildrenJSON(fldr);
        final String res = json.getJSONArray("children").toString();

        if (callback != null)
            return callback + "(" + res + ");";
        else
            return res;
    }

    private JSONObject generateFolderChildrenJSON(Folder folder) throws Exception {
        boolean hasChildren = false;
        JSONObject fldr = new JSONObject();

        try {
            // Populate
            fldr.put("id", folder.getFolderId());
            fldr.put("allowDrag", false);
            fldr.put("allowDrop", false);
            fldr.put("text", folder.getName());
            fldr.put("title", folder.getName());
            fldr.put("icon", "/etl/images/conxbi/etl/folder_close.png");
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
                subFldrObj.put("icon", "/etl/images/conxbi/etl/folder_close.png");
                subFldrObj.put("leaf", false);
                subFldrObj.put("hasChildren", true);
                subFldrObj.put("singleClickExpand", true);
                subFldrObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                subFldrObj.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "true");

                children.put(subFldrObj);
            }

            //Files
            List<FileEntry> fes = ecmService.getFileEntries(Long.toString(folder.getFolderId()));
            for (FileEntry fe : fes) {
                JSONObject feObj = new JSONObject();
                feObj.put("id", fe.getFileEntryId());
                feObj.put("allowDrag", true);
                feObj.put("allowDrop", false);
                feObj.put("text", fe.getName());
                feObj.put("title", fe.getName());
                feObj.put("icon", "/etl/images/conxbi/etl/folder_close.png");
                feObj.put("leaf", true);
                feObj.put("hasChildren", false);
                feObj.put("singleClickExpand", false);
                feObj.put(REPOSITORY_UI_TREE_LOADING_TYPE, REPOSITORY_UI_TREE_LOADING_TYPE_ONDEMAND);
                feObj.put(REPOSITORY_UI_TREE_NODE_DRAGNDROP_NAME, "true");

                children.put(feObj);
            }


        } catch (KettleDatabaseException e) {
            throw e;
        } catch (JSONException e) {
            throw e;
        }


        return fldr;
    }
}
