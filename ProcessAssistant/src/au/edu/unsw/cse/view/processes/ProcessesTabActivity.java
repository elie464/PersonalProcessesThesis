package au.edu.unsw.cse.view.processes;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import au.edu.unsw.cse.R;
import au.edu.unsw.cse.controller.ProcessController;
import au.edu.unsw.cse.controller.helper.ProcessAssistantException;
import au.edu.unsw.cse.model.graph.Process;

public class ProcessesTabActivity extends TabActivity {

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.processes_tab);
		

		
		Bundle b = this.getIntent().getExtras();
		int tab=0;
		
		//after refresh from a view settings, check which tab previously tabbed
		if(b!=null){
			tab=b.getInt("tab");
		}
		
		
		
		b = initProcess();

		

		
		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, ProcessesActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(b);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("processes").setIndicator(prepareTabView(this, "Processes"))
				.setContent(intent);
		tabHost.addTab(spec);

		// Do the same for the other tabs
		intent = new Intent().setClass(this, TasksActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(b);
		spec = tabHost
				.newTabSpec("tasks")
				.setIndicator(prepareTabView(this, "Tasks"))
				.setContent(intent);
		tabHost.addTab(spec);
		
		
		
		intent = new Intent().setClass(this, SuggestionsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(b);
		spec = tabHost
				.newTabSpec("suggestions")
				.setIndicator(prepareTabView(this, "Suggestions"))
				.setContent(intent);
		tabHost.addTab(spec);
		
		tabHost.setCurrentTab(tab);

//		
//		ProgressDialog dialog = ProgressDialog.show(ProcessesTabActivity.this, "", 
//                "Loading. Please wait...", true);
//		dialog.dismiss();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i("ProcessesTabActivity", "Tab resumed");
		
		
	}
	
	public static View prepareTabView(Context context, String text) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.tab_indicator, null);
        TextView tv = (TextView) view.findViewById(R.id.tabIndicatorTextView);
        tv.setText(text);

        return view;
    }
	
	private Bundle initProcess(){
		List<Process> processes = new ArrayList<Process>();

		Bundle b= new Bundle();
		
		//init processController
		ProcessController processController = new ProcessController(this);
		try {
			processController.init();
		} catch (ProcessAssistantException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//parcel up processes object for PC in Processes/Tasks/Suggestions tab

		try {
			processes = processController.getAllProcesses();
		} catch (ProcessAssistantException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("ProcessesTabActivity", "size = " + processes.size());
		
		//convert processes to an arraylist object and parcel
		ArrayList<Process> processesarraylist = new ArrayList<Process>(processes);
		b.putParcelableArrayList("ProcessesParcel",  processesarraylist);
		Log.i("ProcessesTabActivity", "completed parcelling");
		
		return b;
	}
	

}
