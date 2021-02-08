package org.patterncontrol.vaadin.model.dao;

import org.ini4j.Ini;
import org.patterncontrol.vaadin.control.service.Neo4jconnection;
import org.patterncontrol.vaadin.model.dto.DB_UserDTO;

import java.io.File;

public class UserDAO {
	private static UserDAO instance=null;
	private static String databaseUrl;
	private static String username;
	private static String password;

	private UserDAO(){}
	public static UserDAO getInstance() throws Exception{
		if(instance==null) {
			Ini ini = new Ini(new File("src/main/java/org/patterncontrol/vaadin/model/util/projectDatabaseConfiguration.ini"));
			String portType = ini.get("remote", "portType");
			String ip = ini.get("remote", "ip");
			String port = ini.get("remote", "port");
			databaseUrl = portType + "://" + ip + ":" + port;
			username = ini.get("remote", "username");
			password = ini.get("remote", "password");
			instance = new UserDAO();
		}
		return instance;
	}


	public DB_UserDTO getUser(String loginUsername, String loginPassword) throws Exception {
		// class to get user data from database
		try ( Neo4jconnection connection = new Neo4jconnection(databaseUrl, username, password))
		{
			// send request with username and password to neo4j
			// connection return userdto_db with username if successful
			return connection.getUserData(loginUsername, loginPassword);
		}
	}
}
