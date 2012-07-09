package au.edu.unsw.cse.model.graph;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Edge {

	@Element
	Vertex from;
	@Element
	Vertex to;
	
	public Edge(){
		
	}
	
	public Edge(Vertex from, Vertex to){
		this.from = from;
		this.to = to;
	}
	
	public Vertex getFrom() {
		return from;
	}
	public void setFrom(Vertex from) {
		this.from = from;
	}
	public Vertex getTo() {
		return to;
	}
	public void setTo(Vertex to) {
		this.to = to;
	}
}
