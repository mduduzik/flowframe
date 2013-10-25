package org.flowframe.etl.pentaho.server.plugins.core.resource;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.flowframe.etl.pentaho.server.carte.standalone.impl.utils.XML2JSONTransformer;
import org.flowframe.etl.pentaho.server.plugins.core.model.TransMetaDTO;
import org.flowframe.etl.pentaho.server.plugins.core.utils.RepositoryUtil;
import org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.JSONStencilSet2TransformationConverter;
import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.www.SlaveServerTransStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
        if (dirPathId == null) {
            RepositoryDirectoryInterface transDir = null;
            transDir = repository.provideTransDirectoryForTenant(tenant);
            dirPathId = RepositoryUtil.generatePathID(transDir);
        }

        //-- Translate model json into Meta
        TransMeta transMeta = JSONStencilSet2TransformationConverter.toTransMeta(repository, jsonModel);
        transMeta.setName(title);
        transMeta.setDescription(summary);

        //-- Add/Update
        String transNameWithDirPath = RepositoryUtil.addOrReplaceTransMeta(dirPathId, repository, transMeta,jsonModel,svgModel);
        TransMetaDTO dto = new TransMetaDTO(transMeta,dirPathId,jsonModel,svgModel);

        return dto.toJSON();
    }
}
