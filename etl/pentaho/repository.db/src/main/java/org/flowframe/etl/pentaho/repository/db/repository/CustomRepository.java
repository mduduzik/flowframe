package org.flowframe.etl.pentaho.repository.db.repository;

import org.flowframe.kernel.common.mdm.domain.organization.Organization;
import org.pentaho.di.core.database.Database;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.repository.UserInfo;
import org.pentaho.di.repository.kdr.delegates.*;

public interface CustomRepository extends Repository {
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
    public RepositoryDirectoryInterface provideDBConnectionsMetadataDirectoryForTenant(Organization tenant) throws KettleException;
    public RepositoryDirectoryInterface provideExcelMetadataDirectoryForTenant(Organization tenant) throws KettleException;
    public RepositoryDirectoryInterface provideDelimitedMetadataDirectoryForTenant(Organization tenant) throws KettleException;
    /**
	 * 
	 * Transformations Tenant Directory Methods
	 */
	public RepositoryDirectoryInterface provideTransDirectoryForTenant(Organization tenant) throws KettleException;

	public RepositoryDirectoryInterface provideTransStepDirectoryForTenant(Organization tenant) throws KettleException;
	
	public RepositoryDirectoryInterface provideInputTransStepDirectoryForTenant(Organization tenant) throws KettleException;
	public RepositoryDirectoryInterface provideOutputTransStepDirectoryForTenant(Organization tenant) throws KettleException;
	public RepositoryDirectoryInterface provideTransformTransStepDirectoryForTenant(Organization tenant) throws KettleException;
	public RepositoryDirectoryInterface provideTransformMappingsTransStepDirectoryForTenant(Organization tenant) throws KettleException;
	
	/**
	 * 
	 * Jobs Tenant Directory Methods
	 */
	public RepositoryDirectoryInterface provideJobsDirectoryForTenant(Organization tenant) throws KettleException;
	public RepositoryDirectoryInterface provideJobGeneralDirectoryForTenant(Organization tenant) throws KettleException;	
	public RepositoryDirectoryInterface provideJobFileTransferDirectoryForTenant(Organization tenant) throws KettleException;
	public RepositoryDirectoryInterface provideJobFileManagementDirectoryForTenant(Organization tenant) throws KettleException;


    public Database getSupportingDatabase();
}
