package com.conx.bi.etl.pentaho.repository.db.services.repository;

import com.conx.bi.etl.pentaho.repository.db.services.CustomRepository;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.ProgressMonitorListener;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.*;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.csvinput.CsvInputMeta;
import org.pentaho.di.trans.steps.excelinput.ExcelInputField;
import org.pentaho.di.trans.steps.excelinput.ExcelInputMeta;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputField;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Mduduzi
 * Date: 8/13/13
 * Time: 10:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class RefDataListing {

    public static JSONObject listDatabaseTypes(CustomRepository repo) {
        JSONObject wrapper = new JSONObject();

        String databaseTypesTable = repo.getRepositoryDatabaseDelegate().quoteTable(KettleDatabaseRepository.TABLE_R_DATABASE_TYPE);
        String sql = "SELECT * FROM " + databaseTypesTable + " ORDER BY CODE ASC";


        ResultSet resultSet = null;
        JSONArray types = new JSONArray();
        try {
            List<Object[]> results = repo.getRepositoryConnectionDelegate().getDatabase().getRows(sql, 0);
            for (Object[] result : results) {
                JSONObject obj = new JSONObject();
                obj.put("id",Integer.valueOf(result[0].toString()));
                obj.put("code",result[1].toString());
                obj.put("description",result[2].toString());
                types.put(obj);
            }
            wrapper.put("totalCount",types.length());
            wrapper.put("types",types);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }



        return wrapper;
    }
}
