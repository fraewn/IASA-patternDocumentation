# README 

### Requirements
* Maven
* Java 8
* running neo4j instance
* you executed IASA-getProject component and have classes and patterns in your running neo4j instance 

### Build 
1. Have your project database ready (running neo4j instance)

2. Add database connection as `.ini` file to the project in the dao/util package `org.patterncontrol.vaadin.model.util
.projectDatabaseCredentials.ini`. Use [remote] as key and fill portType (e.g. bolt), port, ip, username and password as values. 

3. Execute maven commands: `clean install`

4. Execute maven commands: `jetty:run`

### Errors - FAQ
* If you are using neo4j version 4+ and getting an error like "connection lost due to instability or database restart"
then go to control.service.Neo4jconnection and change

```java
public Neo4jconnection(String uri, String user, String password){
		driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
	}
``` 

to 

```java
public Neo4jconnection(String uri, String user, String password){
		driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password), Config.builder().withoutEncryption().toConfig());
	}
``` 

* If maven clean install does not work: make sure all vaadin dependencies were downloaded/ enable auto import in IDE



