<%@page import="edu.gatech.clairvoyance.session.Directory"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.io.File,java.io.FilenameFilter,java.util.Arrays"
    import = "java.util.Map"%>
<jsp:useBean id="data" scope="session" class="edu.gatech.clairvoyance.session.Data"/>
<%
	String dir=request.getParameter("dir");
	String readRes="fail";
	if (new File(dir).exists()) {
		readRes="success";
		data.setRootDir(dir);
	} else {
		String redirectURL = "index.jsp?msg=Please select the root directory!";
	    response.sendRedirect(redirectURL);
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
			$.post('connectors/jqueryFileTreeForSubDir.jsp', 
					{ dir: root }, function(data) {
				$('#test').append(data);
			});
		});
	</script>
	<title>Sub Dir Configure</title>
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
						<li class="current_page_item"><a href="#">SubDir</a></li>
						<li><a href="#">Nodes</a></li>
						<li><a href="#">Files</a></li>
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
					<h2 class="title">Results for sub directories</h2>
					<h4>Read directory <%=readRes%>!</h4>
					<h4>The sub-directories in : <span id="dir"><%=dir%></span></h4>
					<h4>Please select mode, and enter workload information</h4>
					<form name="nodes" method="post" action="nodes.jsp">
						<ul><li>
							<div id="test">
							</div>
						<li></ul>
						<input class="dir" name="dir" type="hidden"></input>
						<p><input type="submit" name="submit" value="Next Step" class="more"></input></p>
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