package org.patterncontrol.vaadin.model.dao;

import org.ini4j.Ini;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.patterncontrol.vaadin.control.service.Neo4jconnection;
import org.patterncontrol.vaadin.model.dto.DB_PatternDTO;

import java.io.File;

public class PatternDAO{
	private static PatternDAO instance=null;
	private static String databaseUrl;
	private static String username;
	private static String password;

	private PatternDAO(){}
	public static PatternDAO getInstance() throws Exception{
		if(instance==null) {
			Ini ini = new Ini(new File("src/main/java/org/patterncontrol/vaadin/model/util/projectDatabaseConfiguration.ini"));
			String portType = ini.get("remote", "portType");
			String ip = ini.get("remote", "ip");
			String port = ini.get("remote", "port");
			databaseUrl = portType + "://" + ip + ":" + port;
			username = ini.get("remote", "username");
			password = ini.get("remote", "password");
			instance = new PatternDAO();
		}
		return instance;
	}

	public DB_PatternDTO getAllPatterns() throws Exception, DatabaseException {
		try ( Neo4jconnection connection = new Neo4jconnection(databaseUrl, username, password)) {
			return connection.getAllPatterns();
		}
	}
}
