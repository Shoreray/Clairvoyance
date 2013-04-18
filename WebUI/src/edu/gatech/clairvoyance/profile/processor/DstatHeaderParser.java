package edu.gatech.clairvoyance.profile.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

import edu.gatech.clairvoyance.profile.*;

/**
 * This is to pass the dstat file and create profile by using that
 */
public class DstatHeaderParser implements ProfileProcessor{

    public Map<String,List<Profile> > process(File resultsDir) throws Exception {
        Map<String,List<Profile> > mapping=new HashMap<String,List<Profile> >();
        
        if (resultsDir.isDirectory()) {
            File[] dirs = resultsDir.listFiles();
            for (File dir : dirs) {
            	//For each sub-data directory,
                if (dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    List<Profile> profiles=null;
                    for (File file : files) {
                    	//For each data file
                        if (file.getName().endsWith(".csv")) {
                            BufferedReader in = new BufferedReader(new FileReader(file));
                            String str;
                            while ((str = in.readLine()) != null) {
                                if (str.startsWith("\"Dstat")) {
                                	if(profiles==null){
                                		profiles=createProfiles(in);
                                	}
                                	mapping.put(file.getName(), profiles);
                                	
                                    
                                } else {
                                    in.close();
                                    break;
                                }
                            }
                        }//End of if
                        
                    }
                }
            }
        }
        return mapping;
    }

    private static List<Profile> createProfiles(BufferedReader in) throws Exception {
        List<Profile> dstatProfiles = new ArrayList<Profile>();
        String str;
        List<String> values = new ArrayList<String>();
        while ((str = in.readLine()) != null) {
            if ("".equals(str)) continue;
            if (str.startsWith("\"")) {
                values.add(str);
                continue;
            }
            break;
        }
        if (values.size() > 2) {
            String highLevelHeaders = values.get(values.size() - 2);
            String headers = values.get(values.size() - 1);
            String[] hlValues = highLevelHeaders.split("\\,");
            String[] hValues = headers.split("\\,");
            DstatProfile currentProfile = null;
            for (int i = 0; i < hValues.length; i++) {
                if (i < hlValues.length) {
                    String hlv = hlValues[i];
                    if (hlv.startsWith("\"")) {
                        // found a new profile
                        currentProfile = new DstatProfile();
                        currentProfile.setName(constructName(hlv));
                        dstatProfiles.add(currentProfile);
                    }
                }
                if (currentProfile == null) continue;
                currentProfile.addMapping(hValues[i], i);
            }
        }
        return dstatProfiles;
    }

    private static String constructName(String in) {
        in = in.replaceAll("\"", "");
        in = in.replaceAll("/", "_");
        in = in.replaceAll(" ", "_");
        return in.toUpperCase();
    }

    public static void main(String[] args) throws Exception {
        //String path = "D:\\georgia_tech\\Elba_SVN\\module\\mulini\\src\\dataimport";
    	File dataDir=new File("/home/xiangyu/ClairvoyanceSampleData/2013-03-09T070056-0500");
        Map<String,List<Profile> > mapping = (new DstatHeaderParser()).process(dataDir);
        //System.out.println("profiles = " + profiles);
        for(String str:mapping.keySet()){
        	List<Profile> profiles=mapping.get(str);
        	System.out.println("profiles = "+profiles);
        	break;
        }
        System.out.println("Applicable files: ");
        for(String str:mapping.keySet()){
        	System.out.println(str);
        }
    }
}
