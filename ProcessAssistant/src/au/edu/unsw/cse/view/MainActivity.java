package au.edu.unsw.cse.view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import au.edu.unsw.cse.R;
import au.edu.unsw.cse.view.processes.ProcessesTabActivity;
import au.edu.unsw.cse.view.repo.RepoTabActivity;

public class MainActivity extends Activity{
	
    private static final int ACTIVITY_CREATE=0;

 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.main);

		ImageButton processes = (ImageButton) findViewById(R.id.processImage);
		processes.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				Log.i("MainActivity", "Clicked Processes Image");
				Intent i = new Intent(MainActivity.this, ProcessesTabActivity.class);
		        startActivityForResult(i, ACTIVITY_CREATE);
			}
		});
		
		
		ImageView download = (ImageView) findViewById(R.id.downloadImage);
		download.setFocusable(true);
		download.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				Log.i("MainActivity", "Clicked Repo Image");
				Intent i = new Intent(MainActivity.this, RepoTabActivity.class);
				Toast.makeText(getApplicationContext(),
						"Connecting to repository",
						Toast.LENGTH_SHORT).show();
				startActivityForResult(i, ACTIVITY_CREATE);
			}
		});
 
	}
	
	private class ConnectToRepository extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();

					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "";
					while ((s = buffer.readLine()) != null) {
						response += s;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return response;
		}

	}
 

	public void readWebpage(View view) {
		ConnectToRepository task = new ConnectToRepository();
		task.execute(new String[] { "http://www.vogella.de" });

	}
	
    /**
     * Modify back button to go to the Main page
     * 
     */
	@Override
	public void onBackPressed() {
	   return;
	}
	
}
