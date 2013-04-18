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
			var root = $('#dir').text();
			$.post('connectors/jqueryFileTreeForFiles.jsp', 
					{ dir: root, parser : "", server : ""}, function(data) {
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
		<h2>Read directory <%=readRes%>!</h2>
		<h2>The files in : <span id="dir"><%=dir%></span></h2>
		<h2>Please select parser and server</h2>
		<div id="testing"></div>
		<div id="test"></div>
	</body>
</html>