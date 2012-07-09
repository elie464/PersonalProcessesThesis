package au.edu.unsw.cse.controller.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import au.edu.unsw.cse.controller.ProcessSettingsDBController;
import au.edu.unsw.cse.model.ProcessAnalyticsBean;
import au.edu.unsw.cse.model.ProcessCacheBean;
import au.edu.unsw.cse.model.ProcessMappingBean;
import au.edu.unsw.cse.model.ProcessSettingsBean;
import au.edu.unsw.cse.model.ProcessStateBean;

public class DBAdapter {

	// GENERIC

	private static final String TAG = "DBAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mCtx;
	private static final String DATABASE_NAME = "processassistant";
	private static final int DATABASE_VERSION = 1;
	private static final int TRUE = 1;
	private static final int FALSE = 0;

	// PROCESS STATE DATABASE
	private static final String KEY_PSD_ROWID = "_id";
	private static final String KEY_PSD_PROCESSID = "_processid";
	private static final String KEY_PSD_TASKID = "_taskid";
	private static final String KEY_PSD_CONFIRMED = "_confirmed";
	private static final String KEY_PSD_IGNORED = "_ignored";

	private static final int PSD_ROWID_INDEX = 0;
	private static final int PSD_PROCESSID_INDEX = 1;
	private static final int PSD_TASKID_INDEX = 2;
	private static final int PSD_CONFIRMED_INDEX = 3;
	private static final int PSD_IGNORED_INDEX = 4;

	private static final String DATABASE_TABLE_PSD = "process_state";

	private static final String DATABASE_CREATE_PSD = "create table process_state (_id integer primary key autoincrement, "
			+ "_processid integer not null, _taskid integer not null, _confirmed integer, _ignored integer);";

	private static final String DATABASE_CREATE_UNIQUE_PSD = "CREATE UNIQUE INDEX IF NOT EXISTS unique_PSD ON process_state (_processid, _taskid);";

	// PROCESS MAPPING DATABASE
	private static final String KEY_PMD_ROWID = "_id";
	private static final String KEY_PMD_PROCESSID = "_processid";
	private static final String KEY_PMD_PROCESSNAME = "_processname";

	private static final int PMD_ROWID_INDEX = 0;
	private static final int PMD_PROCESSID_INDEX = 1;
	private static final int PMD_PROCESSNAME_INDEX = 2;

	private static final String DATABASE_TABLE_PMD = "process_mapping";

	private static final String DATABASE_CREATE_PMD = "create table process_mapping (_id integer primary key autoincrement, "
			+ "_processid integer not null, _processname text);";

	private static final String DATABASE_CREATE_UNIQUE_PMD = "CREATE UNIQUE INDEX IF NOT EXISTS unique_PMD ON process_mapping (_processid);";

	// PROCESS ANALYTICS DATABASE
	private static final String KEY_PAD_ROWID = "_id";
	private static final String KEY_PAD_PROCESSID = "_processid";
	private static final String KEY_PAD_TASKID = "_taskid";
	private static final String KEY_PAD_TASKSTART = "_taskstart";
	private static final String KEY_PAD_TASKEND = "_taskend";
	private static final String KEY_PAD_PROCESSRATING = "_processrating";
	private static final String KEY_PAD_COMMENTS = "_comments";


	private static final int PAD_ROWID_INDEX = 0;
	private static final int PAD_PROCESSID_INDEX = 1;
	private static final int PAD_TASKID_INDEX = 2;
	private static final int PAD_TASKSTART_INDEX = 3;
	private static final int PAD_TASKEND_INDEX = 4;
	private static final int PAD_PROCESSRATING_INDEX = 5;
	private static final int PAD_COMMENTS_INDEX = 6;


	private static final String DATABASE_TABLE_PAD = "process_analytics";

	private static final String DATABASE_CREATE_PAD = "create table process_analytics (_id integer primary key autoincrement, "
			+ "_processid integer not null, _taskid integer not null, _taskstart text, _taskend text, _processrating integer, _comments text);";

