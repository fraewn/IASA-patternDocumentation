# README 

## Requirements
* Maven
* Java 8
* running neo4j instance
* execute IASA-getProject component 

#### Build 
1. Database url
Go to org.patterncontrol.vaadin.view.components.LoginView and change database url to your database url 

2. 

#### Errors
If you are using neo4j version 4+ and getting an error like "connection lost due to instability or database restart"
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



