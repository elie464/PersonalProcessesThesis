package au.edu.unsw.cse.view.processes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import au.edu.unsw.cse.R;
import au.edu.unsw.cse.controller.ProcessController;
import au.edu.unsw.cse.controller.helper.Helper;
import au.edu.unsw.cse.model.graph.Task;

public class TaskInfoActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taskinfo);

		Bundle b = this.getIntent().getExtras();

		if (b != null) {
			int processID = b.getInt("PID");
			int taskID = b.getInt("TID");

			ProcessController PC = new ProcessController(
					getApplicationContext());
			final Task info = PC.getTask(processID, taskID);

			TextView tv1 = (TextView) (findViewById(R.id.taskinfonameheading));
			TextView tv2 = (TextView) (findViewById(R.id.taskinfonametext));
			tv1.setText("\nName\n");
			tv2.setText(info.getName());

			TextView tv3 = (TextView) (findViewById(R.id.taskinfondescheading));
			TextView tv4 = (TextView) (findViewById(R.id.taskinfondesctext));
			tv3.setText("\nDescription\n");
			tv4.setText(info.getDescription());

			TextView tv5 = (TextView) (findViewById(R.id.taskinfonameinternetheading));
			TextView tv6 = (TextView) (findViewById(R.id.taskinfointernettext));
			tv5.setText("\nInternet Connectivity\n");
			if (info.isInternet()) {

				tv6.setText("Required");

			} else {
				tv6.setText("Not Required");

			}

			TextView tv7 = (TextView) (findViewById(R.id.taskinfoavailabilityheading));
			TextView tv8 = (TextView) (findViewById(R.id.taskinfoavailabilitytext));
			tv7.setText("\nTask Availability\n");
			if (info.hasStartDate() && info.hasEndDate() && info.hasStartTime()
					&& info.hasEndTime()) {
				tv8.setText(Helper.DateToString(info.getStartDate()) + " "
						+ Helper.TimeToString(info.getStartTime()) + " To "
						+ Helper.DateToString(info.getEndDate()) + " "
						+ Helper.TimeToString(info.getEndTime()));
			} else if (info.hasStartDate() && info.hasEndDate()) {
				tv8.setText(
						 Helper.DateToString(info.getStartDate()) + " To "
						+ Helper.DateToString(info.getEndDate()));
			} else if (info.hasStartTime() && info.hasEndTime()) {
				tv8.setText( Helper.TimeToString(info.getStartTime()) + " To "
						+ Helper.TimeToString(info.getEndTime()));
			}
			else {
				tv8.setText("Not Stated");
			}
			TextView tv9 = (TextView) (findViewById(R.id.taskinfodurationheading));
			TextView tv10 = (TextView) (findViewById(R.id.taskinfodurationtext));
			tv9.setText("\nDuration\n");
			if(info.getDuration()!=0.0){
				tv10.setText(info.getDuration()+"hrs");
			}else{
				tv10.setText("Not Stated");

			}
			

			// TextView tv = (TextView) (findViewById(R.id.taskinfotext));
			//
			// tv.setText("Task ID: " + info.getId());
			// tv.append("\n");
			//
			// tv.append("Name: " + info.getName());
			// tv.append("\n");
			//
			// tv.append("Description: " + info.getDescription());
			// tv.append("\n");
			//
			// tv.append("Longitude: " + info.getLongtitude());
			// tv.append("\n");
			//
			// tv.append("Latitude: " + info.getLatitude());
			// tv.append("\n");
			//
			// if (info.isInternet()) {
			//
			// tv.append("Internet Connectivity: Required");
			// tv.append("\n");
			// } else {
			// tv.append("Internet Connectivity: Not Required");
			// tv.append("\n");
			// }
			// if(info.hasStartDate()&&info.hasEndDate()&&info.hasStartTime()&&info.hasEndTime()){
			// tv.append("Task Availability: From "
			// +
			// Helper.DateToString(info.getStartDate())+" "+Helper.TimeToString(info.getStartTime())+" To "+Helper.DateToString(info.getEndDate())+" "+Helper.TimeToString(info.getEndTime()));
			// }
			// else if(info.hasStartDate()&&info.hasEndDate()){
			// tv.append("Task Availability: From "
			// +
			// Helper.DateToString(info.getStartDate())+" To "+Helper.DateToString(info.getEndDate()));
			// }
			// else if(info.hasStartTime()&&info.hasEndTime()){
			// tv.append("Task Availability: From "
			// +Helper.TimeToString(info.getStartTime())+" To "+Helper.TimeToString(info.getEndTime()));
			// }
			// tv.append("\n");
			// tv.append("Duration: " + info.getDuration()+"hrs");
			// tv.append("\n");
			//
			//
			// tv.append("\n");
			// Log.i("TaskInfoActivity",
			// "lon="+info.getLongtitude()+" lat="+info.getLatitude());
			if ((info.getLatitude() != 0) && (info.getLongtitude() != 0)) {
				Log.i("TaskInfoActivity", "here");
				final Button mapButton = (Button) findViewById(R.id.taskgmapbutton);
				mapButton.setText("View Map");
				mapButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						// Perform action on click
						// a function that takes in a id and return boolean
						// if true -> activity to view tasks
						Toast.makeText(getApplicationContext(), "Showing Map",
								Toast.LENGTH_SHORT).show();
						Intent i = new Intent(TaskInfoActivity.this,
								TaskGmapActivity.class);
						Bundle b = new Bundle();
						b.putDouble("lat", info.getLatitude());
						b.putDouble("lon", info.getLongtitude());
						b.putString("name", info.getName());
						b.putString("description", info.getDescription());

						i.putExtras(b);

						startActivity(i);
					}
				});
			} else {
				Button viewMapButton = (Button) getWindow().getDecorView()
						.findViewById(R.id.taskgmapbutton);
				viewMapButton.setVisibility(View.INVISIBLE);
			}

		} else {

			TextView tv = (TextView) (findViewById(R.id.taskinfonameheading));

			tv.setText("Sorry an error has occcured");

		}

	}

}
