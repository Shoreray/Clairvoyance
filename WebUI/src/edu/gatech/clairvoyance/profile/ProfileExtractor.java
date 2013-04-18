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
				ClassLoader loader=new URLClassLoader(urls);
				Class processorClass=loader.loadClass(processorClassName);
				ProfileProcessor processor=(ProfileProcessor) processorClass.newInstance();
				processors.add(processor);
				System.out.println("Successfully loaded processor "+processorClassName);
				
			}else{
				throw new IllegalArgumentException("Incorrect processor configuration file format.");
			}
		}
		
		
	}
	
	public static void main(String[] args) throws Exception{
		new ProfileExtractor();
	}

}
