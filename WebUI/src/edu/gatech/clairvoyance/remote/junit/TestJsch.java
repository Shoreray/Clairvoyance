package edu.gatech.clairvoyance.remote.junit;

import static org.junit.Assert.*;

import org.junit.Test;
import com.jcraft.jsch.*;
import java.io.*;

public class TestJsch {

	@Test
	public void test() throws Exception {
		JSch jsch=new JSch();
		Session session=jsch.getSession("clair", "invent.cc.gt.atl.ga.us");
		session.setPassword("123");
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect();
		
		
		ChannelExec exec=null;
		exec=(ChannelExec)session.openChannel("exec");
		exec.setCommand("cd /home/xiangyu/; pwd;ls -l;");
		
		exec.connect();
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(exec.getInputStream()));
		BufferedReader err=new BufferedReader(new InputStreamReader(exec.getErrStream()));
		String line=null;
		while((line=err.readLine())!=null){
			System.err.println(err);
		}
		line=null;
		while((line=reader.readLine())!=null){
			System.out.println(line);
		}
		exec.disconnect();
		
		exec=(ChannelExec)session.openChannel("exec");
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
		
		
		session.disconnect();
	}

}
