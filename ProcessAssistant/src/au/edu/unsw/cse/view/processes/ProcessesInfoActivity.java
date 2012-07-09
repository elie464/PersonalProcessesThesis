package au.edu.unsw.cse.view.processes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import au.edu.unsw.cse.R;
import au.edu.unsw.cse.controller.ProcessController;
import au.edu.unsw.cse.controller.helper.Helper;
import au.edu.unsw.cse.model.ProcessMetaDataBean;

public class ProcessesInfoActivity extends Activity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.processesinfo);

		Bundle b = this.getIntent().getExtras();
		
		
		if (b != null) {
			final int processID = b.getInt("PID");
		
		ProcessController PC = new ProcessController(getApplicationContext());
		ProcessMetaDataBean processInfo = PC.getProcessMetaData(processID);
		
		TextView tv1 = (TextView) (findViewById(R.id.processesinfonameheading));
		TextView tv2 = (TextView) (findViewById(R.id.processesinfonametext));
		tv1.setText("\nName\n");
		tv2.setText(Helper.stripIdFromName(processInfo.getName(), processInfo.getProcessID()));

		TextView tv3 = (TextView) (findViewById(R.id.processesinfoauthorheading));
		TextView tv4 = (TextView) (findViewById(R.id.processesinfoauthortext));
		tv3.setText("\nAuthor\n");
		tv4.setText(processInfo.getAuthor());

		TextView tv5 = (TextView) (findViewById(R.id.processesinfodescheading));
		TextView tv6 = (TextView) (findViewById(R.id.processesinfodesctext));
		tv5.setText("\nDescription\n");
		tv6.setText(processInfo.getDescription());

		TextView tv7 = (TextView) (findViewById(R.id.processesinfosizeheading));
		TextView tv8 = (TextView) (findViewById(R.id.processesinfosizetext));
		tv7.setText("\nFile size in KB\n");
		tv8.setText(String.valueOf(processInfo.getFileSizeKB()));

		TextView tv9 = (TextView) (findViewById(R.id.processesinfodateheading));
		TextView tv10 = (TextView) (findViewById(R.id.processesinfodatetext));
		tv9.setText("\nDate Created\n");
		tv10.setText(Helper.DateToString(processInfo.getDateCreated()));

		
//		tv.setText("Process ID: " + processInfo.getProcessID());
//		tv.append("\n");
//
//		tv.append("Name: " + Helper.stripIdFromName(processInfo.getName(), processInfo.getProcessID()));
//		tv.append("\n");
//		tv.append("Author: " + processInfo.getAuthor());
//		tv.append("\n");
//
//		tv.append("Description: " + processInfo.getDescription());
//		tv.append("\n");
//		
//		
//		tv.append("File size in KB: " + processInfo.getFileSizeKB());
//		tv.append("\n");
//		
//		tv.append("Date Created: "
//				+ Helper.DateToString(processInfo.getDateCreated()));
//		tv.append("\n");
//		tv.append("\n");
		
		final Button mapButton = (Button) findViewById(R.id.processgmapbutton);
		mapButton.setText("View Map");
		mapButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				// a function that takes in a id and return boolean
				// if true -> activity to view tasks
				Toast.makeText(getApplicationContext(),
						"Showing Map",
						Toast.LENGTH_SHORT).show();
				Intent i = new Intent(ProcessesInfoActivity.this, ProcessGmapActivity.class);
				Bundle b = new Bundle();
				b.putInt("PID", processID);
				i.putExtras(b);

				startActivity(i);
			}
		});
		
		
		}else{
			
			TextView tv = (TextView) (findViewById(R.id.processesinfonameheading));
			
			
			
			tv.setText("Sorry an error has occcured");		}
		

	}
}
