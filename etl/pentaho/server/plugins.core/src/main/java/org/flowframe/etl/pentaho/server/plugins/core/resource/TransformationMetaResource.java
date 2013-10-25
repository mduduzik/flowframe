package org.flowframe.etl.pentaho.server.plugins.core.resource;

import org.codehaus.jettison.json.JSONException;
import org.flowframe.etl.pentaho.server.plugins.core.model.TransMetaDTO;
import org.flowframe.etl.pentaho.server.plugins.core.utils.RepositoryUtil;
import org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.JSONStencilSet2TransformationConverter;
import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.LongObjectId;
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

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/15/13
 * Time: 6:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("transmeta")
@Component
public class TransformationMetaResource {
    @Autowired
    private ICustomRepository repository;


    @Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String add(@FormParam("data") String jsonModel,
                      @FormParam("svg") String svgModel,
                      @FormParam("title") String title,
                      @FormParam("summary") String summary,
                      @FormParam("type") String namespace,
                      @FormParam("dirPathId") String dirPathId) throws JSONException, KettleException, IOException, URISyntaxException, TransformerException {
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
        TransMeta transMeta = JSONStencilSet2TransformationConverter.toTransMeta(repository, jsonModel);
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

    @Path("/delete")
    @POST
    public Response delete(@FormParam("objectId") String objectId) throws JSONException, KettleException, IOException, URISyntaxException, TransformerException {
        LongObjectId longObjectId = new LongObjectId(Long.valueOf(objectId));
        TransMeta transMeta = repository.loadTransformation(longObjectId, null);

        repository.deleteTransformation(longObjectId);

        return Response.ok("Transformation " + transMeta.getName() + " deleted successfully", MediaType.TEXT_PLAIN).build();
    }
}
