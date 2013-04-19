<%@page import="edu.gatech.clairvoyance.session.Node"%>
<%@page import="java.util.Map.Entry"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.io.File,java.io.FilenameFilter,java.util.Arrays"
    import="java.util.Map"
    import="edu.gatech.clairvoyance.session.Directory"%>
<jsp:useBean id="data" scope="session" class="edu.gatech.clairvoyance.session.Data"/>
<%
	String dir=data.getRootDir()+data.getSubDirs().get(0).getDirName();
	String readRes="fail";
	if (new File(dir).exists()) {
		readRes="success";
	}
	
	String num=request.getParameter("num");
	int total=Integer.parseInt(num);
	Map<String, String[]> parameters = request.getParameterMap();
	
	String option="";
	for(int i=1; i <= total; i++){
		String name=parameters.get(i+"_name")[0];
		String ip=parameters.get(i+"_ip")[0];
		if(name != null && name.length() > 0 && ip != null && ip.length() > 0){
			Node node = new Node(name, ip);
			data.addNode(node);
			option += "<option value='" + name + "'>" + name + "</option>";
		}
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
			var option = $('#option').html();
			var root = $('#dir').text();
			$.post('connectors/jqueryFileTreeForFiles.jsp', 
					{ dir: root, option : option }, function(data) {
				$('#test').append(data);
			});
			/*
			$.get("Config/server.xml", function(data) {
				var parsers = "<select id='parser' name='parser'>";
				$(data).find('parser').find('value').each(function(){
					parsers += "<option value='" + $(this).text() +"'>" + $(this).text() + "</option>";
				});
				parsers += "</select>";
				
				var servers = "<select id='server' name='server'>";
				$(data).find('server').find('value').each(function(){
					servers += "<option value='" + $(this).text() +"'>" + $(this).text() + "</option>";
				});
				servers += "</select>";
			});
			*/
		});
	</script>
	<title>Files Configure</title>
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
						<li class="current_page_item"><a href="#">Files</a></li>
						<li><a href="#">Other</a></li>
						<li><a href="#">XML</a></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- end #header -->
		
		<div id="page">
			<div id="content">
				<div class="post item">
					<h2 class="title">Results for data files</h2>
					<h4>Read directory <%=readRes%>!</h4>
					<h4>The files in : <span id="dir"><%=dir%></span></h4>
					<h4>Please select node info for each file</h4>
					<form name="other" method="get" action="other.jsp">
						<ul><li>
							<div id="test">
							</div>
						<li></ul>
						<p><input type="submit" name="submit" value="Next Step" class="more"></input></p>
					</form>
					<div id="option" style="display: none"><%=option%></div>
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