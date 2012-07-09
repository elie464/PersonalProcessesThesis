package au.edu.unsw.cse.view.processes;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import au.edu.unsw.cse.R;
import au.edu.unsw.cse.controller.ProcessController;
import au.edu.unsw.cse.controller.ProcessStateDBController;
import au.edu.unsw.cse.controller.helper.Helper;
import au.edu.unsw.cse.controller.helper.ProcessAssistantException;
import au.edu.unsw.cse.model.graph.Task;

public class CustomTasksAdapter extends ArrayAdapter<Task>{
	
	private final List<Task> taskList;
	private final Context context;
	private final int VECTOR_TASK_POS = 1;
	private final int VECTOR_PROCESS_POS = 0;
	ProcessStateDBController stateController;
	ProcessController PC;
    private Map<Integer, Vector<Integer>> taskRowMapping;

	
	public CustomTasksAdapter(Context context, List<Task> taskList, ProcessStateDBController stateController, 
			Map<Integer, Vector<Integer>> taskRowMapping, ProcessController PC) {
		super(context, R.layout.tasks_layout, taskList);
		this.context = context;
		this.taskList = taskList;
		this.stateController = stateController;
    	this.taskRowMapping = taskRowMapping;
    	this.PC = PC;

	}
	
	static class ViewHolder {
		protected CheckedTextView checkbox;
		protected Button button;
		protected TextView processName;
		protected TextView distance;
		protected TextView days;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
////    	View rowView = null;
//		processID = processRowMapping.get(groupPosition);
//		taskID = masterRowMapping.get(processID).get(childPosition);
//    	
////    	if(convertView == null){
			int taskID = taskRowMapping.get(position).get(VECTOR_TASK_POS);
			int processID = taskRowMapping.get(position).get(VECTOR_PROCESS_POS);
			Task task = PC.getTask(processID, taskID);
	    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView= inflater.inflate(R.layout.tasks_only_layout, null);
//
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.checkbox = (CheckedTextView) rowView.findViewById(R.id.tasks_taskCheckbox);
			viewHolder.button = (Button) rowView.findViewById(R.id.tasks_confirmTaskButton);
			viewHolder.processName = (TextView) rowView.findViewById(R.id.tasks_processNameText);
			viewHolder.distance = (TextView) rowView.findViewById(R.id.tasks_distance);
			viewHolder.days = (TextView) rowView.findViewById(R.id.tasks_days);


//			
//			//onCheckBox click listener
//			viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//				
//				@Override
//				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//					
//					if(isChecked && viewHolder.checkbox.isEnabled()){
//						
//						ProcessStateBean state = (ProcessStateBean) viewHolder.button.getTag();
//						if(!stateController.isIgnored(state.getProcessID(), state.getTaskID())){
//							viewHolder.button.setVisibility(View.VISIBLE);
//							
//						}
//					}else{
//						viewHolder.button.setVisibility(View.GONE);
//					}
//				}
//			});
//			
//			//need to return false to register for context menu
//			viewHolder.checkbox.setOnLongClickListener(new View.OnLongClickListener() {
//	    		
//				@Override
//				public boolean onLongClick(View v) {
//					return false;
//				}
//
//			});
//			
//			//button click listener
//			viewHolder.button.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					
//					ProcessStateBean state = (ProcessStateBean) viewHolder.button.getTag();
//					//Log.i("CustomTasksAdapter", "Proceeding to confirm task with: PID = " + state.getProcessID() + " TID = " + state.getTaskID());
//					stateController.ConfirmTask(state.getProcessID(), state.getTaskID());
//					viewHolder.checkbox.setEnabled(false);
//					viewHolder.button.setVisibility(View.GONE);
//				}
//			});
//			
			rowView.setTag(viewHolder);	
			Log.i("CustomTasksAdapter", "Binding ProcessStateBean to button with TID = " + taskID);
			viewHolder.button.setTag(stateController.getTaskState(task.getProcessID(), taskID));
//
////    	}else{
////
////    		//after first setup
////    		rowView = convertView;
////    		((ViewHolder) rowView.getTag()).checkbox.setTag(stateController.getTaskState(processID, taskID));
////
////    	}
//    	
//    	
    	ViewHolder holder = (ViewHolder) rowView.getTag();
//    	
//    	//disable and check any already confirmed tasks, hide button
//    	holder.checkbox.setEnabled(!stateController.isConfirmed(processID, taskID));
//    	holder.checkbox.setChecked(stateController.isConfirmed(processID, taskID));
//    	if(!holder.checkbox.isEnabled()){
//        	holder.button.setVisibility(View.GONE);
//    	}
//    	
//    	//set color to red for ignored tasks, hide button
//    	else if(stateController.isIgnored(processID, taskID)){
//    		holder.checkbox.setBackgroundColor(0xFFFF6A6A);
//        	holder.button.setVisibility(View.GONE);
//
//    	}
//
    	//set text
		holder.checkbox.setText(task.getName());	
		holder.processName.setText(Helper.stripIdFromName(PC.getProcessMetaData(task.getProcessID()).getName(),task.getProcessID()));
		holder.button.setText("Confirm");
		holder.button.setEnabled(false);
		holder.distance.setVisibility(View.GONE);
		holder.days.setVisibility(View.GONE);

		//display distance
		int distance = PC.getDistanceToTaskInMetres(task);
		if(distance != -1){
			holder.distance.setVisibility(View.VISIBLE);
			if(distance > 1000){
				int distanceInKm = distance/1000;
				int cutoff = distance%1000;
			    
				holder.distance.setText("Distance to Task: " + String.valueOf(distanceInKm)+"." + String.valueOf(cutoff) + "km");

			}else{
				holder.distance.setText("Distance to Task: " + String.valueOf(distance) + "m");
			}
		}
		
		
		//display days to task end
		int days;
		try {
			days = PC.getDaysToTaskEndDate(task);
			holder.days.setVisibility(View.VISIBLE);

			if(days == 0){
				holder.days.setText("Due today");
			}
			
			else if(days <0){
				holder.days.setText("Overdue by " + String.valueOf((days*(-1))) + " days");
			}
			
			else{
				holder.days.setText("Due in " + String.valueOf(days) + " days");
			}
		} catch (ProcessAssistantException e1) {
			Log.i("CustomTasksAdapter", "No due days");
		}
		

		//display ignored tasks in different colour
		if(stateController.isIgnored(task.getProcessID(), taskID)){
			rowView.setBackgroundColor(0xFFDC143C);

    	}
		//enable button only if its valid
		else if(PC.isValid(task.getProcessID(), taskID)){
			holder.button.setEnabled(true);
		}
		
		rowView.setPadding(0, 0, 0, 10);
//
//    }
		return rowView;
}

	
}
