package edu.gatech.clairvoyance.xml;

public class DBInfo {
	
	private String username;
	private String password;
	private String url;
	private String dbclass;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDbclass() {
		return dbclass;
	}
	public void setDbclass(String dbclass) {
		this.dbclass = dbclass;
	}
	public String toString(){
		StringBuilder buffer=new StringBuilder();
		buffer.append("\n");
		buffer.append(XmlHelper.fromTag("username", username));
		
		buffer.append(XmlHelper.fromTag("password", password));
		
		buffer.append(XmlHelper.fromTag("url", url));
		
		buffer.append(XmlHelper.fromTag("dbclass", dbclass));
		
		return XmlHelper.fromTag("dbinfo", buffer.toString());
	}

}
