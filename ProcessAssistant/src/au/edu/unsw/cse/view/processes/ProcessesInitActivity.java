package au.edu.unsw.cse.view.processes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import au.edu.unsw.cse.R;
import au.edu.unsw.cse.controller.ProcessController;
import au.edu.unsw.cse.controller.helper.ProcessAssistantException;
import au.edu.unsw.cse.model.graph.Process;

public class ProcessesInitActivity extends Activity {

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.main);
		ProgressDialog dialog = ProgressDialog.show(ProcessesInitActivity.this, "", 
      "Loading. Please wait...", true);
		
		
		Bundle b = new Bundle();
		b = initProcess();

		Intent intent = new Intent().setClass(this, ProcessesTabActivity.class);
		intent.putExtras(b);

		//dialog.dismiss();

		//startActivity(intent);


		

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
		Log.i("ProcessesInitActivity", "size = " + processes.size());
		
		//convert processes to an arraylist object and parcel
		ArrayList<Process> processesarraylist = new ArrayList<Process>(processes);
		b.putParcelableArrayList("ProcessesParcel",  processesarraylist);
		Log.i("ProcessesInitActivity", "completed parcelling");
		
		return b;
	}
	

}
