package org.patterncontrol.vaadin.view.components;

import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.patterncontrol.vaadin.control.logic.association.FilePatternAssociation;
import org.patterncontrol.vaadin.control.logic.login.Login;
import org.patterncontrol.vaadin.model.dto.DB_PatternDTO;
import org.patterncontrol.vaadin.model.dto.FE_AssociationDTO;
import org.patterncontrol.vaadin.model.dto.FE_PatternDTO;
import org.patterncontrol.vaadin.view.util.Views;

import java.util.ArrayList;

public class PatternView extends VerticalLayout implements View {
	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		this.setUp();
	}
	public void setUp() {
		Page.getCurrent().setTitle("File/Pattern Association");
		final VerticalLayout layout = new VerticalLayout();

		Button goBackButton = new Button("Go to previous page");
		layout.addComponent(goBackButton);
		goBackButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				// Navigate to login view
				UI.getCurrent().getNavigator().navigateTo(Views.FILETOPATTERNVIEW);
				System.out.println("navigate to pattern view");
			}
		});

		Label label = new Label("Please select a pattern you want to associate with the file you just chose.");
		layout.addComponent(label);

		FE_AssociationDTO fe_associationDTO = (FE_AssociationDTO) VaadinServletService.getCurrentServletRequest().getSession().getAttribute("association_dto");
		Label showSelectedFile = new Label("Selected File: " + fe_associationDTO.getFile());
		layout.addComponent(showSelectedFile);

		// add table as a component
		Table patterntable = new Table("Patterns");
		patterntable.addContainerProperty("Patterns", String.class, null);

		// fill table with data from db_filesdto
		ArrayList<String> patternList = this.getPatternsForView().getAllPatterns();
		for(int i=0; i < patternList.size(); i++){
			String item = patternList.get(i);
			patterntable.addItem(new Object[]{item}, i);
		}

		// table characteristics
		patterntable.setPageLength(patterntable.size());
		patterntable.setSelectable(true);
		patterntable.setImmediate(true);

		// add table as a component
		layout.addComponent(patterntable);

		// track pattern choice
		FE_PatternDTO fe_patternDTO = FE_PatternDTO.getInstance();
		patterntable.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				// save item in pattern dto
				String item = patternList.get((int) patterntable.getValue()).toString();
				fe_patternDTO.setPatternChoice(item);
			}
		});

		// Add button to confirm pattern choice
		Button patternChoiceButton = new Button("Confirm choice");
		layout.addComponent(patternChoiceButton);

		// button click event to confirm choice and save data persistently
		patternChoiceButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				// get association dto from vaadin session
				FE_AssociationDTO associationDTO = (FE_AssociationDTO) VaadinServletService.getCurrentServletRequest().getSession().getAttribute("association_dto");
				// save pattern choice into dto
				associationDTO.setPattern(fe_patternDTO.getPatternChoice());
				// save dto with file choice into vaadin session
				VaadinServletService.getCurrentServletRequest().getSession().setAttribute("association_dto", associationDTO);

				// Navigate to component view
				UI.getCurrent().getNavigator().navigateTo(Views.COMPONENTVIEW);
				System.out.println("change view from file choice to pattern choice");
			}
		});

		layout.setMargin(true);
		layout.setSpacing(true);
		this.addComponent(layout);
		this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
	}

	public DB_PatternDTO getPatternsForView() {
		DB_PatternDTO db_patternDTO = null;
		try {
			db_patternDTO = DB_PatternDTO.getInstance();
			System.out.println("tries to get files from DB");
			// db request for files is made in order to show them in a table
			FilePatternAssociation filePatternAssociation = FilePatternAssociation.getInstance();
			db_patternDTO = filePatternAssociation.getAllPatterns();

		} catch (DatabaseException dbe) {
			Notification.show("DB-Fehler", dbe.getMessage(), Notification.Type.ERROR_MESSAGE);
		} catch (Exception e) {
			Notification.show("Exception", e.getMessage(), Notification.Type.ERROR_MESSAGE);
		}
		return db_patternDTO;
	}
}
