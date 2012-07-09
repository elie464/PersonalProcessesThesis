package web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ProcessBean;
import business.UploadService;




public class UploadCommand implements Command {

	private UploadService uploadService = new UploadService();
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ProcessBean process = new ProcessBean();
		process.setName(request.getParameter("name"));
		process.setAuthor(request.getParameter("author"));
		process.setUrl(request.getParameter("url"));
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date a = null;
		java.sql.Date sqlDate = null;
		try {
			a = dfm.parse(request.getParameter("created"));
			long t = a.getTime();
			sqlDate = new java.sql.Date(t);
			process.setDateCreated(sqlDate);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		process.setDescription(request.getParameter("description"));
		process.setFileSizeKB(new Integer(request.getParameter("filesize"))/1000);
		uploadService.addProcess(process);
		//System.err.println(process.toString());
		
		
		return "/success.jsp";
	}

}
