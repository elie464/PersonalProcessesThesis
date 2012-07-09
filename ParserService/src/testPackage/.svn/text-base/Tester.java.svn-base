package testPackage;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.w3c.dom.Document;

import parserPackage.Parser;
import parserPackage.YAWLParser;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import au.edu.unsw.cse.model.graph.Process;
import au.edu.unsw.cse.model.graph.Task;

	
public class Tester {
	public static void main(String[] args) {
//		ClientConfig config = new DefaultClientConfig();
//		Client client = Client.create(config);
//		WebResource service = client.resource(getBaseURI());
		// Create one book
		
//		//Get books/1
//		System.out.println(service.path("rest").path("parse/1").accept(
//				MediaType.APPLICATION_XML).get(String.class));
		
		Parser parser = new YAWLParser();
		Document doc = parser.parse("SetupARCclub.yawl");
		Process process = parser.createProcess(doc, 1);
		
		System.out.println(process.getVertex(3).getName());

	}
	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://mashsheet.cse.unsw.edu.au:8080/ParserService/").build();
	}
}
