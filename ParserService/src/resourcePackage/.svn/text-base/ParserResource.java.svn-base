package resourcePackage;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;



@Path("/parse")
public class ParserResource {
	// Allows to insert contextual objects into the class, 
		// e.g. ServletContext, Request, Response, UriInfo
		@Context
		UriInfo uriInfo;
		@Context
		Request request;
		
		// Important to note that this Path annotation define.
		// This will match xxx.xxx.xxx/rest/books/{book}
		// It says 'the thing that comes after books/ is a parameter
		// and it is passed to the BookResource class for processing
		// e.g., http://localhost:8080/cs9322.simple.rest.books/rest/books/3
	    // This matches this method which returns BookResource.
		@Path("{parse}")
		public ParseResource getBook(
				@PathParam("parse") String id) {
			return new ParseResource(uriInfo, request, id);
		}
		
}
