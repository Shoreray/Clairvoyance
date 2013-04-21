<%@page import="java.io.FileReader"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="edu.gatech.clairvoyance.xml.WorkLoad"%>
<%@page import="edu.gatech.clairvoyance.session.Directory"%>
<%@page import="edu.gatech.clairvoyance.session.Node"%>
<%@page import="edu.gatech.clairvoyance.profile.ProfileExtractor"%>
<%@page import="edu.gatech.clairvoyance.xml.DBInfo"%>
<%@page import="edu.gatech.clairvoyance.xml.Configuration"%>
<%@page import="java.util.Map,java.util.Map.Entry"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="data" scope="session" class="edu.gatech.clairvoyance.session.Data"/>
<%
	Map<String, String[]> parameters = request.getParameterMap();
	String xml = "";
	String outputPath = "";
	
	String expName = parameters.get("ExpName")[0];
	String expDesc = parameters.get("ExpDesc")[0];
	String user = parameters.get("User")[0];
	String date = parameters.get("Date")[0];
	String cloud = parameters.get("Cloud")[0];
	String userName = parameters.get("UserName")[0];
	String pwd = parameters.get("Pwd")[0];
	String url = parameters.get("Url")[0];
	String dbclass = parameters.get("Dbclass")[0];
	
	DBInfo db = new DBInfo();
	db.setDbclass(dbclass);
	db.setPassword(pwd);
	db.setUrl(url);
	db.setUsername(userName);
	ProfileExtractor extractor = new ProfileExtractor();
	
	try{
		File dataDir = new File(data.getRootDir());
		File processorFile = new File("processors.xml");
		
		Configuration conf = new Configuration();
		conf.setApplicationName("");
		conf.setCloudName(cloud);
		conf.setDate(date);
		conf.setDbinfo(db);
		conf.setDescription(expDesc);
		conf.setExperimentName(expName);
		conf.setProfileMapping(extractor.getProfileMapping(dataDir));
		conf.setUser(user);
		for(Entry<String, Node> entry : data.getFile2Node().entrySet()){
			Node node = entry.getValue();
			edu.gatech.clairvoyance.xml.Node item 
						= new edu.gatech.clairvoyance.xml.Node(node.getName(), node.getIp());
			conf.addNodeMapping(entry.getKey(), item);
		}
		
		for(Entry<String, Directory> entry : data.getName2dir().entrySet()){
			Directory dir = entry.getValue();
			boolean mode = dir.getMode().equals("ReadOnly") ? true : false;
			WorkLoad workLoad = new WorkLoad(dir.getDirName(), dir.getName(), mode);
			conf.addWorkloadInformation(workLoad);
		}
		
		xml = conf.toXML();
		//File output = new File("output.xml");
		//PrintWriter writer = new PrintWriter(output);
		//writer.println(xml);
		//writer.close();
		//outputPath = output.getAbsolutePath();
		
		//data.setOutputFile(outputPath);
		//File current = new File(".");
		//outputPath = current.getAbsolutePath();
	} catch(Exception e){
		e.printStackTrace();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
	<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
	<script src="js/jquery.easing.1.3.js" type="text/javascript"></script>
	<script src="js/jqueryFileTree.js" type="text/javascript"></script>
		
	<link href="css/style.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="css/jqueryFileTree.css" rel="stylesheet" type="text/css" media="screen" />
	<link href="css/jqueryFileTreeList.css" rel="stylesheet" type="text/css" media="screen" />
	<!-- 
	<link href='http://fonts.googleapis.com/css?family=Archivo+Narrow:400,700|Open+Sans:400,300' rel='stylesheet' type='text/css'>
	 -->
		
	<script type="text/javascript">	
		$(document).ready( function() {
			//$('#display').text($('#hidden').html());
		});
	</script>
	<title>Final Configure</title>
</head>

<body>
	<div id="wrapper">
		<div id="header-wrapper">
			<div id="header" class="container">
				<div id="logo">
					<h1>Clairvoyance</h1>
				</div>
				<div id="menu">
					<ul>
						<li><a href="#">Home</a></li>
						<li><a href="#">SubDir</a></li>
						<li><a href="#">Nodes</a></li>
						<li><a href="#">Files</a></li>
						<li><a href="#">Other</a></li>
						<li class="current_page_item"><a href="#">XML</a></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- end #header -->
		
		<div id="page">
			<div id="content">
				<div class="post item">
					<h2 class="title">The generated xml</h2>
					<h4>Please check the xml, and download it!</h4>
					<form name="nodes" method="post" action="download.jsp">
						<textarea id="display" style="width:900px; height:450px" name="xml"><%=xml%></textarea>
						<p>  </p>
						<p><input type="submit" value="Download" class="more"></input></p>
					</form>
					<div id="hidden" style="display: none"><%=xml%></div>
				</div>
				<div style="clear: both;">&nbsp;</div>
			</div>
			
		</div>
		<!-- end #content -->
		<div style="clear: both;">&nbsp;</div>
	</div>
	<!-- end #wrapper -->
	<div id="footer">
		<p>Copyright (c) 2013 Georgia Tech, College of Computing. All rights reserved. </p>
	</div>
	<!-- end #footer -->
</body>
</html>