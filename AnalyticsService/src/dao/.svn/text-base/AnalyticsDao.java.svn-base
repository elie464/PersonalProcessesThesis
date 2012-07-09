package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import au.edu.unsw.cse.model.AnalyticsDBBean;

public class AnalyticsDao {
	private static Connection con = null;

	public AnalyticsDao() {
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
			// con = DriverManager
			// .getConnection("jdbc:sqlite:/Users/prashanthanranjan/Documents/uni/comp9321/ThesisBdb");

			con = DriverManager
					.getConnection("jdbc:sqlite:/home/ddhoang/ThesisB/Servers/ThesisBdb");

		} catch (SQLException e) {
			System.err.println("Cant connect to DB");
			e.printStackTrace();
		}
	}

	public List<AnalyticsDBBean> getALLAnalytics() {
		ArrayList<AnalyticsDBBean> analytics = new ArrayList<AnalyticsDBBean>();
		Statement stat;
		try {
			if (con == null) {
				System.err.println("con is null");
			}

			System.err.println("GET ALL Analytics");

			stat = con.createStatement();

			ResultSet rs = stat.executeQuery("select * from Analytics;");
			while (rs.next()) {
				AnalyticsDBBean abean = new AnalyticsDBBean();
				abean.setAnalyticID(rs.getInt("AnalyticID"));
				abean.setDeviceID(rs.getString("DeviceID"));
				abean.setProcessID(rs.getInt("ProcessID"));
				abean.setTaskID(rs.getInt("TaskID"));
				abean.setTaskStart(new Date(rs.getLong("taskStart")));
				abean.setTaskEnd(new Date(rs.getLong("taskEnd")));
				abean.setProcessRating(rs.getInt("ProcessRating"));
				abean.setComment(rs.getString("Comment"));

				analytics.add(abean);

			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return analytics;
	}

	// public AnalyticsDBBean getAnalytic(int id) {
	//
	// cse.unsw.edu.au.repo.ProcessMetaDataBean p = null;
	//
	// Statement stat;
	// try {
	// if (con == null) {
	// System.err.println("con is null");
	// }
	//
	// System.err.println("GET  PROCESS");
	//
	// stat = con.createStatement();
	//
	// ResultSet rs = stat
	// .executeQuery("select * from Processes where ProcessID="
	// + String.valueOf(id) + ";");
	// while (rs.next()) {
	// ProcessBean pbean = new ProcessBean();
	// pbean.setListingID(rs.getInt("ProcessID"));
	// pbean.setName(rs.getString("name") + pbean.getListingID());
	// pbean.setAuthor(rs.getString("author"));
	// pbean.setUrl(rs.getString("url"));
	// pbean.setDateCreated(rs.getDate("dateCreated"));
	// pbean.setDescription(rs.getString("description"));
	// pbean.setFileSizeKB(rs.getInt("filesizeKB"));
	//
	// p = new cse.unsw.edu.au.repo.ProcessMetaDataBean(pbean);
	//
	// }
	// rs.close();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// return p;
	//
	// }

	public boolean deleteAnalytic(int DeviceId, int ProcessID) {

		boolean result = false;
		Statement stat;
		try {
			if (con == null) {
				System.err.println("con is null");
			}

			System.err.println("DELETE  Analytic");

			stat = con.createStatement();

			result = stat.execute("delete from Analytics where ProcessID="
					+ String.valueOf(ProcessID) + " and " + "DeviceID="
					+ String.valueOf(DeviceId) + ";");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public boolean addListing(AnalyticsDBBean analytic) {

		try {
			if (con == null) {
				System.err.println("con is null");
			}

			System.err.println("INSERT Analytic");
			// PreparedStatement prep = con
			//

			PreparedStatement prep = con
					.prepareStatement("insert into Analytics(DeviceID,ProcessID,TaskID,taskStart,taskEnd,ProcessRating,Comment) values(?,?,?,?,?,?,?);");

			prep.setString(1, analytic.getDeviceID());
			prep.setInt(2, analytic.getProcessID());
			prep.setInt(3, analytic.getTaskID());
			prep.setDate(4, analytic.getTaskStart());
			prep.setDate(5, analytic.getTaskEnd());
			
			if(analytic.getProcessRating()<0){
				prep.setNull(6, java.sql.Types.NULL);

			}else{
				prep.setInt(6, analytic.getProcessRating());

			}
			
			
			prep.setString(7, analytic.getComment());

			System.err.println(prep.toString());
			int n = 0;
			n = prep.executeUpdate();

			System.err.println(n);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;

		}
		return true;

	}
	
	public List<String> getComments(int PID){
		
		ArrayList<String> comments = new ArrayList<String>();
		Statement stat;
		try {
			if (con == null) {
				System.err.println("con is null");
			}

			System.err.println("GET ALL Analytics");

			stat = con.createStatement();

			ResultSet rs = stat.executeQuery("select distinct ProcessID,Comment from Analytics where ProcessID="+PID+";");
			while (rs.next()) {
				

				comments.add(rs.getString("Comment"));

			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return comments;
	}
	public int getAvgRating(int PID){
		Statement stat;
		int avg=0;
		try {
			if (con == null) {
				System.err.println("con is null");
			}

			System.err.println("GET ALL Analytics");

			stat = con.createStatement();

			ResultSet rs = stat.executeQuery("select avg(ProcessRating) from Analytics where ProcessID="+PID+";");
			avg = rs.getInt(1);
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return avg;
	}

}
