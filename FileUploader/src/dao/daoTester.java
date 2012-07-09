package dao;

import beans.ProcessBean;

public class daoTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProcessDAO dao = new ProcessDAO();
		dao.addListing(new ProcessBean());

	}

}
