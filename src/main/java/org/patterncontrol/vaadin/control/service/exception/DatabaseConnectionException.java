package org.patterncontrol.vaadin.control.service.exception;

import java.util.logging.Level;

public class DatabaseConnectionException extends AbstractException {

	private static final long serialVersionUID = 2065477166822441838L;

	public DatabaseConnectionException(String reason, Class clazz, Exception e) {
		super(reason);

		java.util.logging.Logger.getLogger(clazz.getName()).log(Level.SEVERE, reason, e);
	}
}
