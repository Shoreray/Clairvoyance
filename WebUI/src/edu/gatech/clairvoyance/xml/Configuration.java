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
	private Map<String,Node> nodeMapping=new HashMap<String,Node>();
	private ArrayList<WorkLoad> workloadInformation=new ArrayList<WorkLoad>();
	
	private String experimentName="";
	private String description="";
	private String user="";
	private String date="";
	private String cloudName="";
	private String applicationName="";
	
	private DBInfo dbinfo;
	
	public void setProfileMapping(Map<String,List<Profile> > mapping){
		profileMapping=mapping;
	}
	
	public Map<String,List<Profile> > getProfileMapping(){
		return profileMapping;
	}
	
	public void addNodeMapping(String filename,Node node){
		nodeMapping.put(filename, node);
		
	}
	
	public Node getNodeMapping(String filename){
		return nodeMapping.get(filename);
	}
	
	public void addWorkloadInformation(WorkLoad workload){
		if(workloadInformation.contains(workload)){
			return;
		}
		workloadInformation.add(workload);
	}
	
	public ArrayList<WorkLoad> getWorkloadInformation(){
		return workloadInformation;
	}
	
	public String getExperimentName() {
		return experimentName;
	}

	public void setExperimentName(String experimentName) {
		this.experimentName = experimentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCloudName() {
		return cloudName;
	}

	public void setCloudName(String cloudName) {
		this.cloudName = cloudName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public DBInfo getDbinfo() {
		return dbinfo;
	}

	public void setDbinfo(DBInfo dbinfo) {
		this.dbinfo = dbinfo;
	}
	/*
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
	*/
	
	public String toXML(){
		StringBuilder buffer=new StringBuilder();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		buffer.append("<datamapping>\n");
		buffer.append("\t" + XmlHelper.fromTag("name", experimentName));
		buffer.append("\t" + XmlHelper.fromTag("description",description));
		buffer.append("\t" + XmlHelper.fromTag("user",user));
		buffer.append("\t" + XmlHelper.fromTag("date",date));
		buffer.append("\t" + XmlHelper.fromTag("cloud",cloudName));
		ArrayList<Node> nodes=new ArrayList<Node>();
		for(String filename:nodeMapping.keySet()){
			if(!nodes.contains(nodeMapping.get(filename))){
				nodes.add(nodeMapping.get(filename));
			}
		}
		buffer.append("\t" + XmlHelper.fromTag("nodecount",""+nodes.size()));
		buffer.append("\t" + XmlHelper.fromTag("rampuptime",null));
		buffer.append("\t" + XmlHelper.fromTag("runningtime",null));
		buffer.append("\t" + XmlHelper.fromTag("downramptime",null));
		buffer.append("\t" + XmlHelper.fromTag("application",applicationName));
		buffer.append("\t" + "<profiles>\n");
		ArrayList<Profile> profiles=new ArrayList<Profile>();
		for(List<Profile> list:profileMapping.values()){
			for(Profile p:list){
				if(!profiles.contains(p)){
					profiles.add(p);
				}
			}
		}
		for(Profile profile:profiles){
			buffer.append(profile.toString());
		}
		buffer.append("\t" + "</profiles>\n");
		buffer.append("\t" + "<mappings>\n");
		for(String filename:profileMapping.keySet()){
			buffer.append("\t\t" + "<mapping filename=\"");
			buffer.append(filename);
			if(nodeMapping.get(filename)!=null){
				buffer.append("\" nodename=\"");
				buffer.append(nodeMapping.get(filename).getName());
			}
			buffer.append("\" profiles=\"");
			List<Profile> profilesOfFile=profileMapping.get(filename);
			
			for(int i=0;i<profilesOfFile.size();++i){
				buffer.append(profilesOfFile.get(i).getName());
				if(i!=profilesOfFile.size()-1){
					buffer.append(", ");
				}
			}
			buffer.append("\" startwith=\"false\" />\n");
		}
		buffer.append("\t" + "</mappings>\n");
		
		buffer.append("\t" + "<nodes>\n");
		
		for(Node n:nodes){
			buffer.append("\t\t" + n.toString());
		}
		buffer.append("\t" + "</nodes>\n");
		
		buffer.append("\t" + "<workloads>\n");
		for(WorkLoad wl:workloadInformation){
			buffer.append("\t\t" + wl.toString());
		}
		buffer.append("\t" + "</workloads>\n");
		
		if(dbinfo!=null){
			buffer.append("\t" + dbinfo.toString());
		}
		
		buffer.append("</datamapping>\n");
		return buffer.toString();
	}
	
	public static void main(String[] args) throws Exception{
		Map<String,List<Profile> > mapping=(new ProfileExtractor()).getProfileMapping(new File("/home/xiangyu/ClairvoyanceSampleData/2013-03-09T070056-0500"));
		Configuration config=new Configuration();
		config.setApplicationName("Clairvoyance");
		config.setCloudName("Invent");
		config.setDate("2013-4-1");
		config.setExperimentName("Test Clairvoyance");
		config.setDescription("This is to test the configuration file generator of the Clairvoyance project.");
		config.setUser("God Mie");
		config.setProfileMapping(mapping);
		for(String file:mapping.keySet()){
			Node node=new Node("node of "+file,"192.168.0."+(int)Math.random()*255);
			config.addNodeMapping(file, node);
			
		}
		for(int i=0;i<10;i++){
			config.addWorkloadInformation(new WorkLoad("Run"+i,""+(int)Math.random()*5000,Math.random()>0.5));
			
		}
		config.addWorkloadInformation(new WorkLoad("Run1","1000",false));
		config.addWorkloadInformation(new WorkLoad("Run1","1000",false));
		System.out.println(config.toXML());
		
	}
	
}
