package au.edu.unsw.cse.view.processes;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import au.edu.unsw.cse.R;
import au.edu.unsw.cse.controller.ProcessController;
import au.edu.unsw.cse.controller.helper.CustomItemizedOverlay;
import au.edu.unsw.cse.model.graph.Task;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class ProcessGmapActivity extends MapActivity {

	private MapView mapView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.taskgmap);

		mapView = (MapView) findViewById(R.id.taskgmap_view);
		mapView.setBuiltInZoomControls(true);
		Bundle b = this.getIntent().getExtras();
		int ProcessID = b.getInt("PID");
		ProcessController PC = new ProcessController(getApplicationContext());
		List<GeoPoint> items = new ArrayList<GeoPoint>();
		
		List<Task> tasklist = PC.getTaskList(ProcessID);
		 for (Task task : tasklist) {
			if (!(task.getLatitude()==0 && task.getLongtitude()==0)){
				double lat = task.getLatitude();
				double lon = task.getLongtitude();
				String name = task.getName();
				String desc = task.getDescription();
				
				double dlatE6=  (lat)*(1000000);
				double dlonE6=  (lon)*(1000000);
				
				int latE6 = (int) dlatE6;
				int lonE6 = (int) dlonE6;

				Log.i("TaskGmapActivity", "lat="+lat);
				Log.i("TaskGmapActivity", "lon="+lon);
				Log.i("TaskGmapActivity", "latE6="+latE6);
				Log.i("TaskGmapActivity", "lonE6="+lonE6);

				
				
				List<Overlay> mapOverlays = mapView.getOverlays();
		        Drawable drawable = this.getResources().getDrawable(R.drawable.star3);
		        CustomItemizedOverlay itemizedOverlay = 
		             new CustomItemizedOverlay(drawable, this);
		        
		        GeoPoint point = new GeoPoint(latE6, lonE6);
		        items.add(point);
		        OverlayItem overlayitem = 
		             new OverlayItem(point, name, desc);
		        
		        itemizedOverlay.addOverlay(overlayitem);
		        mapOverlays.add(itemizedOverlay);
			}
		}
		//Add current location
		 Location current = PC.getCurrentLocation();
		 	double cdlatE6=  (current.getLatitude())*(1000000);
		 	double cdlonE6= (current.getLongitude())*(1000000);
			int clatE6= (int)cdlatE6;
			int clonE6=(int) cdlonE6;
			Log.i("TaskGmapActivity", "clat="+current.getLatitude());
			Log.i("TaskGmapActivity", "clon="+current.getLongitude());
			Log.i("TaskGmapActivity", "cdlatE6="+cdlatE6);
			Log.i("TaskGmapActivity", "cdlonE6="+cdlonE6);

			String cname= "Current location";
			String cdesc = "This is your current location";
	        Drawable cdrawable = this.getResources().getDrawable(R.drawable.pedestriancrossing);
	        CustomItemizedOverlay itemizedOverlay = 
		             new CustomItemizedOverlay(cdrawable, this);
	        GeoPoint cpoint = new GeoPoint(clatE6, clonE6);
	        items.add(cpoint);
	        OverlayItem coverlayitem = 
		             new OverlayItem(cpoint, cname, cdesc);
	        itemizedOverlay.addOverlay(coverlayitem);
			List<Overlay> mapOverlays = mapView.getOverlays();
			mapOverlays.add(itemizedOverlay);


		 
		 int minLat = Integer.MAX_VALUE;
		 int maxLat = Integer.MIN_VALUE;
		 int minLon = Integer.MAX_VALUE;
		 int maxLon = Integer.MIN_VALUE;

		 for (GeoPoint item : items) 
		 { 

		       int lat = item.getLatitudeE6();
		       int lon = item.getLongitudeE6();

		       maxLat = Math.max(lat, maxLat);
		       minLat = Math.min(lat, minLat);
		       maxLon = Math.max(lon, maxLon);
		       minLon = Math.min(lon, minLon);
		  }
		 MapController mapController = mapView.getController();
		 mapController.zoomToSpan(Math.abs(maxLat - minLat), Math.abs(maxLon - minLon));
		 mapController.animateTo(new GeoPoint( (maxLat + minLat)/2, 
		 (maxLon + minLon)/2 )); 
		
        
       
        
        
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		
		return false;
	}

}
