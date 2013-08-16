package org.flowframe.etl.pentaho.repository.db.repository;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/14/13
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class IdentityUtil {
    static public String generatePathID(StepMeta stepMeta, int index) {
        return generatePathID(stepMeta.getParentTransMeta().getRepositoryDirectory())+"/trans/"+stepMeta.getParentTransMeta().getObjectId()+"/step/"+stepMeta.getTypeId()+"#"+index;
    }

    static public String generatePathID(TransMeta transMeta, DatabaseMeta dbConn) {
        return generatePathID(transMeta.getRepositoryDirectory())+"/trans/"+transMeta.getObjectId()+"/database/"+dbConn.getObjectId();
    }

    static public String generatePathID(RepositoryDirectoryInterface dir) {
        return "/dir/"+dir.getObjectId();
    }

    static public String generatePathID(RepositoryDirectoryInterface dir, StepMeta stepMeta) {
        return "/dir/"+stepMeta.getTypeId()+"#"+dir.getObjectId();
    }

    static public String generatePathID(RepositoryDirectoryInterface dir, String typeId) {
        return "/dir/"+typeId+"#"+dir.getObjectId();
    }

    static public StepMeta getStep(CustomRepository repo, String pathID) {
       // /trans/1/step/2
        String[] pathTokens = pathID.split("/");
        int len = pathTokens.length;

        String[] idStr = pathTokens[len - 1].split("#");
        int stepIndex = Integer.valueOf(idStr[1]);
        Long transId = Long.valueOf(pathTokens[len-3]);


        TransMeta trans = null;
        try {
            trans = repo.loadTransformation(new LongObjectId(transId),"null");
        } catch (KettleException e) {
            throw new IllegalArgumentException("Error fetching trans with id["+transId+"]");
        }

        StepMeta stepMeta = trans.getStep(stepIndex);

        return stepMeta;
    }

    static public DatabaseMeta getDatabase(CustomRepository repo, String pathID) {
        // /trans/1/database/2
        String[] pathTokens = pathID.split("/");
        int len = pathTokens.length;
        int databaseId = Integer.valueOf(pathTokens[len-1]);
        Long transId = Long.valueOf(pathTokens[len-3]);


        TransMeta trans = null;
        try {
            trans = repo.loadTransformation(new LongObjectId(transId),"null");
        } catch (KettleException e) {
            throw new IllegalArgumentException("Error fetching trans with id["+transId+"]");
        }

        DatabaseMeta database = trans.getDatabase(databaseId);

        return database;
    }

    static public RepositoryDirectoryInterface getDirectory(CustomRepository repo, String pathID) {
        String[] pathTokens = pathID.split("/");
        if (pathTokens.length < 2)
            throw new IllegalArgumentException("PathID["+pathID+"] is invalid as dir id");

        String dirIDToken = null;
        if (pathTokens[0].trim().length() < 1)
            dirIDToken = pathTokens[2];
        else
            dirIDToken = pathTokens[1];

        if (dirIDToken.indexOf("#") > 0)
        {
            String[] tkns = dirIDToken.split("#");
            dirIDToken = tkns[1];
        }


        RepositoryDirectoryInterface dir = null;
        try {
            dir = repo.findDirectory(new LongObjectId(Long.valueOf(dirIDToken)));
        } catch (KettleException e) {
            throw new IllegalArgumentException("Error fetching dir with id["+dirIDToken+"]");
        }

        return dir;
    }
}
