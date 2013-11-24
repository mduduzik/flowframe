package org.flowframe.etl.pentaho.server.plugins.core.resource.carte;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.flowframe.etl.pentaho.server.carte.services.ICarteJobService;
import org.flowframe.etl.pentaho.server.carte.standalone.impl.utils.XML2JSONTransformer;
import org.flowframe.etl.pentaho.server.plugins.core.exception.TransConversionException;
import org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.BaseDialogDelegateResource;
import org.flowframe.etl.pentaho.server.plugins.core.utils.RepositoryUtil;
import org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.JSONStencilSet2TransMetaConverter;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.flowframe.kernel.common.utils.HTMLUtil;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.www.SlaveServerTransStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/21/13
 * Time: 9:37 AM
 * To change this template use File | Settings | File Templates.
 */
@Path("/transjobservice")
public class TransformationJobServiceResource extends BaseDialogDelegateResource {

    @Autowired
    private ICarteJobService carteJobService;

    private HTMLUtil htmlUtil = new HTMLUtil();


    @Path("/executedraft")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String executeDraft(@FormParam("data") String jsonModel) throws JSONException, KettleException, IOException, URISyntaxException, TransformerException, TransConversionException {
        //TODO: Hack
        Organization tenant = new Organization();
        tenant.setId(1L);

        HashMap<String, Object> options = new HashMap<String, Object>() {{
            put("etlRepository", repository);
            put("docRepositoryService", getDocRepositoryService());
        }};
        TransMeta transMeta = JSONStencilSet2TransMetaConverter.toTransMeta(options,
                jsonModel, true/*externalize for carte submission*/);

        String transNameWithDirPath = RepositoryUtil.addOrReplaceTransMetaDraft(tenant,repository,transMeta);

        //-- Execute trans
        SlaveServerTransStatus resp = carteJobService.executeTransformationJob(transNameWithDirPath,"detailed");
        String xml = resp.getXML();

        JSONArray json = XML2JSONTransformer.transform(xml,tmpDir);

        return json.toString();
    }

    @Path("/execute")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String execute(@FormParam("objectId") String objectId) throws JSONException, KettleException, IOException, URISyntaxException, TransformerException, TransConversionException {
        //TODO: Hack
        Organization tenant = new Organization();
        tenant.setId(1L);

        LongObjectId longObjectId = new LongObjectId(Long.valueOf(objectId));
        TransMeta transMeta = repository.loadTransformation(longObjectId, null);

        //-- Execute trans
        transMeta = JSONStencilSet2TransMetaConverter.externalize(transMeta,getOptions());
        SlaveServerTransStatus resp = carteJobService.executeTransformationJob(transMeta.getPathAndName(),"detailed");
        String xml = resp.getXML();

        JSONArray json = XML2JSONTransformer.transform(xml,tmpDir);

        return json.toString();
    }    
}
