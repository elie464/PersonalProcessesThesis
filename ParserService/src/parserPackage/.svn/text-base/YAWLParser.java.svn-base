package parserPackage;



import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import au.edu.unsw.cse.model.graph.Edge;
import au.edu.unsw.cse.model.graph.Graph;
import au.edu.unsw.cse.model.graph.Task;
import au.edu.unsw.cse.model.graph.Vertex;
import au.edu.unsw.cse.model.graph.Process;

/**
 * This class implements Parser by reading YAWL files and using DOM parser
 * 
 * @author ernestlie
 *
 */
public class YAWLParser implements Parser {


	/**
	 * Parser function to generate Document file
	 * 
	 */
	public Document parse(String filename) {
		DOMParser parser = new DOMParser();
		try {
			parser.parse(filename);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document doc = parser.getDocument();
				
		return doc;
		
	}
	
	
	/**
	 * Create graph from document file and assign it an ID
	 * 
	 */
	public Process createProcess(Document doc, int id){
		Process process = new Process(id);
		process.setId(id);
		
		NodeList nodelist = doc.getElementsByTagName("processControlElements");

		// create tasks
		List<Vertex> vertices = createTasks(nodelist, id);		

		// create edges
		List<Edge >edges = createEdges(vertices, nodelist);
		
		List<Vertex> vertices_updated = checkSplitAndJoinCodes(vertices, edges);
		vertices_updated = fillOutInVertices(vertices_updated, edges);
		process.getVertices().addAll(vertices_updated);
		process.getEdges().addAll(edges);
		return process;
	}
	
	/**
	 * Adjust out and invertices given a list of edges
	 * 
	 * @param vertices
	 * @param edges
	 * @return
	 */
	private List<Vertex> fillOutInVertices(List<Vertex> vertices, List<Edge> edges) {
		for (Edge edge : edges) {
			int from = edge.getFrom().getId();
			int to = edge.getTo().getId();
			vertices.get(getIndex(vertices, to)).getInvertices().add(from);
			vertices.get(getIndex(vertices, from)).getOutvertices().add(to);
			
			//System.out.println("Added to vertex " + vertices.get(getIndex(vertices, from.getId())).getName()
			//		+ " outvertex " + to.getName());
			//System.out.println("Added to vertex " + vertices.get(getIndex(vertices, to.getId())).getName()
			//		+ " invertex " + from.getName());

		}
		
		return vertices;
	}
	
	/**
	 * 
	 * Helper to get index given an ID
	 * return -1 otherwise
	 * 
	 * @param ID
	 * @return
	 */
	private int getIndex(List<Vertex> vertices, int ID){
		for (int i=0; i<vertices.size();i++) {
			if(vertices.get(i).getId() == ID){
				return i;
			}
		}
		
		return -1;
	}


	/**
	 * Split and join codes have been assigned, but default is
	 * Join = XOR, Split = AND, need to check for these cases
	 * 
	 * if a vertex has only been counted once in edges as a from vertex, it can't have a split
	 * if a vertex has only been counted once in edges as a to vertex, it can't have a join
	 * 
	 * @param vertices
	 * @param edges
	 */
	private List<Vertex> checkSplitAndJoinCodes(List<Vertex> vertices, List<Edge> edges) {
		
		Map<Integer, Integer> mapFrom = createMappingFrom(edges);
		Map<Integer, Integer> mapTo = createMappingTo(edges);
		
		List<Vertex> vertices_updated = new ArrayList<Vertex>();
		
		for (int i =0; i< vertices.size();i++) {
			
			//System.out.println("Vertex ID " + vertices.get(i).getId() + ": " + vertices.get(i).getName());

			if(vertices.get(i).getClass().equals(Task.class)){
				//System.out.println("\tobject = task");
				Task task = (Task) vertices.get(i);

				if(mapFrom.containsKey(task.getId())){
					//System.out.println("\tnum occurences from = " +mapFrom.get(vertices.get(i).getId()));

					if(mapFrom.get(task.getId()).equals(1)){
						//System.out.println("setting split code to null");
						task.setSplitCode("null");
					}

				}
				
				if(mapTo.containsKey(task.getId())){
					//System.out.println("\tnum occurences to = " +mapTo.get(vertices.get(i).getId()));

					if(mapTo.get(task.getId()).equals(1)){
						//System.out.println("setting join code to null");
						task.setJoinCode("null");
					}
				}	
				
				vertices_updated.add(task);
			}
			else{
				vertices_updated.add(vertices.get(i));
			}
		}
		return vertices_updated;
	}
	
	/**
	 * 
	 * Create mapping of Task -> num occurences in edges from node
	 * 
	 * @param edges
	 * @return
	 */
	private Map<Integer, Integer> createMappingFrom(List<Edge> edges) {
		
		Map<Integer, Integer> mapFrom = new HashMap<Integer, Integer>();
		
		for (Edge edge : edges) {
//			System.out.println("\tEdge from = " + edge.getFrom().getName() + " to =" + edge.getTo().getName());

			//edge must not involve start node
			if(edge.getFrom().getId()!= 1){
				
				if(mapFrom.containsKey(edge.getFrom().getId())){
					mapFrom.put(edge.getFrom().getId(), mapFrom.get(edge.getFrom().getId())+1);
//					System.out.println("incrementing mapFrom key = " + edge.getFrom().getId() + " value =" + mapFrom.get(edge.getFrom().getId()));
				}
				
				else{
					mapFrom.put(edge.getFrom().getId(), 1);
//					System.out.println("initialising mapFrom key = " + edge.getFrom().getId() + " value =" + mapFrom.get(edge.getFrom().getId()));
				}
			}
		}
		
		return mapFrom;
	}
	
	/**
	 * 
	 * Create mapping of Task -> num occurences in edges to node
	 * 
	 * @param edges
	 * @return
	 */
	private Map<Integer, Integer> createMappingTo(List<Edge> edges) {
		
		Map<Integer, Integer> mapTo = new HashMap<Integer, Integer>();
		
		for (Edge edge : edges) {
			//System.out.println("\tEdge from = " + edge.getFrom().getName() + " to =" + edge.getTo().getName());

			//edge must not end node
			if(edge.getTo().getId() != 2){
				
				if(mapTo.containsKey(edge.getTo().getId())){
					mapTo.put(edge.getTo().getId(), mapTo.get(edge.getTo().getId())+1);
					//System.out.println("incrementing mapTo key = " + edge.getTo().getId() + " value =" + mapTo.get(edge.getTo().getId()));
				}

				else{
					mapTo.put(edge.getTo().getId(), 1);
					//System.out.println("initialising mapTo key = " + edge.getTo().getId() + " value =" + mapTo.get(edge.getTo().getId()));
				}
			}
		}
		
		return mapTo;
	}
	


	/**
	 * create list of edges
	 * 
	 * @param vertices
	 * @param nodelist
	 * @return
	 */
	private List<Edge> createEdges(List<Vertex> vertices, NodeList nodelist) {
		//System.out.println("Creating Edges");

		List<Edge> edges = new ArrayList<Edge>();
		String fromVertexID = "";
		String toVertexID = "";
		
		for (Node node = nodelist.item(0).getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType()==Node.ELEMENT_NODE) {
				//System.out.println(node.getNodeType() + node.getNodeName());
				
				//if start node
				if (node.getNodeName()=="inputCondition") {
					Vertex from = getVertex(vertices, 1);
					
					//System.out.println("\tinputCondition");
					//step through inputcondition for multiple flowsinto tag

					for (Node nodeInput = node.getChildNodes().item(0); nodeInput != null; nodeInput = nodeInput.getNextSibling()) {
						if (nodeInput.getNodeType()==Node.ELEMENT_NODE && nodeInput.getNodeName() == "flowsInto") {
							toVertexID = nodeInput.getFirstChild().getNextSibling().getAttributes().item(0).getNodeValue();
							Vertex to = getVertex(vertices, getIDfromYAWLTaskID(toVertexID));
							//System.out.println("Adding new Edge " + " from = " + from.getName() + " to " + to.getName());
							Edge edge = new Edge(from, to);
							edges.add(edge);
						}
					}
				}
				
				//if task node
				else if (node.getNodeName()=="task") {
					
					//add taskId
					//System.out.println("Calling TaskID conversion functon for " + taskid);
					fromVertexID =  node.getAttributes().item(0).getNodeValue();



					//loop through task items
					for (Node n = node.getFirstChild(); n != null; n = n.getNextSibling()) {
						//System.out.println("  "+ n.getNodeType() + n.getNodeName());
						

						//add name
						if (n.getNodeName()=="name") {
							//System.out.println("\t   "+ n.getFirstChild().getNodeValue());
							
						}
						
						//add flows into
						if (n.getNodeName()=="flowsInto") {
							//System.out.println("flows into = " + n.getFirstChild().getNextSibling().getAttributes().item(0).getNodeValue());
							toVertexID = n.getFirstChild().getNextSibling().getAttributes().item(0).getNodeValue();
							
							Vertex from = getVertex(vertices, getIDfromYAWLTaskID(fromVertexID));
							Vertex to = getVertex(vertices, getIDfromYAWLTaskID(toVertexID));
							//System.out.println("Adding new Edge " + " from = " + from.getName() + " to " + to.getName());
							Edge edge = new Edge(from, to);
							edges.add(edge);
						}		
					}
				}
			}
		}
	
		return edges;
	}


