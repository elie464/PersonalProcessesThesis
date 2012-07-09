package au.edu.unsw.cse.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import au.edu.unsw.cse.controller.helper.Helper;
import au.edu.unsw.cse.controller.helper.ProcessAssistantException;
import au.edu.unsw.cse.model.graph.Task;
import au.edu.unsw.cse.model.graph.Process;

public class SimpleSuggestionController implements SuggestionController {

	Context appContext;
	static final int LIMIT = 3;
	ProcessController PC;
	
	
	public SimpleSuggestionController(List<Process> processesParcel, Context appContext) {
		super();
		this.appContext = appContext;
		PC = new ProcessController(appContext, processesParcel);

	}
	



	@Override
	public List<Task> suggest(List<Task> tasks) {
		

		
		
		List<Task> suggestedTasks = new ArrayList<Task>();
		//suggestedTasks=PC.getAllTasks();
		suggestedTasks = PC.getAllValidTasks();
		suggestedTasks = PC.getTimeValidTasks(suggestedTasks); //need to fix date year issue in parser service
		suggestedTasks = PC.getInternetValidTasks(suggestedTasks);
		
		suggestedTasks = PC.sortTasksbyLocation(suggestedTasks);
		//suggestedTasks = smartSort(suggestedTasks, PC);
		int size=suggestedTasks.size();
		
		return suggestedTasks.subList(0, Math.min(4, size));

		
//		return suggestedTasks;
	}
	
	
	public List<Task> smartSort(List<Task> tasks, ProcessController PC){
		 
		tasks = PC.sortTasksbyTime(tasks);
		Task task= null;
		Task task2 =null;
		for (int i=0; i<tasks.size();i++){
			task = tasks.get(i);
			for(int j=i+1; j<tasks.size()-1;j++){
				task2 =tasks.get(j);
				if(compareDeadlines(task,task2)==0){
					if(compareLocation(task,task2)==1){
						Collections.swap(tasks, i, j);
					}
				}
			}
			
			
		}
		
		
		return tasks;
	}
	
	int compareLocation (Task t1, Task t2){
		
		int distance1=PC.getDistanceToTaskInMetres(t1);
		int distance2=PC.getDistanceToTaskInMetres(t2);
		if(distance2<distance1){
			return 1;
		}
		if(distance1 ==distance2){
			return 0;
		}
		
		return -1;
		
	}
	
	int compareDeadlines(Task t1, Task t2){
		
		
		
		return 0;
	}
}
