package org.patterncontrol.vaadin.model.dao;

import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.patterncontrol.vaadin.control.service.Neo4jconnection;
import org.patterncontrol.vaadin.model.util.ProjectDatabaseCredentials;

public class AssociationDAO {
	private static AssociationDAO instance=null;
	private AssociationDAO(){}
	public static AssociationDAO getInstance(){
		if(instance==null) {
			instance = new AssociationDAO();
		}
		return instance;
	}
	public Boolean createAssociation(String component, String file) throws Exception, DatabaseException {
		String url = ProjectDatabaseCredentials.getProjectdatabase_url();
		String user = ProjectDatabaseCredentials.getProjectdatabase_username();
		String password = ProjectDatabaseCredentials.getProjectdatabase_password();

		try ( Neo4jconnection connection = new Neo4jconnection(url, user, password)) {
			return connection.createAssociation(component, file);
		}

	}
	public void addAdditionalInfo(String file, String info) throws Exception, DatabaseException {
		String url = ProjectDatabaseCredentials.getProjectdatabase_url();
		String user = ProjectDatabaseCredentials.getProjectdatabase_username();
		String password = ProjectDatabaseCredentials.getProjectdatabase_password();

		try ( Neo4jconnection connection = new Neo4jconnection(url, user, password)) {
				connection.addAdditionalInfo(file, info);
		}
	}

	public void addAdditionalInfoFalse(String file, String info) throws Exception, DatabaseException {
		String url = ProjectDatabaseCredentials.getProjectdatabase_url();
		String user = ProjectDatabaseCredentials.getProjectdatabase_username();
		String password = ProjectDatabaseCredentials.getProjectdatabase_password();

		try ( Neo4jconnection connection = new Neo4jconnection(url, user, password)) {
			connection.addAdditionalInfoFalse(file, info);
		}
	}

	public boolean deleteAssociationControl(String component, String file) throws Exception, DatabaseException {
		String url = ProjectDatabaseCredentials.getProjectdatabase_url();
		String user = ProjectDatabaseCredentials.getProjectdatabase_username();
		String password = ProjectDatabaseCredentials.getProjectdatabase_password();

		try ( Neo4jconnection connection = new Neo4jconnection(url, user, password)) {
			return connection.deleteAssociationControl(component, file);
		}
	}
}
