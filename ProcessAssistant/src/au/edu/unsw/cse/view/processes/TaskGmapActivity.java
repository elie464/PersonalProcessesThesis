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

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class TaskGmapActivity extends MapActivity {

	private MapView mapView;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.taskgmap);
		ProcessController PC = new ProcessController(getApplicationContext());

		mapView = (MapView) findViewById(R.id.taskgmap_view);
		mapView.setBuiltInZoomControls(true);
		
		List<GeoPoint> items = new ArrayList<GeoPoint>();

		Bundle b = this.getIntent().getExtras();
		double lat = b.getDouble("lat");
		double lon = b.getDouble("lon");
		String name =b.getString("name");
		String desc = b.getString("description");
		
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
        
        
      //Add current location
		 Location current = PC.getCurrentLocation();
		 	double cdlatE6=  (current.getLatitude())*(1000000);
		 	double cdlonE6= (current.getLongitude())*(1000000);
			int clatE6= (int)cdlatE6;
			int clonE6=(int) cdlonE6;
			String cname= "Current location";
			String cdesc = "This is your current location";
	        Drawable cdrawable = this.getResources().getDrawable(R.drawable.pedestriancrossing);
	        CustomItemizedOverlay citemizedOverlay = 
		             new CustomItemizedOverlay(cdrawable, this);
	        
	        GeoPoint cpoint = new GeoPoint(clatE6, clonE6);
	        items.add(cpoint);
	        OverlayItem coverlayitem = 
		             new OverlayItem(cpoint, cname, cdesc);
	        citemizedOverlay.addOverlay(coverlayitem);
			mapOverlays.add(citemizedOverlay);
			

        
			
			 int minLat = Integer.MAX_VALUE;
			 int maxLat = Integer.MIN_VALUE;
			 int minLon = Integer.MAX_VALUE;
			 int maxLon = Integer.MIN_VALUE;

			 for (GeoPoint item : items) 
			 { 

			       int lat1 = item.getLatitudeE6();
			       int lon1 = item.getLongitudeE6();

			       maxLat = Math.max(lat1, maxLat);
			       minLat = Math.min(lat1, minLat);
			       maxLon = Math.max(lon1, maxLon);
			       minLon = Math.min(lon1, minLon);
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
