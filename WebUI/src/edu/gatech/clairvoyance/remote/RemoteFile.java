package edu.gatech.clairvoyance.remote;
import com.jcraft.jsch.*;
public class RemoteFile {
	
	private RemoteFileSystem rfs;
	private String path;
	
	public RemoteFile(RemoteFileSystem rfs, String path){
		this.rfs=rfs;
		this.path=path;
	}
	
	

}
