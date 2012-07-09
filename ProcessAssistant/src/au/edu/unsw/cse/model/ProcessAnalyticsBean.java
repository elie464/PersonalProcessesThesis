package au.edu.unsw.cse.model;

import java.util.Date;

public class ProcessAnalyticsBean {

	int RowID;
	int ProcessID;
	int TaskID;
	Date TaskStart;
	Date TaskEnd;
	int ProcessRating;
	String Comments;
	
	/**
	 * @return the comments
	 */
	public String getComments() {
		return Comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		Comments = comments;
	}
	public ProcessAnalyticsBean() {
		super();
		
	}
	public ProcessAnalyticsBean(int rowID, int processID, int taskID,
			Date taskStart, Date taskEnd, int processRating, String comments) {
		super();
		RowID = rowID;
		ProcessID = processID;
		TaskID = taskID;
		TaskStart = taskStart;
		TaskEnd = taskEnd;
		ProcessRating = processRating;
		Comments = comments;
	}
	/**
	 * @return the rowID
	 */
	public int getRowID() {
		return RowID;
	}
	/**
	 * @param rowID the rowID to set
	 */
	public void setRowID(int rowID) {
		RowID = rowID;
	}
	/**
	 * @return the processID
	 */
	public int getProcessID() {
		return ProcessID;
	}
	/**
	 * @param processID the processID to set
	 */
	public void setProcessID(int processID) {
		ProcessID = processID;
	}
	/**
	 * @return the taskID
	 */
	public int getTaskID() {
		return TaskID;
	}
	/**
	 * @param taskID the taskID to set
	 */
	public void setTaskID(int taskID) {
		TaskID = taskID;
	}
	/**
	 * @return the taskStart
	 */
	public Date getTaskStart() {
		return TaskStart;
	}
	/**
	 * @param taskStart the taskStart to set
	 */
	public void setTaskStart(Date taskStart) {
		TaskStart = taskStart;
	}
	/**
	 * @return the taskEnd
	 */
	public Date getTaskEnd() {
		return TaskEnd;
	}
	/**
	 * @param taskEnd the taskEnd to set
	 */
	public void setTaskEnd(Date taskEnd) {
		TaskEnd = taskEnd;
	}
	/**
	 * @return the processRating
	 */
	public int getProcessRating() {
		return ProcessRating;
	}
	/**
	 * @param processRating the processRating to set
	 */
	public void setProcessRating(int processRating) {
		ProcessRating = processRating;
	}
}
