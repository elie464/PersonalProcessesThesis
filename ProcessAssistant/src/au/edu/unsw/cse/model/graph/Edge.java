package au.edu.unsw.cse.model.graph;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

@Root
public class Edge implements Parcelable {

	@Element
	Vertex from;
	@Element
	Vertex to;
	
	public static final Creator CREATOR = new Creator() {
		public Edge createFromParcel(Parcel in) {
			return new Edge (in);
		}

		@Override
		public Edge[] newArray(int size) {
			return new Edge[size];
		}
	};
	
	private Edge (Parcel in){
		readFromParcel(in);
	}
	
	public void readFromParcel(Parcel in) {
		this.from = in.readParcelable(Vertex.class.getClassLoader());
		this.to = in.readParcelable(Vertex.class.getClassLoader());
	}

	@Override
	public void writeToParcel(Parcel out, int flags){
		out.writeParcelable(from, flags);
		out.writeParcelable(to, flags);
	}
	
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
}
