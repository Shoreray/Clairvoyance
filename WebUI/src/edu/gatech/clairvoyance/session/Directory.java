package edu.gatech.clairvoyance.session;

public class Directory {
	private String dirName;
	private String name;
	private String mode;
	private String random;
	
	public Directory(String dirName){
		this.dirName = dirName;
		this.mode = "ReadOnly";
		this.random = "false";
		this.name = "";
	}
	
	public String getDirName() {
		return dirName;
	}
	
	public void setDirName(String dirName) {
		this.dirName = dirName;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public String getRandom() {
		return random;
	}
	
	public void setRandom(String random) {
		this.random = random;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String toString(){
		return dirName + " " + name + " " + mode;
	}
}
