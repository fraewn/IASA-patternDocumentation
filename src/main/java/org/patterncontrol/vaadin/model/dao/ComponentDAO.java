package org.patterncontrol.vaadin.model.dao;

import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.patterncontrol.vaadin.control.service.Neo4jconnection;
import org.patterncontrol.vaadin.model.dto.DB_ComponentDTO;
import org.patterncontrol.vaadin.model.util.ProjectDatabaseCredentials;

public class ComponentDAO {
	private static ComponentDAO instance=null;
	private ComponentDAO(){}
	public static ComponentDAO getInstance(){
		if(instance==null) {
			instance = new ComponentDAO();
		}
		return instance;
	}
	public DB_ComponentDTO getComponentsByPattern(String pattern) throws Exception, DatabaseException {
		String url = ProjectDatabaseCredentials.getProjectdatabase_url();
		String user = ProjectDatabaseCredentials.getProjectdatabase_username();
		String password = ProjectDatabaseCredentials.getProjectdatabase_password();

		try ( Neo4jconnection connection = new Neo4jconnection(url, user, password)) {
			return connection.getComponentsByPattern(pattern);
		}
	}
}
