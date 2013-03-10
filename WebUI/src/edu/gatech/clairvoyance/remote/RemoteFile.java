package edu.gatech.clairvoyance.remote;

import java.io.IOException;
import java.util.ArrayList;

import com.jcraft.jsch.JSchException;

public class RemoteFile {
	
	private RemoteFileSystem rfs;
	private String path;
	private int size;
	private StringBuilder content=new StringBuilder();
	private int bytesRead=0;
	private int filePointer=0;
	//Buffer size determines how many bytes of data it will read from the server each time.
	private static final int BUFFER_SIZE=1024;
	
	
	public RemoteFile(RemoteFileSystem rfs, String path) throws JSchException, IOException{
		if(rfs == null){
			throw new IllegalArgumentException("Remote file must be associated with a remote file system.");
		}
		this.rfs=rfs;
		if(path == null || !path.startsWith("/") || path.endsWith("/")){
			throw new IllegalArgumentException("Invalid path: only unix-style absolute path is allowed.");
		}
		this.path=path;
		ExecResult result=null;
		try {
			result=rfs.executeCommand("ls -l -d "+path);
		}  catch(IllegalStateException e){
			throw new IllegalStateException("Remote file system already disconnected.");
		}
		if(result.exitCode==0){
			String fileDetails=result.getOutput().get(0);
			if(!fileDetails.startsWith("-")){
				throw new IllegalArgumentException("Specified path is a directory rather than a regular file");
			}
			String sizeStr=fileDetails.split(" ")[4];
			size=Integer.parseInt(sizeStr);
		}else{
			throw new IllegalArgumentException("File doesn't exist");
		}
		
	}
	
	public String getFileName(){
		String[] fields=path.split("/");
		if(fields.length<1){
			//This should not happen
			throw new RuntimeException("Unexpected file path.");
		}
		return fields[fields.length-1];
	}
	
	public String getPath(){
		return path;
		
	}
	
	public boolean seek(int pointer){
		if(pointer>=0 && pointer <=size-1){
			filePointer=pointer;
			return true;
		}else{
			return false;
		}
	}
	
	public char read(){
		return (char)0;
	}
	
	/**
	 * 
	 * @return
	 * 		Number of bytes fetched if the operation is successful. Each fetching operation attempts to fetch BUFFER_SIZE bytes of data.
	 * 		0 if the entire file has been fetched.
	 * 		-1 if any error occurred.
	 */
	private int fetchMoreContent(){
		
		try {
			if(bytesRead+BUFFER_SIZE <= size){
				ExecResult result=rfs.executeCommand("head -c "+(bytesRead+BUFFER_SIZE)+" \""+path+"\" | tail -c "+BUFFER_SIZE);
				if(result.exitCode == 0){
					content.append(result.rawOutput);
					bytesRead+=BUFFER_SIZE;
					return BUFFER_SIZE;
				}else{
					return -1;
				}
			}else if(bytesRead < size){
				ExecResult result=rfs.executeCommand("tail -c "+(size-bytesRead)+" \""+path+"\"");
				if(result.exitCode == 0){
					content.append(result.rawOutput);
					int r=size-bytesRead;
					bytesRead=size;
					return r;
				}else{
					return -1;
				}
			}else{
				return 0;
			}
			
			
		} catch (JSchException e) {
			
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			
			e.printStackTrace();
			return -1;
		} catch(IllegalStateException e){
			throw new IllegalStateException("Remote file system already disconnected.");
		}
		
	}

}
