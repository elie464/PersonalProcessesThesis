package au.edu.unsw.cse.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Log;
import au.edu.unsw.cse.controller.helper.DBAdapter;
import au.edu.unsw.cse.controller.helper.Helper;
import au.edu.unsw.cse.model.AnalyticsDBBean;
import au.edu.unsw.cse.model.ProcessAnalyticsBean;
import au.edu.unsw.cse.model.graph.Task;

public class ProcessAnalyticsDBController {

	DBAdapter db;
	Context ctx;

	public ProcessAnalyticsDBController(Context appContext) {
		super();
		db = new DBAdapter(appContext);
		ctx = appContext;
	}

	public boolean insertTask(int ProcessID, int TaskID) {
		db.open();
		boolean result = db.PADcreateProcessAnalytic(ProcessID, TaskID) > 0;
		db.close();
		return result;
	}

	public boolean startTask(int ProcessID, int TaskID, Date startTime) {
		db.open();
		Log.i("ProcessAnalyticsDBC", "PID = " + ProcessID + "TaskID = " + TaskID + "startTime = " +Helper.DateTimeToString(startTime));
		
		boolean result = db.PADstartTask(ProcessID, TaskID, startTime);
		
		Log.i("ProcessAnalyticsDBC", "PID = " + ProcessID + "TaskID = " + TaskID + "read startTime = " +Helper.DateTimeToString(db.PADgetAnalytic(ProcessID, TaskID).getTaskStart()));

		db.close();
		

		return result;
	}

	public boolean endTask(int ProcessID, int TaskID, Date endTime) {
		db.open();
		Log.i("ProcessAnalyticsDBC", "PID = " + ProcessID + "TaskID = " + TaskID + "endTime = " +Helper.DateTimeToString(endTime));

		boolean result = db.PADendTask(ProcessID, TaskID, endTime);
		db.close();
		return result;
	}

	/**
	 * @param rating
	 *            0...5
	 */
	public boolean rateProcess(int ProcessID, int rating) {
		Log.i("ProcessAnalyticsDBController", "rating in int = " + rating);

		db.open();
		boolean result = db.PADrateProcess(ProcessID, rating);
		db.close();
		return result;
	}

	public boolean CommentProcess(int ProcessID, String comments) {
		db.open();
		boolean result = db.PADCommentProcess(ProcessID, comments);
		db.close();
		return result;
	}

	public boolean deleteAnalytics(int ProcessID) {
		db.open();
		boolean result = db.PADdeleteAnalytics(ProcessID);
		db.close();
		return result;
	}

	public boolean syncAnalytics(int ProcessID, List<Task> tasklist) {
		List<Task> tasks = tasklist;
		List<ProcessAnalyticsBean> analytics = new ArrayList<ProcessAnalyticsBean>();

		db.open();
		for (Task t : tasks) {
			analytics.add(db.PADgetAnalytic(ProcessID, t.getId()));
		}
		db.close();

//		ContentResolver rsl = ctx.getContentResolver();
//		String id = Secure.getString(rsl, Secure.ANDROID_ID);
//		Log.i("ProcessAnalyticsDBController", id);
//
//		ClientConfig config = new DefaultClientConfig();
//		Client client = Client.create(config);
//		WebResource service = client.resource(UriBuilder.fromUri(
//				"http://mashsheet.cse.unsw.edu.au:8081/AnalyticsService/")
//				.build());
//		
//		Log.i("ProcessAnalyticsDBController", "syncing analytics");
//
//		for (ProcessAnalyticsBean pab : analytics) {
//			AnalyticsDBBean abean = new AnalyticsDBBean(id, pab);
//			Log.i("ProcessAnalyticsDBController", "Analytics abean deviceId = " + abean.getDeviceID());
//			Log.i("ProcessAnalyticsDBController", "Analytics abean pid = " + abean.getProcessID());
//
//			ClientResponse response = service.path("rest").path("analytics")
//					.accept("MediaType.APPLICATION_XML").put(ClientResponse.class, abean);
//			Log.i("ProcessAnalyticsDBController",
//					String.valueOf(response.getStatus()));
//		}
//
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
			"http://mashsheet.cse.unsw.edu.au:8081/AnalyticsService/rest/analytics/post");
		ContentResolver rsl = ctx.getContentResolver();
		String id = Secure.getString(rsl, Secure.ANDROID_ID);
 
		for (ProcessAnalyticsBean pab : analytics) {
			AnalyticsDBBean abean = new AnalyticsDBBean(id, pab);
			Log.i("ProcessAnalyticsDBController", "Analytics abean pid = " + abean.getProcessID());
			Log.i("ProcessAnalyticsDBController", "Analytics abean tid = " + abean.getTaskID());
			Log.i("ProcessAnalyticsDBController", "Analytics abean taskstart = " + Helper.DateTimeToString(abean.getTaskStart()));
			Log.i("ProcessAnalyticsDBController", "Analytics abean taskstend = " + Helper.DateTimeToString(abean.getTaskEnd()));




			Serializer serializer = new Persister();
			StringWriter abean_string = new StringWriter();
			try {
				serializer.write(abean, abean_string);
				StringEntity input;
					input = new StringEntity(abean_string.toString());
				
				Log.i("ProcessAnalyticsDBController", "String = " + abean_string);

				input.setContentType(MediaType.APPLICATION_XML);
				postRequest.setEntity(input);
		 
				HttpResponse response;

				response = httpClient.execute(postRequest);
				

				Log.i("ProcessAnalyticsDBController",
					String.valueOf(response.getStatusLine().getStatusCode()));
				response.getEntity().consumeContent();
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		
		httpClient.getConnectionManager().shutdown();

		

		
		
		return true;
	}
}
