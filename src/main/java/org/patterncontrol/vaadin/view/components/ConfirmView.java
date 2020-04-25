package org.patterncontrol.vaadin.view.components;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServletService;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.patterncontrol.vaadin.control.logic.association.FilePatternAssociation;
import org.patterncontrol.vaadin.control.logic.login.Login;
import org.patterncontrol.vaadin.model.dto.FE_AssociationDTO;
import org.patterncontrol.vaadin.view.util.Views;
import org.patterncontrol.vaadin.view.util.AddInfo;

public class ConfirmView extends VerticalLayout implements com.vaadin.navigator.View {

	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		this.setUp();
	}
	public void setUp(){
		Page.getCurrent().setTitle("File/Pattern Association");
		final VerticalLayout layout = new VerticalLayout();

		Button logoutButton = new Button("Logout");
		layout.addComponent(logoutButton);
		logoutButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				Login.logout();
				// Navigate to login view
				UI.getCurrent().getNavigator().navigateTo(Views.LOGINVIEW);
				System.out.println("logged out");
			}
		});

		Button goBackButton = new Button("Go to previous page");
		layout.addComponent(goBackButton);
		goBackButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				// Navigate to login view
				UI.getCurrent().getNavigator().navigateTo(Views.COMPONENTVIEW);
				System.out.println("Navigate to component view");
			}
		});

		Button goToBeginButton = new Button("Go back to start");
		layout.addComponent(goToBeginButton);
		goToBeginButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				// Navigate to login view
				UI.getCurrent().getNavigator().navigateTo(Views.FILETOPATTERNVIEW);
				System.out.println("Navigate to pattern view");
			}
		});

		com.vaadin.ui.Label label = new com.vaadin.ui.Label("Please confirm the file/pattern association shown below:");
		layout.addComponent(label);

		FE_AssociationDTO fe_associationDTO = (FE_AssociationDTO) VaadinServletService.getCurrentServletRequest().getSession().getAttribute("association_dto");

		com.vaadin.ui.Label showSelectedFile = new com.vaadin.ui.Label("Selected File: " + fe_associationDTO.getFile());
		layout.addComponent(showSelectedFile);

		com.vaadin.ui.Label showSelectedPattern = new Label("Selected Pattern: " + fe_associationDTO.getPattern());
		layout.addComponent(showSelectedPattern);

		com.vaadin.ui.Label showSelectedComponent = new com.vaadin.ui.Label("Selected Pattern Component: " + fe_associationDTO.getComponent());
		layout.addComponent(showSelectedComponent);

		//use only in case there are additional infos
		if (fe_associationDTO.getAdditionalinformationstrue().isEmpty()!=true){

			String shortinfo =(String) fe_associationDTO.getAdditionalinformationstrue().get(0);
			Label showAdditionalInfo = new Label ("Additional Info: " + AddInfo.getDescription(shortinfo));
			layout.addComponent(showAdditionalInfo);
			if (fe_associationDTO.getAdditionalinformationstrue().size()>1){
				String shortinfo2 =(String) fe_associationDTO.getAdditionalinformationstrue().get(1);
				Label showAdditionalInfo2 = new Label ("Additional Info: " + AddInfo.getDescription(shortinfo2));
				layout.addComponent(showAdditionalInfo2);
			}
			if (fe_associationDTO.getAdditionalinformationstrue().size()>2){
				String shortinfo3 =(String) fe_associationDTO.getAdditionalinformationstrue().get(2);
				Label showAdditionalInfo3 = new Label ("Additional Info: " + AddInfo.getDescription(shortinfo3));
				layout.addComponent(showAdditionalInfo3);
			}
			if (fe_associationDTO.getAdditionalinformationstrue().size()>3){
				String shortinfo4 =(String) fe_associationDTO.getAdditionalinformationstrue().get(3);
				Label showAdditionalInfo4 = new Label ("Additional Info: " + AddInfo.getDescription(shortinfo4));
				layout.addComponent(showAdditionalInfo4);
			}
		}


		com.vaadin.ui.Button componentChoiceButton = new com.vaadin.ui.Button("Save association persistently");
		layout.addComponent(componentChoiceButton);

		// button click event to confirm choice
		componentChoiceButton.addClickListener(new com.vaadin.ui.Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				try{
					FilePatternAssociation filePatternAssociation = FilePatternAssociation.getInstance();
					String result = filePatternAssociation.createAssociation();
					filePatternAssociation.addAdditionalInfo();
					Label showResult = new Label(result);
					layout.addComponent(showResult);
				} catch (DatabaseException dbe) {
					Notification.show("DB-Fehler", dbe.getMessage(), Notification.Type.ERROR_MESSAGE);
				} catch (Exception e) {
					Notification.show("Exception", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				}
				System.out.println("saving association data");
			}
		});

		com.vaadin.ui.Button deleteAssociationButton = new com.vaadin.ui.Button("Delete association from database");
		layout.addComponent(deleteAssociationButton);

		// button click event to confirm choice
		deleteAssociationButton.addClickListener(new com.vaadin.ui.Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				try{
					FilePatternAssociation filePatternAssociation = FilePatternAssociation.getInstance();
					String result = filePatternAssociation.deleteAssociation();
					Label showResult = new Label(result);
					layout.addComponent(showResult);
				} catch (DatabaseException dbe) {
					Notification.show("DB-Fehler", dbe.getMessage(), Notification.Type.ERROR_MESSAGE);
				} catch (Exception e) {
					Notification.show("Exception", e.getMessage(), Notification.Type.ERROR_MESSAGE);
				}
				System.out.println("saving association data");
			}
		});

		layout.setMargin(true);
		layout.setSpacing(true);
		this.addComponent(layout);
		this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
	}
}
