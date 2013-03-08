package edu.gatech.clairvoyance.remote.junit;

import static org.junit.Assert.*;

import org.junit.Test;
import com.jcraft.jsch.*;
import java.io.*;

public class TestJsch {

	@Test
	public void test() throws Exception {
		JSch jsch=new JSch();
		//Session session=jsch.getSession("clair", "invent.cc.gt.atl.ga.us");
		Session session=jsch.getSession("xli354", "killerbee1.cc.gatech.edu");
		//session.setPassword("123");
		session.setPassword("Cappucc1n@");
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();
		
		
		ChannelExec exec=null;
		exec=(ChannelExec)session.openChannel("exec");
		exec.setCommand("pwd;ls -l;");
		exec.setInputStream(null);
		exec.setErrStream(null);
		InputStream in=exec.getInputStream();
		InputStream errStream=exec.getErrStream();
		exec.connect(); 
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(in));
		BufferedReader err=new BufferedReader(new InputStreamReader(errStream));
		String line=null;
		
		while((line=err.readLine())!=null){
			System.err.println(line);
		}
		line=null;
		
		while((line=reader.readLine())!=null){
			System.out.println(line);
		}
		System.out.println("Exit code: "+exec.getExitStatus());
		exec.disconnect();
		
		/*exec=(ChannelExec)session.openChannel("exec");
		exec.setCommand("pwd");
		exec.connect();
		reader=new BufferedReader(new InputStreamReader(exec.getInputStream()));
		line=null;
		while((line=err.readLine())!=null){
			System.err.println(err);
		}
		line=null;
		while((line=reader.readLine())!=null){
			System.out.println(line);
		}
		exec.disconnect();
		
		
		session.disconnect();*/
	}

}
