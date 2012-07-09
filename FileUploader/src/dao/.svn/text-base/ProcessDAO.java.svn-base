package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import beans.ProcessBean;

public class ProcessDAO {
	//private DBConnectionFactory services; 
	private static Connection con=null;

	public ProcessDAO() {
		// TODO Auto-generated constructor stub

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// database path, if it's new data base it will be created in project
		// folder
		try {
			//con = DriverManager
					//.getConnection("jdbc:sqlite:/Users/prashanthanranjan/Documents/uni/comp9321/ThesisBdb");
			
			con = DriverManager
					.getConnection("jdbc:sqlite:/home/ddhoang/ThesisB/Servers/ThesisBdb");
			
		} catch (SQLException e) {
			System.err.println("Cant connect to DB");
			e.printStackTrace();
		}
	}

	
	public void addListing(ProcessBean process) {
		// Statement stat = null;
		// try {
		// stat = con.createStatement();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// try {
		// stat.executeUpdate("drop table Processes");
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		try {
			if (con==null){System.err.println("con is null");}
			
			System.err.println("INSERT");
//			PreparedStatement prep = con
//					.prepareStatement("insert into Processes(ProcessID,name,author,url,dateCreated,description,filesizeKB) values(?,?,?,?,?,?,?);");
//			prep.setInt(1, 1000);//need to create sequence and trigger for this
//			prep.setString(2, process.getName());
//			prep.setString(3, process.getAuthor());
//			prep.setString(4, process.getUrl());
//			prep.setDate(5, process.getDateCreated());
//			prep.setString(6, process.getDescription());
//			prep.setInt(7, process.getFileSizeKB());
			
			PreparedStatement prep = con
					.prepareStatement("insert into Processes(name,author,url,dateCreated,description,filesizeKB) values(?,?,?,?,?,?);");
			
			prep.setString(1, process.getName());
			prep.setString(2, process.getAuthor());
			prep.setString(3, process.getUrl());
			prep.setDate(4, process.getDateCreated());
			prep.setString(5, process.getDescription());
			prep.setInt(6, process.getFileSizeKB());
			
			
			
			System.err.println(prep.toString());
			int n=0;
			n=prep.executeUpdate();
			
			System.err.println(n);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}
