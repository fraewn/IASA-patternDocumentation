package org.patterncontrol.vaadin.control.logic.association;

import com.vaadin.server.VaadinServletService;
import org.neo4j.driver.v1.exceptions.DatabaseException;
import org.patterncontrol.vaadin.model.dao.AssociationDAO;
import org.patterncontrol.vaadin.model.dao.ComponentDAO;
import org.patterncontrol.vaadin.model.dao.FileDAO;
import org.patterncontrol.vaadin.model.dao.PatternDAO;
import org.patterncontrol.vaadin.model.dto.*;
import org.patterncontrol.vaadin.view.util.Roles;

import javax.xml.crypto.Data;
import java.util.List;

public class FilePatternAssociation {
	// Singleton
	private static FilePatternAssociation instance = null;
	private FilePatternAssociation(){}
	public static FilePatternAssociation getInstance(){
		if(instance == null){
			instance = new FilePatternAssociation();
		}
		return instance;
	}

	public DB_FileDTO getAllFiles() throws Exception, DatabaseException{
		FileDAO filedao = FileDAO.getInstance();
		String user = (String) VaadinServletService.getCurrentServletRequest().getSession().getAttribute(Roles.CURRENTUSER);
		DB_FileDTO db_filedto = DB_FileDTO.getInstance();
		db_filedto = filedao.getAllFilesByDev(user);
		return db_filedto;
	}
	public DB_PatternDTO getAllPatterns() throws Exception, DatabaseException{
		PatternDAO patternDAO = PatternDAO.getInstance();
		DB_PatternDTO patternDTO = DB_PatternDTO.getInstance();
		patternDTO = patternDAO.getAllPatterns();
		return patternDTO;
	}
	public DB_ComponentDTO getComponents(String pattern) throws Exception, DatabaseException{
		ComponentDAO componentDAO = ComponentDAO.getInstance();
		return componentDAO.getComponentsByPattern(pattern);
	}
	public String createAssociation() throws Exception, DatabaseException{
		FE_AssociationDTO fe_associationDTO = (FE_AssociationDTO) VaadinServletService.getCurrentServletRequest().getSession().getAttribute("association_dto");
		String file = fe_associationDTO.getFile();
		String component = fe_associationDTO.getComponent();
		AssociationDAO associationDAO = AssociationDAO.getInstance();
		// relationship already existed is false
		if(associationDAO.createAssociation(component, file)==false){
			return "file '" + file + "' was associated with pattern component '" + component + "'";
		}
		// relationship already existed is true
		return "association already exists, no changes made" ;
	}
	public void addAdditionalInfo() throws Exception, DatabaseException{
		FE_AssociationDTO fe_associationDTO = (FE_AssociationDTO) VaadinServletService.getCurrentServletRequest().getSession().getAttribute("association_dto");
		String file = fe_associationDTO.getFile();
		List additionalinformationListtrue = fe_associationDTO.getAdditionalinformationstrue();
		List additionalinformationListfalse = fe_associationDTO.getAdditionalinformationsfalse();
		if (additionalinformationListtrue.isEmpty()==false){
			for (int i=0; i<additionalinformationListtrue.size(); i++){
				String info = (String)fe_associationDTO.getAdditionalinformationstrue().get(i);
				AssociationDAO associationDAO = AssociationDAO.getInstance();
				associationDAO.addAdditionalInfo(file, info);
			}
		}
		if (additionalinformationListfalse.isEmpty()==false){
			for (int i=0; i<additionalinformationListfalse.size(); i++){
				String info = (String)fe_associationDTO.getAdditionalinformationsfalse().get(i);
				AssociationDAO associationDAO = AssociationDAO.getInstance();
				associationDAO.addAdditionalInfoFalse(file, info);
			}
		}
		// relationship already existed is false

	}


	public String deleteAssociation() throws Exception, DatabaseException{
		FE_AssociationDTO fe_associationDTO = (FE_AssociationDTO) VaadinServletService.getCurrentServletRequest().getSession().getAttribute("association_dto");
		String file = fe_associationDTO.getFile();
		String component = fe_associationDTO.getComponent();
		AssociationDAO associationDAO = AssociationDAO.getInstance();
		if(associationDAO.deleteAssociationControl(component, file)==true){
			return "association between file '" + file + "' and component '" + component + "' was successfully deleted";
		}
		return "association did not exist, no changes made";
	}
}
