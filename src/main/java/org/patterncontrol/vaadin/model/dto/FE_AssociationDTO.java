package org.patterncontrol.vaadin.model.dto;

import java.util.ArrayList;
import java.util.List;

public class FE_AssociationDTO {
	private static FE_AssociationDTO instance = null;
	private FE_AssociationDTO(){}
	public static FE_AssociationDTO getInstance(){
		if (instance==null){
			instance = new FE_AssociationDTO();
		}
		return instance;
	}
	private String file;
	private String pattern;
	private String component;
	private List additionalinformationstrue = new ArrayList();
	private List additionalinformationsfalse = new ArrayList();

	public String getFile(){ return  file; }
	public String getPattern(){ return  pattern; }
	public String getComponent(){ return  component; }

	public void setFile(String file){
		this.file = file;
	}
	public void setPattern(String pattern){
		this.pattern = pattern;
	}
	public void setComponent(String component){
		this.component = component;
	}
	public void setAdditionalinformationstrue(String info){
		additionalinformationstrue.add(info);
	}
	public List getAdditionalinformationstrue() {return additionalinformationstrue;}
	public void setAdditionalinformationsfalse(String info){
		additionalinformationsfalse.add(info);
	}
	public List getAdditionalinformationsfalse() {return additionalinformationsfalse;}
	public void cleanAdditionalinformations(){
		additionalinformationstrue= new ArrayList();
		additionalinformationsfalse= new ArrayList();
	}

}
