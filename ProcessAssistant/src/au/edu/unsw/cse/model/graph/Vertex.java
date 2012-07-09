package au.edu.unsw.cse.model.graph;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import au.edu.unsw.cse.model.ProcessMetaDataBean;

@Root
public class Vertex implements Parcelable {

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
	
	public static final Creator CREATOR = new Creator() {
		public Vertex createFromParcel(Parcel in) {
			return new Vertex (in);
		}

		@Override
		public Vertex[] newArray(int size) {
			return new Vertex[size];
		}
	};
	
	private Vertex (Parcel in){
		readFromParcel(in);
	}
	
	public void readFromParcel(Parcel in) {
		this.id = in.readInt();
		this.name = in.readString();
		
		this.outvertices = in.readArrayList(Integer.class.getClassLoader());
		this.invertices = in.readArrayList(Integer.class.getClassLoader());
		this.inedges = in.readArrayList(Integer.class.getClassLoader());
		this.outedges = in.readArrayList(Integer.class.getClassLoader());

		
	}

	@Override
	public void writeToParcel(Parcel out, int flags){
		out.writeInt(id);
		out.writeString(name);
		out.writeList(outvertices);
		out.writeList(invertices);
		out.writeList(outedges);
		out.writeList(inedges);
	}
	
	
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}



	
}