	//private static final String DATABASE_CREATE_UNIQUE_PAD = "CREATE UNIQUE INDEX IF NOT EXISTS unique_PAD ON process_analytics (_processid);";

	
	//PROCESS SETTINGS DATABASE
	private static final String KEY_POD_ROWID = "_id";
	private static final String KEY_POD_SETTINGID = "_settingid";
	private static final String KEY_POD_SETTINGVALUE = "_settingvalue";
	
	private static final int POD_ROWID_INDEX = 0;
	private static final int POD_SETTINGID_INDEX = 1;
	private static final int POD_SETTINGVALUE_INDEX = 2;
	
	private static final String DATABASE_TABLE_POD = "process_settings";

	private static final String DATABASE_CREATE_POD = "create table process_settings (_id integer primary key autoincrement, "
			+ "_settingid integer not null, _settingvalue integer not null);";
	
	private static final String DATABASE_CREATE_UNIQUE_POD = "CREATE UNIQUE INDEX IF NOT EXISTS unique_PAD ON process_settings (_settingid);";

	
	
	
	
	//PROCESS CACHE DATABASE
	private static final String KEY_PCD_ROWID = "_id";
	private static final String KEY_PCD_PROCESSID = "_processid";
	private static final String KEY_PCD_TASKID = "_taskid";
	
	private static final int PCD_ROWID_INDEX = 0;
	private static final int PCD_PROCESS_INDEX = 1;
	private static final int PCD_TASKID_INDEX = 2;
	
	private static final String DATABASE_TABLE_PCD = "process_cache";

	private static final String DATABASE_CREATE_PCD = "create table process_cache (_id integer primary key autoincrement, "
			+ "_processid integer not null, _taskid integer not null);";
	
	//private static final String DATABASE_CREATE_UNIQUE_PCD = "CREATE UNIQUE INDEX IF NOT EXISTS unique_PAD ON process_cache (_processid);";

	
	// GENERIC
	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			db.execSQL(DATABASE_CREATE_PSD);
			db.execSQL(DATABASE_CREATE_PMD);
			db.execSQL(DATABASE_CREATE_PAD);
			db.execSQL(DATABASE_CREATE_POD);
			db.execSQL(DATABASE_CREATE_PCD);



			db.execSQL(DATABASE_CREATE_UNIQUE_PMD);
			db.execSQL(DATABASE_CREATE_UNIQUE_PSD);
			//db.execSQL(DATABASE_CREATE_UNIQUE_PAD);
			db.execSQL(DATABASE_CREATE_UNIQUE_POD);
		//	db.execSQL(DATABASE_CREATE_UNIQUE_PCD);
			
			



		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS process_state");
			db.execSQL("DROP TABLE IF EXISTS process_mapping");
			db.execSQL("DROP TABLE IF EXISTS process_analytics");
			db.execSQL("DROP TABLE IF EXISTS process_settings");
			db.execSQL("DROP TABLE IF EXISTS process_cache");


