package org.flowframe.etl.pentaho.server.repository.util;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.plugins.PluginRegistry;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.steps.csvinput.CsvInputMeta;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/17/13
 * Time: 2:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransformationMetaUtil {

    static public Boolean csvStepMetaExists(ICustomRepository repo, String dirId, String stepName) throws KettleException {
        RepositoryDirectoryInterface dir = RepositoryUtil.getDirectory(repo, new LongObjectId(Long.valueOf(dirId)));
        final CsvInputMeta stepMeta = new CsvInputMeta();
        String csvInputPid = PluginRegistry.getInstance().getPluginId(StepPluginType.class,stepMeta);
        return stepMetaExists(repo,dir,csvInputPid,stepName);
    }


    static public Boolean stepMetaExists(ICustomRepository repo, RepositoryDirectoryInterface dir, String stepInputPid, String stepName) throws KettleException {
        TransMeta transMeta = RepositoryUtil.provideTransformation(repo, dir, stepInputPid);
        String[] stepNames = transMeta.getStepNames();
        return Arrays.asList(stepNames).contains(stepName);
    }
}
