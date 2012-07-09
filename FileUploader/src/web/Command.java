/*
 * Command.java
 *
 * Created on 9 August 2003, 11:03
 */

package web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author  
 */
public interface Command {
	
	String execute(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException;
	
}
