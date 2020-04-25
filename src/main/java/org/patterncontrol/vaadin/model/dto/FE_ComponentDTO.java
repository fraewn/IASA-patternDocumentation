package org.patterncontrol.vaadin.model.dto;

import java.util.ArrayList;
import java.util.List;

public class FE_ComponentDTO {
	public String componentchoice;


	// Singleton
	private static FE_ComponentDTO instance;
	private FE_ComponentDTO(){}
	public static FE_ComponentDTO getInstance(){
		if (instance==null){
			instance = new FE_ComponentDTO();
		}
		return instance;
	}

	// getter and setter
	public String getComponentChoice(){
		return componentchoice;
	}
	public void setComponentChoice(String componentchoice){
		this.componentchoice = componentchoice;
	}

}
