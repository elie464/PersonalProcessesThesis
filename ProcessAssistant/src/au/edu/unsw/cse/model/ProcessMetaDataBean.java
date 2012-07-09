package au.edu.unsw.cse.model;

import java.util.Date;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import au.edu.unsw.cse.model.graph.Process;

@Root
public class ProcessMetaDataBean implements Parcelable {
	
	@Element
	int ProcessID;
	
	@Element
	String Name;
	@Element
	String Author;
	@Element
	String Description;
	@Element
	String URL;
	@Element
	Date DateCreated;
	@Element
	int fileSizeKB;
	
	public static final Creator CREATOR = new Creator() {
		public ProcessMetaDataBean createFromParcel(Parcel in) {
			return new ProcessMetaDataBean (in);
		}

		@Override
		public ProcessMetaDataBean[] newArray(int size) {
			return new ProcessMetaDataBean[size];
		}
	};
	
	private ProcessMetaDataBean (Parcel in){
		readFromParcel(in);
	}
	
	public void readFromParcel(Parcel in) {
		this.ProcessID = in.readInt();
		this.Name = in.readString();
		this.Author = in.readString();
		this.Description = in.readString();
		this.URL = in.readString();
		this.DateCreated = new Date(in.readLong());
		this.fileSizeKB = in.readInt();
		
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags){
		out.writeInt(ProcessID);
		out.writeString(Name);
		out.writeString(Author);
		out.writeString(Description);
		out.writeString(URL);
		out.writeLong(DateCreated.getTime());
		out.writeInt(fileSizeKB);
	}
	

	public ProcessMetaDataBean(int processID, String name, String author,
			String description, String uRL, Date dateCreated, int fileSizeKB) {
		super();
		ProcessID = processID;
		Name = name;
		Author = author;
		Description = description;
		URL = uRL;
		DateCreated = dateCreated;
		this.fileSizeKB = fileSizeKB;
	}
	
	public ProcessMetaDataBean() {
		super();
	}

	/**
	 * @return the processID
	 */
	public int getProcessID() {
		return ProcessID;
	}
	/**
	 * @param processID the processID to set
	 */
	public void setProcessID(int processID) {
		ProcessID = processID;
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
	 * @return the uRL
	 */
	public String getURL() {
		return URL;
	}
	/**
	 * @param uRL the uRL to set
	 */
	public void setURL(String uRL) {
		URL = uRL;
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

}
