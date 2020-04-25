package org.patterncontrol.vaadin.model.dto;

// this user data type object contains user data retrieved from the database
// it will later be filled with data like amount of connections going out of user node
public class DB_UserDTO {
	private static DB_UserDTO instance;
	private DB_UserDTO(){}
	public static DB_UserDTO getInstance(){
		if (instance==null){
			instance = new DB_UserDTO();
		}
		return instance;
	}

	public String username = "";

	public String getUsername(){ return username; }
	public void setUsername(String username){
		this.username = username;
	}
}
