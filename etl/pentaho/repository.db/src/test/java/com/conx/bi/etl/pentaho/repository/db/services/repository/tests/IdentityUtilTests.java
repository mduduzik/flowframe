package com.conx.bi.etl.pentaho.repository.db.services.repository.tests;

import org.junit.Ignore;


@Ignore
public class IdentityUtilTests {
/*
	@Autowired
	private CustomRepository repository;
	private RepositoryDirectoryInterface rootDir;
	private RepositoryDirectoryInterface testDir;
    private CustomRepository repo;

    @Before
	public void setUp() throws KettleException{
        try {
            this.repo = DBRepositoryWrapperImpl.getINSTANCE();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Error e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
	}
	
	@After
	public void cleanUp() throws KettleException{
		//-- Delete test folder
        repo.disconnect();
	}
	
	@Test
	public void testBasics() throws KettleException {
        Organization tenant = new Organization();
        tenant.setId(1L);
        tenant.setName("Test");

        try {
            //Add db - dir id(2)/DBConnections
            DatabaseMetaDTO dto = new DatabaseMetaDTO();
            dto.setDirObjectId(2L);
            dto.setName("test1");
            dto.setDatabasePort(20);
            dto.setDatabaseType("MSSQL");

            RepositoryDirectoryInterface dir = RepositoryUtil.getDirectory(repo, new LongObjectId(2L));

            DatabaseMeta db = DatabaseMetaUtil.addDatabaseMeta(tenant, repo, dir, dto);
            Assert.notNull(db);

            //Get tenant dbs
            List<DatabaseMeta> dbs = DatabaseMetaUtil.getDatabasesBySubDirAndTenantId(repo, dir, tenant.getId().toString());
            Assert.notNull(dbs);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }*/
}
