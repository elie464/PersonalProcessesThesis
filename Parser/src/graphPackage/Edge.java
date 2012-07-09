package graphPackage;

public class Edge {

	Vertex from;
	Vertex to;
	
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
