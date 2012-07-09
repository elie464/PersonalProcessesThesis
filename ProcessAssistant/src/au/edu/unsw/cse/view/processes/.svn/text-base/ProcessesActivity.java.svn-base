package au.edu.unsw.cse.view.processes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;
import au.edu.unsw.cse.R;
import au.edu.unsw.cse.controller.ProcessAnalyticsDBController;
import au.edu.unsw.cse.controller.ProcessCacheController;
import au.edu.unsw.cse.controller.ProcessController;
import au.edu.unsw.cse.controller.ProcessSettingsDBController;
import au.edu.unsw.cse.controller.ProcessStateDBController;
import au.edu.unsw.cse.controller.helper.Helper;
import au.edu.unsw.cse.controller.helper.ProcessAssistantException;
import au.edu.unsw.cse.model.ProcessStateBean;
import au.edu.unsw.cse.model.graph.Process;
import au.edu.unsw.cse.model.graph.Task;
import au.edu.unsw.cse.view.MainActivity;
import au.edu.unsw.cse.view.repo.RepoTabActivity;

/**
 * Demonstrates expandable lists using a custom {@link ExpandableListAdapter}
 * from {@link BaseExpandableListAdapter}.
 */
public class ProcessesActivity extends ExpandableListActivity {

	private final int RATING_SUBMITTED=1;
	private final int GET_RATING=1;


	
	ExpandableListAdapter mAdapter;
	ProcessStateDBController stateController;
	ProcessController processController;
	ProcessAnalyticsDBController processAnalyticsController;
	ProcessSettingsDBController settingsController;
	ProcessCacheController cacheController;

	String[] processesData;
	String[][] tasksData;
	Map<Integer, Integer> processRowMapping; // groupRowID -> processID
	Map<Integer, Integer> taskRowMapping; // childRowID -> taskID
	Map<Integer, Map<Integer, Integer>> masterRowMapping; // processID -> taskRowMapping

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = this.getIntent().getExtras();
		List<Process> processesParcel =null;
		
		if(b!=null){
			//Log.i("ProcessesActivity", "b is not null");
		    processesParcel = b.getParcelableArrayList("ProcessesParcel");
			//Log.i("ProcessesActivity", "size = " + processesParcel.size());
		    if(processesParcel.size() == 0){
		    	popupRepo();
		    }

		}
				
		processRowMapping = new HashMap<Integer, Integer>();
		masterRowMapping = new HashMap<Integer, Map<Integer, Integer>>();
		stateController = new ProcessStateDBController(getApplicationContext());
		processController = new ProcessController(this, processesParcel);
		processAnalyticsController = new ProcessAnalyticsDBController(getApplicationContext());
		settingsController = new ProcessSettingsDBController(getApplicationContext());
		cacheController = new ProcessCacheController(getApplicationContext(), processController);



		// Set up our adapter
		refreshData(); 
		createListeners();

		//long startTime = System.currentTimeMillis();

		mAdapter = new CustomExpandableListAdapter(this, processesData,tasksData, 
				stateController, processRowMapping, masterRowMapping, processController);
		getExpandableListView().setAdapter(mAdapter);
		//long finishTime = System.currentTimeMillis();

		//Log.i("ProcessesActivity", "That took: "+(finishTime-startTime)+ " ms for adapter");

