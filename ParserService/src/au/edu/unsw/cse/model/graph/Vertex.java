package au.edu.unsw.cse.model.graph;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Vertex {

	@Element
	int id;
	@Element
	int processID;
	@Element
	String name;
	@ElementList
	List<Integer> outvertices;
	@ElementList
	List<Integer> invertices;
	@ElementList
	List<Edge> outedges;
	@ElementList
	List<Edge> inedges;
	
	public Vertex(){
		outvertices = new ArrayList<Integer>();
		invertices = new ArrayList<Integer>();
		outedges = new ArrayList<Edge>();
		inedges = new ArrayList<Edge>();

	}
	
	public List<Integer> getOutvertices() {
		return outvertices;
	}

	public void setOutvertices(List<Integer> outvertices) {
		this.outvertices = outvertices;
	}

	public List<Integer> getInvertices() {
		return invertices;
	}

	public void setInvertices(List<Integer> invertices) {
		this.invertices = invertices;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public List<Edge> getOutedges() {
		return outedges;
	}
	public void setOutedges(List<Edge> outedges) {
		this.outedges = outedges;
	}
	public List<Edge> getInedges() {
		return inedges;
	}
	public void setInedges(List<Edge> inedges) {
		this.inedges = inedges;
	}

	public int getProcessID() {
		return processID;
	}

	public void setProcessID(int processID) {
		this.processID = processID;
	}
	
	


	
}
