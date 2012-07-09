package au.edu.unsw.cse.model;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;



@Root
public class ProcessMetaDataBeanList {
	@ElementList
	private List<ProcessMetaDataBean> processesList;
	
	
	public ProcessMetaDataBeanList() {
		super();
		
	}


	/**
	 * @return the processesList
	 */
	public List<ProcessMetaDataBean> getProcessesList() {
		return processesList;
	}


	/**
	 * @param processesList the processesList to set
	 */
	public void setProcessesList(List<ProcessMetaDataBean> processesList) {
		this.processesList = processesList;
	}


	public ProcessMetaDataBeanList(List<ProcessMetaDataBean> processesList) {
		super();
		this.processesList = processesList;
	}


	public ProcessMetaDataBean findProcessbyID(int id){
		for (ProcessMetaDataBean process : processesList) {
			if (process.getProcessID()==id){
				return process;
			}
		}
		return null;
		
	}
}
