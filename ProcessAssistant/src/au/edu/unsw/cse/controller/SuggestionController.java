package au.edu.unsw.cse.controller;

import java.util.List;

import au.edu.unsw.cse.model.graph.Task;

public interface SuggestionController {
	
	List<Task> suggest (List<Task> tasks);

}
