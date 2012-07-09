package au.edu.unsw.cse.view.processes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import au.edu.unsw.cse.R;
import au.edu.unsw.cse.controller.ProcessAnalyticsDBController;
import au.edu.unsw.cse.controller.ProcessController;
import au.edu.unsw.cse.controller.ProcessSettingsDBController;
import au.edu.unsw.cse.controller.ProcessStateDBController;
import au.edu.unsw.cse.controller.helper.Helper;
import au.edu.unsw.cse.model.ProcessStateBean;
import au.edu.unsw.cse.model.graph.Process;
import au.edu.unsw.cse.model.graph.Task;

public class TasksActivity extends ListActivity {

	private final int VECTOR_TASK_POS = 1;
	private final int VECTOR_PROCESS_POS = 0;


	
	ListAdapter adapter;
	ProcessSettingsDBController settingsController;
	List<Task> tasks=new ArrayList<Task>();
	ProcessStateDBController stateController;
	ProcessController PC;
	Map<Integer, Vector<Integer>> taskRowMapping; // rowid -> taskID
	ProcessAnalyticsDBController processAnalyticsController;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//check parcel for pre-loaded processes
		Bundle b = this.getIntent().getExtras();
		List<Process> processesParcel =null;
		
		if(b!=null){
		    processesParcel = b.getParcelableArrayList("ProcessesParcel");

		}
		
		PC = new ProcessController(this, processesParcel);
		stateController = new ProcessStateDBController(getApplicationContext());
		settingsController = new ProcessSettingsDBController(getApplicationContext());
		processAnalyticsController = new ProcessAnalyticsDBController(getApplicationContext());


		refreshData();
		createListeners();

