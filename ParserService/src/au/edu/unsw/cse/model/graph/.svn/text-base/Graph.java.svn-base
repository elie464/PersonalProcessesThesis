package au.edu.unsw.cse.model.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import resourcePackage.ProcessMetaDataBean;

@Root
public class Graph {

	@ElementList
	private List<Vertex> vertices;
	@ElementList
	private List<Edge> edges;
	@Element
	private int id;
	@Element
	private ProcessMetaDataBean processMetadata;
	

	public Graph(){
		this.processMetadata = new ProcessMetaDataBean();
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		
	}
	
	public Graph(int id){
		this.processMetadata = new ProcessMetaDataBean();
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
	
	public ProcessMetaDataBean getProcessMetadata() {
		return processMetadata;
	}

	public void setProcessMetadata(ProcessMetaDataBean processMetadata) {
		this.processMetadata = processMetadata;
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

