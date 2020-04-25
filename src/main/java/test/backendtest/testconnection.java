package test.backendtest;

import org.patterncontrol.vaadin.control.logic.association.FilePatternAssociation;
import org.patterncontrol.vaadin.control.service.Neo4jconnection;
import org.patterncontrol.vaadin.model.dao.AssociationDAO;
import org.patterncontrol.vaadin.model.dao.ComponentDAO;
import org.patterncontrol.vaadin.model.dao.FileDAO;
import org.patterncontrol.vaadin.model.dto.*;
import org.patterncontrol.vaadin.view.util.Util;

import java.util.ArrayList;

public class testconnection {
	public static void main(String... args) throws Exception {

		try (Neo4jconnection greeter = new Neo4jconnection("bolt://35.198.120.4:7687", "neo4j", "abc")) {
			//greeter.printGreeting( "hello, world" );
			//greeter.getUserData("Franziska Kuesters", "abc");
			/*DB_PatternDTO patterndto = DB_PatternDTO.getInstance();
			patterndto = greeter.getAllPatterns();
			ArrayList<String> testarray = patterndto.getAllPatterns();
			int i = 0;
			while(i < testarray.size()){
				System.out.println(testarray.get(i));
				i = i + 1;
			}

			DB_FileDTO filedto = DB_FileDTO.getInstance();
			filedto = greeter.getAllFilesByDev();
			ArrayList<String> testarrays = filedto.getAllFilesByDev();
			int j = 0;
			while(j < testarrays.size()){
				System.out.println(testarrays.get(j));
				j = j + 1;
			}*/

			//boolean result = testCreateAssociation("AbstractCommand","Command.class");
			//boolean result = testGetFilesByUser("Franziska Kuesters");

			//boolean a = testCheckFileAssociation("AbstractStrategy", "IStrategy.class" );
			//boolean b = testCheckFileAssociation("AbstractStrategy", "DumpActorCommand.class" );
			//System.out.println(a);
			//System.out.println(b);
			Boolean result = testDeleteAssociationProcess("ConcreteCommand", "Command.class");
			System.out.println(result);
		}
	}

	public static boolean testCreateAssociation(String patterncomponent, String file) {
		try (Neo4jconnection greeter = new Neo4jconnection("bolt://35.198.120.4:7687", "neo4j", "abc")) {
			AssociationDAO associationDAO = AssociationDAO.getInstance();
			Boolean result = associationDAO.createAssociation(patterncomponent, file);
			System.out.println(result);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public static boolean testUtil() {
		try{
			ArrayList<String> arrayList = new ArrayList<>();
			arrayList.add("\"hello\"");
			arrayList.add("\"test\"");
			Util util = new Util();
			arrayList = util.convertInput(arrayList);

			ComponentDAO componentDAO = ComponentDAO.getInstance();
			String pattern = "Command";
			DB_ComponentDTO db_componentDTO = DB_ComponentDTO.getInstance();
			db_componentDTO = componentDAO.getComponentsByPattern(pattern);
			ArrayList<String> testarray = db_componentDTO.getAllComponents();
			int i = 0;
			while (i < arrayList.size()) {
				System.out.println(arrayList.get(i));
				i = i + 1;
			}
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public static boolean testGetFilesByUser(String user){
		try (Neo4jconnection greeter = new Neo4jconnection("bolt://35.198.120.4:7687", "neo4j", "abc")) {
			FileDAO fileDao = FileDAO.getInstance();
			DB_FileDTO files = fileDao.getAllFilesByDev(user);
			System.out.println(files.getAllFiles().size());
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public static boolean testDeleteAssociationDirectly(String component, String file){
		try (Neo4jconnection greeter = new Neo4jconnection("bolt://35.198.120.4:7687", "neo4j", "abc")) {
			String result = greeter.deleteAssocation(component, file);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false ;
	}

	public static boolean testDeleteAssociationProcess(String component, String file){
		try{
			AssociationDAO associationDAO = AssociationDAO.getInstance();
			associationDAO.deleteAssociationControl(component, file);
			return true;
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false ;


	}

}
