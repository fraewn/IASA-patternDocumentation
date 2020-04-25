package org.patterncontrol.vaadin.control.service.exception;

public abstract class AbstractException extends Exception {
	private String reason;
	public AbstractException(String reason) {
		this.reason=reason;
	}
	public String getReason(){
		return reason;
	}
	public void setReason(String reason){
		this.reason=reason;
	}
}