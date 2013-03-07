package edu.gatech.clairvoyance.remote.junit;

import static org.junit.Assert.*;

import org.junit.Test;
import edu.gatech.clairvoyance.remote.*;

public class TestRemoteFileSystem {

	@Test
	public void testConnect() {
		System.out.println("Start testing...");
		RemoteFileSystem rfs=new RemoteFileSystem("invent.cc.gt.atl.ga.us");
		System.out.println("RemoteFileSystem initialized.");
		rfs.setUserConfidentials("clair", "123");
		System.out.println("Set confidentials successfully.");
		assertTrue(rfs.connect());
		System.out.println("Remote file system connected");
		System.out.println("Initial directory: "+rfs.getCurrentDirecotry());
		rfs.disconnect();
		
	}

}
