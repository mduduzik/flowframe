package org.flowframe.etl.pentaho.server.repository.db.repository;

import org.flowframe.etl.pentaho.server.repository.util.ICustomRepository;
import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LoggingObjectInterface;
import org.pentaho.di.core.logging.LoggingObjectType;
import org.pentaho.di.core.logging.SimpleLoggingObject;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.UserInfo;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;
import org.pentaho.di.repository.kdr.delegates.*;

import java.io.IOException;
import java.util.Properties;

public class DBRepositoryWrapperImpl extends KettleDatabaseRepository implements ICustomRepository {
    public static final LoggingObjectInterface loggingObject = new SimpleLoggingObject("DBRepositoryWrapperImpl repository", LoggingObjectType.REPOSITORY, null);

    private static final String FOLDER_METADATA = "Metadata";
    private static final String FOLDER_METADATA_DBCONNECTIONS = "DBConnections";
    private static final String FOLDER_METADATA_EXCEL = "Excel";
    private static final String FOLDER_METADATA_DELIMITED = "Delimited";

	private static final String FOLDER_TRANS = "Transformations";
	private static final String FOLDER_TRANS_STEPS = "Steps";
	private static final String FOLDER_TRANS_INPUTS = "Inputs";
	private static final String FOLDER_TRANS_OUTPUTS = "Outputs";
	private static final String FOLDER_TRANS_TRANSFORMS = "Transforms";
    private static final String FOLDER_TRANS_TRANSFORMS_DRAFTS = "Drafts";
	private static final String FOLDER_TRANS_MAPPINGS = "Mappings";
	
	
	private static final String FOLDER_JOBS = "Jobs";
    private static final String FOLDER_JOBS_JOBS_DRAFTS = "Drafts";
	private static final String FOLDER_JOBS_GENERAL = "General";
	private static final String FOLDER_JOBS_FILE_TRANSFER = "File Transfer";
	private static final String FOLDER_JOBS_FILE_MANAGEMENT = "File Management";

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

	static public Properties repoProperties = new Properties();

	private static KettleDatabaseRepositoryMeta repositoryMeta;
	private UserInfo userInfo;
	private RepositoryDirectoryInterface rootDir;
	private RepositoryDirectoryInterface tenantDir;

    private Database supportingDatabase;

    private static ICustomRepository INSTANCE = null;
    private String reponame;
    private String repodescription;
    private String username;
    private String userfullname;
    private String userpassword;
    private String dbhostname;
    private String dbtype;
    private String dbname;
    private String dbport;
    private String dbusername;
    private String dbuserpassword;
    private DatabaseMeta pooledDBConnection;
    private DatabaseMeta unpooledDBConnection;

    public static ICustomRepository getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new DBRepositoryWrapperImpl();
            try {
                ((ICustomRepository)INSTANCE).init();
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return INSTANCE;
    }

	@Override
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void init() throws KettleException, ClassNotFoundException {
		initProperties();
        INSTANCE = this;
	}

	public void initProperties() throws KettleException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

		KettleEnvironment.init();
		repositoryMeta = new KettleDatabaseRepositoryMeta();
		repositoryMeta.setName(reponame);
		repositoryMeta.setDescription(repodescription);

		this.pooledDBConnection = new DatabaseMeta();
		pooledDBConnection.setDatabaseType(dbtype);
		pooledDBConnection.setHostname(dbhostname);
		pooledDBConnection.setDBName(dbname);
		pooledDBConnection.setDBPort(dbport);
		pooledDBConnection.setUsername(dbusername);
		pooledDBConnection.setPassword(dbuserpassword);
        pooledDBConnection.setUsingConnectionPool(true);
        pooledDBConnection.setInitialPoolSize(20);
		repositoryMeta.setConnection(pooledDBConnection);

		userInfo = new UserInfo(username, userpassword, userfullname, userfullname, true);

		init(repositoryMeta);
		
		connect(username, userpassword);