		registerForContextMenu(getExpandableListView());

			
	}

	private void createListeners(){
		getExpandableListView().setOnChildClickListener(new OnChildClickListener()
	    {
	        
	        @Override
	        public boolean onChildClick(ExpandableListView expView, View view, int arg2, int arg3, long arg4)
	        {
				
				final CheckedTextView checkbox = (CheckedTextView) view.findViewById(R.id.taskCheckbox);
				final Button button = (Button) view.findViewById(R.id.confirmTaskButton);
//					if(!checkbox.isChecked()){
//						checkbox.setChecked(true);
//					}
				
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
						getExpandableListView().setAdapter(mAdapter);
					}
				});
				
				if(!checkbox.isChecked()){
					checkbox.setChecked(true);
					button.setVisibility(View.VISIBLE);

				}else{
					checkbox.setChecked(false);
					button.setVisibility(View.GONE);
				}
				
	            return false;
	        }
	    });
	}
	

	/**
	 * Display popup to link to repo if list is empty
	 * 
	 */
	private void popupRepo() {
		AlertDialog.Builder altDialog = new AlertDialog.Builder(this);
		altDialog.setMessage("Your current list of processes is empty. Please visit the repository and download  a process");
		altDialog.setCancelable(true);

		altDialog.setPositiveButton("Go To Repo",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(ProcessesActivity.this, RepoTabActivity.class);
						startActivity(i);
					}
				});

		altDialog.show();
	}

	
	/**
	 * Called when user clicks confirm button
	 * 
	 * @param ProcessID
	 */
	public void refreshAdapter(int ProcessID){
		
		if(processController.isProcessFinished(ProcessID)){
			Intent intent = new Intent(this, ProcessRatingCommentsActivity.class);
			intent.putExtra("pid", ProcessID);
			startActivityForResult(intent, GET_RATING);
			
		}
		else{
			Log.i("ProcessesActivity", "refreshing processes");
			processController.refreshProcesses(ProcessID);

			refreshData();
			mAdapter = new CustomExpandableListAdapter(this, processesData,tasksData, 
					stateController, processRowMapping, masterRowMapping, processController);
		}
		

	}

	
	/**
	 * This method rebuilds the processes list and task list for each process by
	 * checking what has been saved in process mapping. This is done via the
	 * Process Controller.
	 * 
	 */
	public boolean refreshData() {

		//long startTimeWhole = System.currentTimeMillis();

		// Get all processes saved
		List<Process> processesList = null;
		List<Task> tasksList = null;

		try {
			processesList = processController.getAllProcesses();
			
			processesData = new String[processesList.size()];
			tasksData = new String[processesList.size()][];

			// allocate to processData and processRowMapping
			for (int i = 0; i < processesList.size(); i++) {
				
				processesData[i] = Helper.stripIdFromName(processesList.get(i).getProcessMetadata().getName(), processesList.get(i).getId());
				processRowMapping.put(i, processesList.get(i).getId());

				// for each process allocate tasks to tasksData and
				// taskRowMapping
				//long startTime = System.currentTimeMillis();
				
				//tasksList = processController.getValidTasksforProcess(processesList.get(i).getId());
				tasksList = Helper.getTasksBasedOnSettings(processController, tasksList, settingsController, processesList.get(i).getId());

				//long finishTime = System.currentTimeMillis();

				//Log.i("ProcessesActivity", "That took: "+(finishTime-startTime)+ " ms for getTaskList");
				tasksData[i] = new String[tasksList.size()];
				taskRowMapping = new HashMap<Integer, Integer>();

				for (int j = 0; j < tasksList.size(); j++) {
					tasksData[i][j] = tasksList.get(j).getName();
					taskRowMapping.put(j, tasksList.get(j).getId());
				}

				masterRowMapping.put(processesList.get(i).getId(),taskRowMapping);

			}
		} catch (ProcessAssistantException pae) {
			return false;
		}

		//long finishTimeWhole = System.currentTimeMillis();
		//Log.i("ProcessesActivity", "That took: "+(finishTimeWhole-startTimeWhole)+ " ms for fillData");
		return true;
	}
	

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) menuInfo;
		int type = ExpandableListView.getPackedPositionType(info.packedPosition);
		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			menu.setHeaderTitle("Task options");
			menu.add(0, 0, 0, "Task Info");
			
			//check whether ignored
		    View view = info.targetView;
			Button button = (Button) view.findViewById(R.id.confirmTaskButton);
			
			ProcessStateBean state = (ProcessStateBean) button.getTag();
