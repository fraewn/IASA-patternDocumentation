package org.patterncontrol.vaadin.model.dto;

public class FE_FileDTO {
	public String filechoice;

	// Singleton
	private static FE_FileDTO instance;
	private FE_FileDTO(){}
	public static FE_FileDTO getInstance(){
		if (instance==null){
			instance = new FE_FileDTO();
		}
		return instance;
	}

	// getter and setter
	public String getFileChoice(){
		return filechoice;
	}
	public void setFileChoice(String filechoice){
		this.filechoice = filechoice;
	}
}
