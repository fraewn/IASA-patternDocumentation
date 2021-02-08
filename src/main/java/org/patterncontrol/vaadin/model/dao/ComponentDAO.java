package org.patterncontrol.vaadin.model.dao;

import org.ini4j.Ini;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.patterncontrol.vaadin.control.service.Neo4jconnection;
import org.patterncontrol.vaadin.model.dto.DB_ComponentDTO;

import java.io.File;

public class ComponentDAO {
	private static ComponentDAO instance=null;
	private static String databaseUrl;
	private static String username;
	private static String password;

	private ComponentDAO(){}
	public static ComponentDAO getInstance() throws Exception{
		if(instance==null) {
			Ini ini = new Ini(new File("src/main/java/org/patterncontrol/vaadin/model/util/projectDatabaseConfiguration.ini"));
			String portType = ini.get("remote", "portType");
			String ip = ini.get("remote", "ip");
			String port = ini.get("remote", "port");
			databaseUrl = portType + "://" + ip + ":" + port;
			username = ini.get("remote", "username");
			password = ini.get("remote", "password");
			instance = new ComponentDAO();
		}
		return instance;
	}
	public DB_ComponentDTO getComponentsByPattern(String pattern) throws Exception, DatabaseException {
		try ( Neo4jconnection connection = new Neo4jconnection(databaseUrl, username, password)) {
			return connection.getComponentsByPattern(pattern);
		}
	}
}
