package edu.gatech.clairvoyance.remote.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;
import edu.gatech.clairvoyance.remote.*;

/**
 * Issues found: sometimes remote actions (connecting, changing directory) fail and then succeed on retry.
 * @author xiangyu
 *
 */
public class TestRemoteFileSystem {
	
	private RemoteFileSystem rfs;
	
	@Before
	public void setup(){
		//rfs=new RemoteFileSystem("invent.cc.gt.atl.ga.us");
		rfs=new RemoteFileSystem("killerbee1.cc.gatech.edu");
		//rfs.setUserConfidentials("clair", "123"); 
		rfs.setUserConfidentials("xli354", "Cappucc1n@"); 
	}
	
	@After
	public void tearDown(){
		rfs.disconnect();
		rfs=null;
	}

	@Test
	public void testConnect() {
		System.out.println("****Test connect****");
		while(!rfs.connect()){
			System.out.println("Warning: connecting failed, retry...");
		}
		assertTrue(rfs.isConnected());
		System.out.println("Remote file system connected");
		System.out.println("Initial directory: "+rfs.getCurrentDirecotry());
		assertTrue("/home/clair".equals(rfs.getCurrentDirecotry())); 
		
	}
	@Test
	public void testChangeDirAbsolute(){
		System.out.println("****Test change directory absolute****");
		while(!rfs.connect()){
			System.out.println("Warning: connecting failed, retry...");
		}
		while(!rfs.changeDirectory("/home/xiangyu")){
			System.out.println("Warning: changing directory failed, retry...");
			//Ensure when changing directory fails, any change is safely undone.
			assertTrue("/home/clair".equals(rfs.getCurrentDirecotry()));
		}
		
		assertTrue("/home/xiangyu".equals(rfs.getCurrentDirecotry()));
		System.out.println("Current directory: "+rfs.getCurrentDirecotry());
		
	}
	
	@Test
	public void testChangeDirRelative(){
		System.out.println("****Test change directory relative****");
		while(!rfs.connect()){
			System.out.println("Warning: connecting failed, retry...");
		}
		while(!rfs.changeDirectory("/home/xiangyu")){
			System.out.println("Warning: changing directory failed, retry...");
			//Ensure when changing directory fails, any change is safely undone.
			assertTrue("/home/clair".equals(rfs.getCurrentDirecotry()));
		}
		
		
		assertTrue("/home/xiangyu".equals(rfs.getCurrentDirecotry()));
		System.out.println("Current directory: "+rfs.getCurrentDirecotry());
		
	}
	
	@Test
	public void testChangeInvalidDir(){
		System.out.println("****Test change direcotry to non-existence path****");
		while(!rfs.connect()){
			System.out.println("Warning: connecting failed, retry...");
		}
		for(int i=0;i<5;i++){
			assertFalse(rfs.changeDirectory("/someNonExistPath"));
			
			assertTrue("/home/clair".equals(rfs.getCurrentDirecotry()));
		}
	}
	
	@Test
	public void testListDirectory(){
		System.out.println("****Test directory listing****");
		while(!rfs.connect()){
			System.out.println("Warning: connecting failed, retry...");
		}
		while(!rfs.changeDirectory("../xiangyu")){
			System.out.println("Warning: changing directory failed, retry...");
			//Ensure when changing directory fails, any change is safely undone.
			assertTrue("/home/clair".equals(rfs.getCurrentDirecotry()));
		}
		ArrayList<String> files=rfs.listAll();
		for(String file:files){
			System.out.println(file);
		}
		
	}
	@Test
	public void testListFiles(){
		System.out.println("****Test listing files****");
		while(!rfs.connect()){
			System.out.println("Warning: connecting failed, retry...");
		}
		while(!rfs.changeDirectory("../xiangyu")){
			System.out.println("Warning: changing directory failed, retry...");
			//Ensure when changing directory fails, any change is safely undone.
			assertTrue("/home/clair".equals(rfs.getCurrentDirecotry()));
		}
		ArrayList<String> files=rfs.listFiles();
		for(String file:files){
			System.out.println(file);
		}
	}
	
	@Test
	public void testListSubdirs(){
		System.out.println("****Test listing subdirectories****");
		while(!rfs.connect()){
			System.out.println("Warning: connecting failed, retry...");
		}
		while(!rfs.changeDirectory("../xiangyu")){
			System.out.println("Warning: changing directory failed, retry...");
			//Ensure when changing directory fails, any change is safely undone.
			assertTrue("/home/clair".equals(rfs.getCurrentDirecotry()));
		}
		ArrayList<String> files=rfs.listSubdirectories();
		for(String file:files){
			System.out.println(file);
		}
	}

}
