package au.edu.unsw.cse.view.processes;

import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.TextView;
import au.edu.unsw.cse.R;
import au.edu.unsw.cse.controller.ProcessController;
import au.edu.unsw.cse.controller.ProcessStateDBController;
import au.edu.unsw.cse.controller.helper.ProcessAssistantException;
import au.edu.unsw.cse.model.graph.Task;

/**
 * A simple adapter which maintains an ArrayList of photo resource Ids. 
 * Each photo is displayed as an image. This adapter supports clearing the
 * list of photos and adding a new photo.
 *
 */
public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    // Sample data set.  children[i] contains the children (String[]) for groups[i].

    private final Context context;
    private String[] groups;
    private String[][] children;
    private ProcessStateDBController stateController;
	private ProcessController PC;
    private Map<Integer, Integer>processRowMapping;
    private Map<Integer, Map<Integer, Integer>> masterRowMapping;
    private final int UNUSED_ID = -1;
    private int processID;
    private int taskID;
    
    
    public CustomExpandableListAdapter(Context context, String[] groups, String[][] children, 
    		ProcessStateDBController stateController, Map<Integer, Integer> processRowMapping, 
    		Map<Integer, Map<Integer, Integer>> masterRowMapping, ProcessController PC){
    	this.context = context;
    	this.groups = groups;
    	this.children = children;
    	this.stateController = stateController;
    	this.processRowMapping = processRowMapping;
    	this.masterRowMapping = masterRowMapping;
    	this.processID = UNUSED_ID;
    	this.taskID = UNUSED_ID;
    	this.PC = PC;

    }


    public Object getChild(int groupPosition, int childPosition) {
        return children[groupPosition][childPosition];
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        return children[groupPosition].length;
    }

    public TextView getGenericView() {
        // Layout parameters for the ExpandableListView
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 64);

        TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        // Center the text vertically
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        // Set the text starting position
        textView.setPadding(36, 0, 0, 0);
        return textView;
    }
    
	static class ViewHolder {
		protected CheckedTextView checkbox;
		protected Button button;
		protected TextView distance;
		protected TextView days;
	}

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
    	
//    	View rowView = null;
		processID = processRowMapping.get(groupPosition);	//find out processID based on processMapping
		taskID = masterRowMapping.get(processID).get(childPosition);
		Task task = PC.getTask(processID, taskID);
    	
//    	if(rowView == null){
	    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View rowView= inflater.inflate(R.layout.tasks_layout, null);

			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.checkbox = (CheckedTextView) rowView.findViewById(R.id.taskCheckbox);
			viewHolder.button = (Button) rowView.findViewById(R.id.confirmTaskButton);
			viewHolder.distance = (TextView) rowView.findViewById(R.id.distance);
			viewHolder.days = (TextView) rowView.findViewById(R.id.days);

			
//			//onCheckBox click listener
//			viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					
//				}
//			}); 
				
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
			
//			//need to return false to register for context menu
//			viewHolder.checkbox.setOnLongClickListener(new View.OnLongClickListener() {
//	    		
//				@Override
//				public boolean onLongClick(View v) {
//					return false;
//				}
//
//			});
			
//			//button click listener
//			viewHolder.button.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					
//					ProcessStateBean state = (ProcessStateBean) viewHolder.button.getTag();
//					//Log.i("CustomExpandableListAdapter", "Proceeding to confirm task with: PID = " + state.getProcessID() + " TID = " + state.getTaskID());
//					stateController.ConfirmTask(state.getProcessID(), state.getTaskID());
//					viewHolder.checkbox.setEnabled(false);
//					viewHolder.button.setVisibility(View.GONE);
//
//				}
//			});
			
			rowView.setTag(viewHolder);	
			Log.i("CustomExpandableListAdapter", "Binding ProcessStateBean to button with PID = " + processID + " TID = " + taskID);
			viewHolder.button.setTag(stateController.getTaskState(processID, taskID));
//    	}
//    	}else{
//
//    		//after first setup
//    		rowView = convertView;
//    		((ViewHolder) rowView.getTag()).checkbox.setTag(stateController.getTaskState(processID, taskID));
//
//    	}
    	
    	
    	ViewHolder holder = (ViewHolder) rowView.getTag();
    	
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

		holder.checkbox.setText(task.getName());
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
		
		
		if(stateController.isIgnored(processID, taskID)){
			rowView.setBackgroundColor(0xFFDC143C);

    	}
		else if(PC.isValid(processID, taskID)){
			holder.button.setEnabled(true);
		}
		
		rowView.setPadding(0, 0, 0, 10);

		return rowView;

    }
    


    public Object getGroup(int groupPosition) {
        return groups[groupPosition];
    }

    public int getGroupCount() {
        return groups.length;
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
    	
    	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	View rowView = inflater.inflate(R.layout.process_layout, parent, false);


    	TextView tv = (TextView) rowView.findViewById(R.id.tvParent);
    	tv.setText(getGroup(groupPosition).toString());
    	return rowView;
    	
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public boolean hasStableIds() {
        return true;
    }
    

}
