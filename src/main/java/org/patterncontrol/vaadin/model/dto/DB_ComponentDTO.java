package org.patterncontrol.vaadin.model.dto;

import org.patterncontrol.vaadin.view.util.Util;

import java.util.ArrayList;

public class DB_ComponentDTO {
	public ArrayList<String> componentArray;

	// Singleton
	private static DB_ComponentDTO instance = null;
	private DB_ComponentDTO(){}
	public static DB_ComponentDTO getInstance(){
		if (instance==null){
			instance = new DB_ComponentDTO();
		}
		return instance;
	}

	// getter and setter
	public ArrayList<String> getAllComponents(){
		return componentArray;
	}
	public void setAllComponents(ArrayList<String> componentArray){
		// convert Strings in list to replace " as a char with nothing
		Util util = new Util();
		this.componentArray = util.convertInput(componentArray);
	}
}
