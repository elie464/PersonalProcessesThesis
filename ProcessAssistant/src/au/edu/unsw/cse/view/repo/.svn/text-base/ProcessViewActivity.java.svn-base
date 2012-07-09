package au.edu.unsw.cse.view.repo;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import au.edu.unsw.cse.R;
import au.edu.unsw.cse.controller.ProcessAnalyticsDBController;
import au.edu.unsw.cse.controller.ProcessController;
import au.edu.unsw.cse.controller.ProcessMappingDBController;
import au.edu.unsw.cse.controller.ProcessStateDBController;
import au.edu.unsw.cse.controller.helper.Helper;
import au.edu.unsw.cse.model.ProcessMetaDataBean;
import au.edu.unsw.cse.view.processes.ProcessesTabActivity;

public class ProcessViewActivity extends Activity {

	ProcessMetaDataBean currProcess;
	boolean deleteProcess;
	String tag = "ProcessViewActivity";
	String[] commentsData = new String[]{""};
	ListView commentsThread;
	ArrayList<String> commentsArrayList;
	private int averageRatingValue=-1;
	private RatingBar averageRating;
	private ScrollView scrollview;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.processview);

		Bundle b = this.getIntent().getExtras();
		deleteProcess = false;
		currProcess = null;
		if (b != null) {
			currProcess = new ProcessMetaDataBean(b.getInt("ID"),
					b.getString("NAME"), b.getString("AUTHOR"),
					b.getString("DESCRIPTION"), b.getString("URL"),
					Helper.StringToDate(b.getString("DATE")), b.getInt("SIZE"));
			averageRatingValue = b.getInt("averageRating");
			commentsArrayList = b.getStringArrayList("comments");
			
		}
		
		commentsThread = (ListView) findViewById(R.id.commentsthread);
		averageRating = (RatingBar) findViewById(R.id.averagerating);
		scrollview = (ScrollView) findViewById(R.id.processViewScroll);
		

		TextView tv1 = (TextView) (findViewById(R.id.processviewnameheading));
		TextView tv2 = (TextView) (findViewById(R.id.processviewnametext));
		tv1.setText("\nName\n");
		tv2.setText(Helper.stripIdFromName(currProcess.getName(), currProcess.getProcessID()));
		
		TextView tv3 = (TextView) (findViewById(R.id.processviewauthorheading));
		TextView tv4 = (TextView) (findViewById(R.id.processviewauthortext));
		tv3.setText("\nAuthor\n");
		tv4.setText(currProcess.getAuthor());
		
		TextView tv5 = (TextView) (findViewById(R.id.processviewdescriptionheading));
		TextView tv6 = (TextView) (findViewById(R.id.processviewdescriptiontext));
		tv5.setText("\nDescription\n");
		tv6.setText(currProcess.getDescription());
		
		TextView tv7 = (TextView) (findViewById(R.id.processviewdateheading));
		TextView tv8 = (TextView) (findViewById(R.id.processviewdatetext));
		tv7.setText("\nDate Created\n");
		tv8.setText(Helper.DateToString(currProcess.getDateCreated()));

	
		
		setAverageRating();

		final Button dloadButton = (Button) findViewById(R.id.dloadbutton);
		dloadButton.setText("Download Process");
		dloadButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				// a function that takes in a id and return boolean
				// if true -> activity to view tasks
				Toast.makeText(getApplicationContext(),
						"Downloading Process",
						Toast.LENGTH_SHORT).show();
				parse();

			}
		});
		
		appendComments();
		scrollview.fullScroll(View.FOCUS_UP);
		scrollview.pageScroll(View.FOCUS_UP);

	}

	private void setAverageRating() {
		
		if(averageRatingValue >0){
			
			averageRating.setRating(averageRatingValue);
		}
		else{
			averageRating.setRating(0);
		}
	}

	private void appendComments() {
		
		if(commentsArrayList.size()>0){
			Log.i("ProcessViewActivity", "commentsArrayList size = " + commentsArrayList.size());
			Log.i("ProcessViewActivity", "commentsArrayList = " + commentsArrayList.get(0));
			commentsData = new String[commentsArrayList.size()];
			commentsArrayList.toArray(commentsData);
			Log.i("ProcessViewActivity", "commentsData size = " + commentsData.length);
			commentsThread.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, commentsData));

		}
		else{
			commentsThread.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, commentsData));
		}
	}

	private void parse() {

		// delete all tasks with listingID

		// ProcessStateDBController psDB = new ProcessStateDBController(
		// getApplicationContext());
		Log.i(tag, "process ID=" + currProcess.getProcessID());
		ProcessMappingDBController pmDB = new ProcessMappingDBController(
				getApplicationContext());
		if (!pmDB.processMapExists(currProcess.getProcessID())) { // psDB.processExists(currProcess.getProcessID())
			// warning box
			createProcess(currProcess.getProcessID(), currProcess.getName());
			downloadProcess(currProcess);
			moveToProcessesActivity();

		} else {

			Log.i(tag, "process exists");
			AlertDialog.Builder altDialog = new AlertDialog.Builder(this);
			altDialog
					.setMessage("This will delete the process that currently exists in your tasklist. Ignore this if you have completed the process");
			altDialog.setCancelable(true);

			altDialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

							Log.i(tag, "user tapped delete");
							deleteProcess(currProcess.getProcessID());

							
							createProcess(currProcess.getProcessID(),
									currProcess.getName());
							downloadProcess(currProcess);
							moveToProcessesActivity();
							//
						}
					});

			altDialog.show();
			Log.i(tag, "show button");

		}

		// else{

		// }

	}

	private boolean deleteProcess(int PID) {
		ProcessStateDBController psDB = new ProcessStateDBController(
				getApplicationContext());
		ProcessMappingDBController pmDB = new ProcessMappingDBController(
				getApplicationContext());
		
		ProcessAnalyticsDBController padDB = new ProcessAnalyticsDBController(getApplicationContext());
		
		Log.i(tag, "delete process");
		boolean result= psDB.DeleteProcessState(PID);
		boolean result2 = pmDB.deleteProcessMap(PID);
		

		return (result && result2);

	}

	private boolean createProcess(int PID, String name) {
		ProcessMappingDBController pmDB = new ProcessMappingDBController(
				getApplicationContext());
		Log.i(tag, "create process");

		return pmDB.createProcessMap(PID, name);
	}

	private boolean downloadProcess(ProcessMetaDataBean process) {
		ProcessController PC = new ProcessController(getApplicationContext());

		return PC.downloadProcess(process);
	}

	private void moveToProcessesActivity() {

		
		Intent i = new Intent(this, ProcessesTabActivity.class);
		Log.i(tag, "Start activity");

		startActivity(i);

	}

}
