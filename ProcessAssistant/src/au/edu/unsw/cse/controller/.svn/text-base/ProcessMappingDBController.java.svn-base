package au.edu.unsw.cse.controller;

import java.util.List;

import android.content.Context;
import android.util.Log;
import au.edu.unsw.cse.controller.helper.DBAdapter;
import au.edu.unsw.cse.controller.helper.Helper;
import au.edu.unsw.cse.model.ProcessMappingBean;

public class ProcessMappingDBController {

	DBAdapter db;
	
	
	
	
	
	
	public ProcessMappingDBController(Context appContext) {
		super();
		db = new DBAdapter(appContext);
	}

	public boolean createProcessMap(int PID,String Name){
		
		db.open();
		boolean result =db.PMDcreateProcessMap(PID, Name)>0;
		db.close();
		Log.i("ProcessMappingDBController", "PID="+PID+" Name="+Name);
		return result;
	}
	
	public boolean deleteProcessMap (int PID){
		db.open();
		Log.i("ProcessMappingDBController", "deleting PID="+PID);
		boolean result =db.PMDdeleteProcessMap(PID);
		db.close();
		
		return result;
	}
	
	public String getName(int PID){
		db.open();
		String result =db.PMDgetName(PID);
		db.close();
		return result;
	}
	
	public List<ProcessMappingBean> getProcessMappings(){
		db.open();
		List<ProcessMappingBean> results =db.PMDgetProcessMappings();
		db.close();
		return results;
	}
	public boolean processMapExists(int PID){
		db.open();
		String result =db.PMDgetName(PID);
		db.close();
		return (result!=null);
	}
}
