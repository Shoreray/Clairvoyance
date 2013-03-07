package edu.gatech.clairvoyance.remote;
import java.util.*;

public class ExecResult {
	
	public ArrayList<String> output;
	public ArrayList<String> errput;
	public int exitCode;
	
	public ExecResult(){
		
	}
	public ExecResult(ArrayList<String> output,ArrayList<String> errput,int exitCode){
		this.output=output;
		this.errput=errput;
		this.exitCode=exitCode;
	}

}
