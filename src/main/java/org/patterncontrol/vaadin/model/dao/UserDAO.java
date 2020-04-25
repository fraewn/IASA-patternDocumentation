package org.patterncontrol.vaadin.model.dao;

import org.patterncontrol.vaadin.control.service.Neo4jconnection;
import org.patterncontrol.vaadin.model.dto.DB_UserDTO;
import org.patterncontrol.vaadin.model.util.ProjectDatabaseCredentials;

public class UserDAO {
	private static UserDAO instance=null;
	private UserDAO(){}
	public static UserDAO getInstance(){
		if(instance==null) {
			instance = new UserDAO();
		}
		return instance;
	}


	public DB_UserDTO getUser(String username, String user_password) throws Exception {
		String url = ProjectDatabaseCredentials.getProjectdatabase_url();
		String user = ProjectDatabaseCredentials.getProjectdatabase_username();
		String password = ProjectDatabaseCredentials.getProjectdatabase_password();

		// class to get user data from database
		try ( Neo4jconnection connection = new Neo4jconnection(url, user, password))
		{
			// send request with username and password to neo4j
			// connection return userdto_db with username if successful
			return connection.getUserData(username, user_password);
		}
	}
}