			onCreate(db);
		}

	}

	public DBAdapter(Context ctx) {
		this.mCtx = ctx;
	}

	public DBAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	// PROCESS STATE DATABASE OPS

	public long PSDcreateProcessState(int ProcessID, int TaskID, int confirmed,
			int ignored) {

		ContentValues values = new ContentValues();
		values.put(KEY_PSD_PROCESSID, ProcessID);
		values.put(KEY_PSD_TASKID, TaskID);
		values.put(KEY_PSD_CONFIRMED, confirmed);
		values.put(KEY_PSD_IGNORED, ignored);

		return mDb.insert(DATABASE_TABLE_PSD, null, values);
	}

	public long PSDdeleteProcessState(int ProcessID) {
		return mDb.delete(DATABASE_TABLE_PSD, KEY_PSD_PROCESSID + "="
				+ ProcessID, null);

	}

	public boolean PSDconfirmTask(int ProcessID, int TaskID) {
		ProcessStateBean current = PSDgetTask(ProcessID, TaskID);

		return PSDupdateTask(current.getProcessID(), current.getTaskID(), true,
				current.getIgnored());
	}

	public boolean PSDignoreTask(int ProcessID, int TaskID, boolean ignore) {
		ProcessStateBean current = PSDgetTask(ProcessID, TaskID);

		return PSDupdateTask(current.getProcessID(), current.getTaskID(),
				current.getConfirmed(), ignore);
	}

	public List<ProcessStateBean> PSDgetTasks(int ProcessID) {

		Cursor results = mDb.query(true, DATABASE_TABLE_PSD, new String[] {
				KEY_PSD_ROWID, KEY_PSD_PROCESSID, KEY_PSD_TASKID,
				KEY_PSD_CONFIRMED, KEY_PSD_IGNORED }, KEY_PSD_PROCESSID + "="
				+ ProcessID, null, null, null, null, null);

		List<ProcessStateBean> resultList = new ArrayList<ProcessStateBean>();
		if (results.getCount() > 0) {
			results.moveToFirst();
			while (!results.isAfterLast()) {
				resultList.add(new ProcessStateBean(results
						.getInt(PSD_ROWID_INDEX), results
						.getInt(PSD_PROCESSID_INDEX), results
						.getInt(PSD_TASKID_INDEX), results
						.getInt(PSD_CONFIRMED_INDEX) > 0, results
						.getInt(PSD_IGNORED_INDEX) > 0));
				results.moveToNext();
			}

			return resultList;
		}
		return null;

	}

	public ProcessStateBean PSDgetTask(int ProcessID, int TaskID) {
		Cursor results = mDb.query(true, DATABASE_TABLE_PSD, new String[] {
				KEY_PSD_ROWID, KEY_PSD_PROCESSID, KEY_PSD_TASKID,
				KEY_PSD_CONFIRMED, KEY_PSD_IGNORED }, KEY_PSD_PROCESSID + "="
				+ ProcessID + " and " + KEY_PSD_TASKID + "=" + TaskID, null,
				null, null, null, null);
		if (results.getCount() > 0) {
			results.moveToFirst();
			return new ProcessStateBean(results.getInt(PSD_ROWID_INDEX),
					results.getInt(PSD_PROCESSID_INDEX),
					results.getInt(PSD_TASKID_INDEX),
					results.getInt(PSD_CONFIRMED_INDEX) > 0,
					results.getInt(PSD_IGNORED_INDEX) > 0);
		}
		return null;

	}

	public List<ProcessStateBean> PSDgetConfirmedTasks(int ProcessID) {
		Cursor results = mDb.query(true, DATABASE_TABLE_PSD, new String[] {
				KEY_PSD_ROWID, KEY_PSD_PROCESSID, KEY_PSD_TASKID,
				KEY_PSD_CONFIRMED, KEY_PSD_IGNORED }, KEY_PSD_PROCESSID + "="
				+ ProcessID + " and " + KEY_PSD_CONFIRMED + ">0", null, null,
				null, null, null);

		List<ProcessStateBean> resultList = new ArrayList<ProcessStateBean>();
		if (results.getCount() > 0) {
			results.moveToFirst();
			while (!results.isAfterLast()) {
				resultList.add(new ProcessStateBean(results
						.getInt(PSD_ROWID_INDEX), results
						.getInt(PSD_PROCESSID_INDEX), results
						.getInt(PSD_TASKID_INDEX), results
						.getInt(PSD_CONFIRMED_INDEX) > 0, results
						.getInt(PSD_IGNORED_INDEX) > 0));
				results.moveToNext();
			}

			return resultList;
		}
		return null;
	}

	public List<ProcessStateBean> PSDgetALLConfirmedTasks() {
		Cursor results = mDb.query(true, DATABASE_TABLE_PSD, new String[] {
				KEY_PSD_ROWID, KEY_PSD_PROCESSID, KEY_PSD_TASKID,
				KEY_PSD_CONFIRMED, KEY_PSD_IGNORED }, KEY_PSD_CONFIRMED + ">0",
				null, null, null, null, null);

		List<ProcessStateBean> resultList = new ArrayList<ProcessStateBean>();
		if (results.getCount() > 0) {
			results.moveToFirst();
			while (!results.isAfterLast()) {
				resultList.add(new ProcessStateBean(results
						.getInt(PSD_ROWID_INDEX), results
						.getInt(PSD_PROCESSID_INDEX), results
						.getInt(PSD_TASKID_INDEX), results
						.getInt(PSD_CONFIRMED_INDEX) > 0, results
						.getInt(PSD_IGNORED_INDEX) > 0));
				results.moveToNext();
			}

			return resultList;
		}
		return null;

	}

	private boolean PSDupdateTask(int ProcessID, int TaskID, boolean confirmed,
			boolean ignored) {
		ContentValues args = new ContentValues();
		args.put(KEY_PSD_PROCESSID, ProcessID);
		args.put(KEY_PSD_TASKID, TaskID);
		args.put(KEY_PSD_CONFIRMED, Helper.booleanToInt(confirmed));
		args.put(KEY_PSD_IGNORED, Helper.booleanToInt(ignored));

		ProcessStateBean current = PSDgetTask(ProcessID, TaskID);
		return mDb.update(DATABASE_TABLE_PSD, args, KEY_PSD_ROWID + "="
				+ current.getRowID(), null) > 0;

	}

	// PROCESS MAPPING DATABASE OPS
	public long PMDcreateProcessMap(int PID, String Name) {

		ContentValues values = new ContentValues();
		values.put(KEY_PMD_PROCESSID, PID);
		values.put(KEY_PMD_PROCESSNAME, Name);
		Log.i("DBAdapter", "PID=" + PID + " Name=" + Name);

		return mDb.insert(DATABASE_TABLE_PMD, null, values);

	}

	public boolean PMDdeleteProcessMap(int PID) {
		Log.i("DBAdapter", " deletePMD PID=" + PID);
		return mDb.delete(DATABASE_TABLE_PMD, KEY_PMD_PROCESSID + "=" + PID,
				null) > 0;
	}

	public String PMDgetName(int PID) {
		Cursor results = mDb.query(true, DATABASE_TABLE_PMD, new String[] {
				KEY_PMD_ROWID, KEY_PMD_PROCESSID, KEY_PMD_PROCESSNAME },
				KEY_PMD_PROCESSID + "=" + PID, null, null, null, null, null);
		if (results.getCount() > 0) {
			results.moveToFirst();
			return new ProcessMappingBean(results.getInt(PMD_ROWID_INDEX),
					results.getInt(PMD_PROCESSID_INDEX),
					results.getString(PMD_PROCESSNAME_INDEX)).getProcessName();
		}
		return null;
	}

	public List<ProcessMappingBean> PMDgetProcessMappings() {
		Cursor results = mDb.query(true, DATABASE_TABLE_PMD, new String[] {
				KEY_PMD_ROWID, KEY_PMD_PROCESSID, KEY_PMD_PROCESSNAME }, null,
				null, null, null, null, null);
		List<ProcessMappingBean> resultList = new ArrayList<ProcessMappingBean>();
		if (results.getCount() > 0) {
			results.moveToFirst();
			while (!results.isAfterLast()) {
				resultList.add(new ProcessMappingBean(results
						.getInt(PMD_ROWID_INDEX), results
						.getInt(PMD_PROCESSID_INDEX), results
						.getString(PMD_PROCESSNAME_INDEX)));
				results.moveToNext();
			}

			return resultList;

		}
		return null;
	}

	// PROCESS ANALYTICS DATABASE OPS
	public long PADcreateProcessAnalytic(int PID, int TID) {

		ContentValues values = new ContentValues();
		values.put(KEY_PAD_PROCESSID, PID);
		values.put(KEY_PAD_TASKID, TID);
		values.put(KEY_PAD_TASKSTART,"");
		values.put(KEY_PAD_TASKEND,"");
		values.putNull(KEY_PAD_PROCESSRATING);
		values.putNull(KEY_PAD_COMMENTS);

		Log.i("DBAdapter", "PID=" + PID + " TID=" + TID);

		return mDb.insert(DATABASE_TABLE_PAD, null, values);

	}

	public ProcessAnalyticsBean PADgetAnalytic(int ProcessID, int TaskID) {
		Cursor results = mDb.query(true, DATABASE_TABLE_PAD, new String[] {
				KEY_PAD_ROWID, KEY_PAD_PROCESSID, KEY_PAD_TASKID,
				KEY_PAD_TASKSTART, KEY_PAD_TASKEND, KEY_PAD_PROCESSRATING, KEY_PAD_COMMENTS },
				KEY_PAD_PROCESSID + "=" + ProcessID + " and " + KEY_PAD_TASKID
						+ "=" + TaskID, null, null, null, null, null);
		if (results.getCount() > 0) {
			results.moveToFirst();
			Log.i("DBAdapter", "getAnalytics pid " + results.getInt(PAD_PROCESSID_INDEX));
			Log.i("DBAdapter", "getAnalytics tid " + results.getInt(PAD_TASKID_INDEX));
			Log.i("DBAdapter", "getAnalytics start " + results.getString(PAD_TASKSTART_INDEX));
			Log.i("DBAdapter", "getAnalytics end " + results.getString(PAD_TASKEND_INDEX));

			return new ProcessAnalyticsBean(results.getInt(PAD_ROWID_INDEX),
					results.getInt(PAD_PROCESSID_INDEX),
					results.getInt(PAD_TASKID_INDEX), 
							Helper.StringToDateTime(results.getString(PAD_TASKSTART_INDEX)), Helper.StringToDateTime(
							results.getString(PAD_TASKEND_INDEX)),
					results.getInt(PAD_PROCESSRATING_INDEX),results.getString(PAD_COMMENTS_INDEX));
		}
		return null;

	}
	private boolean PADupdateAnalytic(int ProcessID, int TaskID, Date taskStart,
			Date taskEnd, int processRating, String comments) {
		ContentValues args = new ContentValues();
		args.put(KEY_PAD_PROCESSID, ProcessID);
		args.put(KEY_PAD_TASKID, TaskID);
		
		if(taskStart!=null){
			Log.i("DBAdapter", "update analytics " + String.valueOf(taskStart.getTime()));
		args.put(KEY_PAD_TASKSTART, Helper.DateTimeToString(taskStart));
		}else{
			args.put(KEY_PAD_TASKSTART,"");

		}
		if(taskEnd!=null){
		args.put(KEY_PAD_TASKEND, Helper.DateTimeToString(taskEnd));
		}else{
			args.put(KEY_PAD_TASKEND,"");

		}
		args.put(KEY_PAD_PROCESSRATING, processRating);
		args.put(KEY_PAD_COMMENTS, comments);

		ProcessAnalyticsBean current = PADgetAnalytic(ProcessID, TaskID);
		return mDb.update(DATABASE_TABLE_PAD, args, KEY_PAD_ROWID + "="
				+ current.getRowID(), null) > 0;

	}

	public boolean PADstartTask(int ProcessID, int TaskID, Date startTime) {
		ProcessAnalyticsBean current = PADgetAnalytic(ProcessID, TaskID);
		return PADupdateAnalytic(current.getProcessID(), current.getTaskID(), startTime, current.getTaskEnd(), current.getProcessRating(),current.getComments());
	}
	
	public boolean PADendTask(int ProcessID, int TaskID,Date endTime) {
		ProcessAnalyticsBean current = PADgetAnalytic(ProcessID, TaskID);
		return PADupdateAnalytic(current.getProcessID(), current.getTaskID(), current.getTaskStart(), endTime, current.getProcessRating(),current.getComments());
	}
	
	public boolean PADrateProcess(int ProcessID, int rating) {
		ContentValues args = new ContentValues();
		Log.i("DBAdapter", "rating in int = " + rating);
		args.put(KEY_PAD_PROCESSRATING, rating);
		return mDb.update(DATABASE_TABLE_PAD, args, KEY_PAD_PROCESSID + "="
				+ ProcessID, null) > 0;
	}
	
	public boolean PADCommentProcess(int ProcessID, String comments) {
		ContentValues args = new ContentValues();
		args.put(KEY_PAD_COMMENTS, comments);
		return mDb.update(DATABASE_TABLE_PAD, args, KEY_PAD_PROCESSID + "="
				+ ProcessID, null) > 0;
	}
	
	public boolean PADdeleteAnalytics(int ProcessID){
		return mDb.delete(DATABASE_TABLE_PAD, KEY_PAD_PROCESSID + "=" + ProcessID,
				null) > 0;
	}
	
	//PROCESS SETTINGS DATABASE
	
	public long PODcreateSetting(ProcessSettingsBean setting){
		
		ContentValues values = new ContentValues();
		values.put(KEY_POD_SETTINGID, setting.getSettingID());
		values.put(KEY_POD_SETTINGVALUE, setting.getSettingValue());
		

		Log.i("DBAdapter", "SID=" + setting.getSettingID() + " SVID=" + setting.getSettingValue());

		return mDb.insert(DATABASE_TABLE_POD, null, values);

	}
	
	public ProcessSettingsBean PODgetSetting(int SettingID) {
		Cursor results = mDb.query(true, DATABASE_TABLE_POD, new String[] {
				KEY_POD_ROWID, KEY_POD_SETTINGID, KEY_POD_SETTINGVALUE },
				KEY_POD_SETTINGID + "=" + SettingID, null, null, null, null, null);
		if (results.getCount() > 0) {
			results.moveToFirst();
			return new ProcessSettingsBean(results.getInt(POD_ROWID_INDEX),
					results.getInt(POD_SETTINGID_INDEX),
					results.getInt(POD_SETTINGVALUE_INDEX));
		}
		return null;

	}
	public boolean PODupdateSetting(int SettingID, int SettingValue) {
		ContentValues args = new ContentValues();
		args.put(KEY_POD_SETTINGID, SettingID);
		args.put(KEY_POD_SETTINGVALUE, SettingValue);
	
		ProcessSettingsBean current = PODgetSetting(SettingID);
		return mDb.update(DATABASE_TABLE_POD, args, KEY_POD_ROWID + "="
				+ current.getRowID(), null) > 0;

	}
	
	public boolean PODdeleteSetting(int SettingID) {
		Log.i("DBAdapter", " deletePOD SettingID=" + SettingID);
		return mDb.delete(DATABASE_TABLE_POD, KEY_POD_SETTINGID + "=" + SettingID,
				null) > 0;
	}
	
	
	//PROCESS CACHE DATABASE
	
	public long PCDcreateProcessCache(ProcessCacheBean cache){

		ContentValues values = new ContentValues();
		values.put(KEY_PCD_PROCESSID, cache.getProcessID());
		values.put(KEY_PCD_TASKID, cache.getTaskID());
		

		Log.i("DBAdapter", "PID=" + cache.getProcessID() + " TID=" + cache.getTaskID());

		return mDb.insert(DATABASE_TABLE_PCD, null, values);
	}
	
	public List<ProcessCacheBean> PCDgetCache(int ProcessID){
		List<ProcessCacheBean> resultList= new ArrayList<ProcessCacheBean>();
		Cursor results = mDb.query(true, DATABASE_TABLE_PCD, new String[] {
				KEY_PCD_ROWID, KEY_PCD_PROCESSID, KEY_PCD_TASKID },
				KEY_PCD_PROCESSID + "=" + ProcessID, null, null, null, null, null);
		if (results.getCount() > 0) {
			results.moveToFirst();
			while(!results.isAfterLast()){
				ProcessCacheBean cache = new ProcessCacheBean(results.getInt(PCD_ROWID_INDEX),
					results.getInt(PCD_PROCESS_INDEX),
					results.getInt(PCD_TASKID_INDEX));
				resultList.add(cache);
				results.moveToNext();
			}

		}
		return resultList;
	}

	public boolean PCDdeleteProcessCache(int ProcessID){
		Log.i("DBAdapter", " deletePCD ProcessID=" + ProcessID);
		return mDb.delete(DATABASE_TABLE_PCD, KEY_PCD_PROCESSID + "=" + ProcessID,
				null) > 0;
	}
	
	
	
}
