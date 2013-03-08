package edu.gatech.clairvoyance.remote;
import java.util.*;

public class ExecResult {
	
	private ArrayList<String> output;
	private ArrayList<String> errput;
	public String rawOutput;
	public String rawErrput;
	public int exitCode;
	
	
	public ExecResult(){
		exitCode=0;
	}
	
	
	public ArrayList<String> getOutput(){
		if(output==null){
			
			output=new ArrayList<String>();
			for(String str:rawOutput.split("\n")){
				output.add(str);
			}
		}
		return output;
	}
	
	public ArrayList<String> getErrput(){
		if(errput==null){
			errput=new ArrayList<String>();
			for(String str:rawErrput.split("\n")){
				errput.add(str);
			}
		}
		return errput;
	}

}
