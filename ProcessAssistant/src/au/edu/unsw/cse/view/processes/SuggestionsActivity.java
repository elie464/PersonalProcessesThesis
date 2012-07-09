package au.edu.unsw.cse.view.processes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import au.edu.unsw.cse.R;
import au.edu.unsw.cse.controller.ProcessAnalyticsDBController;
import au.edu.unsw.cse.controller.ProcessController;
import au.edu.unsw.cse.controller.ProcessSettingsDBController;
import au.edu.unsw.cse.controller.ProcessStateDBController;
import au.edu.unsw.cse.controller.SimpleSuggestionController;
import au.edu.unsw.cse.controller.SuggestionController;
import au.edu.unsw.cse.controller.helper.Helper;
import au.edu.unsw.cse.model.ProcessStateBean;
import au.edu.unsw.cse.model.graph.Process;
import au.edu.unsw.cse.model.graph.Task;

public class SuggestionsActivity extends ListActivity {
	
	private final int VECTOR_TASK_POS = 1;

	
	ListAdapter adapter;
	ProcessSettingsDBController settingsController;
	List<Task> suggestedTasks=new ArrayList<Task>();
	ProcessStateDBController stateController;
	ProcessController PC;
	Map<Integer, Vector<Integer>> taskRowMapping; // rowid -> taskID
	ProcessAnalyticsDBController processAnalyticsController;
	SuggestionController ssg;
	

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
		ssg = new SimpleSuggestionController(processesParcel, getApplicationContext());
		
		refreshData();
		createListeners();
		
		adapter = new CustomTasksAdapter(this.getApplicationContext(), suggestedTasks, stateController, taskRowMapping, PC);		
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
		
		adapter = new CustomTasksAdapter(this.getApplicationContext(), suggestedTasks, stateController, taskRowMapping, PC);		

	}
	
	private void refreshData() {
		
		
		suggestedTasks=ssg.suggest(suggestedTasks);
		
		//get tasks based on view settings
		taskRowMapping = new HashMap<Integer, Vector<Integer>>();

		//map row id to taskid
		for (int i = 0; i < suggestedTasks.size(); i++) {
			Vector<Integer> v = new Vector<Integer>();
			v.add(suggestedTasks.get(i).getProcessID());
			v.add(suggestedTasks.get(i).getId());
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
	
	
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
	super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Task options");
		menu.add(0, v.getId(), 0, "Task Info");
	}
	
    @Override
	public boolean onContextItemSelected(MenuItem item) {
    	
    	AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	
       	if(item.getTitle().equals("Task Info")){
       		// Find processID and taskID clicked and send Intent to TaskInfoActivity
			Intent i = new Intent();
			Bundle b = new Bundle();
			int rowid = (int) info.id;
			int taskID = taskRowMapping.get(rowid).get(VECTOR_TASK_POS);
			b.putInt("TID", taskID);
			b.putInt("PID", findProcessByTaskID(taskID));
			i.setClass(SuggestionsActivity.this, TaskInfoActivity.class);
			i.putExtras(b);
			
			String title = ((TextView) info.targetView.findViewById(R.id.tasks_taskCheckbox)).getText().toString();


			Toast.makeText(this,title + ": PID " + b.getInt("PID") + " TID "+ b.getInt("TID"), 
					Toast.LENGTH_SHORT).show();
			startActivity(i);
			return true;
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
		for (int i=0; i<suggestedTasks.size(); i++) {
			if(suggestedTasks.get(i).getId() == taskid){
				return suggestedTasks.get(i).getProcessID();
			}
		}
		return -1;
	}

	
	
}
