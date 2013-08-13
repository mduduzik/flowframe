package com.conx.bi.etl.pentaho.repository.db.services.repository;

import java.io.IOException;
import java.util.Properties;

import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.UserInfo;
import org.pentaho.di.repository.kdr.KettleDatabaseRepository;
import org.pentaho.di.repository.kdr.KettleDatabaseRepositoryMeta;

import com.conx.bi.etl.pentaho.repository.db.services.CustomRepository;

public class DBRepositoryWrapperImpl extends KettleDatabaseRepository implements CustomRepository {

    private static final String FOLDER_METADATA = "Metadata";
    private static final String FOLDER_METADATA_DBCONNECTIONS = "DBConnections";
    private static final String FOLDER_METADATA_EXCEL = "Excel";
    private static final String FOLDER_METADATA_DELIMITED = "Delimited";

	private static final String FOLDER_TRANS = "Trans";
	private static final String FOLDER_TRANS_STEPS = "Steps";
	private static final String FOLDER_TRANS_INPUTS = "Inputs";
	private static final String FOLDER_TRANS_OUTPUTS = "Outputs";
	private static final String FOLDER_TRANS_TRANSFORMS = "Transforms";
	private static final String FOLDER_TRANS_MAPPINGS = "Mappings";
	
	
	private static final String FOLDER_JOBS = "Jobs";
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

    private static CustomRepository INSTANCE = null;

    public static CustomRepository getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new DBRepositoryWrapperImpl();
            try {
                ((DBRepositoryWrapperImpl)INSTANCE).init();
            } catch (KettleException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return INSTANCE;
    }

	@Override
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void init() throws KettleException{
		initProperties();
	}

	public void initProperties() throws KettleException {
		KettleEnvironment.init();
		
		loadRepoProperties();

		String reponame = repoProperties.getProperty(REPO_NAME);
		String repodescription= repoProperties.getProperty(REPO_DESCRIPTION);
		String username = repoProperties.getProperty(REPO_USER_NAME);
		String userfullname = repoProperties.getProperty(REPO_USER_FULLNAME);
		String userpassword = repoProperties.getProperty(REPO_USER_PWD);
		String dbhostname= repoProperties.getProperty(REPO_DATABASE_HOSTNAME);
		String dbtype= repoProperties.getProperty(REPO_DATABASE_TYPE);
		String dbname= repoProperties.getProperty(REPO_DATABASE_NAME);
		String dbport = repoProperties.getProperty(REPO_DATABASE_PORT);
		String dbusername = repoProperties.getProperty(REPO_DATABASE_USERNAME);
		String dbuserpassword = repoProperties.getProperty(REPO_DATABASE_PWD);

		repositoryMeta = new KettleDatabaseRepositoryMeta();
		repositoryMeta.setName(reponame);
		repositoryMeta.setDescription(repodescription);

		DatabaseMeta connection = new DatabaseMeta();
		connection.setDatabaseType(dbtype);
		connection.setHostname(dbhostname);
		connection.setDBName(dbname);
		connection.setDBPort(dbport);
		connection.setUsername(dbusername);
		connection.setPassword(dbuserpassword);

		repositoryMeta.setConnection(connection);

		userInfo = new UserInfo(username, userpassword, userfullname, userfullname, true);

		init(repositoryMeta);
		
		connect(username, userpassword);
		
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
	
}