//			Log.i("ProcessesActivity", "Button PID = " + state.getProcessID() + " TID = " + state.getTaskID());
			if(stateController.isIgnored(state.getProcessID(), state.getTaskID())){
				menu.add(0, 0, 0, "Confirm Task");
			}
			else if(!stateController.isConfirmed(state.getProcessID(), state.getTaskID())){
				if(processController.isValid(state.getProcessID(), state.getTaskID())){
					menu.add(0, 0, 0, "Ignore Task");
				}
			}
		} else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			menu.setHeaderTitle("Process options");
			menu.add(0, 0, 0, "Process Info");
		}

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

		int type = ExpandableListView.getPackedPositionType(info.packedPosition);

		
		int processID;
		int taskID;
		
		if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
			int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
			int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);

			processID =processRowMapping.get(groupPos);
			taskID = masterRowMapping.get(processRowMapping.get(groupPos)).get(childPos);
			
			String title = ((TextView) info.targetView.findViewById(R.id.taskCheckbox)).getText().toString();

			if(item.getTitle().equals("Task Info")){

	
				// Find processID and taskID clicked and send Intent to TaskInfoActivity
				Intent i = new Intent();
				Bundle b = new Bundle();
				b.putInt("PID", processID);
				b.putInt("TID", taskID);
				i.setClass(ProcessesActivity.this, TaskInfoActivity.class);
				i.putExtras(b);
	
				Toast.makeText(this,title + ": PID " + b.getInt("PID") + " TID "+ b.getInt("TID"), 
						Toast.LENGTH_SHORT).show();
				startActivity(i);
				return true;
				
			}
			
			else if(item.getTitle().equals("Ignore Task")){

				//Set ProcessStateController to ignored, set text to red

				
				stateController.IgnoreTask(processID, taskID, true);
				stateController.ConfirmTask(processID, taskID);
				processAnalyticsController.endTask(processID, taskID, new Date());
				refreshAdapter(processID);
				getExpandableListView().setAdapter(mAdapter);


			    View view = info.targetView;
				CheckedTextView cb = (CheckedTextView) view.findViewById(R.id.taskCheckbox);
				cb.setBackgroundColor(0xFFDC143C);

				return true;
			}
			
			else if(item.getTitle().equals("Confirm Task")){
				stateController.IgnoreTask(processID, taskID, false);
				((BaseExpandableListAdapter) mAdapter).notifyDataSetChanged();

			}

		} else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
			int groupPos = ExpandableListView
					.getPackedPositionGroup(info.packedPosition);


			String title = ((TextView) info.targetView.findViewById(R.id.tvParent)).getText().toString();

			// Find processID clicked and send Intent to ProcessesInfoActivity
			Intent i = new Intent();
			Bundle b = new Bundle();
			b.putInt("PID", processRowMapping.get(groupPos));
			i.setClass(ProcessesActivity.this, ProcessesInfoActivity.class);
			i.putExtras(b);
			Toast.makeText(this, title + ": PID " + b.getInt("PID"),
					Toast.LENGTH_SHORT).show();

			startActivity(i);
			return true;
		}

		return false;
	}
	
	

	/**
	 * Modify back button to go to the Main page
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent i = new Intent(ProcessesActivity.this, MainActivity.class);
			startActivity(i);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * Options Menu
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	      MenuInflater inflater = getMenuInflater();
	      inflater.inflate(R.menu.options_menu_processes, menu);
	      return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	      switch (item.getItemId()) {
		      case R.id.process_option_all:
		            Toast.makeText(this, "You have chosen to view all tasks",
		                        Toast.LENGTH_SHORT).show();
		            settingsController.updateSetting(ProcessSettingsDBController.VIEW_SETTING, ProcessSettingsDBController.ALL_TASKS_VIEW);
		            refreshScreen();
		            return true;
		      case R.id.process_option_valid:
		            Toast.makeText(this, "You have chosen to view valid tasks",
		                        Toast.LENGTH_SHORT).show();
		            settingsController.updateSetting(ProcessSettingsDBController.VIEW_SETTING, ProcessSettingsDBController.VALID_TASKS_VIEW);
		            refreshScreen();
		            return true;
		      case R.id.process_option_validFuture:
		            Toast.makeText(this, "You have chosen to view valid and upcoming tasks",
		                        Toast.LENGTH_SHORT).show();
		            settingsController.updateSetting(ProcessSettingsDBController.VIEW_SETTING, ProcessSettingsDBController.VALID_FUTURE_TASKS_VIEW);

		      case R.id.process_option_toggleInternet:
		    	  Toast.makeText(this, "You have chosen to only view internet valid tasks",
	                        Toast.LENGTH_SHORT).show(); 
		            settingsController.updateSetting(ProcessSettingsDBController.VALID_TASKS_FILTER, ProcessSettingsDBController.INTERNET_VALID_TASKS);
		            refreshScreen();
		            return true;

		      case R.id.process_option_toggleTime:
		    	  Toast.makeText(this, "You have chosen to only view time valid tasks",
	                        Toast.LENGTH_SHORT).show();  
		            settingsController.updateSetting(ProcessSettingsDBController.VALID_TASKS_FILTER, ProcessSettingsDBController.TIME_VALID_TASKS);
		            refreshScreen();
		            return true;
		      
		      case R.id.process_option_noFilter:
		    	  Toast.makeText(this, "You have chosen to not filter the tasks",
	                        Toast.LENGTH_SHORT).show();  
		            settingsController.updateSetting(ProcessSettingsDBController.VALID_TASKS_FILTER, ProcessSettingsDBController.NO_VALID_FILTER);
		            refreshScreen();
		            return true;
		    	  
		      default:
		            return super.onOptionsItemSelected(item);
	      }
	}
	
	public void refreshScreen(){
		Intent refresh = new Intent(this, ProcessesTabActivity.class);
		Bundle b = new Bundle();
		b.putInt("tab", 0);
		refresh.putExtras(b);
		startActivity(refresh);
		this.finish();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		
		Log.i("ProcessesActivity", "in OnActivityResult");

		if(requestCode == GET_RATING){
			if(resultCode == RATING_SUBMITTED){
				Log.i("ProcessesActivity", "in OnActivityResult RATING_SUBMITTED");
				int processID = data.getIntExtra("pid", -1);
				float rating = data.getFloatExtra("rating", -1);
				String comments = data.getStringExtra("comments");
				Log.i("ProcessesActivity", "rating = " + rating);
				Log.i("ProcessesActivity", "pid = " + processID);
				Log.i("ProcessRatingCommentsActivity", "comments = " + comments);

				try {
					processController.init();
				} catch (ProcessAssistantException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				processController.updateRating(processID, rating);
				processController.updateComments(processID, comments);
				Log.i("ProcessesActivity", "refreshing processes");

				processController.refreshProcesses(processID);

				refreshScreen();

			}
		}
	}
	
	
	

}