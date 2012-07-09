package au.edu.unsw.cse.model;

public class ProcessSettingsBean {

	int RowID;
	int SettingID;
	int SettingValue;
	public ProcessSettingsBean(int rowID, int settingID, int settingValue) {
		super();
		RowID = rowID;
		SettingID = settingID;
		SettingValue = settingValue;
	}
	public ProcessSettingsBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProcessSettingsBean( int settingID, int settingValue) {
		super();
		SettingID = settingID;
		SettingValue = settingValue;
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
	 * @return the settingID
	 */
	public int getSettingID() {
		return SettingID;
	}
	/**
	 * @param settingID the settingID to set
	 */
	public void setSettingID(int settingID) {
		SettingID = settingID;
	}
	/**
	 * @return the settingValue
	 */
	public int getSettingValue() {
		return SettingValue;
	}
	/**
	 * @param settingValue the settingValue to set
	 */
	public void setSettingValue(int settingValue) {
		SettingValue = settingValue;
	}
}
