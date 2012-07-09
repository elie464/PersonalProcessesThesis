package au.edu.unsw.cse.controller.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.location.Location;
import android.util.Log;
import au.edu.unsw.cse.controller.ProcessController;
import au.edu.unsw.cse.controller.ProcessSettingsDBController;
import au.edu.unsw.cse.model.graph.Task;

public class Helper {

	static final String tag = "Helper";
	

	public static int booleanToInt(boolean value) {
		if (value) {
			return 1;
		} else
			return 0;
	}

	public static String DateToString(Date d) {
		DateFormat formatter;

		formatter = new SimpleDateFormat("dd-MMM-yyyy");

		String s = new StringBuilder(formatter.format(d)).toString();
		return s;
	}

	public static String DateTimeToString(Date d) {
		DateFormat formatter;

		formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
		formatter.setTimeZone(TimeZone.getTimeZone("EST"));

		String s = new StringBuilder(formatter.format(d)).toString();
		return s;
	}

	public static String TimeToString(Date d) {
		DateFormat formatter;

		formatter = new SimpleDateFormat("HH-mm");
		formatter.setTimeZone(TimeZone.getTimeZone("EST"));


		String s = new StringBuilder(formatter.format(d)).toString();
		return s;
	}

	public static Date StringToDate(String s) {
		DateFormat formatter;

		formatter = new SimpleDateFormat("dd-MMM-yyyy");
		formatter.setTimeZone(TimeZone.getTimeZone("EST"));

		Date d = new Date();

		try {
			d = formatter.parse(s);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return d;
	}
	public static Date StringToDateTime(String s) {
		
		if(s==""){
			return null;
		}
		DateFormat formatter;

		formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
		formatter.setTimeZone(TimeZone.getTimeZone("EST"));

		Date d = new Date();
		Log.i("Helper", "String to convert to date = " + s);
		try {
			d = formatter.parse(s);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return d;
	}
	

	public static String stripIdFromName(String processName, int id) {
		Integer ID = Integer.valueOf(id);

		String[] result = processName.split(String.valueOf(id));
		return result[0];
	}

	public static List<Task> sortLocationTasksByLocation(List<Task> tasks,
			final Location current) {

		if (current == null) {
			Log.i("Helper", "current is null");
		}
		List<Task> sortedTasks = tasks;
		Collections.sort(sortedTasks, new Comparator<Task>() {

			public int compare(Task t1, Task t2) {
				Location l1 = new Location(current);
				l1.setLatitude(t1.getLatitude());
				l1.setLongitude(t1.getLongtitude());

				Location l2 = new Location(current);
				l2.setLatitude(t2.getLatitude());
				l2.setLongitude(t2.getLongtitude());

				Float dist1 = l1.distanceTo(current);
				Float dist2 = l2.distanceTo(current);

				return dist1.compareTo(dist2);
			}
		});

		return sortedTasks;
	}

	public static Float distanceToTask(Task task, Location current) {
		// TODO
		if ((task.getLatitude() != 0) && (task.getLongtitude() != 0)) {
			Location l1 = new Location(current);
			l1.setLatitude(task.getLatitude());
			l1.setLongitude(task.getLongtitude());
			return l1.distanceTo(current);
		}

		return null;

	}

	public static List<Task> getInternetTasks(List<Task> tasks) {
		List<Task> internetTasks = new ArrayList<Task>();

		for (Task task : tasks) {
			if (task.isInternet()) {
				internetTasks.add(task);
			}
		}

		return internetTasks;
	}

	public static List<Task> getTimeValidTasks(List<Task> tasks) {
		Log.i(tag, "Get time valid tasks");
		

		List<Task> validTasks = new ArrayList<Task>();

		Date current = new Date();

		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		SimpleDateFormat hour = new SimpleDateFormat("HH");
		hour.setTimeZone(TimeZone.getTimeZone("EST"));
		year.setTimeZone(TimeZone.getTimeZone("EST"));

		// current>start && current+duration<end
		String currentHR = hour.format(current);

		for (Task task : tasks) {
			
			
			

			Date startTime = task.getStartTime();
			
			Date endTime = task.getEndTime();
			Date startDate = task.getStartDate();
			Date endDate = task.getEndDate();
			boolean hasStartDate = task.hasStartDate();
			boolean hasEndDate = task.hasEndDate();
			boolean hasStartTime = task.hasStartTime();
			boolean hasEndTime = task.hasEndTime();

			Log.i(tag, "Taskname = " + task.getName());
			Log.i(tag, "String startTime= "+TimeToString(startTime));
			Log.i(tag,"Start time= "+hour.format(startTime));
			Log.i(tag, "String endTime= "+TimeToString(endTime));
			Log.i(tag,"End time= "+hour.format(endTime));
			


			double durationMins = task.getDuration() * 60;

			if (((hasStartDate) && (hasEndDate))
					|| ((hasStartTime) && (hasEndTime))) {// check
																	// valid
				// dates
				// or
				// valid
				// times
				Log.i(tag, "Date or time is provided");


				if ((hasStartDate) && (hasEndDate)
						&& (!hasStartTime) && (!hasEndTime)) {// only
																		// date
																		// provided
					Log.i(tag, "Only Date is provided");

					if ((startDate.before(current)) && (endDate.after(current))) { // date
																					// is
																					// valid

						validTasks.add(task);
						Log.i(tag, "Date is valid");


					}
				}
				if ((!hasStartDate) && (!hasEndDate)
						&& (hasStartTime) && (hasEndTime)) {// only
																		// time
																		// provided
					Log.i(tag, "Only Time is provided");


					if ((startTime.before(current)) && (endTime.after(current))) { // time
																					// is
						if ((Integer.valueOf(currentHR) + task.getDuration()) < Integer
								.valueOf(hour.format(endTime))) { // valid
							validTasks.add(task);
							Log.i(tag, "Time is valid");


						}
					}

				}
				if ((hasStartDate) && (hasEndDate)
						&& (hasStartTime) && (hasEndTime)) {// date
																		// and
																		// time
																		// provided
					Log.i(tag, "Only Date & Time is provided");

					if ((task.getStartDate().before(current))
							&& (task.getEndDate().after(current))) { // time is
						Log.i(tag, "Date & Time is valid");
						Log.i(tag, "Current HR = "+currentHR);
						if ((Integer.valueOf(currentHR) + task.getDuration()) < Integer
								.valueOf(hour.format(endTime))) { // valid
							validTasks.add(task);
							
							Log.i(tag, "Date & Time+Duration is valid");

							


						}
					}
				}
			}
			if (((!hasEndTime) && (!hasEndDate))
					&& ((!hasStartTime) && (!hasEndTime))) {// nothing
																	// provided
				Log.i(tag, "Nothing is provided");
				Log.i(tag, "Nothing provided so valid");


				validTasks.add(task);
			}

		}
		return validTasks;
	}

	public static List<Task> sortTasksbyTime(List<Task> tasks) {
		List<Task> sortedTasks = new ArrayList<Task>();
		List<Task> validTasks = new ArrayList<Task>();
		List<Task> validTasks2 = new ArrayList<Task>();
		List<Task> validTasks3 = new ArrayList<Task>();
		List<Task> validTasks4 = new ArrayList<Task>();

		// Date current = new Date();

		// SimpleDateFormat year = new SimpleDateFormat("YYYY");
		// SimpleDateFormat hour = new SimpleDateFormat("HH");
		// current>start && current+duration<end

		for (Task task : tasks) {

			
			boolean hasStartDate = task.hasStartDate();
			boolean hasEndDate = task.hasEndDate();
			boolean hasStartTime = task.hasStartTime();
			boolean hasEndTime = task.hasEndTime();
			// String currentHR = hour.format(current);

			if (((hasStartDate) && (hasEndDate))
					|| ((hasStartTime) && (hasEndTime))) {// check
																	// valid
																	// dates
																	// or
																	// valid
																	// times

				if ((hasStartDate) && (hasEndDate)
						&& (!hasStartTime) && (!hasEndTime)) {// only
																		// date
																		// provided

					validTasks.add(task);

				}
				if ((!hasStartDate) && (!hasEndDate)
						&& (hasStartTime) && (hasEndTime)) {// only
																		// time
																		// provided

					validTasks2.add(task);

				}
				if ((hasStartDate) && (hasEndDate)
						&& (hasStartTime) && (hasEndTime)) {// date
																		// and
																		// time

					validTasks3.add(task);

				}
				if ((!hasStartDate) && (!hasEndDate)
						&& (!hasStartTime) && (!hasEndTime)) {// date
																		// and
																		// time

					validTasks4.add(task);

				}
			}

		}

		Collections.sort(validTasks, new Comparator<Task>() {

			public int compare(Task t1, Task t2) {
				return t1.getEndDate().compareTo(t2.getEndDate());
			}
		});

		Collections.sort(validTasks2, new Comparator<Task>() {

			public int compare(Task t1, Task t2) {
				return t1.getEndTime().compareTo(t2.getEndTime());
			}
		});

		Collections.sort(validTasks3, new Comparator<Task>() {

			public int compare(Task t1, Task t2) {
				Date endDateTime1 = Helper.combineDateTime(t1.getEndDate(),
						t1.getEndTime());
				Date endDateTime2 = Helper.combineDateTime(t2.getEndDate(),
						t2.getEndTime());

				return endDateTime1.compareTo(endDateTime2);
			}
		});

		sortedTasks.addAll(validTasks3);
		sortedTasks.addAll(validTasks2);
		sortedTasks.addAll(validTasks);
		sortedTasks.addAll(validTasks4);
		return sortedTasks;
	}

	public static Date combineDateTime(Date date, Date time) {

		SimpleDateFormat timeF = new SimpleDateFormat("HH-mm");
		SimpleDateFormat dateF = new SimpleDateFormat("DD-MM-yyyy");
		SimpleDateFormat dateTimeF = new SimpleDateFormat("DD-MM-yyyy-HH-mm");

		String dateStr = dateF.format(date);
		String timeStr = timeF.format(time);
		String result = dateStr + "-" + timeStr;

		Date resultDateTime = null;

		try {
			resultDateTime = dateTimeF.parse(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resultDateTime;
	}
	
	public static List<Task> getTasksBasedOnSettings(ProcessController PC,
			List<Task> tasks, ProcessSettingsDBController settingsController) {

		switch (settingsController.getSetting(ProcessSettingsDBController.VIEW_SETTING)) {
		
		case ProcessSettingsDBController.ALL_TASKS_VIEW:
			tasks = PC.getAllTasks();
			break;

		case ProcessSettingsDBController.VALID_TASKS_VIEW:
			tasks = PC.getAllValidTasks();
			break;
		case ProcessSettingsDBController.VALID_FUTURE_TASKS_VIEW:
			tasks = PC.getAllValidFutureTasks();
			break;
			
		default:
			break;
		}
		
		switch (settingsController.getSetting(ProcessSettingsDBController.VALID_TASKS_FILTER)) {
		case ProcessSettingsDBController.NO_VALID_FILTER:
			tasks = tasks;
			break;
			
		case ProcessSettingsDBController.INTERNET_VALID_TASKS:
			tasks = PC.getInternetValidTasks(tasks);
			break;
		case ProcessSettingsDBController.TIME_VALID_TASKS:
			tasks = PC.getTimeValidTasks(tasks);
			break;

		default:
			break;
		}
		
		switch (settingsController.getSetting(ProcessSettingsDBController.SORT_OPTION)) {
		case ProcessSettingsDBController.SORT_TIME:
			tasks = PC.sortTasksbyTime(tasks);
			break;
			
		case ProcessSettingsDBController.SORT_LOCATION:
			tasks = PC.sortTasksbyLocation(tasks);
			break;
		
		case ProcessSettingsDBController.NO_SORT:
			tasks = tasks;
			break;
		default:
			break;
		}
		return tasks;
	}
	
	/**
	 * 
	 * With processID
	 * 
	 * @param PC
	 * @param tasks
	 * @param settingsController
	 * @param ProcessID
	 * @return
	 */
	public static List<Task> getTasksBasedOnSettings(ProcessController PC,
			List<Task> tasks, ProcessSettingsDBController settingsController, int ProcessID) {

		switch (settingsController.getSetting(ProcessSettingsDBController.VIEW_SETTING)) {
		
		
		case ProcessSettingsDBController.ALL_TASKS_VIEW:
			tasks = PC.getTaskList(ProcessID);
			break;

		case ProcessSettingsDBController.VALID_TASKS_VIEW:
			tasks = PC.getValidTasksforProcess(ProcessID);
			break;
		case ProcessSettingsDBController.VALID_FUTURE_TASKS_VIEW:
			tasks = PC.getValidFutureTasksForProcess(ProcessID);
			break;
			
		default:
			break;
		}
		
		switch (settingsController.getSetting(ProcessSettingsDBController.VALID_TASKS_FILTER)) {
		case ProcessSettingsDBController.NO_VALID_FILTER:
			tasks = tasks;
			break;
			
		case ProcessSettingsDBController.INTERNET_VALID_TASKS:
			tasks = PC.getInternetValidTasks(tasks);
			break;
		case ProcessSettingsDBController.TIME_VALID_TASKS:
			tasks = PC.getTimeValidTasks(tasks);
			break;

		default:
			break;
		}
		
		return tasks;
	}
}
