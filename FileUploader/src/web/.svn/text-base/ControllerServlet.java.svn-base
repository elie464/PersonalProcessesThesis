package web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import web.Command;
/**
 * Controller Servlet that accepts all client requests and performs the
 * lookup required to process the request. This class uses the command
 * design pattern to find the required <i>Command</i> class that will
 * process the request.
 * 
 * @author  $author
 * @version $Revision
 */
public class ControllerServlet extends HttpServlet {
	
	private Map<String, Command> commands;
	
	/** Initializes the servlet.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		commands = new HashMap<String, Command>();
		commands.put("uploadFile", new UploadCommand()); //operation=ViewerRegister
		
	}

	
	/** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		   
		Command cmd = resolveCommand(request, response);
		String next = cmd.execute(request, response);
		if (next.indexOf('.') < 0) {
			cmd = (Command) commands.get(next);
			next = cmd.execute(request, response);
		}		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(next);
		dispatcher.forward(request, response);
	}
	
	
	private Command resolveCommand(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Command cmd = (Command) commands.get(request.getParameter("operation"));
		if (cmd == null) {
			cmd = (Command) commands.get("PAGE_NOT_FOUND");
		}
		return cmd;
	}
	
	/** Handles the HTTP <code>GET</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		processRequest(request, response);

	}
	
	/** Handles the HTTP <code>POST</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		processRequest(request, response);
	}
	
	/** Returns a short description of the servlet.
	 */
	public String getServletInfo() {
		return "This servlet implements a command pattern for a upload application";
	}
	
}
