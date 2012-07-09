package resources;

import java.io.StringWriter;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import au.edu.unsw.cse.model.CommentList;
import dao.AnalyticsDao;



public class AnalyticResource {

	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	public AnalyticResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	
	
	

	

	@GET
	@Path("comments")
	@Produces(MediaType.APPLICATION_XML)
	public String getComments() {
	AnalyticsDao adao = new AnalyticsDao();
		List<String> comments = adao.getComments(Integer.parseInt(id));
		CommentList commentsList = new CommentList(comments);
		
		
		
		Serializer serializer = new Persister();
		StringWriter commentsWriter = new StringWriter();

		try {
			serializer.write(commentsList, commentsWriter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return commentsWriter.toString();
	}
	
	@GET
	@Path("rating")
	@Produces(MediaType.TEXT_PLAIN)
	public String getRating() {
	AnalyticsDao adao = new AnalyticsDao();
		
		return String.valueOf(adao.getAvgRating(Integer.parseInt(id)));
	}
	
	
	

	
	
//	@GET
//	@Path("delete")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String deleteProcessBrowser() {
////		Book delb = BooksDao.instance.getStore().remove(id);
////		if(delb==null)
////			throw new RuntimeException("DELETE: Book with " + id +  " not found");
//		ProcessDao pdao = new ProcessDao();
//		if(pdao.deleteProcess(Integer.valueOf(id))){
//			throw new RuntimeException("GET: Process with" + id +  " not found");
//
//		}
//		return "Deleted Process";
//	}
	
	
}
