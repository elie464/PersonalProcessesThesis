package au.edu.unsw.cse.model.graph;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import au.edu.unsw.cse.controller.helper.Helper;

@Root
public class Task extends Vertex implements Parcelable {


	@Element
	String splitCode;
	@Element
	String joinCode;
	@Element
	String description;
	@Element
	double longtitude;
	@Element
	double latitude;
	@Element
	boolean internet;
	@Element
	Date endDate;
	@Element
	boolean hasEndDate;
	@Element
	Date endTime;
	@Element
	boolean hasEndTime;
	@Element
	Date startDate;
	@Element
	boolean hasStartDate;
	@Element
	Date startTime;
	@Element
	boolean hasStartTime;
	@Element
	double duration;
	
	public static final Creator CREATOR = new Creator() {
		public Task createFromParcel(Parcel in) {
			return new Task (in);
		}

		@Override
		public Task[] newArray(int size) {
			return new Task[size];
		}
	};
	
	private Task (Parcel in){
		readFromParcel(in);
	}
	
	public void readFromParcel(Parcel in) {
		splitCode = in.readString();
		joinCode = in.readString();
		description = in.readString();
		longtitude = in.readDouble();
		latitude = in.readDouble();
		boolean[] boolArray = new boolean[2];
		in.readBooleanArray(boolArray);
		internet = boolArray[0];
		endDate = new Date(in.readLong());
		endTime = new Date(in.readLong());
		startDate = new Date(in.readLong());
		startTime = new Date(in.readLong());
		duration = in.readDouble();

		
	}

	@Override
	public void writeToParcel(Parcel out, int flags){
		out.writeString(splitCode);
		out.writeString(joinCode);
		out.writeString(description);
		out.writeDouble(longtitude);
		out.writeDouble(latitude);
		out.writeBooleanArray(new boolean[]{internet});
		out.writeLong(endDate.getTime());
		out.writeLong(endTime.getTime());
		out.writeLong(startDate.getTime());
		out.writeLong(startTime.getTime());
		out.writeDouble(duration);
		
	}
	
	public Task(){
		this.internet = false;
		this.splitCode = "NULL";
		this.joinCode = "NULL";
		this.description = "NULL";
		this.longtitude = 0;
		this.latitude = 0;
		this.duration = 0;
		this.startDate =  stringToDate("00-00-0000-00-00");
		this.endDate =  stringToDate("00-00-0000-00-00");

	}
	
	public String getSplitCode() {
		return splitCode;
	}
	public void setSplitCode(String splitCode) {
		this.splitCode = splitCode;
	}
	public String getJoinCode() {
		return joinCode;
	}
	public void setJoinCode(String joinCode) {
		this.joinCode = joinCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getLongtitude() {
		return longtitude;
	}
	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public boolean isInternet() {
		return internet;
	}
	public void setInternet(boolean internet) {
		this.internet = internet;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	

	
	
	public boolean hasEndDate() {
		return hasEndDate;
	}

	public void setHasEndDate(boolean hasEndDate) {
		this.hasEndDate = hasEndDate;
	}

	public boolean hasStartDate() {
		return hasStartDate;
	}

	public void setHasStartDate(boolean hasStartDate) {
		this.hasStartDate = hasStartDate;
	}

	public boolean hasEndTime() {
		return hasEndTime;
	}

	public void setHasEndTime(boolean hasEndTime) {
		this.hasEndTime = hasEndTime;
	}

	public boolean hasStartTime() {
		return hasStartTime;
	}

	public void setHasStartTime(boolean hasStartTime) {
		this.hasStartTime = hasStartTime;
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
}
