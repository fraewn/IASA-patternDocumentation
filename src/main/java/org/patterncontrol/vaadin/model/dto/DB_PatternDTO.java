package org.patterncontrol.vaadin.model.dto;

import org.patterncontrol.vaadin.view.util.Util;

import java.util.ArrayList;

public class DB_PatternDTO {
	public ArrayList<String> pattern;

	// Singleton
	private static DB_PatternDTO instance;
	private DB_PatternDTO(){}
	public static DB_PatternDTO getInstance(){
		if (instance==null){
			instance = new DB_PatternDTO();
		}
		return instance;
	}

	// getter and setter
	public ArrayList<String> getAllPatterns(){
		return pattern;
	}
	public void setAllPatterns(ArrayList<String> pattern){
		Util util = new Util();
		this.pattern = util.convertInput(pattern);
	}
}
