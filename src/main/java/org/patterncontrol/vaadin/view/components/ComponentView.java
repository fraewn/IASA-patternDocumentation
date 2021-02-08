package org.patterncontrol.vaadin.view.components;

import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinServletService;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.patterncontrol.vaadin.control.logic.login.Login;
import org.patterncontrol.vaadin.model.dao.ComponentDAO;
import org.patterncontrol.vaadin.model.dto.*;
import org.patterncontrol.vaadin.view.util.Views;

import java.util.ArrayList;

public class ComponentView extends VerticalLayout implements View{
	@Override
	public void enter(ViewChangeListener.ViewChangeEvent event) {
		this.setUp();
	}
	public void setUp(){
		Page.getCurrent().setTitle("File/Pattern Association");
		final VerticalLayout layout = new VerticalLayout();

		Button goBackButton = new Button("Go to previous page");
		layout.addComponent(goBackButton);
		goBackButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				// Navigate to login view
				UI.getCurrent().getNavigator().navigateTo(Views.PATTERNTOCOMPONENTVIEW);
				System.out.println("Navigate to pattern view");
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

		Label label = new Label("Please select a pattern component you want to associate with the file you just chose.");
		layout.addComponent(label);

		FE_AssociationDTO fe_associationDTO = (FE_AssociationDTO) VaadinServletService.getCurrentServletRequest().getSession().getAttribute("association_dto");

		Label showSelectedFile = new Label("Selected File: " + fe_associationDTO.getFile());
		layout.addComponent(showSelectedFile);

		Label showSelectedPattern = new Label("Selected Pattern: " + fe_associationDTO.getPattern());
		layout.addComponent(showSelectedPattern);

		// add table as a component
		Table componenttable = new Table("Components");
		componenttable.addContainerProperty("Components", String.class, null);

		// get components from database
		ArrayList<String> componentList = this.getComponentsForView().getAllComponents();

		// fill table with components from database
		for(int i=0; i < componentList.size(); i++){
			String item = componentList.get(i);
			componenttable.addItem(new Object[]{item}, i);
		}

		// table characteristics
		componenttable.setPageLength(componenttable.size());
		componenttable.setSelectable(true);
		componenttable.setImmediate(true);

		// add table as a component
		layout.addComponent(componenttable);

		//Items for additional Information Table
		Table additionalinformation = new Table ("Additional Informations");
		additionalinformation.addContainerProperty("Question", String.class, null);
		additionalinformation.addContainerProperty("Answer", CheckBox.class, null);
		String sichtbarkeit = "Is the visibility of the constructor private?";
		String klassenattribut = "Does the class own a private static variable to save the Singleton object?";
		String getterMethode = "Does the class own a static getter-method to return the Singleton object?";
		CheckBox checkBoxSichtbarkeit =new CheckBox();
		CheckBox checkBoxKlassenattribut = new CheckBox();
		CheckBox checkBoxGetterMethode = new CheckBox();
		String aufrufCreateMethod = "Does the class call a create method from the DAO?";
		CheckBox checkBoxAufrufCreateMethod = new CheckBox();
		String aufrufReadMethod = "Does the class call a read method from the DAO?";
		CheckBox checkBoxAufrufReadMethod = new CheckBox();
		String equalsMethodDAO = "Does the class implement a equals method?";
		CheckBox checkBoxEqualsMethodDAO = new CheckBox();
		String synchro = "Is the class set to synchronized?";
		CheckBox checkBoxSynchro = new CheckBox();

		// New component frontend dto
		FE_ComponentDTO fe_componentDTO = FE_ComponentDTO.getInstance();

		Button componentChoiceButton = new Button("Confirm choice");
		//layout.addComponent(componentChoiceButton);

		componenttable.addValueChangeListener(new Property.ValueChangeListener() {
			@Override
			public void valueChange(Property.ValueChangeEvent event) {
				// get selected item from table
				String item = componentList.get((int) componenttable.getValue());
				layout.removeComponent(additionalinformation);
				layout.removeComponent(componentChoiceButton);
				//cleanup table
				additionalinformation.removeAllItems();
				if (item.equals("SingletonClass")){
					additionalinformation.addItem(new Object [] {sichtbarkeit, checkBoxSichtbarkeit}, 0);
					additionalinformation.addItem(new Object [] {klassenattribut, checkBoxKlassenattribut}, 1);
					additionalinformation.addItem(new Object [] {getterMethode, checkBoxGetterMethode}, 2);
					additionalinformation.addItem(new Object [] {synchro, checkBoxSynchro}, 3);
					additionalinformation.setPageLength(additionalinformation.size());
					layout.addComponent(additionalinformation);
					layout.addComponent(componentChoiceButton);
				}
				else if (item.equals("TestClass")){
					additionalinformation.addItem(new Object [] {aufrufCreateMethod, checkBoxAufrufCreateMethod}, 0);
					additionalinformation.addItem(new Object [] {aufrufReadMethod, checkBoxAufrufReadMethod}, 1);
					additionalinformation.setPageLength(additionalinformation.size());

					layout.addComponent(additionalinformation);
					layout.addComponent(componentChoiceButton);
				}
				else if (item.equals("CompareClass")){
					additionalinformation.addItem(new Object [] {equalsMethodDAO, checkBoxEqualsMethodDAO}, 0);
					additionalinformation.setPageLength(additionalinformation.size());
					layout.addComponent(additionalinformation);
					layout.addComponent(componentChoiceButton);
				}
				else {
					layout.removeComponent(additionalinformation);
					layout.addComponent(componentChoiceButton);
				}


			}
		});



		// button click event to confirm choice
		componentChoiceButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent event) {
				//FilePatternAssociation.fromPatternsToComponents(fe_patternDTO);
				System.out.println("change view from component choice to final choice");
				String item = componentList.get((int) componenttable.getValue());
				// write item in component dto
				fe_componentDTO.setComponentChoice(item);
				// get association dto from vaadin session
				FE_AssociationDTO associationDTO = (FE_AssociationDTO) VaadinServletService.getCurrentServletRequest().getSession().getAttribute("association_dto");
				//clean up of additional infos
				associationDTO.cleanAdditionalinformations();
				// add additional info to dto
				if (item.equals("TestClass")){
					if (checkBoxAufrufReadMethod.getValue()){
						associationDTO.setAdditionalinformationstrue("calldaoread");
					}
					else {
						associationDTO.setAdditionalinformationsfalse("calldaoread");
					}
					if (checkBoxAufrufCreateMethod.getValue()){
						associationDTO.setAdditionalinformationstrue("calldaocreate");
					}
					else {
						associationDTO.setAdditionalinformationsfalse("calldaocreate");
					}
				}
				else if (item.equals("CompareClass")){
					if (checkBoxEqualsMethodDAO.getValue()){
						associationDTO.setAdditionalinformationstrue("implementsequalsmethod");
					}
					else {
						associationDTO.setAdditionalinformationsfalse("implementsequalsmethod");
					}
				}

				else if (item.equals("SingletonClass")){
					if (checkBoxSichtbarkeit.getValue()){
						associationDTO.setAdditionalinformationstrue("constructorprivate");
					}
					else {
						associationDTO.setAdditionalinformationsfalse("constructorprivate");
					}
					if (checkBoxKlassenattribut.getValue()){
						associationDTO.setAdditionalinformationstrue("privatestaticclassvar");
					}
					else {
						associationDTO.setAdditionalinformationsfalse("privatestaticclassvar");
					}
					if (checkBoxGetterMethode.getValue()){
						associationDTO.setAdditionalinformationstrue("gettermethod");
					}
					else {
						associationDTO.setAdditionalinformationsfalse("gettermethod");
					}
					if (checkBoxSynchro.getValue()){
						associationDTO.setAdditionalinformationstrue("synchronized");
					}
					else {
						associationDTO.setAdditionalinformationsfalse("synchronized");
					}
				}




				// save file choice into dto
				associationDTO.setComponent(fe_componentDTO.getComponentChoice());
				// save dto with file choice into vaadin session
				VaadinServletService.getCurrentServletRequest().getSession().setAttribute("association_dto", associationDTO);

				System.out.println("test: " + item);
				UI.getCurrent().getNavigator().navigateTo(Views.CONFIRMVIEW);
			}
		});

		layout.setMargin(true);
		layout.setSpacing(true);
		this.addComponent(layout);
		this.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
	}

	public DB_ComponentDTO getComponentsForView() {
		try {
			// get selected pattern from vaadin session attribute saved in association dto
			FE_AssociationDTO fe_associationDTO = (FE_AssociationDTO) VaadinServletService.getCurrentServletRequest().getSession().getAttribute("association_dto");
			String pattern = fe_associationDTO.getPattern();
			// get components for pattern from database
			ComponentDAO componentDAO = ComponentDAO.getInstance();
			DB_ComponentDTO db_componentDTO = componentDAO.getComponentsByPattern(pattern);
			// return dto with components
			return db_componentDTO;
		} catch (DatabaseException dbe) {
			Notification.show("DB-Fehler", dbe.getMessage(), Notification.Type.ERROR_MESSAGE);
		} catch (Exception e) {
			Notification.show("Exception", e.getMessage(), Notification.Type.ERROR_MESSAGE);
		}
		return null;
	}
}
