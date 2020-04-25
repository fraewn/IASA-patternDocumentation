package org.patterncontrol.vaadin.model.dto;

public class FE_PatternDTO {
	public String patternchoice;

	// Singleton
	private static FE_PatternDTO instance;
	private FE_PatternDTO(){}
	public static FE_PatternDTO getInstance(){
		if (instance==null){
			instance = new FE_PatternDTO();
		}
		return instance;
	}

	// getter and setter
	public String getPatternChoice(){
		return patternchoice;
	}
	public void setPatternChoice(String patternchoice){
		this.patternchoice = patternchoice;
	}
}
