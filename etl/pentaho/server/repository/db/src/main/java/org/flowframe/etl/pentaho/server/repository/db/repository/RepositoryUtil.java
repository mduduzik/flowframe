package org.flowframe.etl.pentaho.server.repository.db.repository;

import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.LongObjectId;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.RepositoryObjectType;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/14/13
 * Time: 10:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class RepositoryUtil {

    public static String DATABASES_TRANSFORMTION_NAME = "databases";
    public static String CSVFILE_STEPS_TRANSFORMTION_NAME = "csvfile_steps_metadata";
    public static String DELIMITEDFILE_STEPS_TRANSFORMTION_NAME = "delimited_steps_metadata";
    public static String EXCELFILE_STEPS_TRANSFORMTION_NAME = "excel_steps_metadata";

    /**
     *   Provide transformation metadata
     */
    //CSVFile Steps
    public static TransMeta provideDatabaseTransformation(CustomRepository repo, RepositoryDirectoryInterface dir) throws KettleException {
        TransMeta res = null;
        if (!repo.getRepositoryTransDelegate().existsTransMeta(DATABASES_TRANSFORMTION_NAME,dir, RepositoryObjectType.TRANSFORMATION)) {
            res = new TransMeta();
            res.setRepositoryDirectory(dir);
            res.setName(DATABASES_TRANSFORMTION_NAME);
            repo.getRepositoryTransDelegate().saveTransformation(res,"initial",null,true);
        }
        else  {
            res = repo.loadTransformation(DATABASES_TRANSFORMTION_NAME,dir,null,false,null);
        }

        return res;
    }

    //CSVFile Steps
    public static TransMeta provideCSVFileTransformation(CustomRepository repo, RepositoryDirectoryInterface dir) throws KettleException {
        TransMeta res = null;
        if (!repo.getRepositoryTransDelegate().existsTransMeta(CSVFILE_STEPS_TRANSFORMTION_NAME,dir, RepositoryObjectType.TRANSFORMATION)) {
            res = new TransMeta();
            res.setRepositoryDirectory(dir);
            res.setName(CSVFILE_STEPS_TRANSFORMTION_NAME);
            repo.getRepositoryTransDelegate().saveTransformation(res,"initial",null,true);
        }
        else  {
            res = repo.loadTransformation(CSVFILE_STEPS_TRANSFORMTION_NAME,dir,null,false,null);
        }

        return res;
    }

    /**
     *
     */
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

    static public String generatePathID(RepositoryDirectoryInterface dir, DatabaseMeta dbMeta) {
        return "/dir/"+dir.getObjectId()+"/db/"+dbMeta.getObjectId();
    }

    static public ObjectId getDBObjectIDFromPathID(String pathID) {
        // /dir/1/db/2
        String[] pathTokens = pathID.split("/");
        int len = pathTokens.length;
        return new LongObjectId(Long.valueOf(pathTokens[4]));
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

    static public synchronized String addStep(CustomRepository repo, String dirObjId, StepMeta stepMeta) {
        // /trans/1/step/2
        TransMeta trans = null;
        String pathId = null;
        try {
            RepositoryDirectoryInterface dir = getDirectory(repo, new LongObjectId(Long.valueOf(dirObjId)));
            trans = provideTransformation(repo, dir, stepMeta.getStepID());
            int insertIndex = trans.getSteps().size();

            //Check for name colusion
            boolean nameExists = TransformationMetaUtil.stepMetaExists(repo,dir,stepMeta.getStepID(),stepMeta.getName());
            if (nameExists)
                stepMeta.setName(stepMeta.getName()+"-"+insertIndex);

            trans.addStep(insertIndex,stepMeta);

            repo.getRepositoryTransDelegate().saveTransformation(trans,"added step",null,true);

            pathId = generatePathID(stepMeta,insertIndex);
        } catch (KettleException e) {
            throw new IllegalArgumentException(e);
        }

        return pathId;
    }

    public static TransMeta provideTransformation(CustomRepository repo, RepositoryDirectoryInterface dir, String stepInputPid) throws KettleException {
        if ("CsvInput".equals(stepInputPid))
            return provideCSVFileTransformation(repo,dir);
        return null;  //To change body of created methods use File | Settings | File Templates.
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

    static public RepositoryDirectoryInterface getDirectory(CustomRepository repo, ObjectId objId) throws KettleException {
        return repo.findDirectory(objId);
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
