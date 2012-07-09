package au.edu.unsw.cse.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.util.Log;
import au.edu.unsw.cse.model.CommentList;
import au.edu.unsw.cse.model.ProcessMetaDataBean;
import au.edu.unsw.cse.model.ProcessMetaDataBeanList;

public class RepoController {

	Context appContext;

	public RepoController(Context appContext) {
		super();
		this.appContext = appContext;
	}

	final static String URL = "http://mashsheet.cse.unsw.edu.au:8081/RepoManagerService/rest/processes";
	final static String URL_analytics = "http://mashsheet.cse.unsw.edu.au:8081/AnalyticsService/rest/analytics/";
	final static String tag = "repocontroller";

	public ProcessMetaDataBeanList getAllProcesses() {
		// Log.i(tag, "Printing");
		ProcessMetaDataBeanList processes = null;
		String result = "";

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(URL);
		Log.i(tag, request.getURI().toString());
		// request.addHeader("deviceId", deviceId);
		ResponseHandler<String> handler = new BasicResponseHandler();
		try {
			result = httpclient.execute(request, handler);

			//Log.i(tag, "result = " + result);

			String FILENAME = "processes.xml";
			File file = new File(appContext.getFilesDir() + "/"
					+ "processes.xml");

			FileOutputStream fOut = appContext.openFileOutput(FILENAME,
					appContext.MODE_WORLD_READABLE);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);

			// Write the string to the file
			osw.write(result);

			/*
			 * ensure that everything is really written out and close
			 */
			osw.flush();
			osw.close();

			FileInputStream fIn = appContext.openFileInput(FILENAME);
			InputStreamReader isr = new InputStreamReader(fIn);

			/*
			 * Prepare a char-Array that will hold the chars we read back in.
			 */
			char[] inputBuffer = new char[result.length()];

			// Fill the Buffer with data from the file
			isr.read(inputBuffer);

			// Transform the chars to a String
			String readString = new String(inputBuffer);
			// Log.i(tag, "readstring = " + readString);

			// Log.i(tag, "fileloc = " + this.getFilesDir());

			Serializer serializer = new Persister();

			processes = serializer.read(ProcessMetaDataBeanList.class, file);
			// Log.i(tag, "Processes = " +
			// processes.getProcesses().get(0).getListingID());

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Test");
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
//		Log.i(tag, "result = " + result);
		return processes;
	}
	
	public String getAverageRating(int ProcessID) {

		Log.i(tag, "getting average rating" );

		String result = "";

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(URL_analytics + ProcessID+ "/rating");
		Log.i(tag, request.getURI().toString());
		ResponseHandler<String> handler = new BasicResponseHandler();
		try {
			result = httpclient.execute(request, handler);

			Log.i(tag, "average rating = " + result);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
		return result;
	}
	
	public ArrayList<String> getComments(int ProcessID) {

		Log.i(tag, "getting comments" );

		String result = "";
		CommentList comments = null;


		HttpClient httpclient = new DefaultHttpClient();
		HttpGet request = new HttpGet(URL_analytics + ProcessID+ "/comments");
		Log.i(tag, request.getURI().toString());
		ResponseHandler<String> handler = new BasicResponseHandler();
		try {
			result = httpclient.execute(request, handler);

			Log.i(tag, "comments = " + result);
			Serializer serializer = new Persister();

			comments = serializer.read(CommentList.class, result);
			Log.i(tag, "comments (0)= " + comments.getComments().get(0));

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
		return new ArrayList<String>(comments.getComments());
	}

	public ProcessMetaDataBean getProcess(int ProcessID) {

		// TODO Prash
		return null;
	}

	public List<ProcessMetaDataBean> searchRepository(String query) {
		// TODO Prash
		return null;

	}

}
