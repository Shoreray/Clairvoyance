package edu.gatech.clairvoyance.remote;
import com.jcraft.jsch.*;
public class RemoteFileSystem {
	private String hostName;
	private String username;
	private String password;
	private Channel shell;
	
	public RemoteFileSystem(String hostName){
		setHostName(hostName);
	}
	
	public RemoteFileSystem(String hostName, String username, String password){
		this(hostName);
		setUserConfidentials(username,password);
	}
	
	public void setHostName(String hostname){
		if(hostname == null){
			throw new IllegalArgumentException("Host name should not be null.");
		}
		this.hostName=hostname;
	}
	
	public void setUserConfidentials(String username, String password){
		if(username == null){
			throw new IllegalArgumentException("Username should not be null.");
		}
		this.username=username;
		if(password == null){
			throw new IllegalArgumentException("Password should not be null.");
		}
		this.password=password;
	}
	
	private Channel getShellChannel(){
		assert(hostName != null);
		if(username == null || password == null ){
			throw new IllegalStateException("User confidential is not specified.");
		}
		JSch jsch=new JSch();
		
		return null;
	}

}
