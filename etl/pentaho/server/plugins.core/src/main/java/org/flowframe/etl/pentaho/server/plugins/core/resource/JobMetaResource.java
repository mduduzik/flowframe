package org.flowframe.etl.pentaho.server.plugins.core.resource;

import org.codehaus.jettison.json.JSONException;
import org.flowframe.etl.pentaho.server.plugins.core.model.JobMetaDTO;
import org.flowframe.etl.pentaho.server.plugins.core.utils.RepositoryUtil;
import org.flowframe.etl.pentaho.server.plugins.core.utils.job.JSONStencilSet2JobConverter;
import org.flowframe.etl.pentaho.server.plugins.core.utils.transformation.JSONStencilSet2TransformationConverter;
import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
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
@Path("jobmeta")
@Component
public class JobMetaResource {
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

        RepositoryDirectoryInterface jobRootDir = null;
        jobRootDir = repository.provideJobsDirectoryForTenant(tenant);
        String jobRootDirPathId = RepositoryUtil.generatePathID(jobRootDir);

        if (dirPathId == null) {
            dirPathId = RepositoryUtil.generatePathID(jobRootDir);
        }


        //-- Translate model json into Meta
        JobMeta jobMeta = JSONStencilSet2JobConverter.toJobMeta(repository, jsonModel);
        jobMeta.setName(title);
        jobMeta.setDescription(summary);

        //-- Add/Update
        String jobNameWithDirPath = RepositoryUtil.addOrReplaceJobMeta(dirPathId, repository, jobMeta,jsonModel,svgModel);
        JobMetaDTO dto = new JobMetaDTO(jobMeta,dirPathId,jsonModel,svgModel);

        //TODO: hack
        if (jobRootDirPathId.equals(dirPathId))
           dto.setSubDirPathId("jobs");

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
        JobMeta transMeta = JSONStencilSet2JobConverter.toJobMeta(repository, jsonModel);
        transMeta.setName(title);
        transMeta.setDescription(summary);

        //-- Add/Update
        String transNameWithDirPath = RepositoryUtil.addOrReplaceJobMeta(dirPathId, repository, transMeta,jsonModel,svgModel);
        JobMetaDTO dto = new JobMetaDTO(transMeta,dirPathId,jsonModel,svgModel);

        //TODO: hack
        if (transRootDirPathId.equals(dirPathId))
            dto.setSubDirPathId("jobs");

        return dto.toJSON();
    }

    @Path("/delete")
    @POST
    public Response delete(@FormParam("objectId") String objectId) throws JSONException, KettleException, IOException, URISyntaxException, TransformerException {
        LongObjectId longObjectId = new LongObjectId(Long.valueOf(objectId));
        JobMeta transMeta = repository.loadJob(longObjectId, null);

        repository.deleteTransformation(longObjectId);

        return Response.ok("Job " + transMeta.getName() + " deleted successfully", MediaType.TEXT_PLAIN).build();
    }

    @Path("/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String get(@QueryParam("objectId") String objectId) throws JSONException, KettleException, IOException, URISyntaxException, TransformerException {
        LongObjectId longObjectId = new LongObjectId(Long.valueOf(objectId));
        JobMeta transMeta = repository.loadJob(longObjectId, null);

        String dirPathId = RepositoryUtil.generatePathID(transMeta.getRepositoryDirectory());

        String jsonModel = transMeta.getNote(0).toString();
        String svgModel = transMeta.getNote(1).toString();
        JobMetaDTO dto = new JobMetaDTO(transMeta,dirPathId,jsonModel,svgModel);

        return dto.toJSON();
    }
}
