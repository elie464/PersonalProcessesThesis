package au.edu.unsw.cse.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.util.Log;
import au.edu.unsw.cse.controller.helper.FileHelper;
import au.edu.unsw.cse.controller.helper.Helper;
import au.edu.unsw.cse.controller.helper.ProcessAssistantException;
import au.edu.unsw.cse.model.ProcessCacheBean;
import au.edu.unsw.cse.model.ProcessMappingBean;
import au.edu.unsw.cse.model.ProcessMetaDataBean;
import au.edu.unsw.cse.model.ProcessStateBean;
import au.edu.unsw.cse.model.graph.Process;
import au.edu.unsw.cse.model.graph.Task;
import au.edu.unsw.cse.model.graph.Vertex;

/**
 * 
 * Controller class for manipulating process data
 * 
 * @author ernestlie
 * 
 */
public class ProcessController {

	private final int START_NODE = 1;
	private final int END_NODE = 2;
	private final int AND = 1;
	private final int OR = 2;
	private final int XOR = 3;
	private final int NO_SPLIT_JOIN = 0;

	Context appContext;
	private final static String FILELOC = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/Thesis/";
	ProcessStateDBController psc;
	List<Process> processes = new ArrayList<Process>();
	ProcessAnalyticsDBController pac;
	ProcessCacheController pcc;
	int splitJoinFlag;


	public ProcessController(Context appContext) {
		this.appContext = appContext;
		psc = new ProcessStateDBController(appContext);
		pac = new ProcessAnalyticsDBController(appContext);
		this.pcc = new ProcessCacheController(appContext, this);
		splitJoinFlag = NO_SPLIT_JOIN;

	}

	public ProcessController(Context appContext,
			List<Process> processesParcel) {
		this.appContext = appContext;
		psc = new ProcessStateDBController(appContext);
		this.processes =processesParcel;
		pac = new ProcessAnalyticsDBController(appContext);
		this.pcc = new ProcessCacheController(appContext, this);

		
		// TODO Auto-generated constructor stub
	}

	public void init() throws ProcessAssistantException {
		// get process mapping controller to get list of saved processes
		ProcessMappingDBController processMapController = new ProcessMappingDBController(
				appContext);
		List<ProcessMappingBean> processMappings = processMapController
				.getProcessMappings();

		if (processMappings == null) {
			throw new ProcessAssistantException("No processes downloaded yet");
		}

		// get list of processnames from processmappings, and convert to
		// processes
		ListIterator<ProcessMappingBean> list = processMappings.listIterator();
		List<Process> processes = new ArrayList<Process>();

		while (list.hasNext()) {

			ProcessMappingBean processMapping = list.next();

			processes
					.add(processNameToProcess(processMapping.getProcessName()));

		}
		this.processes = processes;
	}

	/**
	 * Function to list information about a process.
	 * 
	 * @param ProcessID
	 * @return
	 */
	public ProcessMetaDataBean getProcessMetaData(int ProcessID) {

		Process process = getProcess(ProcessID);
		return process.getProcessMetadata();
	}

