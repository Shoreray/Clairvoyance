package edu.gatech.clairvoyance.profile;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class ProfileExtractor {
	
	private ArrayList<ProfileProcessor> processors=new ArrayList<ProfileProcessor>();
	
	public ProfileExtractor() throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		this(new File("processors.xml"));
	}
	
	public ProfileExtractor(File configFile) throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(configFile);
		
		doc.getDocumentElement().normalize();
		
		NodeList processorList=doc.getElementsByTagName("processor");
		
		for(int i=0;i<processorList.getLength();++i){
			Node processorNode=processorList.item(i);
			if(processorNode.getNodeType()==Node.ELEMENT_NODE){
				Element processorEle=(Element)processorNode;
				String processorClassName=processorEle.getAttribute("classname");
				String classpath=processorEle.getAttribute("classpath");
				
				URL url=new File(classpath).toURI().toURL();
				URL[] urls=new URL[]{url};
				URLClassLoader loader=new URLClassLoader(urls);
				Class<?> processorClass=loader.loadClass(processorClassName);
				loader.close();
				ProfileProcessor processor=(ProfileProcessor) processorClass.newInstance();
				processors.add(processor);
				System.out.println("Successfully loaded processor "+processorClassName);
				
			}else{
				throw new IllegalArgumentException("Incorrect processor configuration file format.");
			}
		}
		
		
	}
	
	public Map<String,List<Profile> > getProfileMapping(File datadirPath) throws Exception{
		HashMap<String, List<Profile> > mappingHash=new HashMap<String,List<Profile> >();
		for(ProfileProcessor processor:processors){
			Map<String,List<Profile> > mapping=processor.process(datadirPath);
			if(mapping == null){
				continue;
			}
			for(String filename:mapping.keySet()){
				List<Profile> partProfiles=mapping.get(filename);
				if(!mappingHash.containsKey(filename)){
					mappingHash.put(filename, new ArrayList<Profile>());
					
				}
				ArrayList<Profile> arrProfiles=(ArrayList<Profile>)mappingHash.get(filename);
				for(Profile profile:partProfiles){
					arrProfiles.add(profile);
				}
			}
		}
		return mappingHash;
	}
	
	public static void main(String[] args) throws Exception{
		Map<String,List<Profile> > mapping=(new ProfileExtractor()).getProfileMapping(new File("/home/xiangyu/ClairvoyanceSampleData/2013-03-09T070056-0500"));
		System.out.println("Value set size: "+mapping.values().size());
		for(List<Profile> profiles:mapping.values()){
        	//List<Profile> profiles=mapping.get(str);
        	System.out.println("profiles = "+profiles);
        	break;
        }
        System.out.println("Applicable files: ");
        for(String str:mapping.keySet()){
        	System.out.println(str);
        }
	}

}