		adapter = new CustomTasksAdapter(this.getApplicationContext(), tasks, stateController, taskRowMapping, PC);		
		setListAdapter(adapter);
		registerForContextMenu(getListView());

		
	}
	
	private void createListeners() {
		
		
		getListView().setOnItemClickListener(new OnItemClickListener()
		{
			
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int pos,
					long rowid) {
				

				final CheckedTextView checkbox = (CheckedTextView) view.findViewById(R.id.tasks_taskCheckbox);
				final Button button = (Button) view.findViewById(R.id.tasks_confirmTaskButton);
				

//				checkbox.setOnClickListener(new View.OnClickListener() {
//					
//					@Override
//					public void onClick(View arg0) {
//						if(!checkbox.isChecked()){
//							checkbox.setChecked(true);
//							button.setVisibility(View.VISIBLE);
//
//						}else{
//							checkbox.setChecked(false);
//							button.setVisibility(View.GONE);
//						}
//						
//					}
//				});
//				
//				//need to return false to register for context menu
//				checkbox.setOnLongClickListener(new View.OnLongClickListener() {
//		    		
//					@Override
//					public boolean onLongClick(View v) {
//						return false;
//					}
//	
//				});
				
				//button click listener
				button.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						ProcessStateBean state = (ProcessStateBean) button.getTag();
						//Log.i("CustomExpandableListAdapter", "Proceeding to confirm task with: PID = " + state.getProcessID() + " TID = " + state.getTaskID());
						stateController.ConfirmTask(state.getProcessID(), state.getTaskID());
						//endTask
						processAnalyticsController.endTask(state.getProcessID(), state.getTaskID(), new Date());
						checkbox.setEnabled(false);
						button.setVisibility(View.GONE);
						refreshAdapter(state.getProcessID());
						getListView().setAdapter(adapter);
					}

				});
				
				if(!checkbox.isChecked()){
					checkbox.setChecked(true);
					button.setVisibility(View.VISIBLE);

				}
				
				else{
					checkbox.setChecked(false);
					button.setVisibility(View.GONE);
				}		        
			}

		});
	}

	/**
	 * Called when user clicks confirm button
	 * 
	 * @param ProcessID
	 */
	public void refreshAdapter(int ProcessID){
		PC.refreshProcesses(ProcessID);
		refreshData();
		
		adapter = new CustomTasksAdapter(this.getApplicationContext(), tasks, stateController, taskRowMapping, PC);		

	}
	
	private void refreshData() {
		
		//get tasks based on view settings
		tasks = Helper.getTasksBasedOnSettings(PC, tasks, settingsController);
		taskRowMapping = new HashMap<Integer, Vector<Integer>>();

		//map row id to taskid
		for (int i = 0; i < tasks.size(); i++) {
			Vector<Integer> v = new Vector<Integer>();
			v.add(tasks.get(i).getProcessID());
			v.add(tasks.get(i).getId());
			taskRowMapping.put(i, v);
		}
	}
	
	
	
	public void refreshScreen(){
		Intent refresh = new Intent(this, ProcessesTabActivity.class);
		Bundle b = new Bundle();
		b.putInt("tab", 1);
		refresh.putExtras(b);
		startActivity(refresh);
		this.finish();
	}
	/**
	 * Options Menu
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	      MenuInflater inflater = getMenuInflater();
	      inflater.inflate(R.menu.options_menu_tasks, menu);
	      return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
		      case R.id.option_all:
		            Toast.makeText(this, "You have chosen to view all tasks",
		                        Toast.LENGTH_SHORT).show();
		            settingsController.updateSetting(ProcessSettingsDBController.VIEW_SETTING, ProcessSettingsDBController.ALL_TASKS_VIEW);
		            refreshScreen();
		            return true;
		      case R.id.option_valid:
		            Toast.makeText(this, "You have chosen to view valid tasks",
		                        Toast.LENGTH_SHORT).show();
		            settingsController.updateSetting(ProcessSettingsDBController.VIEW_SETTING, ProcessSettingsDBController.VALID_TASKS_VIEW);
		            refreshScreen();
		            return true;
		      case R.id.option_validFuture:
		            Toast.makeText(this, "You have chosen to view valid and upcoming tasks",
		                        Toast.LENGTH_SHORT).show();
		            settingsController.updateSetting(ProcessSettingsDBController.VIEW_SETTING, ProcessSettingsDBController.VALID_FUTURE_TASKS_VIEW);
		      case R.id.option_settings:
		    	  Toast.makeText(this, "You have chosen to view the settings menu",
	                        Toast.LENGTH_SHORT).show();
		    	  refreshScreen();
		            return true;
//		      case R.id.option_sort:
//		    	  Toast.makeText(this, "You have chosen to view the sort menu",
//	                        Toast.LENGTH_SHORT).show();
		    	  
		      case R.id.option_sort_location:
		    	  Toast.makeText(this, "You have chosen to sort by location",
	                        Toast.LENGTH_SHORT).show();  
		            settingsController.updateSetting(ProcessSettingsDBController.SORT_OPTION, ProcessSettingsDBController.SORT_LOCATION);
		            refreshScreen();
		            return true;

		      case R.id.option_sort_time:
		    	  Toast.makeText(this, "You have chosen to sort by time",
	                        Toast.LENGTH_SHORT).show();  
		            settingsController.updateSetting(ProcessSettingsDBController.SORT_OPTION, ProcessSettingsDBController.SORT_TIME);
		            refreshScreen();
		            return true;


		      case R.id.option_toggleInternet:
		    	  Toast.makeText(this, "You have chosen to only view internet valid tasks",
	                        Toast.LENGTH_SHORT).show(); 
		            settingsController.updateSetting(ProcessSettingsDBController.VALID_TASKS_FILTER, ProcessSettingsDBController.INTERNET_VALID_TASKS);
		            refreshScreen();
		            return true;

		      case R.id.option_toggleTime:
		    	  Toast.makeText(this, "You have chosen to only view time valid tasks",
	                        Toast.LENGTH_SHORT).show();  
		            settingsController.updateSetting(ProcessSettingsDBController.VALID_TASKS_FILTER, ProcessSettingsDBController.TIME_VALID_TASKS);
		            refreshScreen();
		            return true;
		      
		      case R.id.option_noFilter:
		    	  Toast.makeText(this, "You have chosen to not filter the tasks",
	                        Toast.LENGTH_SHORT).show();  
		            settingsController.updateSetting(ProcessSettingsDBController.VALID_TASKS_FILTER, ProcessSettingsDBController.NO_VALID_FILTER);
		            refreshScreen();
		            return true;
		    	  
		      default:
		            return super.onOptionsItemSelected(item);
	      }
	}
	
	
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
	super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Task options");
		menu.add(0, v.getId(), 0, "Task Info");
		
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		
		//check whether ignored
	    View view = info.targetView;
		Button button = (Button) view.findViewById(R.id.tasks_confirmTaskButton);
		ProcessStateBean state = (ProcessStateBean) button.getTag();

		if(stateController.isIgnored(state.getProcessID(), state.getTaskID())){
			menu.add(0, 0, 0, "Confirm Task");
		}
		else if(!stateController.isConfirmed(state.getProcessID(), state.getTaskID())){
			if(PC.isValid(state.getProcessID(), state.getTaskID())){
				menu.add(0, 0, 0, "Ignore Task");
			}
		}
	}
	
    @Override
	public boolean onContextItemSelected(MenuItem item) {
    	
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int rowid = (int) info.id;

		int taskID = taskRowMapping.get(rowid).get(VECTOR_TASK_POS);
		int processID = taskRowMapping.get(rowid).get(VECTOR_PROCESS_POS);
    	
       	if(item.getTitle().equals("Task Info")){
       		// Find processID and taskID clicked and send Intent to TaskInfoActivity
			Intent i = new Intent();
			Bundle b = new Bundle();

			b.putInt("TID", taskID);
			b.putInt("PID", findProcessByTaskID(taskID));
			i.setClass(TasksActivity.this, TaskInfoActivity.class);
			i.putExtras(b);
			
			String title = ((TextView) info.targetView.findViewById(R.id.tasks_taskCheckbox)).getText().toString();


			Toast.makeText(this,title + ": PID " + b.getInt("PID") + " TID "+ b.getInt("TID"), 
					Toast.LENGTH_SHORT).show();
			startActivity(i);
			return true;
       	}
    	else if(item.getTitle()=="Ignore Task"){
			//Set ProcessStateController to ignored, set text to red

			
			stateController.IgnoreTask(processID, taskID, true);
			stateController.ConfirmTask(processID, taskID);
			processAnalyticsController.endTask(processID, taskID, new Date());
			refreshAdapter(processID);
			getListView().setAdapter(adapter);


		    View view = info.targetView;
			CheckedTextView cb = (CheckedTextView) view.findViewById(R.id.tasks_taskCheckbox);
			cb.setBackgroundColor(0xFFDC143C);

			return true;    	
		}
       	
		else if(item.getTitle().equals("Confirm Task")){
			stateController.IgnoreTask(processID, taskID, false);
			((ArrayAdapter<Task>) adapter).notifyDataSetChanged();

		}

       	return true;
	}

	/**
	 * given a TaskID find the ProcessID of that task in tasks
	 * 
	 * @param integer
	 * @return
	 */
	private int findProcessByTaskID(int taskid) {
		for (int i=0; i<tasks.size(); i++) {
			if(tasks.get(i).getId() == taskid){
				return tasks.get(i).getProcessID();
			}
		}
		return -1;
	}
	
	
}
