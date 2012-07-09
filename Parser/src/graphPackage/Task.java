package graphPackage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Task extends Vertex {

	String splitCode;
	String joinCode;
	String description;
	double longtitude;
	double latitude;
	boolean internet;
	Date endDate;
	boolean hasEndDate;
	Date startDate;
	boolean hasStartDate;
	Date endTime;
	boolean hasEndTime;
	Date startTime;
	boolean hasStartTime;
	double duration;
	
	public Task(){
		this.internet = false;
		this.splitCode = "NULL";
		this.joinCode = "NULL";
		this.description = "NULL";
		this.longtitude = 0;
		this.latitude = 0;
		this.duration = 0;
		this.startDate =  new Date(0);
		this.startTime =  stringToTime("00-00");
		this.endDate =  new Date(0);
		this.endTime =  stringToTime("00-00");
		this.hasEndDate = false;
		this.hasEndTime = false;
		this.hasStartDate = false;
		this.hasStartTime = false;

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
