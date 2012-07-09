package au.edu.unsw.cse.model.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import au.edu.unsw.cse.model.ProcessMetaDataBean;

@Root
public class Process implements Parcelable{

	@ElementList
	private List<Vertex> vertices;
	@ElementList
	private List<Edge> edges;
	@Element
	private int id;
	@Element
	private ProcessMetaDataBean processMetadata;
	

	public static final Creator CREATOR = new Creator() {
		public Process createFromParcel(Parcel in) {
			return new Process(in);
		}

		@Override
		public Process[] newArray(int size) {
			return new Process[size];
		}
	};
	
	private Process(Parcel in){
		readFromParcel(in);
		processMetadata = in.readParcelable(ProcessMetaDataBean.class.getClassLoader());
		if(vertices == null){
			vertices = new ArrayList<Vertex>();
		}
		
		if(edges == null){
			edges = new ArrayList<Edge>();
		}
		
		in.readTypedList(vertices, Vertex.CREATOR);
		in.readTypedList(edges, Edge.CREATOR);

		
	}
	
	private void readFromParcel(Parcel in) {
		this.id = in.readInt();
	}

	public Process(){
		this.processMetadata = new ProcessMetaDataBean();
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		
	}
	
	public Process(int id){
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
	      Log.v("Process", "writeToParcel..."+ flags);
	      out.writeInt(id);
	      out.writeParcelable((Parcelable) processMetadata, flags);
	      out.writeTypedList(vertices);
	      out.writeTypedList(edges);
	}
	
}

