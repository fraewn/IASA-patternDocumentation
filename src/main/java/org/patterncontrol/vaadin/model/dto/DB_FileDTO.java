package org.patterncontrol.vaadin.model.dto;

import org.patterncontrol.vaadin.view.util.Util;

import java.util.ArrayList;

public class DB_FileDTO {
	public ArrayList<String> fileArray;

	// Singleton
	private static DB_FileDTO instance = null;
	private DB_FileDTO(){}
	public static DB_FileDTO getInstance(){
		if (instance==null){
			instance = new DB_FileDTO();
		}
		return instance;
	}

	// getter and setter
	public ArrayList<String> getAllFiles(){
		return fileArray;
	}
	public void setAllFiles(ArrayList<String> fileArray){
		Util util = new Util();
		this.fileArray = util.convertInput(fileArray);
	}
}
