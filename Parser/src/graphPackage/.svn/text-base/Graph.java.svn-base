package graphPackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import parserPackage.ProcessMetaDataBean;


public class Graph {


	private List<Vertex> vertices;

	private List<Edge> edges;

	private int id;
	
	private ProcessMetaDataBean processmetadata;
	
	public Graph(){
		this.processmetadata = new ProcessMetaDataBean();
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		
	}
	
	public Graph(int id){
		this.processmetadata = new ProcessMetaDataBean();
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		this.id = id;
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
        this.id = id;
    }

	
	public List<Vertex> getVertices(){
		return vertices;
	}
	
	
	public List<Edge> getEdges(){
		return edges;
	}
	
	public Vertex getVertex(int id){
		Iterator<Vertex> it = (Iterator<Vertex>) vertices.iterator();
		while(it.hasNext()){
			Vertex v = it.next();
			if(v.id == id){
				return v;
			}
		}
		
		return null;
	}
	
}


