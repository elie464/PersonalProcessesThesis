package au.edu.unsw.cse.view.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import au.edu.unsw.cse.R;
import au.edu.unsw.cse.controller.RepoController;
import au.edu.unsw.cse.controller.helper.Helper;
import au.edu.unsw.cse.model.ProcessMetaDataBean;
import au.edu.unsw.cse.model.ProcessMetaDataBeanList;

public class RepoViewerActivity extends ListActivity {

	final static String tag = "repo";

	// static String deviceId = "32304";
	String[] PROCESSES = { "Could not connect to repository" };
	Map<Integer, Integer> mapProcesses;
	RepoController RC;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// if no internet..warn user
		ConnectivityManager cm = (ConnectivityManager) this
				.getSystemService(CONNECTIVITY_SERVICE);
		if (cm.getActiveNetworkInfo() != null) {
			RC = new RepoController(getApplicationContext());
			final ProcessMetaDataBeanList processes = RC.getAllProcesses();
			if (processes == null) {
				Toast.makeText(getApplicationContext(),
						"Could not connect to repository. Please try again",
						Toast.LENGTH_LONG).show();
			}
			PROCESSES = new String[processes.getProcessesList().size()];
			int count = 0;
			mapProcesses = new HashMap<Integer, Integer>();

			for (ProcessMetaDataBean p : processes.getProcessesList()) {
				PROCESSES[count] = Helper.stripIdFromName(p.getName(),
						p.getProcessID());
				mapProcesses.put(count, p.getProcessID());
				Log.i(tag,
						"count = " + count + " Map = "
								+ mapProcesses.get(count));
				Log.i(tag,
						"count = " + count + "ProcessDescription="
								+ p.getDescription());

				count++;
			}

			setListAdapter(new ArrayAdapter<String>(this, R.layout.repolist,
					PROCESSES));

			ListView lv = getListView();
			lv.setTextFilterEnabled(true);

			lv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// When clicked, show a toast with the TextView text
					// Toast.makeText(getApplicationContext(),
					// String.valueOf(mapProcesses.get(position)),
					// Toast.LENGTH_SHORT).show();
					Intent i = new Intent();
					Bundle b = new Bundle();

					ProcessMetaDataBean currProcess = processes
							.findProcessbyID(mapProcesses.get(position));
					b.putInt("ID", currProcess.getProcessID());
					b.putString("NAME", currProcess.getName());
					b.putString("AUTHOR", currProcess.getAuthor());
					b.putString("DESCRIPTION", currProcess.getDescription());
					b.putString("DATE",
							Helper.DateToString(currProcess.getDateCreated()));
					b.putString("URL", currProcess.getURL());
					b.putInt("SIZE", currProcess.getFileSizeKB());
					b.putInt("averageRating", Integer.parseInt(RC.getAverageRating(currProcess.getProcessID())));
					b.putStringArrayList("comments", new ArrayList(RC.getComments(currProcess.getProcessID())));
					i.putExtras(b);
					i.setClass(RepoViewerActivity.this,
							ProcessViewActivity.class);
					startActivity(i);

				}
			});
		} else {
			setListAdapter(new ArrayAdapter<String>(this, R.layout.repolist,
					PROCESSES));

		}
	}
}