	/**
	 * Calls the parser web service (e.g.
	 * http://mashsheet.cse.unsw.edu.au:8081/ParserService/rest/parse/1)
	 * downloads it and saves it
	 * 
	 * @param processMetaData
	 * @return
	 */
	public boolean downloadProcess(ProcessMetaDataBean processMetaData) {

		int processID = processMetaData.getProcessID();
		String URL = "http://mashsheet.cse.unsw.edu.au:8081/ParserService/rest/parse/";
		String result = "";

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(URL + processID);
		ResponseHandler<String> handler = new BasicResponseHandler();
		try {
			result = httpclient.execute(request, handler);

			String processName = processMetaData.getName();
			String fileName = processName + ".xml";

			FileHelper.saveFile(result, fileName);
			init();
			boolean result2 = addTasksToAnalyticsDB(processID, getTaskList(processID));
			boolean result3 = addTasksToCacheDB(processID, getFirstTasks(processID));
			return addTasksToStateDB(processID, getTaskList(processID));

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception error");
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();

		return false;
	}
	
	private boolean addTasksToAnalyticsDB(int processID, List<Task> tasks) {
		if (tasks == null) {
			return false;
		}

		for (Task task : tasks) {
			pac.insertTask(processID, task.getId());
		}
		
		for (Task task: getFirstTasks(processID)){
			pac.startTask(processID, task.getId(), new Date());
		}

		return true;
	}
	

	private boolean addTasksToStateDB(int processID, List<Task> tasks) {
		if (tasks == null) {
			Log.i("ProcessController","tasks is null");
			return false;
		}

		for (Task task : tasks) {
			ProcessStateBean state = new ProcessStateBean(0, processID,
					task.getId(), false, false);
			Log.i("ProcessController",String.valueOf(task.getId()));

			psc.CreateProcessState(state);
		}

		return true;
	}
	
	private boolean addTasksToCacheDB(int processID, List<Task> tasks){
		if (tasks == null) {
			Log.i("ProcessController","tasks is null");
			return false;
		}
		
		Log.i("ProcessController","Inserting in processCache");
		ProcessCacheController pcc = new ProcessCacheController(appContext, this);
		pcc.createProcessCache(processID, tasks);

		return true;
	}
	
	

	/**
	 * Get Task object given a processID and taskID
	 * 
	 * @param ProcessID
	 * @param TaskID
	 * @return
	 */
	public Task getTask(int ProcessID, int TaskID) {

		Process process = getProcess(ProcessID);
		List<Vertex> list = process.getVertices();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == TaskID) {
				return (Task) list.get(i);
			}
		}
		return null;
	}

	/**
	 * Given a processID, get a list of task objects associated with it
	 * 
	 * @param ProcessID
	 * @return
	 */
	public List<Task> getTaskList(int ProcessID) {

		Log.i("ProcessController", "getTaskLsit");

		Process process = getProcess(ProcessID);
		List<Vertex> list = process.getVertices();
		List<Task> taskList = new ArrayList<Task>();

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getClass().equals(Task.class)) {
				taskList.add((Task) list.get(i));
			}
		}

		return taskList;
	}

	/**
	 * 
	 * Get a process object saved in database given a processID
	 * 
	 * @param ProcessID
	 * @return
	 */
	public Process getProcess(int ProcessID) {

		if (processes != null) {

			for (Process p : processes) {
				if (p.getId() == ProcessID) {
					return p;
				}
			}
		}

		Log.i("ProcessController", "PC is null");
		// get process mapping controller to get list of saved processes
		ProcessMappingDBController processMapController = new ProcessMappingDBController(
				appContext);
		String processName = processMapController.getName(ProcessID);
		return processNameToProcess(processName);

	}

	/**
	 * Get all process objects saved in database
	 * 
	 * @return
	 */
	public List<Process> getAllProcesses() throws ProcessAssistantException {

		return this.processes;
	}

	public List<Process> searchProcess(String query) {

		// TODO
		return null;
	}

	/**
	 * Helper function to convert a process name to a process object Assumes
	 * process name is unique
	 * 
	 * @param processName
	 * @return
	 */
	private Process processNameToProcess(String processName) {
		long startTime = System.currentTimeMillis();
		File file = new File(FILELOC + processName + ".xml");

		Process process = new Process();
		Serializer serializer = new Persister();
		try {
			process = serializer.read(Process.class, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long finishTime = System.currentTimeMillis();

		Log.i("ProcessesActivity", "That took: " + (finishTime - startTime)
				+ " ms for deserializing");
		return process;
	}

	public List<Task> getFirstTasks(int ProcessID){
		List<Task> firstTasks = new ArrayList<Task>();
		Process p = getProcess(ProcessID);
		
		Vertex startNode = p.getVertex(START_NODE);
		
		for (Integer i : startNode.getOutvertices()) {
			Log.i("ProcessController", "start outvertices - "
					+ getTask(ProcessID, i).getName());
			firstTasks.add(getTask(ProcessID, i));
		}
		return firstTasks;
	}
	
	/**
	 * Only works to 1 degree split and join
	 * 
	 * @param task
	 */
	private void filterSplitsJoins(Task task){
	
		//look for outvertex and invertex
		//if outvertex join = and/or/xor
		//if invertex split = and/or/xor
		Log.i("ProcessController", "Task = " + task.getName());

		List<Vertex> invertices = convertToVertices(task.getProcessID(), task.getInvertices());
		List<Vertex> outvertices = convertToVertices(task.getProcessID(), task.getOutvertices());
		splitJoinFlag = NO_SPLIT_JOIN;
		//previous node
		if(invertices.size() >0){
			if(invertices.get(0).getId() != START_NODE){
					
				Task inVertex = (Task) invertices.get(0);	//assumption
	
				String split;
				
				if(inVertex.getSplitCode()!= null){
					split = inVertex.getSplitCode();
					
					if(split.equals("and")){
						splitJoinFlag = AND;
					}
					
					else if(split.equals("or")){
						splitJoinFlag = OR;
					}
					
					else if(split.equals("xor")){
						splitJoinFlag = XOR;
					}
				}
					
	
			}
		}
		
		//next node
		if(outvertices.size()>0){
			if(outvertices.get(0).getId() != END_NODE){
				
				Task outVertex = (Task) outvertices.get(0);	//assumption
				Log.i("ProcessController", "Out Task = " + outVertex.getName());

				if(outVertex.getJoinCode() != null){
					String join = outVertex.getJoinCode();
					
					if(join.equals("and")){
						splitJoinFlag = AND;
					}
					
					else if(join.equals("or")){
						splitJoinFlag = OR;
					}
					
					else if(join.equals("xor")){
						splitJoinFlag = XOR;
					}
				}
			}
		}
		
		
	}
	

	private List<Vertex> convertToVertices(int processID, List<Integer> integerVertices) {
		List<Vertex> vertices = new ArrayList<Vertex>();
		
		for (Integer taskID : integerVertices) {
			if(taskID != START_NODE && taskID != END_NODE){
				vertices.add(getTask(processID, taskID));
			}
		}

		return vertices;
	}

	/**
	 * Find valid tasks using Breadth First Search
	 * 
	 * @param ProcessID
	 * @return
	 */
	public List<Task> getValidTasksforProcess(int ProcessID) {

		List<Task> validTasks = new ArrayList<Task>();

		Log.i("ProcessController", "ProcessID  = " + ProcessID);
		Queue<Vertex> queue = new LinkedList<Vertex>();
		queue.add(getProcess(ProcessID).getVertex(1)); // add start node

		while (!queue.isEmpty()) {
			Vertex v = (Vertex) queue.element();
			Log.i("ProcessController", "removing from queue - " + v.getName());
			queue.remove();

			if (v.getClass().equals(Task.class)) {
				Task task = (Task) v;
				filterSplitsJoins(task);
				
				
				//check for splits and joins
				if(splitJoinFlag == AND){
					
					Log.i("ProcessController", "in AND clause");
					
					//don't show outvertices if neighbour nodes not completed
					if(!psc.isConfirmed(ProcessID, task.getId())){
						Log.i("ProcessController",
						"adding to queue - " + task.getName());
						validTasks.add(task);
					}
					
					//if completed all the AND tasks, get outvertices
					if(queue.isEmpty() && validTasks.isEmpty()){
						for (Integer i : task.getOutvertices()) {
							if (i != END_NODE) {
								if (!queue.contains(getTask(ProcessID, i))) {
									queue.add(getTask(ProcessID, i));
								}
							}
						}
					}
					
					
				}
				
				else if(splitJoinFlag == OR){
					//show outvertices and neighbours
					//if outvertex is completed, clear queue and jump to outvertex.outvertices
					//else if outvertex is not completed, add to queue notcompleted tasks
					Log.i("ProcessController", "in OR clause");

					List<Vertex> outvertices = convertToVertices(task.getProcessID(), task.getOutvertices());
					
					//check if outvertex exists
					if(outvertices.size()>0){

						Task outVertex = (Task) outvertices.get(0);	//assumption

						//outvertex confirmed
						if(psc.isConfirmed(ProcessID, outVertex.getId())){
							queue.clear();
							validTasks.clear();
							
							for (Integer i : outVertex.getOutvertices()) {
								if (i != END_NODE) {
									if (!queue.contains(getTask(ProcessID, i))) {
										queue.add(getTask(ProcessID, i));
									}
								}
							}
						}
						
						//outvertex not confirmed
						else{
							if (psc.isConfirmed(ProcessID, task.getId())) {
								Log.i("ProcessController",
										"task confirmed - " + task.getName());
			
								
								for (Integer i : task.getOutvertices()) {
									if (i != END_NODE) {
										if (!queue.contains(getTask(ProcessID, i))) {
											queue.add(getTask(ProcessID, i));
										}
									}
								}
							} else {
								Log.i("ProcessController",
										"adding to queue - " + task.getName());
								validTasks.add(task);
							}
						}
					}
					
					//if there is no outvertex
					else{
						Log.i("ProcessController",
								"adding to queue - " + task.getName());
						if(!psc.isConfirmed(ProcessID, task.getId())){
							validTasks.add(task);
						}
					}
				}
				
				//check for splits and joins
				else if(splitJoinFlag == XOR){
					//show outvertices only as soon as 1 of the neighbour nodes completed
				
					Log.i("ProcessController", "in XOR clause");

					//empty neighbouring nodes if 1 of them is confirmed
					if(psc.isConfirmed(ProcessID, task.getId())){
						Log.i("ProcessController", "emptying queue");

						queue.clear();
						validTasks.clear();
						Log.i("ProcessController", "size of queue = " + queue.size());

						for (Integer i : task.getOutvertices()) {
							if (i != END_NODE) {
								if (!queue.contains(getTask(ProcessID, i))) {
									queue.add(getTask(ProcessID, i));
								}
							}
						}
					}
					
					else {
						Log.i("ProcessController",
								"adding to queue - " + task.getName());
						validTasks.add(task);
					}
				}
				
				//no split or join code
				else{
					if (psc.isConfirmed(ProcessID, task.getId())) {
						Log.i("ProcessController",
								"task confirmed - " + task.getName());
	
						
						for (Integer i : task.getOutvertices()) {
							if (i != END_NODE) {
								if (!queue.contains(getTask(ProcessID, i))) {
									queue.add(getTask(ProcessID, i));
								}
							}
						}
					} else {
						Log.i("ProcessController",
								"adding to queue - " + task.getName());
						validTasks.add(task);
					}
					
				}
			}

			else if (v.getId() == START_NODE) {

				for (Integer i : v.getOutvertices()) {
					Log.i("ProcessController", "start outvertices - "
							+ getTask(ProcessID, i).getName());
					queue.add(getTask(ProcessID, i));
				}
			}

		}
		return validTasks;
	}

	
	/**
	 * 
	 * Find all valid tasks
	 * 
	 * @return
	 */
	public List<Task> getAllValidTasks() {
		List<Process> processes = null;
		try {
			processes = getAllProcesses();
		} catch (ProcessAssistantException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (processes != null) {
			List<Task> tasks = new ArrayList<Task>();
			List<Task> valid = new ArrayList<Task>();

			for (Process process : processes) {
				tasks = getValidTasksforProcess(process.getId());
				for (Task task : tasks) {
					valid.add(task);

				}
			}
			return valid;
		}
		return new ArrayList<Task>();
	}
	
	/**
	 * returns whether a task is valid or not
	 * 
	 * @param taskID
	 * @return
	 */
	public boolean isValid(int processID, int taskID){

//		Task task = getTask(processID, taskID);
		List<Task> validtasks = pcc.getCachedValidTasksForProcess(processID);
		
		for (Task t : validtasks) {
			if(t.getId() == taskID){
				return true;
			}
		}
		return false;
	}
	

	public List<Task> sortTasksbyLocation(List<Task> tasks) {
		LocationManager lm = (LocationManager) appContext
				.getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getProviders(true);

		/*
		 * Loop over the array backwards, and if you get an accurate location,
		 * then break out the loop
		 */
		Location l = null;
		Log.i("Process Controller", "providers is "+providers.size());
		int j=-1;
		for (int i = providers.size() - 1; i >= 0; i--) {
			l = lm.getLastKnownLocation(providers.get(i));
			j=i;
			if (l != null)
				break;
		}
		
		Log.i("Process Controller", "i is "+j);

		if (l == null) {
			l = new Location(lm.NETWORK_PROVIDER);
			Log.i("Process Controller", "current is null");
		}
		Log.i("Process Controller",
				"current is " + l.getLatitude() + " " + l.getLongitude());

		List<Task> noLocTasks = new ArrayList<Task>();
		List<Task> LocTasks = new ArrayList<Task>();

		Location current = new Location(l);

		for (Task task : tasks) {
			if (task.getLatitude() != 0.0 && task.getLongtitude() != 0.0) {
				LocTasks.add(task);
			} else {
				noLocTasks.add(task);
			}
		}

		List<Task> sortedTasks = Helper.sortLocationTasksByLocation(LocTasks,
				current);
		sortedTasks.addAll(noLocTasks);

		return sortedTasks;
	}
	public  Location getCurrentLocation(){
		LocationManager lm = (LocationManager) appContext
				.getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getProviders(true);

		/*
		 * Loop over the array backwards, and if you get an accurate location,
		 * then break out the loop
		 */
		Location l = null;

		for (int i = providers.size() - 1; i >= 0; i--) {
			l = lm.getLastKnownLocation(providers.get(i));
			if (l != null)
				break;
		}
		if (l == null) {
			l = new Location(lm.NETWORK_PROVIDER);

			Log.i("Process Controller", "current is null");
		}
		Log.i("Process Controller",
				"current is " + l.getLatitude() + " " + l.getLongitude());

		Location current = new Location(l);
		return current;
	}
	
	
	public int getDaysToTaskEndDate(Task task) throws ProcessAssistantException{
		//TODO: Prash
		if(!task.hasEndDate()){
			throw new ProcessAssistantException("No date for task");
		}
		Date current = new Date();
		 		
		return (int)( (task.getEndDate().getTime() -current.getTime()) / (1000 * 60 * 60 * 24));
	}


	// Check for -1 (no distance)
	public int getDistanceToTaskInMetres(Task task) {
		LocationManager lm = (LocationManager) appContext
				.getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getProviders(true);

		/*
		 * Loop over the array backwards, and if you get an accurate location,
		 * then break out the loop
		 */
		Location l = null;

		for (int i = providers.size() - 1; i >= 0; i--) {
			l = lm.getLastKnownLocation(providers.get(i));
			if (l != null)
				break;
		}
		if (l == null) {
			l = new Location(lm.NETWORK_PROVIDER);

			Log.i("Process Controller", "current is null");
		}
		Log.i("Process Controller",
				"current is " + l.getLatitude() + " " + l.getLongitude());

		Location current = new Location(l);
		Float result = Helper.distanceToTask(task, current);
		if (result != null) {
			return Math.round(Helper.distanceToTask(task, current));
		} else
			return -1;

	}

	public List<Task> sortTasksbyTime(List<Task> tasks) {

		return Helper.sortTasksbyTime(tasks);
	}

	public List<Task> getInternetValidTasks(List<Task> tasks) {
		List<Task> nonInternetTasks = new ArrayList<Task>();
		ConnectivityManager cm = (ConnectivityManager) appContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null) {
			if (cm.getActiveNetworkInfo().isAvailable()) {
				return tasks;
			}
		} else {
			for (Task task : tasks) {
				if (!task.isInternet()) {
					nonInternetTasks.add(task);
				}
			}
		}
		return nonInternetTasks;

	}

	public List<Task> getAllTasks() {
		List<Task> tasks = new ArrayList<Task>();
		List<Process> processes = new ArrayList<Process>();
		try {
			processes = getAllProcesses();
		} catch (ProcessAssistantException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Process process : processes) {
			tasks.addAll(getTaskList(process.getId()));
		}
		return tasks;
	}
	
	/**
	 * 
	 * display ALL valid + upcoming tasks(tasksactivity)
	 * 
	 * @return
	 */
	public List<Task> getAllValidFutureTasks(){
		List<Task> validfuturetasks = new ArrayList<Task>();
		List<Task> alltasks = getAllTasks();
		for (Task task : alltasks) {
			if(!psc.isConfirmed(task.getProcessID(), task.getId())){
				validfuturetasks.add(task);
			}
		}
		
		return validfuturetasks;
	}
	
	public List<Task> getValidFutureTasksForProcess(int processID){
		List<Task> validfuturetasks = new ArrayList<Task>();
		List<Task> tasklist = getTaskList(processID);
		for (Task task : tasklist) {
			if(!psc.isConfirmed(task.getProcessID(), task.getId())){
				validfuturetasks.add(task);
			}
		}
		
		return validfuturetasks;
	}
	
	/**
	 * 
	 * 
	 * @param ProcessID
	 * @return
	 */
	public boolean isProcessFinished(int ProcessID){
		List<Task> updated_tasks = getValidTasksforProcess(ProcessID);
		if(updated_tasks.isEmpty()){
			return true;
		}
		
		return false;
	}


	public void refreshProcesses(int ProcessID) {
		// call get valid tasks
		// check if process is empty
		// if empty, remove from processes
		// remove from mappings database
		// return new list of processes


		List<Task> updated_tasks = getValidTasksforProcess(ProcessID);
		pcc.updateValidTasks(ProcessID, updated_tasks);

		//start updated_tasks
		for (Task task : updated_tasks) {
			pac.startTask(ProcessID, task.getId(), new Date());
		}
		if (updated_tasks.isEmpty()) {
			Log.i("ProcessesActivity", "syncing analytics");

			pac.syncAnalytics(ProcessID,getTaskList(ProcessID));
			Log.i("ProcessesActivity", "deleting analytics");

			pac.deleteAnalytics(ProcessID);
			processes.remove(getProcessPos(ProcessID));
			ProcessMappingDBController mapping = new ProcessMappingDBController(
					appContext);
			mapping.deleteProcessMap(ProcessID);
			psc.DeleteProcessState(ProcessID);
			pcc.deleteProcessCache(ProcessID);
			
		}
		
	}

	private int getProcessPos(int ProcessID) {

		for (int i = 0; i < processes.size(); i++) {
			if (processes.get(i).getId() == ProcessID) {
				return i;
			}
		}
		return -1;
	}

	public List<Task> getTimeValidTasks(List<Task> tasks) {
		// TODO Auto-generated method stub
		return Helper.getTimeValidTasks(tasks);
	}

	public void updateRating(int ProcessID, float rating) {
		Log.i("ProcessController", "rating process");
		pac.rateProcess(ProcessID, Math.round(rating));
	}
	
	public void updateComments(int ProcessID, String comments) {
		Log.i("ProcessController", "commenting process");
		pac.CommentProcess(ProcessID, comments);
	}

}
