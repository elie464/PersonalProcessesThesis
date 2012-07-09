package resources;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import au.edu.unsw.cse.model.AnalyticsDBBean;
import dao.AnalyticsDao;

@Path("/analytics")
public class AnalyticsResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	public Response getAnalyticsFile() {

        File f = getAnalytics();

        ResponseBuilder response = Response.ok((Object) f);
        response.type("text/csv");
        response.header("Content-Disposition", "attachment; filename=\"analytics.csv\"");
        return response.build();
    }

	public File getAnalytics() {
		AnalyticsDao adao = new AnalyticsDao();

		List<AnalyticsDBBean> analytics = new ArrayList<AnalyticsDBBean>();

		String homeDir = System.getProperty("catalina.base");
		String filename = homeDir + "/analytics.csv";

		File analyticsFile = new File(filename);

		analytics = adao.getALLAnalytics();

		try {
			// Create file
			FileWriter fstream = new FileWriter(analyticsFile);
			BufferedWriter out = new BufferedWriter(fstream);
			String headings = "DeviceID," + "ProcessID," + "TaskID,"
					+ "TaskStart," + "TaskEnd," + "ProcessRating," + "Comment"+'\n';
			System.err.println(headings);

			out.write(headings);
			for (AnalyticsDBBean abean : analytics) {

				String deviceID = abean.getDeviceID();
				String processID = String.valueOf(abean.getProcessID());
				String taskID = String.valueOf(abean.getTaskID());
				String taskStart = dateToString(abean.getTaskStart());
				String taskEnd = dateToString(abean.getTaskStart());
				String processRating = String.valueOf(abean.getProcessRating());
				String comment = abean.getComment();
				
				if(comment == null){
					comment = "";
				}

				
				System.err.println("deviceId " + deviceID);
				System.err.println("processID " + processID);
				System.err.println("taskID " + taskID);
				System.err.println("taskStart " + taskStart);
				System.err.println("taskEnd " + taskEnd);
				System.err.println("processRating " + processRating);
				System.err.println("comment " + comment);

				String line = deviceID + ',' + processID
				+ ',' + taskID + ','
				+ taskStart + ','
				+ taskEnd + ','
				+ processRating + ',' + comment + '\n';
				
//				String line = abean.getDeviceID() + ',' + abean.getProcessID()
//						+ ',' + abean.getTaskID() + ','
//						+ dateToString(abean.getTaskStart()) + ','
//						+ dateToString(abean.getTaskEnd()) + ','
//						+ abean.getProcessRating() + ',' + abean.getComment();
//				System.err.println(line);

				out.write(line);
			}

			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return analyticsFile;
	}

	String dateToString(Date d) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
		String result = format.format(d);
		return result;

	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_XML)
	public Response postAnalytic(String abean) {
		System.err.println("Analytics POST service called");
//		AnalyticsDBBean abean = b.getValue();
		return putAndGetResponse(abean);
	}

	private Response putAndGetResponse(String input) {
		Response res;

		AnalyticsDao adao = new AnalyticsDao();
		
		
		Serializer serializer = new Persister();
		AnalyticsDBBean bean = new AnalyticsDBBean();
		
		try {
			serializer.read(bean, input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean result = adao.addListing(bean);

		if (result) {
			res = Response.created(uriInfo.getAbsolutePath()).build();

		} else {
			res = Response.noContent().build();
		}

		return res;
	}
	@Path("{analytic}")
	public AnalyticResource getAnalytic(
			@PathParam("analytic") String id) {
		return new AnalyticResource(uriInfo, request, id);
	}
	
}
