package edu.gatech.clairvoyance.xml;

public class Node {
	
	private String name;
	private String ipString;
	
	public Node(String name,String ip){
		this.name=name;
		this.ipString=ip;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIpString() {
		return ipString;
	}
	public void setIpString(String ipString) {
		this.ipString = ipString;
	}

	public String toString(){
		StringBuilder buffer=new StringBuilder();
		buffer.append("\n");
		buffer.append(XmlHelper.fromTag("name", name));
		
		buffer.append(XmlHelper.fromTag("ip", ipString));
		
		return XmlHelper.fromTag("node", buffer.toString());
	}
	
	public boolean equals(Object o){
		if(!(o instanceof Node)){
			return false;
			
		}
		Node n=(Node) o;
		return n.getName().equals(this.getName());
	}
}
