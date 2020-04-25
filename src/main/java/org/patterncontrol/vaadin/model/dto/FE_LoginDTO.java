package org.patterncontrol.vaadin.model.dto;

import java.util.logging.Level;
import java.util.logging.Logger;

// this login data type object contains the data typed in during the log in on the website
public class FE_LoginDTO {
	public String username = "";
	public String password = "";

	public FE_LoginDTO(String username, String password){
		this.username = username;
		this.password = password;
		Logger.getLogger(FE_LoginDTO.class.getName()).log(Level.SEVERE, "username and password were set");
	}

	public String getUsername(){ return username; }
	public String getPassword() { return password; }
}
