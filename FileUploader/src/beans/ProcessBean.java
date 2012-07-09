package beans;

import java.sql.Date;

public class ProcessBean {

	private int ListingID;
	private String Name;
	private String Author;
	private String url;
	private Date DateCreated;
	private String Description;
	private int fileSizeKB;
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		ProcessBean toCompare = (ProcessBean)arg0;
		
		if (toCompare==null) return false;
		if (toCompare.Author!=this.Author) return false;
		if (toCompare.Name!=this.Name) return false;
		if (toCompare.url!=this.url) return false;
		if (toCompare.DateCreated!=this.DateCreated) return false;
		if (toCompare.Description!=this.Description) return false;
		if (toCompare.fileSizeKB!=this.fileSizeKB) return false;
		
		
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String ProcessString = "Name: "+Name+" Author: "+Author+" URL: "+url+" DateCreated: "+DateCreated+" Description: "+Description+" fileSizeKB: "+fileSizeKB+" ";
		return ProcessString;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return Name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		Name = name;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return Author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		Author = author;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return DateCreated;
	}
	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		DateCreated = dateCreated;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}
	/**
	 * @return the fileSizeKB
	 */
	public int getFileSizeKB() {
		return fileSizeKB;
	}
	/**
	 * @param fileSizeKB the fileSizeKB to set
	 */
	public void setFileSizeKB(int fileSizeKB) {
		this.fileSizeKB = fileSizeKB;
	}
	/**
	 * @return the listingID
	 */
	public int getListingID() {
		return ListingID;
	}
	/**
	 * @param listingID the listingID to set
	 */
	public void setListingID(int listingID) {
		ListingID = listingID;
	}
	
	
	
	
}
