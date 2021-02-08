package org.patterncontrol.vaadin.model.dao;

import org.ini4j.Ini;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.patterncontrol.vaadin.control.service.Neo4jconnection;
import org.patterncontrol.vaadin.model.dto.DB_FileDTO;

import java.io.File;

public class FileDAO {
	private static FileDAO instance=null;
	private static String databaseUrl;
	private static String username;
	private static String password;

	private FileDAO(){}
	public static FileDAO getInstance() throws Exception{
		if(instance==null) {
			Ini ini = new Ini(new File("src/main/java/org/patterncontrol/vaadin/model/util/projectDatabaseConfiguration.ini"));
			String portType = ini.get("remote", "portType");
			String ip = ini.get("remote", "ip");
			String port = ini.get("remote", "port");
			databaseUrl = portType + "://" + ip + ":" + port;
			username = ini.get("remote", "username");
			password = ini.get("remote", "password");
			instance = new FileDAO();
		}
		return instance;
	}

	public DB_FileDTO getAllFilesByDev(String devevelopername) throws Exception, DatabaseException {
		try ( Neo4jconnection connection = new Neo4jconnection(databaseUrl, username, password)) {
			return connection.getAllFilesByDev(devevelopername);
		}
	}
}
