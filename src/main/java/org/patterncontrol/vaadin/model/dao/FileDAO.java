package org.patterncontrol.vaadin.model.dao;

import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.patterncontrol.vaadin.control.service.Neo4jconnection;
import org.patterncontrol.vaadin.model.dto.DB_FileDTO;
import org.patterncontrol.vaadin.model.util.ProjectDatabaseCredentials;

public class FileDAO {
	private static FileDAO instance=null;
	private FileDAO(){}
	public static FileDAO getInstance(){
		if(instance==null) {
			instance = new FileDAO();
		}
		return instance;
	}

	public DB_FileDTO getAllFilesByDev(String devevelopername) throws Exception, DatabaseException {
		String url = ProjectDatabaseCredentials.getProjectdatabase_url();
		String user = ProjectDatabaseCredentials.getProjectdatabase_username();
		String password = ProjectDatabaseCredentials.getProjectdatabase_password();

		try ( Neo4jconnection connection = new Neo4jconnection(url, user, password)) {
			return connection.getAllFilesByDev(devevelopername);
		}
	}
}
