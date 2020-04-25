package org.patterncontrol.vaadin.control.service;


import com.vaadin.server.VaadinServletService;
import org.neo4j.driver.v1.*;
import org.patterncontrol.vaadin.model.dto.*;

import java.util.ArrayList;

import static org.neo4j.driver.v1.Values.parameters;

public class Neo4jconnection implements AutoCloseable{
	private Driver driver;
	public Neo4jconnection(String uri, String user, String password){
		driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
	}

	public void close() throws Exception{
		driver.close();
	}

	public Driver getDriver(){
		return driver;
	}


	// get Username and Password
	public DB_UserDTO getUserData(final String username, final String password){
		try ( Session session = driver.session() )
		{
			String result = session.writeTransaction( new TransactionWork<String>()
			{
				@Override
				public String execute( Transaction tx )
				{
					StatementResult result = tx.run( "MATCH(a:Developer {devname:$username, password:$password}) RETURN a.devname",
							parameters("username", username, "password", password) );

					return result.single().get(0).asString();
				}
			} );
			DB_UserDTO userdto = DB_UserDTO.getInstance();
			userdto.setUsername(result);
			return userdto;
		}
	}
	//first try
	public void exportAssosiations (){
		try (Session session = driver.session()){

		}
	}

	// get all patterns
	public DB_PatternDTO getAllPatterns(){
		try ( Session session = driver.session() )
		{
			ArrayList<String> resultArray = session.writeTransaction( new TransactionWork<ArrayList<String>>()
			{
				@Override
				public ArrayList<String> execute(Transaction tx ) {
					StatementResult result = tx.run("MATCH(a:Pattern)" +
									"RETURN a.patternname",
							parameters());
					ArrayList<String> resultArray = new ArrayList<>();
					while(result.hasNext()){
						Record record = result.next();
						resultArray.add(record.get(0).toString());
					}
					return resultArray;
				}
			} );


			DB_PatternDTO patterndto = DB_PatternDTO.getInstance();
			patterndto.setAllPatterns(resultArray);
			return patterndto;
		}
	}

	// get all files
	public DB_FileDTO getAllFilesByDev(String username){
		try ( Session session = driver.session() )
		{
			ArrayList<String> resultArray = session.writeTransaction( new TransactionWork<ArrayList<String>>()
			{
				@Override
				public ArrayList<String> execute(Transaction tx ) {
					StatementResult result = tx.run("MATCH(a:File)--(b:Developer {devname:$dev}) return a.filename",
							parameters("dev", username));
					ArrayList<String> resultArray = new ArrayList<>();
					while(result.hasNext()){
						Record record = result.next();
						resultArray.add(record.get(0).toString());
					}
					return resultArray;
				}
			} );


			DB_FileDTO filedto = DB_FileDTO.getInstance();
			filedto.setAllFiles(resultArray);
			return filedto;
		}
	}


	// get all patterns
	public DB_ComponentDTO getComponentsByPattern (String pattern){
		try ( Session session = driver.session() )
		{
			ArrayList<String> resultArray = session.writeTransaction( new TransactionWork<ArrayList<String>>()
			{
				@Override
				public ArrayList<String> execute(Transaction tx ) {
					StatementResult result = tx.run("MATCH(a:Pattern {patternname:'" + pattern + "'})--(b:Patterncomponent)RETURN b.componentname");
					ArrayList<String> resultArray = new ArrayList<>();
					while(result.hasNext()){
						Record record = result.next();
						resultArray.add(record.get(0).toString());
					}
					return resultArray;
				}
			} );

			DB_ComponentDTO componentdto = DB_ComponentDTO.getInstance();
			componentdto.setAllComponents(resultArray);
			return componentdto;
		}
	}

	public Boolean  createAssociation(String component, String file){
		try ( Session session = driver.session() )
		{

			Boolean result = session.writeTransaction( new TransactionWork<Boolean>()
			{
				@Override
				public Boolean execute(Transaction tx ) {
					StatementResult result = tx.run("MATCH (p:Patterncomponent {componentname:$componentname}), (f:File {filename:$filename}) " +
									"MERGE (f)-[r:`ASSOCIATED-WITH`]->(p) " +
									"ON CREATE SET r.alreadyExisted=false " +
									"ON MATCH SET r.alreadyExisted=true " +
									"RETURN r.alreadyExisted",
									parameters("filename", file, "componentname", component));
					return result.single().get(0).asBoolean() ;
				}
			} );
			return result;
		}
	}

	public void  addAdditionalInfo(String file, String info){
		try ( Session session = driver.session() )
		{


			String command = "MATCH (n:File {filename:'" +file + "'}) set n." +info+ " = true RETURN n." + info;
				session.writeTransaction( new TransactionWork<Boolean>()
			{
				@Override
				public Boolean execute(Transaction tx ) {
					StatementResult result = tx.run(command);
					return result.single().get(0).asBoolean() ;
				}
			} );

		}
	}
	public void  addAdditionalInfoFalse(String file, String info){
		try ( Session session = driver.session() )
		{


			String command = "MATCH (n:File {filename:'" +file + "'}) set n." +info+ " = false RETURN true" ;
			session.writeTransaction( new TransactionWork<Boolean>()
			{
				@Override
				public Boolean execute(Transaction tx ) {
					StatementResult result = tx.run(command);
					return result.single().get(0).asBoolean() ;
				}
			} );

		}
	}


	public boolean deleteAssociationControl(String component, String file){
		// alreadyExisted==false, meaning relationship did not exist before
		// it must be created to be deleted safely, else the result is going to be empty and cannot be checked
		if(createAssociation(component, file)==false){
			// after not existing relationship was created, it is deleted again
			String result = deleteAssocation(component, file);
			// return false because it did not exist before
			return false;
		}
		// relationship did exist, save delete possible
		deleteAssocation(component, file);
		// return true because relationship did exist
		return true;
	}
	public String deleteAssocation(String component, String file){
		try ( Session session = driver.session() )
		{
			String result = session.writeTransaction( new TransactionWork<String>()
			{
				@Override
				public String execute(Transaction tx ) {
					StatementResult result = tx.run("match (a:Patterncomponent {componentname:$component})-[r:`ASSOCIATED-WITH`]-(b:File {filename:$file}) " +
									"delete r return 'successfully deleted'",
							parameters("file", file, "component", component));
					System.out.println(result.single().get(0).asString());
					return "association deleted succesfully";
				}
			} );
			return "association deleted succesfully";
		}

	}



}
