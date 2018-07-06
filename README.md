# springEmbeddedTomcatJNDI
Spring boot 2.x embedded tomcat JNDI datasource (dev profile) + WAR

This code intend to keep db access via JNDI datasource lookup.

In development, running with embedded tomcat with JNDI Datasource configured
At deployment as WAR, it lookup the real JDNI datasource at the server (e.g. tomcat)

in development env. below JNDI data source parameter can be override by java system variable using "-D" 
i. dev.jndi.name
ii. dev.driver.class.name (if using none mySql db, remember to update the pom.xml too)
iii. dev.url
iv. dev.username
v. dev.password

During startup it will attempt to perform a JNDI lookup to the datasource

Code default implementation is using a mysql db with a schema "test" having a table named "databasechangelog"
the default credential is "db_deployer/password".

the testing endpoint is in ApiController.java, modified the query statement where necessary

# as Standalone Spring Boot 
To test with standalone spring boot, need to set profile as "dev", e.g. "-Dspring.profiles.active=dev" 

GET http://localhost:8080/test

# as WAR deployed to Tomcat Server
Remember to configure the JNDI source in tomcat's context.xml under /conf and put the required JDBC driver under the /lib

The war file name is specified in the pom.xml <finalName> as jndiTest, so if using default tomcat deployment

Test the endpoint at

GET http://localhost:8080/jndiTest/test