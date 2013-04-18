package edu.gatech.clairvoyance.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Data {
	private String rootDir;
	private List<Directory> subDirs;
	private HashMap<String, Directory> name2dir;
	
	public Data(){
		rootDir = null;
		subDirs = new ArrayList<Directory>();
		name2dir = new HashMap<String, Directory>();
	}
	
	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	public List<Directory> getSubDirs() {
		return subDirs;
	}

	public void setSubDirs(List<Directory> subDirs) {
		this.subDirs = subDirs;
	}
	
	public void addSubDir(Directory dir){
		if(!name2dir.containsKey(dir.getDirName())){
			this.subDirs.add(dir);
			name2dir.put(dir.getDirName(), dir);
		}
	}
}
