package edu.gatech.clairvoyance.remote.junit;

import static org.junit.Assert.*;

import org.junit.Test;
import edu.gatech.clairvoyance.remote.*;

public class TestRemoteFileSystem {
	
	private RemoteFileSystem rfs;
	
	public TestRemoteFileSystem(){
		rfs=new RemoteFileSystem("invent.cc.gt.atl.ga.us");
		rfs.setUserConfidentials("clair", "123"); 
		
	}

	@Test
	public void testConnect() {
		rfs.connect();
		assertTrue(rfs.isConnected());
		System.out.println("Remote file system connected");
		System.out.println("Initial directory: "+rfs.getCurrentDirecotry());
		rfs.disconnect();
		
	}
	@Test
	public void testChangeDirAbsolute(){
		rfs.connect();
		assertTrue(rfs.changeDirectory("/home/xiangyu"));
		assertTrue("/home/xiangyu".equals(rfs.getCurrentDirecotry()));
		System.out.println("Current directory: "+rfs.getCurrentDirecotry());
		rfs.disconnect();
	}
	
	@Test
	public void testChangeDirRelative(){
		rfs.connect();
		assertTrue(rfs.changeDirectory("../xiangyu"));
		assertTrue("/home/xiangyu".equals(rfs.getCurrentDirecotry()));
		System.out.println("Current directory: "+rfs.getCurrentDirecotry());
		rfs.disconnect();
	}
	
	

}
