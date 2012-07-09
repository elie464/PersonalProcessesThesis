package au.edu.unsw.cse.model;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class CommentList {
	
	@ElementList
	private List<String> comments;
	
	public CommentList(){
		
	}
	
	public CommentList(List<String> comments){
		this.comments = comments;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	};
	
	
}
