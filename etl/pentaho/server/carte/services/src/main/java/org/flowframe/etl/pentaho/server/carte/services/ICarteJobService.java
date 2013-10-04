package org.flowframe.etl.pentaho.server.carte.services;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.www.SlaveServerStatus;
import org.pentaho.di.www.SlaveServerTransStatus;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Mduduzi on 10/1/13.
 */
public interface ICarteJobService {

    /**
     *
     *  Trans
     *
     */
    public SlaveServerTransStatus addTransformationJob(TransMeta transMeta) throws IOException, KettleException, URISyntaxException;

    public SlaveServerTransStatus startTransformationJob(String transName);

    public SlaveServerTransStatus getTransformationJobStatus(String transName);

    public SlaveServerTransStatus getTransformationJobStatus(String transName, String carteObjectId);

    public SlaveServerTransStatus pauseTransformationJob(String transName);

    public SlaveServerTransStatus executeTransformationJob(final String transName, final String level);

    /**
     *
     * general
     *
     */
     public SlaveServerStatus getCarteStatus();
}
