package au.edu.unsw.cse.model;

public class ProcessStateBean {

	int RowID;
	int ProcessID;
	int TaskID;
	boolean confirmed;
	boolean ignored;

	public ProcessStateBean(int rowID, int processID, int taskID,
			boolean confirmed, boolean ignored) {
		super();
		RowID = rowID;
		ProcessID = processID;
		TaskID = taskID;
		this.confirmed = confirmed;
		this.ignored = ignored;
	}

	public ProcessStateBean() {
		super();
	}

	/**
	 * @return the rowID
	 */
	public int getRowID() {
		return RowID;
	}

	/**
	 * @param rowID
	 *            the rowID to set
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
	 * @return the confirmed (0,1)
	 */
	public boolean getConfirmed() {
		return confirmed;
	}

	/**
	 * @param confirmed (0,1)
	 *            the confirmed to set
	 */
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * @return the ignored (0,1)
	 */
	public boolean getIgnored() {
		return ignored;
	}

	/**
	 * @param ignored (0,1)
	 *            the ignored to set
	 */
	public void setIgnored(boolean ignored) {
		this.ignored = ignored;
	}

}
