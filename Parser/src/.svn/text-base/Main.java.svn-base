import graphPackage.Edge;
import graphPackage.Graph;
import graphPackage.Task;
import graphPackage.Vertex;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;

import org.w3c.dom.Document;

import parserPackage.Parser;
import parserPackage.YAWLParser;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Parser parser = new YAWLParser();
		Document doc = parser.parse("VISAapplication.yawl");
		Graph graph = parser.createGraph(doc,1);
		
		//display all processes from graph into
		List<Vertex> vertices = graph.getVertices();
		ListIterator<Vertex> it = vertices.listIterator();
		System.out.println("Graph has " + vertices.size() + " vertices");

//		for (Vertex vertex : vertices) {
//			System.out.println(vertex.getName());
//
//			if(vertex.getClass().equals(Task.class)){
//				  Task t = (Task) vertex;
//				  System.out.println("\t split = " + t.getSplitCode());
//				  System.out.println("\t join = " + t.getJoinCode());
//
//			}
//		}
		
		
		while(it.hasNext()){
			  
			  Vertex v  = it.next();
			  System.out.println(v.getName());
			  for (Integer i : v.getOutvertices()) {
				  System.out.println("\t outvertex " + i);
			  }
			  for (Integer i : v.getInvertices()) {
				  System.out.println("\t Invertex " + i);
			  }
			  
			  System.out.println();
			  
			  Task t = new Task();
			  if(v.getClass().equals(Task.class)){
				  t = (Task) v;
				  System.out.println("\t description = " + t.getDescription());
				  System.out.println("\t longtitude = " + t.getLongtitude());
				  System.out.println("\t latitude = " + t.getLatitude());
				  System.out.println("\t internet = " + t.isInternet());
				  System.out.println("\t hasStartDate = " + t.hasStartDate());
				  if(t.getStartDate()!= null){
					  System.out.println("\t startDate = " + dateToString(t.getStartDate()));
				  }
				  
				  System.out.println("\t hasStartTime = " + t.hasStartTime());
				  if(t.getStartTime()!= null){
					  System.out.println("\t startTime = " + timeToString(t.getStartTime()));
				  }
				  System.out.println("\t hasEndDate = " + t.hasEndDate());

				  if(t.getEndDate()!=null){
					  System.out.println("\t endDate = " + dateToString(t.getEndDate()));
				  }
				  System.out.println("\t hasEndTime = " + t.hasEndTime());
				  if(t.getEndTime()!=null){
					  System.out.println("\t endTime = " + timeToString(t.getEndTime()));
				  }
				  System.out.println("\t duration= " + t.getDuration());
				  System.out.println("\t split code = " + t.getSplitCode());
				  System.out.println("\t join code = " + t.getJoinCode());

			  }
			  
		}
		
		
		System.out.println("Graph has " + graph.getEdges().size() + " edges");
		for(int i=0;i<graph.getEdges().size();i++){

			Edge e = graph.getEdges().get(i);
			System.out.println("\tEdge from = \'" + e.getFrom().getName() + "\' to \'" + e.getTo().getName() + "\'");

			
		}
		
		
	}
	
	public static String dateToString(Date d) {
		DateFormat formatter ; 
		
		formatter = new SimpleDateFormat("dd-MM-yy");
		//System.out.println(d.toString());
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(d);   // assigns calendar to given date 
		calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		calendar.get(Calendar.MINUTE);        // gets hour in 12h format

		String s = new StringBuilder(formatter.format(d)).toString();
		return s;
	}
	
	public static String timeToString(Date d) {
		DateFormat formatter ; 
		
		formatter = new SimpleDateFormat("HH-mm");
		//System.out.println(d.toString());
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(d);   // assigns calendar to given date 
		calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
		calendar.get(Calendar.MINUTE);        // gets hour in 12h format

		String s = new StringBuilder(formatter.format(d)).toString();
		return s;
	}

}
