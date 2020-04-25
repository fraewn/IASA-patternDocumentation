package org.patterncontrol.vaadin.view.util;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.VerticalLayout;
import org.patterncontrol.vaadin.model.dto.DB_UserDTO;
import org.patterncontrol.vaadin.view.components.Panel_SignedIn;
import org.patterncontrol.vaadin.view.components.Panel_SignedOut;

public class UserPanel {
	public static VerticalLayout getPanel(){
		Object user = VaadinSession.getCurrent().getAttribute(Roles.CURRENTUSER);
		if(user instanceof DB_UserDTO) {
			return new Panel_SignedIn();
		}
		else{
			return new Panel_SignedOut();
		}
	}
}
