package au.edu.unsw.cse.model;

import java.sql.Date;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class AnalyticsDBBean {

	@Element
	int AnalyticID;
	@Element
	String DeviceID;
	@Element
	int ProcessID;
	@Element
	int TaskID;
	@Element
	Date taskStart;
	@Element
	Date taskEnd;
	@Element(required=false)
	int ProcessRating=-1;
	@Element(required=false)
	String Comment;

	public AnalyticsDBBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnalyticsDBBean(String deviceID, int processID, int taskID,
			Date taskStart, Date taskEnd, int processRating, String comment) {
		super();
		DeviceID = deviceID;
		ProcessID = processID;
		TaskID = taskID;
		this.taskStart = taskStart;
		this.taskEnd = taskEnd;
		ProcessRating = processRating;
		Comment = comment;
	}

	public AnalyticsDBBean(String id, ProcessAnalyticsBean pab) {
		// TODO Auto-generated constructor stub
		super();
		DeviceID = id;
		ProcessID = pab.getProcessID();
		TaskID = pab.getTaskID();
		this.taskStart = new Date(pab.getTaskStart().getTime());
		this.taskEnd = new Date(pab.getTaskEnd().getTime());
		ProcessRating = pab.getProcessRating();
		Comment = pab.getComments();
	}

	/**
	 * @return the analyticID
	 */
	public int getAnalyticID() {
		return AnalyticID;
	}

	/**
	 * @param analyticID
	 *            the analyticID to set
	 */
	public void setAnalyticID(int analyticID) {
		AnalyticID = analyticID;
	}

	/**
	 * @return the deviceID
	 */
	public String getDeviceID() {
		return DeviceID;
	}

	/**
	 * @param deviceID
	 *            the deviceID to set
	 */
	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}

	/**
	 * @return the processID
	 */
	public int getProcessID() {
		return ProcessID;
	}

	/**
	 * @param processID
	 *            the processID to set
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
	 * @param taskID
	 *            the taskID to set
	 */
	public void setTaskID(int taskID) {
		TaskID = taskID;
	}

	/**
	 * @return the taskStart
	 */
	public Date getTaskStart() {
		return taskStart;
	}

	/**
	 * @param taskStart
	 *            the taskStart to set
	 */
	public void setTaskStart(Date taskStart) {
		this.taskStart = taskStart;
	}

	/**
	 * @return the taskEnd
	 */
	public Date getTaskEnd() {
		return taskEnd;
	}

	/**
	 * @param taskEnd
	 *            the taskEnd to set
	 */
	public void setTaskEnd(Date taskEnd) {
		this.taskEnd = taskEnd;
	}

	/**
	 * @return the processRating
	 */
	public int getProcessRating() {
		return ProcessRating;
	}

	/**
	 * @param processRating
	 *            the processRating to set
	 */
	public void setProcessRating(int processRating) {
		ProcessRating = processRating;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return Comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		Comment = comment;
	}

}
