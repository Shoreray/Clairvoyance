package edu.gatech.clairvoyance.profile;
import java.util.*;
import java.io.*;


public interface ProfileProcessor {
	
	public Map<String,List<Profile> > process(File datadirPath) throws Exception;

}
