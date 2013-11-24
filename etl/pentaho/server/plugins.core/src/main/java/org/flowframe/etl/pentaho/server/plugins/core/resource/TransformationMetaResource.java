package org.flowframe.etl.pentaho.server.plugins.core.resource;

import org.codehaus.jettison.json.JSONException;
import org.flowframe.etl.pentaho.server.plugins.core.exception.RequestException;
import org.flowframe.etl.pentaho.server.plugins.core.exception.TransConversionException;
import org.flowframe.etl.pentaho.server.plugins.core.model.TransMetaDTO;
import org.flowframe.etl.pentaho.server.plugins.core.utils.RepositoryUtil;
import org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.JSONStencilSet2TransMetaConverter;
import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("transmeta")
@Component
public class TransformationMetaResource extends BaseDelegateResource {
    @Autowired
    private ICustomRepository repository;

    @Path("/onnew")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String onNew(@HeaderParam("userid") String userid,
                        @QueryParam("name") String name,
                        @QueryParam("parentFolderId") String parentFolderId) throws IOException, RequestException, KettleException {
        String res = null;
        try {
            Organization tenant = new Organization();
            tenant.setId(1L);

            RepositoryDirectoryInterface transRootDir = null;
            transRootDir = repository.provideTransDirectoryForTenant(tenant);
            String transRootDirPathId = RepositoryUtil.generatePathID(transRootDir);

            if (parentFolderId == null || "transformations".equals(parentFolderId)) {
                parentFolderId = RepositoryUtil.generatePathID(transRootDir);
            }

            TransMeta transMeta = new TransMeta();
            transMeta.setName(name);

            //-- Add/Update
            String transNameWithDirPath = RepositoryUtil.addOrReplaceTransMeta(parentFolderId, repository, transMeta,"","");
            TransMetaDTO dto = new TransMetaDTO(transMeta,parentFolderId,"","");

            //TODO: hack
            if (parentFolderId.equals(transRootDirPathId))
                dto.setSubDirPathId("transformations");

            res = dto.toJSON();
        } catch (Exception e) {
            throw  new RequestException("Error on /onnew",e);
        }

        return res;
    }


    @Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String add(@FormParam("data") String jsonModel,
                      @FormParam("svg") String svgModel,
                      @FormParam("title") String title,
                      @FormParam("summary") String summary,
                      @FormParam("type") String namespace,
                      @FormParam("dirPathId") String dirPathId) throws JSONException, KettleException, IOException, URISyntaxException, TransformerException, TransConversionException {
        //TODO: Hack
        Organization tenant = new Organization();
        tenant.setId(1L);

        RepositoryDirectoryInterface transRootDir = null;
        transRootDir = repository.provideTransDirectoryForTenant(tenant);
        String transRootDirPathId = RepositoryUtil.generatePathID(transRootDir);

        if (dirPathId == null || "transformations".equals(dirPathId)) {
            dirPathId = RepositoryUtil.generatePathID(transRootDir);
        }


        //-- Translate model json into Meta
        TransMeta transMeta = JSONStencilSet2TransMetaConverter.toTransMeta(getOptions(),jsonModel,false);
        transMeta.setName(title);
        transMeta.setDescription(summary);

        //-- Add/Update
        String transNameWithDirPath = RepositoryUtil.addOrReplaceTransMeta(dirPathId, repository, transMeta,jsonModel,svgModel);
        TransMetaDTO dto = new TransMetaDTO(transMeta,dirPathId,jsonModel,svgModel);

        //TODO: hack
        if (transRootDirPathId.equals(dirPathId))
           dto.setSubDirPathId("transformations");

        return dto.toJSON();
    }

    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@FormParam("data") String jsonModel,
                      @FormParam("svg") String svgModel,
                      @FormParam("title") String title,
                      @FormParam("summary") String summary,
                      @FormParam("type") String namespace,
                      @FormParam("pathId") String pathId,
                      @FormParam("dirPathId") String dirPathId) throws JSONException, KettleException, IOException, URISyntaxException, TransformerException, TransConversionException {
        //TODO: Hack
        Organization tenant = new Organization();
        tenant.setId(1L);

        RepositoryDirectoryInterface transRootDir = null;
        transRootDir = repository.provideTransDirectoryForTenant(tenant);
        String transRootDirPathId = RepositoryUtil.generatePathID(transRootDir);

        if (dirPathId == null) {
            dirPathId = RepositoryUtil.generatePathID(transRootDir);
        }


        //-- Translate model json into Meta
        TransMeta transMeta = JSONStencilSet2TransMetaConverter.toTransMeta(getOptions(), jsonModel, false);
        transMeta.setName(title);
        transMeta.setDescription(summary);

        //-- Add/Update
        String transNameWithDirPath = RepositoryUtil.addOrReplaceTransMeta(dirPathId, repository, transMeta,jsonModel,svgModel);
        transMeta = repository.loadTransformation(transMeta.getName(),RepositoryUtil.getDirectory(repository,dirPathId),null,false,null);
        TransMetaDTO dto = new TransMetaDTO(transMeta,dirPathId,jsonModel,svgModel);

        //TODO: hack
        if (transRootDirPathId.equals(dirPathId))
            dto.setSubDirPathId("transformations");

        return dto.toJSON();
    }


    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@HeaderParam("userid") String userid,
                           Map<String,String> params) throws JSONException, KettleException, IOException, URISyntaxException, TransformerException {
        String pathId = (String)params.get("objectId");
        ObjectId objectId = getObjectIdFromPathID(pathId);

        TransMeta transMeta = repository.loadTransformation(objectId, null);

        repository.deleteTransformation(objectId);

        return Response.ok("Transformation " + transMeta.getName() + " deleted successfully", MediaType.TEXT_PLAIN).build();
    }


    @Path("/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@QueryParam("objectId") String objectId) throws JSONException, KettleException, IOException, URISyntaxException, TransformerException {
        LongObjectId longObjectId = new LongObjectId(Long.valueOf(objectId));
        TransMeta transMeta = repository.loadTransformation(longObjectId, null);

        String dirPathId = RepositoryUtil.generatePathID(transMeta.getRepositoryDirectory());

        String jsonModel = transMeta.getNote(0).toString();
        String svgModel = transMeta.getNote(1).toString();
        TransMetaDTO dto = new TransMetaDTO(transMeta,dirPathId,jsonModel,svgModel);

        return dto.toJSON();
    }

    @Override
    protected Map<String,Object> getOptions() {
        final HashMap<String, Object> options = new HashMap<String, Object>() {{
            put("etlRepository", repository);
            put("docRepositoryService", getDocRepositoryService());
        }};

        return options;
    }


    static ObjectId getObjectIdFromPathID(String pathID) {
        // /dir/1/db/2
        String[] pathTokens = pathID.split("/");
        int len = pathTokens.length;
        return new LongObjectId(Long.valueOf(pathTokens[4]));
    }
}
