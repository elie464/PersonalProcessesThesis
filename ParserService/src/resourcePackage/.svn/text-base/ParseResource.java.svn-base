package resourcePackage;

import java.io.File;
import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;

import parserPackage.Parser;
import parserPackage.YAWLParser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import au.edu.unsw.cse.model.graph.Process;

public class ParseResource {


	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	public ParseResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public File parseProcess(){
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		String url=service.path("rest").path("processes").path(id).path("url").accept(
				MediaType.TEXT_PLAIN).get(String.class);
		
			
		//System.out.println(url);
		Parser parser = new YAWLParser();
		Document yawl_doc = parser.parse(url);
		Process process = parser.createProcess(yawl_doc, Integer.parseInt(id));
		
		
		
		//TODO: Combine with Prash's metadata on graph
		ProcessMetaDataBean processMetaData = getMetaData(id);
		
		//processMetaData.setName(name);
		process.setProcessMetadata(processMetaData);
		
		
		Serializer serializer = new Persister();
		Process graph_serialized = process;
		File graphFile = new File("process.xml");

		try {
			serializer.write(graph_serialized, graphFile);
		} catch (Exception e) {
			System.err.println("error trying to serialize");
			e.printStackTrace();
		}
		System.err.println("successfully parsed graph");
		return graphFile;
		
	}
	


	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://mashsheet.cse.unsw.edu.au:8081/RepoManagerService/").build();
	}

	private ProcessMetaDataBean getMetaData(String id){
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());

		String result=service.path("rest").path("processes").path(id).accept(
				MediaType.APPLICATION_XML).get(String.class);

		
		Serializer serializer2 = new Persister();

		ProcessMetaDataBean data=null;
		try {
			data = serializer2.read(ProcessMetaDataBean.class, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
}