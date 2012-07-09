package au.edu.unsw.cse.model;

public class ProcessCacheBean {
	
	int RowID;
	int ProcessID;
	int TaskID;
	public ProcessCacheBean(int rowID, int processID, int taskID) {
		super();
		RowID = rowID;
		ProcessID = processID;
		TaskID = taskID;
	}
	
	public ProcessCacheBean( int processID, int taskID) {
		super();
		ProcessID = processID;
		TaskID = taskID;
	}

	public ProcessCacheBean() {
		super();
		// TODO Auto-generated constructor stub
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
	
}
