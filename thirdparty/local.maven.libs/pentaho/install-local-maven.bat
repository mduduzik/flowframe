call mvn install:install-file -Dfile=kettle-core.jar -DgroupId=com.pentaho -DartifactId=kettle-core -Dversion=4.4.0 -Dpackaging=jar
call mvn install:install-file -Dfile=kettle-db.jar -DgroupId=com.pentaho -DartifactId=kettle-db -Dversion=4.4.0 -Dpackaging=jar
call mvn install:install-file -Dfile=kettle-engine.jar -DgroupId=com.pentaho -DartifactId=kettle-engine -Dversion=4.4.0 -Dpackaging=jar
call mvn install:install-file -Dfile=kettle-ui-swt.jar -DgroupId=com.pentaho -DartifactId=kettle-ui-swt -Dversion=4.4.0 -Dpackaging=jar
call mvn install:install-file -Dfile=kettle-test.jar -DgroupId=com.pentaho -DartifactId=kettle-test -Dversion=4.4.0 -Dpackaging=jar