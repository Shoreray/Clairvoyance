<%@page import="edu.gatech.clairvoyance.profile.ProfileExtractor"%>
<%@page import="edu.gatech.clairvoyance.xml.DBInfo"%>
<%@page import="edu.gatech.clairvoyance.xml.Configuration"%>
<%@page import="java.util.Map, java.util.Map.Entry"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="data" scope="session" class="edu.gatech.clairvoyance.session.Data"/>
<%
	Map<String, String[]> parameters = request.getParameterMap();

	for(Entry<String, String[]> entry : parameters.entrySet()){
		String fileName = entry.getKey();
		String nodeName = entry.getValue()[0];
		data.addFile2Node(fileName, nodeName);
		
		System.out.println(fileName + " " + nodeName);
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
						<li class="done"><a href="#">Home</a></li>
						<li class="done"><a href="#">SubDir</a></li>
						<li class="done"><a href="#">Nodes</a></li>
						<li class="done"><a href="#">Files</a></li>
						<li class="current_page_item"><a href="#">Other</a></li>
						<li><a href="#">XML</a></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- end #header -->
		
		<div id="page">
			<div id="content">
				<div class="post item">
					<h2 class="title">The last step!</h2>
					<h4>Please fill in other info</h4>
					<form name="xml" method="post" action="xml.jsp">
						<ul><li><div><ul>
							<li>
								<span class="other">Item</span>
								<span class="text nobackground">Data</span>
							</li>
							<li>
								<span class="other">Experiment Name</span>
								<input class="text" type="text" name="ExpName">
							</li>
							<li>
								<span class="other">Experiment Description</span>
								<input class="text" type="text" name="ExpDesc">
							</li>
							<li>
								<span class="other">User</span>
								<input class="text" type="text" name="User">
							</li>
							<li>
								<span class="other">Date</span>
								<input class="text" type="text" name="Date">
							</li>
							<li>
								<span class="other">Cloud</span>
								<input class="text" type="text" name="Cloud">
							</li>
							<li>
								<span style="background: none">Database Information Below</span>
								<span class="text nobackground"></span>
							</li>
							<li>
								<span class="other">UserName</span>
								<input class="text" type="text" name="UserName">
							</li>
							<li>
								<span class="other">Password</span>
								<input class="text" type="text" name="Pwd">
							</li>
							<li>
								<span class="other">URL</span>
								<input class="text" type="text" name="Url">
							</li>
							<li>
								<span class="other">DB class</span>
								<input class="text" type="text" name="Dbclass">
							</li>
						</ul></div></li></ul>
						<p><input type="submit" value="Generate!" class="more"></input></p>
					</form>
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