package org.flowframe.etl.pentaho.server.plugins.core.resource.carte;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.flowframe.etl.pentaho.server.carte.services.ICarteJobService;
import org.flowframe.etl.pentaho.server.carte.standalone.impl.utils.XML2JSONTransformer;
import org.flowframe.etl.pentaho.server.plugins.core.resource.etl.trans.steps.BaseDialogDelegateResource;
import org.flowframe.etl.pentaho.server.plugins.core.utils.RepositoryUtil;
import org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.JSONStencilSet2TransformationConverter;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.flowframe.kernel.common.utils.HTMLUtil;
import org.flowframe.kernel.common.utils.StringUtil;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.www.SlaveServerTransStatus;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URISyntaxException;

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


    @Path("/execute")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String execute(@FormParam("data") String jsonModel) throws JSONException, KettleException, IOException, URISyntaxException, TransformerException {
        //TODO: Hack
        Organization tenant = new Organization();
        tenant.setId(1L);

        TransMeta transMeta = JSONStencilSet2TransformationConverter.toTransMeta(repository, jsonModel);

        String transNameWithDirPath = RepositoryUtil.addOrReplaceTransMetaDraft(tenant,repository,transMeta);

        //-- Execute trans
        SlaveServerTransStatus resp = carteJobService.executeTransformationJob(transNameWithDirPath,"detailed");
        String xml = resp.getXML();

        JSONArray json = XML2JSONTransformer.transform(xml,tmpDir);
        //String jsonStr = StringUtil.replace(json.toString(), "&quot;", "\"");
        //jsonStr = StringUtil.replace(jsonStr,"&apos;","'");

        return json.toString();
    }
}