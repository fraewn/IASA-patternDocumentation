package org.patterncontrol.vaadin.control.logic.login;

import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.patterncontrol.vaadin.model.dao.UserDAO;
import org.patterncontrol.vaadin.model.dto.DB_UserDTO;
import org.patterncontrol.vaadin.model.dto.FE_LoginDTO;
import org.patterncontrol.vaadin.view.util.Roles;
import org.patterncontrol.vaadin.view.util.Views;

public class Login {
	// login method gets typed in data from login function on website
	public static void login(FE_LoginDTO logindto) throws DatabaseException, Exception{
		String useremail = logindto.getUsername();
		String userpassword = logindto.getPassword();

		// it creates a dao object so it can call a method to retrieve the login information
		// from the database
		UserDAO userdao = UserDAO.getInstance();
		DB_UserDTO userdto = userdao.getUser(useremail, userpassword);
		String username = userdto.getUsername();

		VaadinServletService.getCurrentServletRequest().getSession().setAttribute(Roles.CURRENTUSER, username);

		// Navigate to next view
		UI.getCurrent().getNavigator().navigateTo(Views.FILETOPATTERNVIEW);
	}
	public static void logout(){
		UI.getCurrent().getSession().close();
	}
}
