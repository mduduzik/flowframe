package org.flowframe.etl.pentaho.server.repository.util;

import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.UserInfo;
import org.pentaho.di.repository.kdr.delegates.*;

public interface ICustomRepository extends Repository {
    static final String FOLDER_METADATA = "Metadata";
    static final String FOLDER_METADATA_DBCONNECTIONS = "DBConnections";
    static final String FOLDER_METADATA_EXCEL = "Excel";
    static final String FOLDER_METADATA_DELIMITED = "Delimited";

    static final String FOLDER_TRANS = "Transformations";
    static final String FOLDER_TRANS_STEPS = "Steps";
    static final String FOLDER_TRANS_INPUTS = "Inputs";
    static final String FOLDER_TRANS_OUTPUTS = "Outputs";
    static final String FOLDER_TRANS_TRANSFORMS = "Transforms";
    static final String FOLDER_TRANS_TRANSFORMS_DRAFTS = "Drafts";
    static final String FOLDER_TRANS_MAPPINGS = "Mappings";


    static final String FOLDER_JOBS = "Jobs";
    static final String FOLDER_JOBS_JOBS_DRAFTS = "Drafts";
    static final String FOLDER_JOBS_GENERAL = "General";
    static final String FOLDER_JOBS_FILE_TRANSFER = "File Transfer";
    static final String FOLDER_JOBS_FILE_MANAGEMENT = "File Management";


    static final String FOLDER_WORK_GROUPS = "Work Groups";
    static final String FOLDER_WORK_GROUPS_DEFAULT = "Default";
    static final String FOLDER_WORK_GROUPS_DATA_STORES = "Data Stores";
    static final String FOLDER_WORK_GROUPS_DATA_STORES_DEFAULT = "Default";
    static final String FOLDER_WORK_GROUPS_DATA_STORES_LISTS = "Lists";

    static public final String REPO_NAME = "repo.name";// "Kettle Database Repository"
    static public final String REPO_DESCRIPTION = "repo.description";// "Kettle Database Repository"
    static public final String REPO_USER_NAME = "repo.user.name";//admin
    static public final String REPO_USER_PWD = "repo.user.pwd";//admin
    static public final String REPO_USER_FULLNAME = "repo.user.fullname";//"Administrator", "The system administrator",
    static public final String REPO_DATABASE_HOSTNAME = "database.hostname";// localhost
    static public final String REPO_DATABASE_USERNAME = "database.username";//root
    static public final String REPO_DATABASE_PWD = "database.pwd";//root
    static public final String REPO_DATABASE_TYPE = "database.type";// MYSQL
    static public final String REPO_DATABASE_NAME = "database.name";// REPO
    static public final String REPO_DATABASE_PORT = "database.port";// REPO
    
	public UserInfo getUserInfo();
	
	/**
	 * Tenant 
	 * @throws org.pentaho.di.core.exception.KettleException
	 */
	public RepositoryDirectoryInterface provideDirectoryForTenant(Organization tenant) throws KettleException;
	public void deleteDirectoryForTenant(Organization tenant) throws KettleException;

    public KettleDatabaseRepositoryDirectoryDelegate getRepositoryDirectoryDelegate();
    public KettleDatabaseRepositoryTransDelegate getRepositoryTransDelegate();
    public KettleDatabaseRepositoryJobDelegate getRepositoryJobDelegate();
    public KettleDatabaseRepositoryDatabaseDelegate getRepositoryDatabaseDelegate();
    public KettleDatabaseRepositoryConnectionDelegate getRepositoryConnectionDelegate();
    public KettleDatabaseRepositoryStepDelegate getRepositoryStepDelegate();

    /**
     *
     * Metadata Tenant Directory Methods
     */
    public RepositoryDirectoryInterface provideMetadataDirectoryForTenant(Organization tenant) throws KettleException;
    public RepositoryDirectoryInterface provideMetadataDirectoryByItemTypeForTenant(Organization tenant, String itemType) throws KettleException;
    public RepositoryDirectoryInterface provideDBConnectionsMetadataDirectoryForTenant(Organization tenant) throws KettleException;
    public RepositoryDirectoryInterface provideExcelMetadataDirectoryForTenant(Organization tenant) throws KettleException;
    public RepositoryDirectoryInterface provideDelimitedMetadataDirectoryForTenant(Organization tenant) throws KettleException;

    RepositoryDirectoryInterface provideWorkGroupDirectoryForTenant(Organization tenant) throws KettleException;

    RepositoryDirectoryInterface provideDefaultWorkGroupDirectoryForTenant(Organization tenant) throws KettleException;

    RepositoryDirectoryInterface provideDefaultWorkGroupDefaultStoreDirectoryForTenant(Organization tenant) throws KettleException;

    RepositoryDirectoryInterface provideDefaultWorkGroupStoreListsDirectoryForTenant(Organization tenant) throws KettleException;

    /**
	 * 
	 * Transformations Tenant Directory Methods
	 */
	public RepositoryDirectoryInterface provideTransDirectoryForTenant(Organization tenant) throws KettleException;
	public RepositoryDirectoryInterface provideTransStepDirectoryForTenant(Organization tenant) throws KettleException;
	public RepositoryDirectoryInterface provideInputTransStepDirectoryForTenant(Organization tenant) throws KettleException;
	public RepositoryDirectoryInterface provideOutputTransStepDirectoryForTenant(Organization tenant) throws KettleException;
	public RepositoryDirectoryInterface provideTransformTransStepDirectoryForTenant(Organization tenant) throws KettleException;
    public RepositoryDirectoryInterface provideTransformDraftsDirectoryForTenant(Organization tenant) throws KettleException;
    public RepositoryDirectoryInterface provideTransformMappingsTransStepDirectoryForTenant(Organization tenant) throws KettleException;
	
	/**
	 * 
	 * Jobs Tenant Directory Methods
	 */
	public RepositoryDirectoryInterface provideJobsDirectoryForTenant(Organization tenant) throws KettleException;
	public RepositoryDirectoryInterface provideJobGeneralDirectoryForTenant(Organization tenant) throws KettleException;	
	public RepositoryDirectoryInterface provideJobFileTransferDirectoryForTenant(Organization tenant) throws KettleException;
	public RepositoryDirectoryInterface provideJobFileManagementDirectoryForTenant(Organization tenant) throws KettleException;
    public RepositoryDirectoryInterface provideJobDraftsDirectoryForTenant(Organization tenant) throws KettleException;


    public Database getSupportingDatabase();
    public DatabaseMeta getPooledDBConnectionMeta();
    public DatabaseMeta getDBConnectionMeta();

    /**
     *
     */
    public void init() throws KettleException, ClassNotFoundException;
}
