package edu.gatech.clairvoyance.xml;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;
import edu.gatech.clairvoyance.profile.*;

public class Configuration {
	
	private Map<String,List<Profile> > profileMapping;
	private Map<String,Node> nodeMapping;
	private ArrayList<WorkLoad> workloadInformation;
	
	private String experimentName;
	private String description;
	private String user;
	private String date;
	private String cloudName;
	private String applicationName;
	
	
	public static void main(String[] args) throws Exception{
		//Test creating configuration files
		DocumentBuilderFactory docFactory=DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
		
		Document doc=docBuilder.newDocument();
		Element rootElement=doc.createElement("datamapping");
		doc.appendChild(rootElement);
		
		Element element=null;
		
		element=doc.createElement("name");
		element.appendChild(doc.createTextNode("test-03-28-2013"));
		rootElement.appendChild(element);
		
		element=doc.createElement("description");
		element.appendChild(doc.createTextNode("This is to test Clairvoyance configuration file generator"));
		rootElement.appendChild(element);
		
		element=doc.createElement("user");
		element.appendChild(doc.createTextNode("xiangyu"));
		rootElement.appendChild(element);
		
		Element profilesNode=doc.createElement("profiles");
		for(int i=0;i<50;i++){
			Element profileNode=doc.createElement("profile");
			profileNode.appendChild(doc.createElement("separator"));
			
			Element resourceNameNode=doc.createElement("resource-name");
			resourceNameNode.appendChild(doc.createTextNode("Resource"+i));
			profileNode.appendChild(resourceNameNode);
			
			Element processorNode=doc.createElement("processor-class");
			processorNode.appendChild(doc.createTextNode("ProcessorClass"+i));
			profileNode.appendChild(processorNode);
			
			profilesNode.appendChild(profileNode);
		}
		rootElement.appendChild(profilesNode);
		
		doc.setXmlStandalone(true);
		
		TransformerFactory transformerFactory=TransformerFactory.newInstance();
		Transformer transformer=transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		
		DOMSource source=new DOMSource(doc);
		StreamResult file=new StreamResult(new File("/home/xiangyu/ClairvoyanceSampleData/autogened.xml"));
		
		transformer.transform(source, file);
		
	}

	public String toXML(){
		return null;
	}
	
	
}
