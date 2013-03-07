package edu.gatech.clairvoyance.remote;
import java.io.*;
import java.util.Arrays;

import com.jcraft.jsch.*;

public class RemoteFileSystem {
	private String hostName;
	private String username;
	private String password;
	private Session session;
	private Channel shell;
	private PrintWriter output;
	private BufferedReader input;
	private String currentDir;
	
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
	
	public boolean connect(){
		shell=getShellChannel();
		if(shell == null){
			return false;
		}
		
		
		try {
			output=new PrintWriter(shell.getOutputStream(),true);
		} catch (IOException e) {
			
			e.printStackTrace();
			disconnect();
			return false;
		}
		try {
			input=new BufferedReader(new InputStreamReader(shell.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
			return false;
		}
		output.println("pwd");
		System.out.println("Getting current directory from the server.");
		for(int i=0;i<5;++i){
			System.err.println("Flushing initial message("+i+"): ");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clearInputStream();
			
		}
		return true;
		/*
		
		
		try {
			System.out.println("Waiting for server's response");
			currentDir=input.readLine();
			System.out.println("Got current directory name");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			disconnect();
			return false;
		}
		//There shouldn't be anything else in the input stream.
		//Check it just for safety.
		System.out.println("Clearing input stream");
		if(clearInputStream()){
			System.err.println("Warning: Unexpected response from the remote server");
		}
		return true;
		*/
	}
	public void disconnect(){
		if(session == null){
			return;
		}
		session.disconnect();
		shell=null;
		input=null;
		output=null;
		session=null;
		currentDir=null;
	}
	
	public boolean isConnected(){
		return session != null;
	}
	
	public String getCurrentDirecotry(){
		if(isConnected()){
			return currentDir;
		}else{
			throw new IllegalStateException("Not conneted to remote file system.");
		}
	}
	
	private Channel getShellChannel(){
		assert(hostName != null);
		if(username == null || password == null ){
			throw new IllegalStateException("User confidential is not specified.");
		}
		JSch jsch=new JSch();
		System.out.println("Jsch instance initialized.");
		try {
			session=jsch.getSession(username, hostName);
			System.out.println("Jsch session created.");
		} catch (JSchException e) {
			
			e.printStackTrace();
			session=null;
			return null;
		}
		session.setPassword(password);
		session.setConfig("StrictHostKeyChecking", "no");
		try {
			session.connect(30000);
			System.out.println("Jsch session connected.");
		} catch (JSchException e) {
			
			e.printStackTrace();
			session=null;
			return null;
		}
		Channel shell=null;
		try {
			shell=session.openChannel("shell");
			
			shell.connect(); 
			System.out.println("Shell channel opened");
		} catch (JSchException e) {
			
			e.printStackTrace();
			session.disconnect();
			session=null;
			return null;
		}
		
		return shell;
	}
	
	private boolean clearInputStream(){
		boolean result=false;
		char[] buffer=new char[128];
		try {
			while(input.ready()){
				input.read(buffer);
				System.err.print(new String(buffer));
				Arrays.fill(buffer, (char) 0);
				result=true;
			}
			if(result){
				System.err.println();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("IOException when clearing input stream");
		}
		return result;
	}

}
