package au.edu.unsw.cse.view.repo;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import au.edu.unsw.cse.R;




public class RepoTabActivity extends TabActivity{
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.repo);

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent().setClass(this, RepoViewerActivity.class);

		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabHost
				.newTabSpec("browse").setIndicator(prepareTabView(this, "Browse"))
				.setContent(intent);
		tabHost.addTab(spec);

//		// Do the same for the other tabs
//		intent = new Intent().setClass(this, RepoSearchActivity.class);
//		spec = tabHost
//				.newTabSpec("search")
//				.setIndicator(prepareTabView(this, "Search"))
//				.setContent(intent);
//		tabHost.addTab(spec);

		
		
		tabHost.setCurrentTab(0);

	}
	
	public static View prepareTabView(Context context, String text) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.tab_indicator, null);
        TextView tv = (TextView) view.findViewById(R.id.tabIndicatorTextView);
        tv.setText(text);

        return view;
    }
}


