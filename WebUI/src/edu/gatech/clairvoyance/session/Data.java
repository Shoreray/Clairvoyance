package edu.gatech.clairvoyance.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Data {
	private String rootDir;
	private List<Directory> subDirs;
	private HashMap<String, Directory> name2dir;
	private HashMap<String, Node> nodeMap;
	private HashMap<String, Node> file2node;
	
	public Data(){
		rootDir = null;
		subDirs = new ArrayList<Directory>();
		name2dir = new HashMap<String, Directory>();
		nodeMap = new HashMap<String, Node>();
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
	
	public void addNode(Node node){
		if(!nodeMap.containsKey(node.getName())){
			this.nodeMap.put(node.getName(), node);
		}
	}
	
	public void addFile2Node(String file, String node){
		Node item = nodeMap.get(node);
		if(item != null && !file2node.containsKey(file)){
			file2node.put(file, item);
		}
	}
	
	public HashMap<String, Directory> getName2dir() {
		return name2dir;
	}

	public void setName2dir(HashMap<String, Directory> name2dir) {
		this.name2dir = name2dir;
	}

	public HashMap<String, Node> getNodeMap() {
		return nodeMap;
	}

	public void setNodeMap(HashMap<String, Node> nodeMap) {
		this.nodeMap = nodeMap;
	}
	
	public HashMap<String, Node> getFile2Node() {
		return file2node;
	}

	public void setFile2Node(HashMap<String, Node> file2node) {
		this.file2node = file2node;
	}
}
