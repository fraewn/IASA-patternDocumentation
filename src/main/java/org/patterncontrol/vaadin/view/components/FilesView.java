package org.patterncontrol.vaadin.view.components;

import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.patterncontrol.vaadin.control.logic.association.FilePatternAssociation;
import org.patterncontrol.vaadin.control.logic.login.Login;
import org.patterncontrol.vaadin.model.dto.DB_FileDTO;
import org.patterncontrol.vaadin.model.dto.FE_AssociationDTO;
import org.patterncontrol.vaadin.model.dto.FE_FileDTO;
import org.patterncontrol.vaadin.view.util.Views;

import java.io.File;
import java.util.ArrayList;

public class FilesView extends VerticalLayout implements View {
	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		this.setUp();
	}
	public void setUp() {
		FE_AssociationDTO associationDTO = FE_AssociationDTO.getInstance();
		VaadinServletService.getCurrentServletRequest().getSession().setAttribute("association_dto", associationDTO);

		Page.getCurrent().setTitle("File/Pattern Association");
		final VerticalLayout layout = new VerticalLayout();
		Label label = new Label("Files recently added to Git repository are shown down below.\n" +
				"Please select a file you want to associate with a pattern.");
		layout.addComponent(label);

		// add table as a component
		Table filetable = new Table("Recently added files");
		filetable.addContainerProperty("Files", String.class, null);

		// fill table with data from db_filesdto
		ArrayList<String> fileslist = this.getFilesForView().getAllFiles();
		for(int i=0; i < fileslist.size(); i++){
			String item = fileslist.get(i);
			filetable.addItem(new Object[]{item}, i);
		}

		// table characteristics
		filetable.setPageLength(filetable.size());
		filetable.setSelectable(true);
		filetable.setImmediate(true);

		// add table as a component
		layout.addComponent(filetable);

		// track file choice
		FE_FileDTO fe_fileDTO = FE_FileDTO.getInstance();
		filetable.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				// save item in file dto
				String item = fileslist.get((int) filetable.getValue());
				fe_fileDTO.setFileChoice(item);
			}
		});

		Button filechoiceButton = new Button("Confirm choice");
		layout.addComponent(filechoiceButton);


		// button click event to confirm choice and save selected file in association dto which is then saved as vaadin session attribute
		filechoiceButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				// get association dto from vaadin session
				FE_AssociationDTO associationDTO = (FE_AssociationDTO) VaadinServletService.getCurrentServletRequest().getSession().getAttribute("association_dto");
				// save file choice into dto
				associationDTO.setFile(fe_fileDTO.getFileChoice());
				// save dto with file choice into vaadin session
				VaadinServletService.getCurrentServletRequest().getSession().setAttribute("association_dto", associationDTO);

				// Navigate to pattern view
				UI.getCurrent().getNavigator().navigateTo(Views.PATTERNTOCOMPONENTVIEW);
				System.out.println("change view from file choice to pattern choice");
			}
		});

		// Upload associationsImport = new Upload ("Import Associations here:", receiver);

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

		layout.setMargin(true);
		layout.setSpacing(true);
		this.addComponent(layout);
		this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
	}

	public DB_FileDTO getFilesForView() {
		DB_FileDTO db_fileDTO = null;
		try {
			// db request for files is made in order to show them in a table
			db_fileDTO = DB_FileDTO.getInstance();
			FilePatternAssociation filePatternAssociation = FilePatternAssociation.getInstance();
			db_fileDTO = filePatternAssociation.getAllFiles();
			System.out.println("tries to get files from DB");
		} catch (DatabaseException dbe) {
			Notification.show("DB-Fehler", dbe.getMessage(), Notification.Type.ERROR_MESSAGE);
		} catch (Exception e) {
			Notification.show("Exception", e.getMessage(), Notification.Type.ERROR_MESSAGE);
		}
		return db_fileDTO;
	}

}
