package au.edu.unsw.cse.controller;

import java.util.List;

import android.content.Context;
import android.util.Log;
import au.edu.unsw.cse.controller.helper.DBAdapter;
import au.edu.unsw.cse.controller.helper.Helper;
import au.edu.unsw.cse.model.ProcessStateBean;

public class ProcessStateDBController {

	DBAdapter db;
	
	
	public ProcessStateDBController(Context appContext) {
		super();
		db = new DBAdapter(appContext);
	}

	public boolean CreateProcessState(ProcessStateBean in){
		db.open();
		boolean result =db.PSDcreateProcessState(in.getProcessID(), in.getTaskID(), Helper.booleanToInt(in.getConfirmed()), Helper.booleanToInt(in.getIgnored()))>0;
		db.close();
		return result;
	}
	
	public boolean DeleteProcessState(int ProcessID){
		db.open();
		Log.i("ProcessStateDBController", "deleting PID="+ProcessID);
		boolean result =db.PSDdeleteProcessState(ProcessID)>0;
		
		db.close();
		return result;
	}
	
	public boolean ConfirmTask (int ProcessID, int TaskID) {
		db.open();
		boolean result =db.PSDconfirmTask(ProcessID, TaskID);
		db.close();
		return result;
	}
	
	public boolean IgnoreTask(int ProcessID, int TaskID, boolean ignore){
		db.open();
		boolean result =db.PSDignoreTask(ProcessID, TaskID, ignore);
		db.close();
		return result;
	}
	
	public List<ProcessStateBean> getTasksStates (int ProcessID){
		db.open();
		List<ProcessStateBean> results =db.PSDgetTasks(ProcessID);
		db.close();
		return results;
	}
	
	public ProcessStateBean getTaskState (int ProcessID, int TaskID){
		db.open();
		ProcessStateBean result =db.PSDgetTask(ProcessID,TaskID);
		db.close();
		return result;
		
	}
	public List<ProcessStateBean> getConfirmedTasks (int ProcessID){
		db.open();
		List<ProcessStateBean> results =db.PSDgetConfirmedTasks(ProcessID);
		db.close();
		return results;
	}
	
	public List<ProcessStateBean> getALLConfirmedTasks (){
		db.open();
		List<ProcessStateBean> results =db.PSDgetALLConfirmedTasks();
		db.close();
		return results;
	}
	public boolean processExists(int ProcessID){
		db.open();
		List<ProcessStateBean> results =db.PSDgetTasks(ProcessID);
		db.close();
		
		return (results.size()>0);
	}
	
	public boolean isConfirmed (int ProcessID, int TaskID){
		db.open();
		ProcessStateBean result =db.PSDgetTask(ProcessID,TaskID);
		db.close();
		return result.getConfirmed();
		
	}
	public boolean isIgnored (int ProcessID, int TaskID){
		db.open();
		ProcessStateBean result =db.PSDgetTask(ProcessID,TaskID);
		db.close();
		return result.getIgnored();
		
	}
	
	
}
