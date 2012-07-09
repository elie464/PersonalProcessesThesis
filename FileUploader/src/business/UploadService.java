package business;

import beans.ProcessBean;
import dao.ProcessDAO;

public class UploadService {
	ProcessDAO processDAO= new ProcessDAO();
	
	
	public void addProcess(ProcessBean process){
		processDAO.addListing(process);
	}

}
