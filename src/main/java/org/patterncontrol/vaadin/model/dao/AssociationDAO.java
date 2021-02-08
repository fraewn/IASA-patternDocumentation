package org.patterncontrol.vaadin.model.dao;

import org.ini4j.Ini;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.patterncontrol.vaadin.control.service.Neo4jconnection;

import java.io.File;

public class AssociationDAO {
	private static AssociationDAO instance=null;
	private static String databaseUrl;
	private static String username;
	private static String password;

	private AssociationDAO(){}
	public static AssociationDAO getInstance() throws Exception{
		if(instance==null) {
			instance = new AssociationDAO();
				Ini ini = new Ini(new File("src/main/java/org/patterncontrol/vaadin/model/util/projectDatabaseConfiguration.ini"));
				String portType = ini.get("remote", "portType");
				String ip = ini.get("remote", "ip");
				String port = ini.get("remote", "port");
				databaseUrl = portType + "://" + ip + ":" + port;
				username = ini.get("remote", "username");
				password = ini.get("remote", "password");

		}
		return instance;
	}
	public Boolean createAssociation(String component, String file) throws Exception, DatabaseException {
		try ( Neo4jconnection connection = new Neo4jconnection(databaseUrl, username, password)) {
			return connection.createAssociation(component, file);
		}

	}
	public void addAdditionalInfo(String file, String info) throws Exception, DatabaseException {
		try ( Neo4jconnection connection = new Neo4jconnection(databaseUrl, username, password)) {
				connection.addAdditionalInfo(file, info);
		}
	}

	public void addAdditionalInfoFalse(String file, String info) throws Exception, DatabaseException {
		try ( Neo4jconnection connection = new Neo4jconnection(databaseUrl, username, password)) {
			connection.addAdditionalInfoFalse(file, info);
		}
	}

	public boolean deleteAssociationControl(String component, String file) throws Exception, DatabaseException {
		try ( Neo4jconnection connection = new Neo4jconnection(databaseUrl, username, password)) {
			return connection.deleteAssociationControl(component, file);
		}
	}
}
