package edu.gatech.clairvoyance.xml;

public class WorkLoad {
	
	private String directory;
	private String workload;
	private boolean readonly;
	
	public WorkLoad(String directory,String workload,boolean readonly){
		this.directory=directory;
		this.workload=workload;
		this.readonly=readonly;
	}
	
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getWorkload() {
		return workload;
	}
	public void setWorkload(String workload) {
		this.workload = workload;
	}
	public boolean isReadonly() {
		return readonly;
	}
	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}
	
	public String toString(){
		StringBuilder buffer=new StringBuilder();
		buffer.append("<workload directory=\"");
		buffer.append(directory);
		buffer.append("\" name=\"");
		buffer.append(workload);
		buffer.append("\" readonly=\"");
		buffer.append(readonly);
		buffer.append("\" />\n");
		return buffer.toString();
	}
	
	public boolean equals(Object o){
		if(!(o instanceof WorkLoad)){
			return false;
		}
		WorkLoad w=(WorkLoad)o;
		return w.readonly==this.readonly && w.directory.equals(this.directory) && w.workload.equals(this.workload);
	}

}