	/**
	 * 
	 * create list of tasks
	 * 
	 * @param nodelist
	 * @return
	 */
	private List<Vertex> createTasks(NodeList nodelist, int pid) {
		
		List<Vertex> tasks = new ArrayList<Vertex>();

		
		for (Node node = nodelist.item(0).getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node.getNodeType()==Node.ELEMENT_NODE) {
				//System.out.println(node.getNodeType() + node.getNodeName());
				
				//if start node
				if (node.getNodeName()=="inputCondition") {
					Vertex input = new Vertex();
					input.setProcessID(pid);
					
					//add taskId
					String inputid = node.getAttributes().item(0).getNodeValue();
					input.setId(getIDfromYAWLTaskID(inputid));
					input.setName("start");					
					tasks.add(input);
					
				}
				
				//if end node
				else if (node.getNodeName()=="outputCondition") {
					Vertex output = new Vertex();
					output.setProcessID(pid);
					
					//add taskId
					String outputid = node.getAttributes().item(0).getNodeValue();
					output.setId(getIDfromYAWLTaskID(outputid));
					output.setName("end");
					tasks.add(output);
					
				}
				
				//if task node
				else if (node.getNodeName()=="task") {
					Task task = new Task();
					task.setProcessID(pid);
					
					//add taskId
					String taskid = node.getAttributes().item(0).getNodeValue();
					//System.out.println("Calling TaskID conversion functon for " + taskid);
					task.setId(getIDfromYAWLTaskID(taskid));
					
					//loop through task items
					for (Node n = node.getFirstChild(); n != null; n = n.getNextSibling()) {
						//System.out.println("  "+ n.getNodeType() + n.getNodeName());
						
						//add name
						if (n.getNodeName()=="name") {
							//System.out.println("\t   "+ n.getFirstChild().getNodeValue());
							task.setName(n.getFirstChild().getNodeValue());
						}
						
						//add documentation info
						if (n.getNodeName()=="documentation") {
							//System.out.println("\t   "+ n.getFirstChild().getNodeValue());
							separateDocoInfo(n.getFirstChild().getNodeValue(), task);
						}	
						
						//set split and join codes if not the same as default
						if (n.getNodeName()=="join") {
							//System.out.println("\t   "+ n.getAttributes().item(0).getNodeValue());
							task.setJoinCode(n.getAttributes().item(0).getNodeValue());
							
						}	
						
						//set split and join codes if not the same as default
						if (n.getNodeName()=="split") {
							//System.out.println("\t   "+ n.getAttributes().item(0).getNodeValue());
							task.setSplitCode(n.getAttributes().item(0).getNodeValue());

						}
						
					}
					tasks.add(task);
				}
			}
		}
		return tasks;
	}



	/**
	 * Separate document information to tags: lon, lat, sta, end
	 * 
	 * @param docoInfo
	 * @param task
	 * @return
	 */
	private Task separateDocoInfo(String docoInfo, Task task) {
		
		if(docoInfo.contains("|")){
					
			String[] docoLine = docoInfo.split("\\|");
			

			Task taskUpdated = new Task();
			for(int i =0; i<docoLine.length;i++){
				String taskTag = docoLine[i].trim().substring(0, 3);
				taskUpdated =  setDocoInfo(task, docoLine, i, taskTag);
			}
			
			return taskUpdated;

		}

		String taskTag = docoInfo.substring(0, 3);
		return setDocoInfo(task, docoInfo, taskTag);	//if only 1 doco info
														//(no "|" characters)
		
	}

	/**
	 * 
	 * set tag information to task (contains "|")
	 * 
	 * @param task
	 * @param docoLine
	 * @param i
	 * @param taskTag
	 * @return
	 */
	private Task setDocoInfo(Task task, String[] docoLine, int i, String taskTag) {
		if(taskTag.equals("des")){
			String taskDescription = docoLine[i].substring(docoLine[i].indexOf(':')+1, docoLine[i].length());
			task.setDescription(taskDescription);
		}
		
		if(taskTag.equals("lon")){
			//System.out.println(docoLine[i]);
			double taskLongtitude = Double.parseDouble(docoLine[i].substring(docoLine[i].indexOf(':')+1, docoLine[i].length()));
			task.setLongtitude(taskLongtitude);

		}
		
		if(taskTag.equals("lat")){
			double taskLatitude = Double.parseDouble(docoLine[i].substring(docoLine[i].indexOf(':')+1, docoLine[i].length()));
			task.setLatitude(taskLatitude);

		}
		
		if(taskTag.equals("int")){
			String taskInternet = docoLine[i].substring(docoLine[i].indexOf(':')+1, docoLine[i].length());
			if(taskInternet.equals("true")){
				task.setInternet(true);
			}
		}
		
		if(taskTag.equals("std")){
			String taskStartDate = docoLine[i].substring(docoLine[i].indexOf(':')+1, docoLine[i].length());
			task.setStartDate(stringToDate(taskStartDate));
			task.setHasStartDate(true);
		}
		
		if(taskTag.equals("stt")){
			String taskStartTime = docoLine[i].substring(docoLine[i].indexOf(':')+1, docoLine[i].length());
			task.setStartTime((stringToTime(taskStartTime)));
			task.setHasStartTime(true);

		}
		
		if(taskTag.equals("end")){
			String taskEnd = docoLine[i].substring(docoLine[i].indexOf(':')+1, docoLine[i].length());
			task.setEndDate(stringToDate(taskEnd));
			task.setHasEndDate(true);
		}	
		
		if(taskTag.equals("ent")){
			String taskEndTime = docoLine[i].substring(docoLine[i].indexOf(':')+1, docoLine[i].length());
			task.setEndTime(stringToTime(taskEndTime));
			task.setHasEndTime(true);
		}	
		
		if(taskTag.equals("dur")){
			double taskDuration = Double.parseDouble(docoLine[i].substring(docoLine[i].indexOf(':')+1, docoLine[i].length()));
			task.setDuration(taskDuration);
		}
		
		return task;
	}
	
	/**
	 * 
	 * set tag information to task (does not contain "|")
	 * 
	 * @param task
	 * @param docoInfo
	 * @param taskTag
	 * @return
	 */
	private Task setDocoInfo(Task task, String docoInfo, String taskTag) {
		
		if(taskTag.equals("des")){
			String taskDescription = docoInfo.substring(docoInfo.indexOf(':')+1, docoInfo.length());
			task.setDescription(taskDescription);
		}
		
		if(taskTag.equals("lon")){
			double taskLongtitude = Double.parseDouble(docoInfo.substring(docoInfo.indexOf(':')+1, docoInfo.length()));
			task.setLongtitude(taskLongtitude);
		}
		
		if(taskTag.equals("lat")){
			double taskLatitude = Double.parseDouble(docoInfo.substring(docoInfo.indexOf(':')+1, docoInfo.length()));
			task.setLongtitude(taskLatitude);
		}
		
		if(taskTag.equals("int")){
			String taskInternet = docoInfo.substring(docoInfo.indexOf(':')+1, docoInfo.length());
			if(taskInternet.equals("true")){
				task.setInternet(true);
			}
		}
		
		if(taskTag.equals("std")){
			String taskStartDate = docoInfo.substring(docoInfo.indexOf(':')+1, docoInfo.length());
			task.setStartDate(stringToDate(taskStartDate));
		}
		
		if(taskTag.equals("stt")){
			String taskStartTime = docoInfo.substring(docoInfo.indexOf(':')+1, docoInfo.length());
			task.setStartTime(stringToTime(taskStartTime));
		}
		
		if(taskTag.equals("end")){
			String taskEndDate = docoInfo.substring(docoInfo.indexOf(':')+1, docoInfo.length());
			task.setEndDate(stringToDate(taskEndDate));
		}
		
		if(taskTag.equals("ent")){
			String taskEndTime = docoInfo.substring(docoInfo.indexOf(':')+1, docoInfo.length());
			task.setEndTime(stringToTime(taskEndTime));
		}	
		
		if(taskTag.equals("dur")){
			double taskDuration = Double.parseDouble(docoInfo.substring(docoInfo.indexOf(':')+1, docoInfo.length()));
			task.setDuration(taskDuration);
		}
		
		return task;
	}

	/**
	 * Helper function to convert TaskID into an int for android
	 * e.g. convert TaskID: Sign_up_with_Arc_6 to 6
	 * 
	 * @param YAWLTaskID
	 * @return
	 */
	private int getIDfromYAWLTaskID(String YAWLTaskID) {


		String stringID = YAWLTaskID.substring(YAWLTaskID.lastIndexOf("_")+1, YAWLTaskID.length());

		return Integer.parseInt(stringID);
	}
	
	/**
	 * 
	 * string to date function
	 * 
	 * @param s
	 * @return
	 */
	private Date stringToDate (String s){
		DateFormat formatter  = new SimpleDateFormat("dd-MM-yyyy"); 

		
		Date d= new Date();
		
		try {
			d= formatter.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	/**
	 * 
	 * string to time function
	 * 
	 * @param s
	 * @return
	 */
	private Date stringToTime(String s){
		DateFormat formatter  = new SimpleDateFormat("HH-mm"); 

		Date d= new Date();
		
		try {
			d= formatter.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * return a Vertex object given a Vertex ID and Vertex List
	 * 
	 * @param id
	 * @return
	 */
	private Vertex getVertex(List<Vertex> vertices, int id){
		Iterator<Vertex> it = (Iterator<Vertex>) vertices.iterator();
		while(it.hasNext()){
			Vertex v = it.next();
			if(v.getId() == id){
				return v;
			}
		}
		
		return null;
	}
	

	
}
