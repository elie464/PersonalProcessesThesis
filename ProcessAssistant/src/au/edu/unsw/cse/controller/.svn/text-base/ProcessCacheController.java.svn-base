package au.edu.unsw.cse.controller;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import au.edu.unsw.cse.controller.helper.DBAdapter;
import au.edu.unsw.cse.model.ProcessCacheBean;
import au.edu.unsw.cse.model.ProcessMappingBean;
import au.edu.unsw.cse.model.graph.Task;

public class ProcessCacheController {

	DBAdapter db;
	ProcessMappingDBController PMD;
	ProcessController PC;


	public ProcessCacheController(Context appContext, ProcessController PC) {
		super();
		this.db = new DBAdapter(appContext);
		PMD=new ProcessMappingDBController(appContext);
		this.PC = PC;
	}

	public boolean createProcessCache(int ProcessID, List<Task> tasks) {
		db.open();
		boolean result=false;
		for (Task task : tasks) {
			Log.i("ProcessCacheController",String.valueOf(task.getName()));

			ProcessCacheBean cacheBean = new ProcessCacheBean(ProcessID, task.getId());
			result=db.PCDcreateProcessCache(cacheBean)>0;
		}
		db.close();
		return result;
		
		
		
	}
	
	public boolean deleteProcessCache(int ProcessID){
		db.open();
		boolean result=db.PCDdeleteProcessCache(ProcessID);
		db.close();
		return result;
	}
	

	
	public List<Task> getAllCachedValidTasksForProcess(){
		db.open();
		List<ProcessMappingBean> processes = PMD.getProcessMappings();
		List<Task> result= new ArrayList<Task>();
		
		for (ProcessMappingBean processMappingBean : processes) {
			result.addAll(getCachedValidTasksForProcess(processMappingBean.getProcessID()));
		}
		db.close();
		return result;
	}
	
	public List<Task> getCachedValidTasksForProcess(int ProcessID){
		db.open();
		List<ProcessCacheBean> result=db.PCDgetCache(ProcessID);
		db.close();
		
		List<Task> validtasks = new ArrayList<Task>();
		//TODO Ernest
		//get  valid tasks from task id in result list
		for (ProcessCacheBean processCacheBean : result) {
			Log.i("ProcessCacheController", "Process " + processCacheBean.getProcessID() + 
					" Task " + processCacheBean.getTaskID());
			Task t = PC.getTask(processCacheBean.getProcessID(), processCacheBean.getTaskID());
			validtasks.add(t);


		}
		
		
		return validtasks;
	}
	
	public boolean updateValidTasks(int ProcessID, List<Task> tasks){
		//TODO Ernest
		//delete cache, do DFS search and update cache
		deleteProcessCache(ProcessID);
		
		db.open();
		boolean result=false;
		for (Task task : tasks) {
			Log.i("ProcessCacheController",String.valueOf(task.getName()));
			ProcessCacheBean cacheBean = new ProcessCacheBean(ProcessID, task.getId());
			result=db.PCDcreateProcessCache(cacheBean)>0;
		}
		db.close();
		return result;

	}
}
