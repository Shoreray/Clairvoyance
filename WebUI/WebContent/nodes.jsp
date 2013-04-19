<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.io.File,java.io.FilenameFilter,java.util.Arrays"
    import="java.util.Map"
    import="edu.gatech.clairvoyance.session.Directory"%>
<jsp:useBean id="data" scope="session" class="edu.gatech.clairvoyance.session.Data"/>
<%
	if(data.getRootDir() != null && data.getSubDirs().size() > 0){
		Map<String, String[]> parameters = request.getParameterMap();
		for(Directory dir : data.getSubDirs()){
			String dirName = dir.getDirName();
			System.out.println(dirName);
			String[] modes = parameters.get(dirName + "_mode");
			String[] randoms = parameters.get(dirName + "_random");
			String[] texts = parameters.get(dirName + "_text");
			dir.setMode(modes[0]);
			dir.setName(texts[0]);
			dir.setRandom(randoms[0]);
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
			var nodes = 3;
			$('#num').val(nodes);
			
			$('#add').click(function() {
				nodes++;
				var html = "<li id='" + nodes + "'><span class='file' id='" + nodes + "'>Node " + nodes + "</span>"
							+ "<input class='text' type='text' name='" + nodes + "_name'>"
							+ "<input class='text' type='text' name='" + nodes + "_ip'></li>";
				$('#nodes').append(html);
				$('#num').val(nodes);
			});
			
			$('#remove').click(function() {
				$('#nodes li:last').remove();
				nodes--;
				$('#num').val(nodes);
			});
		});
	</script>
	<title>Node Configure</title>
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
						<li class="current_page_item"><a href="#">Nodes</a></li>
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
					<h2 class="title">Please configure the nodes info</h2>
					<h4>Please fill in the name and IP for the node</h4>
					<div class="entry">
					<form name="files" method="get" action="files.jsp">
						<input class="dir" id="num" name="num" type="hidden"></input>
						<ul><li><div><ul id="nodes">
							<li>
								<span class="file">Node</span>
								<span class="text nobackground">name</span>
								<span class="text nobackground">IP</span>
							</li>
							<li id='1'>
								<span class="file" id="1">Node 1</span>
								<input class="text" type="text" name="1_name">
								<input class="text" type="text" name="1_ip">
							</li>
							<li id='2'>
								<span class="file" id="2">Node 2</span>
								<input class="text" type="text" name="2_name">
								<input class="text" type="text" name="2_ip">
							</li>
							<li id='3'>
								<span class="file" id="3">Node 3</span>
								<input class="text" type="text" name="3_name">
								<input class="text" type="text" name="3_ip">
							</li>
						</ul></div>
						<p>
							<a id='add'>Add More Nodes  </a>
							<a id='remove'>Remove last Nodes</a>  
						</p>
						</li></ul>
						<p><input type="submit" name="submit" value="Next Step" class="more"></input></p>
					</form></div>
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