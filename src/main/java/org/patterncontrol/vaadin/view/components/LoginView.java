package org.patterncontrol.vaadin.view.components;

import com.vaadin.data.Property;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.patterncontrol.vaadin.control.logic.login.Login;
import org.patterncontrol.vaadin.model.dto.DB_UserDTO;
import org.patterncontrol.vaadin.model.dto.FE_LoginDTO;
import org.patterncontrol.vaadin.view.util.Roles;
import org.patterncontrol.vaadin.view.util.Views;

public class LoginView extends VerticalLayout implements View {
	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		DB_UserDTO user = (DB_UserDTO) VaadinSession.getCurrent().getAttribute(Roles.CURRENTUSER);
		if(user!=null){
			UI.getCurrent().getNavigator().navigateTo(Views.FILETOPATTERNVIEW);
		}
		this.setUp();
	}
	public void setUp() {
		// Page
		Page.getCurrent().setTitle("Welcome Page - Developer Login");

		//this.addComponent(UserPanel.getPanel());
		final VerticalLayout layout = new VerticalLayout();

		final TextField username = new TextField();
		username.setId("username_input");
		username.setCaption("Username (Gitlab name):");
		Button loginbutton = new Button("Login", FontAwesome.SIGN_IN);
		ShortcutListener shortcut = new ShortcutListener("Shortcut_Enter", ShortcutAction.KeyCode.ENTER, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				loginbutton.click();
			}
		};
		final PasswordField password = new PasswordField();
		password.setId("password_input");
		password.setCaption("Password:");
		password.addShortcutListener(shortcut);

		loginbutton.setId("login_developer");
		//loginbutton.addStyleName("flatButton");
		//loginbutton.addStyleName("spaceLeftRight");

		Label header = new Label("<h><b>Login to PatternControl as developer</b></h>", ContentMode.HTML);
		header.setIcon(FontAwesome.GRADUATION_CAP);


		layout.addComponents(header, username, password,loginbutton);
		layout.setMargin(true);
		layout.setSpacing(true);

		loginbutton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				String username_input = username.getValue();
				String password_input = password.getValue();
				FE_LoginDTO logindto = new FE_LoginDTO(username_input, password_input);
				try{
					System.out.println("tries to log in");
					System.out.println("username:");
					System.out.print(username_input);
					Login.login(logindto);
				}
				catch (DatabaseException dbe) {
					Notification.show("DB-Fehler", dbe.getMessage(), Notification.Type.ERROR_MESSAGE);
				}
				catch (Exception e){
					Notification.show("Exception", e.getMessage(), Notification.Type.ERROR_MESSAGE);
					e.printStackTrace();
				}

			}
		});



		this.addComponent(layout);
		this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
	}
}
