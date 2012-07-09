package au.edu.unsw.cse.controller;

import android.content.Context;
import au.edu.unsw.cse.controller.helper.DBAdapter;
import au.edu.unsw.cse.model.ProcessSettingsBean;

public class ProcessSettingsDBController {

	DBAdapter db;

	public static final int VIEW_SETTING = 0;// key
	public static final int ALL_TASKS_VIEW = 1;// value
	public static final int VALID_TASKS_VIEW = 2;
	public static final int VALID_FUTURE_TASKS_VIEW = 3;

	public static final int VALID_TASKS_FILTER = 4;
	public static final int INTERNET_VALID_TASKS = 5;
	public static final int TIME_VALID_TASKS = 6;
	public static final int NO_VALID_FILTER = 7;

	public static final int SORT_OPTION = 8;
	public static final int SORT_TIME = 9;
	public static final int SORT_LOCATION = 10;
	public static final int NO_SORT = 11;

	public static final int SUGGESTION_PREFERENCE = 12;
	public static final int LOCATION_PREFERENCE = 13;
	public static final int TIME_PREFERENCE = 14;

	public ProcessSettingsDBController(Context appContext) {
		super();
		this.db = new DBAdapter(appContext);
		initSettings();
	}

	public boolean createSetting(int SettingID, int SettingValue) {

		db.open();
		boolean result = db.PODcreateSetting(new ProcessSettingsBean(SettingID,
				SettingValue)) > 0;
		db.close();

		return result;
	}

	public boolean updateSetting(int SettingID, int SettingValue) {
		db.open();
		boolean result = db.PODupdateSetting(SettingID, SettingValue);
		db.close();
		return result;

	}

	public boolean deleteSetting(int SettingID) {
		db.open();
		boolean result = db.PODdeleteSetting(SettingID);
		db.close();
		return result;
	}

	public int getSetting(int SettingID) {
		db.open();
		int result = db.PODgetSetting(SettingID).getSettingValue();
		db.close();
		return result;
	}

	public void initSettings() {
		db.open();
		ProcessSettingsBean settings = db.PODgetSetting(VIEW_SETTING);
		db.close(); 
		if (settings==null) {
			// init all settings
			
			createSetting(VIEW_SETTING, VALID_TASKS_VIEW);
			createSetting(VALID_TASKS_FILTER, NO_VALID_FILTER);
			createSetting(SORT_OPTION, NO_SORT);
			createSetting(SUGGESTION_PREFERENCE, LOCATION_PREFERENCE);
		}
		

	}
}