        this.unpooledDBConnection = new DatabaseMeta();
        unpooledDBConnection.setDatabaseType(dbtype);
        unpooledDBConnection.setHostname(dbhostname);
        unpooledDBConnection.setDBName(dbname);
        unpooledDBConnection.setDBPort(dbport);
        unpooledDBConnection.setUsername(dbusername);
        unpooledDBConnection.setPassword(dbuserpassword);
        unpooledDBConnection.setUsingConnectionPool(false);
        supportingDatabase = new Database(loggingObject, unpooledDBConnection);
        //supportingDatabase.getDatabaseMeta().setUsingConnectionPool(true);
        //supportingDatabase.getDatabaseMeta().setMaximumPoolSize(20);
        /* supportingDatabase.connect();;*/
		
		//Ensure root and tenant root dir's
		rootDir = findDirectory("/conxbi");
		tenantDir = findDirectory("/conxbi/tenant");
	}


	public Boolean isAvailable() throws Exception {
		return true;// getLoginUserId() != null;
	}

	public static Properties loadRepoProperties() {
		if (!repoProperties.isEmpty()) {
			return repoProperties;
		}
		try {
			repoProperties.load(DBRepositoryWrapperImpl.class.getResourceAsStream("/pentaho.di.repo.properties"));
		} catch (IOException e) {
			throw new RuntimeException("Could not load pentaho.di.repo.properties", e);
		}

		return repoProperties;
	}

    /**
     *
     * Metadata Tenant Directory Methods
     */
    public RepositoryDirectoryInterface provideMetadataDirectoryForTenant(Organization tenant) throws KettleException {
        RepositoryDirectoryInterface tenantDir = provideDirectoryForTenant(tenant);
        RepositoryDirectoryInterface dir = tenantDir.findDirectory(FOLDER_METADATA);
        if (dir == null)
            dir = createRepositoryDirectory(tenantDir, FOLDER_METADATA);
        return dir;
    }
    public RepositoryDirectoryInterface provideDBConnectionsMetadataDirectoryForTenant(Organization tenant) throws KettleException {
        RepositoryDirectoryInterface mdDir = provideMetadataDirectoryForTenant(tenant);
        RepositoryDirectoryInterface dir = mdDir.findDirectory(FOLDER_METADATA_DBCONNECTIONS);
        if (dir == null)
            dir = createRepositoryDirectory(mdDir, FOLDER_METADATA_DBCONNECTIONS);
        return dir;
    }
    public RepositoryDirectoryInterface provideExcelMetadataDirectoryForTenant(Organization tenant) throws KettleException {
        RepositoryDirectoryInterface mdDir = provideMetadataDirectoryForTenant(tenant);
        RepositoryDirectoryInterface dir = mdDir.findDirectory(FOLDER_METADATA_EXCEL);
        if (dir == null)
            dir = createRepositoryDirectory(mdDir, FOLDER_METADATA_EXCEL);
        return dir;
    }
    public RepositoryDirectoryInterface provideDelimitedMetadataDirectoryForTenant(Organization tenant) throws KettleException {
        RepositoryDirectoryInterface mdDir = provideMetadataDirectoryForTenant(tenant);
        RepositoryDirectoryInterface dir = mdDir.findDirectory(FOLDER_METADATA_DELIMITED);
        if (dir == null)
            dir = createRepositoryDirectory(mdDir, FOLDER_METADATA_DELIMITED);
        return dir;
    }
	
	@Override
	public void deleteDirectoryForTenant(Organization tenant) throws KettleException {
		RepositoryDirectoryInterface dir = provideDirectoryForTenant(tenant);
		deleteRepositoryDirectory(dir);
	}

    @Override
    public KettleDatabaseRepositoryDirectoryDelegate getRepositoryDirectoryDelegate() {
        return directoryDelegate;
    }

    @Override
    public KettleDatabaseRepositoryTransDelegate getRepositoryTransDelegate() {
        return transDelegate;
    }

    @Override
    public KettleDatabaseRepositoryJobDelegate getRepositoryJobDelegate() {
        return jobDelegate;
    }

    @Override
    public KettleDatabaseRepositoryDatabaseDelegate getRepositoryDatabaseDelegate() {
        return databaseDelegate;
    }

    @Override
    public KettleDatabaseRepositoryConnectionDelegate getRepositoryConnectionDelegate() {
        return connectionDelegate;
    }

    @Override
    public KettleDatabaseRepositoryStepDelegate getRepositoryStepDelegate() {
        return stepDelegate;
    }

    @Override
	public RepositoryDirectoryInterface provideDirectoryForTenant(Organization tenant) throws KettleException {
		String dirName = "Organization-"+tenant.getId();
		RepositoryDirectoryInterface dir = tenantDir.findDirectory(dirName);
		if (dir == null)
			dir = createRepositoryDirectory(tenantDir, dirName);
		return dir;
	}

	@Override
	public RepositoryDirectoryInterface provideTransDirectoryForTenant(Organization tenant) throws KettleException {
		RepositoryDirectoryInterface tenantDir = provideDirectoryForTenant(tenant);
		RepositoryDirectoryInterface dir = tenantDir.findDirectory(FOLDER_TRANS);
		if (dir == null)
			dir = createRepositoryDirectory(tenantDir, FOLDER_TRANS);
		return dir;		
	}

	@Override
	public RepositoryDirectoryInterface provideTransStepDirectoryForTenant(Organization tenant) throws KettleException {
		RepositoryDirectoryInterface transDirectory = provideTransDirectoryForTenant(tenant);
		RepositoryDirectoryInterface dir = transDirectory.findDirectory(FOLDER_TRANS_STEPS);
		if (dir == null)
			dir = createRepositoryDirectory(transDirectory, FOLDER_TRANS_STEPS);
		return dir;	
	}

	@Override
	public RepositoryDirectoryInterface provideInputTransStepDirectoryForTenant(Organization tenant) throws KettleException {
		RepositoryDirectoryInterface transStepDirectory = provideTransStepDirectoryForTenant(tenant);
		RepositoryDirectoryInterface dir = transStepDirectory.findDirectory(FOLDER_TRANS_INPUTS);
		if (dir == null)
			dir = createRepositoryDirectory(transStepDirectory, FOLDER_TRANS_INPUTS);
		return dir;	
	}

	@Override
	public RepositoryDirectoryInterface provideOutputTransStepDirectoryForTenant(Organization tenant) throws KettleException {
		RepositoryDirectoryInterface transStepDirectory = provideTransStepDirectoryForTenant(tenant);
		RepositoryDirectoryInterface dir = transStepDirectory.findDirectory(FOLDER_TRANS_OUTPUTS);
		if (dir == null)
			dir = createRepositoryDirectory(transStepDirectory, FOLDER_TRANS_OUTPUTS);
		return dir;	
	}

	@Override
	public RepositoryDirectoryInterface provideTransformTransStepDirectoryForTenant(Organization tenant) throws KettleException {
		RepositoryDirectoryInterface transStepDirectory = provideTransStepDirectoryForTenant(tenant);
		RepositoryDirectoryInterface dir = transStepDirectory.findDirectory(FOLDER_TRANS_TRANSFORMS);
		if (dir == null)
			dir = createRepositoryDirectory(transStepDirectory, FOLDER_TRANS_TRANSFORMS);
		return dir;	
	}

    @Override
    public RepositoryDirectoryInterface provideTransformDraftsDirectoryForTenant(Organization tenant) throws KettleException {
        RepositoryDirectoryInterface transStepDirectory = provideTransformTransStepDirectoryForTenant(tenant);
        RepositoryDirectoryInterface dir = transStepDirectory.findDirectory(FOLDER_TRANS_TRANSFORMS_DRAFTS);
        if (dir == null)
            dir = createRepositoryDirectory(transStepDirectory, FOLDER_TRANS_TRANSFORMS_DRAFTS);
        return dir;
    }

	@Override
	public RepositoryDirectoryInterface provideTransformMappingsTransStepDirectoryForTenant(Organization tenant) throws KettleException {
		RepositoryDirectoryInterface transStepDirectory = provideTransStepDirectoryForTenant(tenant);
		RepositoryDirectoryInterface dir = transStepDirectory.findDirectory(FOLDER_TRANS_MAPPINGS);
		if (dir == null)
			dir = createRepositoryDirectory(transStepDirectory, FOLDER_TRANS_MAPPINGS);
		return dir;	
	}

	/**
	 * 
	 * Jobs Tenant Directory Methods
	 */	
	@Override
	public RepositoryDirectoryInterface provideJobsDirectoryForTenant(Organization tenant) throws KettleException {
		RepositoryDirectoryInterface tenantDir = provideDirectoryForTenant(tenant);
		RepositoryDirectoryInterface dir = tenantDir.findDirectory(FOLDER_JOBS);
		if (dir == null)
			dir = createRepositoryDirectory(tenantDir, FOLDER_JOBS);
		return dir;	
	}

	@Override
	public RepositoryDirectoryInterface provideJobGeneralDirectoryForTenant(Organization tenant) throws KettleException {
		RepositoryDirectoryInterface jobsDir = provideJobsDirectoryForTenant(tenant);
		RepositoryDirectoryInterface dir = jobsDir.findDirectory(FOLDER_JOBS_GENERAL);
		if (dir == null)
			dir = createRepositoryDirectory(jobsDir, FOLDER_JOBS_GENERAL);
		return dir;	
	}

	@Override
	public RepositoryDirectoryInterface provideJobFileTransferDirectoryForTenant(Organization tenant) throws KettleException {
		RepositoryDirectoryInterface jobsDir = provideJobsDirectoryForTenant(tenant);
		RepositoryDirectoryInterface dir = jobsDir.findDirectory(FOLDER_JOBS_FILE_TRANSFER);
		if (dir == null)
			dir = createRepositoryDirectory(jobsDir, FOLDER_JOBS_FILE_TRANSFER);
		return dir;	
	}

	@Override
	public RepositoryDirectoryInterface provideJobFileManagementDirectoryForTenant(Organization tenant) throws KettleException {
		RepositoryDirectoryInterface jobsDir = provideJobsDirectoryForTenant(tenant);
		RepositoryDirectoryInterface dir = jobsDir.findDirectory(FOLDER_JOBS_FILE_MANAGEMENT);
		if (dir == null)
			dir = createRepositoryDirectory(jobsDir, FOLDER_JOBS_FILE_MANAGEMENT);
		return dir;	
	}

    public Database getSupportingDatabase() {
        return supportingDatabase;
    }

    public void setSupportingDatabase(Database supportingDatabase) {
        this.supportingDatabase = supportingDatabase;
    }

    public String getReponame() {
        return reponame;
    }

    public void setReponame(String reponame) {
        this.reponame = reponame;
    }

    public String getRepodescription() {
        return repodescription;
    }

    public void setRepodescription(String repodescription) {
        this.repodescription = repodescription;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserfullname() {
        return userfullname;
    }

    public void setUserfullname(String userfullname) {
        this.userfullname = userfullname;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getDbhostname() {
        return dbhostname;
    }

    public void setDbhostname(String dbhostname) {
        this.dbhostname = dbhostname;
    }

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getDbport() {
        return dbport;
    }

    public void setDbport(String dbport) {
        this.dbport = dbport;
    }

    public String getDbusername() {
        return dbusername;
    }

    public void setDbusername(String dbusername) {
        this.dbusername = dbusername;
    }

    public String getDbuserpassword() {
        return dbuserpassword;
    }

    public void setDbuserpassword(String dbuserpassword) {
        this.dbuserpassword = dbuserpassword;
    }

    public DatabaseMeta getPooledDBConnectionMeta() {
        return pooledDBConnection;
    }

    public DatabaseMeta getDBConnectionMeta() {
        return unpooledDBConnection;
    }
}
