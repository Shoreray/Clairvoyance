package edu.gatech.clairvoyance.remote;
import java.io.*;
import java.util.ArrayList;
import com.jcraft.jsch.*;

public class RemoteFileSystem {
	private String hostName;
	private String username;
	private String password;
	private Session session;
	//There is a bug (problem) in some OpenSSH implementation that if all open channels are closed,
	//the session will be automatically closed.
	private Channel keepAlive;
	private String currentDir;
	
	public RemoteFileSystem(String hostName){
		setHostName(hostName);
	}
	
	public RemoteFileSystem(String hostName, String username, String password){
		this(hostName);
		setUserConfidentials(username,password);
	}
	
	public void setHostName(String hostname){
		if (isConnected()) {
			throw new IllegalStateException(
					"Cannot set host name because the connection is already established.\nIf you want to change the server, please disconnect first.\n");
		}
		if(hostname == null){
			throw new IllegalArgumentException("Host name should not be null.");
		}
		this.hostName=hostname;
	}
	
	public void setUserConfidentials(String username, String password){
		if (isConnected()) {
			throw new IllegalStateException(
					"Cannot set user confidential while the connection is already established.\nIf you want to change user, please disconnect first.\n");
		}
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
		if(isConnected()){
			throw new IllegalStateException("The remote file system is already connected.");
		}
		if(!startSession()){
			disconnect();
			return false;
		}
		ExecResult result=null;
		try {
			result = executeCommand("pwd");
		} catch (Exception e) {
			
			e.printStackTrace();
			disconnect();
			return false;
		}
		
		if(result.exitCode != 0){
			disconnect();
			return false;
		}
		
		
		currentDir=result.getOutput().get(0);
		
		return true;
		
	}
	public void disconnect(){
		if(session == null){
			return;
		}
		session.disconnect();
		
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
	/**
	 * This function may expose security risks.
	 * We may need to impose more strict check on the directoryName parameter.
	 * The severity is not very high because what the user can do is still confined 
	 * by its privileges associated with the SSH user.
	 * 
	 * @param directoryName
	 * @return
	 */
	public boolean changeDirectory(String directoryName){
		if(directoryName.contains("\"") || directoryName.contains("\n") ||directoryName.contains("\r")){
			throw new IllegalArgumentException("Directory path cannot contain quotes or line feeds.");
		}
		String oldPath=currentDir;
		if(directoryName.startsWith("/")){
			//Absolute path
			currentDir=directoryName;
		}else{
			//Relative path
			currentDir=currentDir+"/"+directoryName;
		}
		ExecResult result=null;
		try {
			result=executeCommand("pwd");
		} catch (Exception e) {
			
			e.printStackTrace();
			currentDir=oldPath;
			return false;
		} 
		if(result == null || result.exitCode != 0){
			currentDir=oldPath;
			return false;
		}else{
			currentDir=result.getOutput().get(0);
			return true;
		}
	
		
	}
	
	public ArrayList<String> listAll(){
		ArrayList<String> files=listFiles();
		ArrayList<String> dirs=listSubdirectories();
		if(files != null){
			if(dirs != null){
				files.addAll(dirs);
			}
			return files;
		}else{
			return dirs;
		}
		
	}
	
	public ArrayList<String> listFiles(){
		ExecResult result=null;
		try {
			result=executeCommand("ls -l | grep ^-");
		} catch (JSchException | IOException e) {
			
			e.printStackTrace();
			return null;
		}
		if(result == null || result.exitCode != 0){
			return null;
		}
		ArrayList<String> files=new ArrayList<String>();
		for(String info:result.getOutput()){
			String[] fields=info.split("\\s+");
			files.add(fields[fields.length-1]);
		}
		return files;
	}
	
	public ArrayList<String> listSubdirectories(){
		ExecResult result=null;
		try {
			result=executeCommand("ls -l | grep ^d");
		} catch (JSchException | IOException e) {
			
			e.printStackTrace();
			return null;
		}
		if(result == null || result.exitCode != 0){
			return null;
		}
		ArrayList<String> files=new ArrayList<String>();
		for(String info:result.getOutput()){
			String[] fields=info.split("\\s+");
			files.add(fields[fields.length-1]);
		}
		return files;
		
	}
	
	private boolean startSession(){
		
		if(username == null || password == null ){
			throw new IllegalStateException("User confidential is not specified.");
		}
		JSch jsch=new JSch();
		//System.out.println("Jsch instance initialized.");
		try {
			session=jsch.getSession(username, hostName);
			//System.out.println("Jsch session created.");
		} catch (JSchException e) {
			
			e.printStackTrace();
			session=null;
			return false;
		}
		session.setPassword(password);
		session.setConfig("StrictHostKeyChecking", "no");
		try {
			session.connect(30000);
			//System.out.println("Jsch session connected.");
		} catch (Exception e) {
			
			e.printStackTrace();
			
			session=null;
			return false;
		}
		try {
			keepAlive=session.openChannel("shell");
			keepAlive.connect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session=null;
			return false;
		}
		
		return true;
	}
	
	ExecResult executeCommand(String command) throws JSchException, IOException{
		if(session == null){
			throw new IllegalStateException("Not conneted to remote file system.");
		}
		
		ChannelExec exec=(ChannelExec)session.openChannel("exec");
		
		if(currentDir != null){
			command="cd \""+currentDir+"\" && "+command;
		}
		exec.setCommand(command);
		exec.setInputStream(null);
		exec.setErrStream(null);
		InputStream inStream=exec.getInputStream();
		InputStream errStream=exec.getErrStream();
		exec.connect();
		ExecResult result=new ExecResult();
		
		//ArrayList<String> output=new ArrayList<String>();
		BufferedReader reader=new BufferedReader(new InputStreamReader(inStream));
		
		//String line=null;
		StringBuilder output=new StringBuilder();
		byte[] buffer=new byte[1024];
		int bytesRead=0;
		while((bytesRead=inStream.read(buffer))!=-1){
			output.append(new String(buffer,0,bytesRead));
		}
		result.rawOutput=output.toString();
		
		
		//reader=new BufferedReader(new InputStreamReader(errStream));
		//line=null;
		//ArrayList<String> errput=new ArrayList<String>();
		StringBuilder errput=new StringBuilder();
		bytesRead=0;
		while((bytesRead=errStream.read(buffer))!=-1){
			errput.append(new String(buffer,0,bytesRead));
		}
		result.rawErrput=errput.toString();
		
		//For testing only
		if(!exec.isClosed()){
			throw new RuntimeException("Unexpected situation where input streams are ended but channel is not closed.");
		}
		
		result.exitCode=exec.getExitStatus();
		return result;
	}
	
	ChannelExec getChannelExec() throws JSchException{
		if(!isConnected()){
			throw new IllegalStateException("Not connected to remote file system");
		}
		return (ChannelExec) session.openChannel("exec");
	}

}
