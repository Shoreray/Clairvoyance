<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="data" scope="session" class="edu.gatech.clairvoyance.session.Data"/>
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
			$.get("Config/server.xml", function(data) {
				var root = $(data).find('root').text();
				
				$('#fileTreeDemo_1').fileTree({ root: root, script: 'connectors/jqueryFileTreeDirOnly.jsp' }, 
					function(file) {}, function(dir){
					$('#dirSpan').text(dir);
					$('.dir').val(dir);
			    });
			})
			.fail(function() { 
				alert("can't find config file, will set the root directory to '/''"); 
				
				$('#fileTreeDemo_1').fileTree({ root: "/", script: 'connectors/jqueryFileTreeDirOnly.jsp' }, 
					function(file) {}, function(dir){
					$('#dirSpan').text(dir);
					$('.dir').val(dir);
				});
			});
		});
	</script>

	<title>Clairvoyance</title>
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
						<li class="current_page_item"><a href="#">Home</a></li>
						<li><a href="#">SubDir</a></li>
						<li><a href="#">Nodes</a></li>
						<li><a href="#">Files</a></li>
						<li><a href="#">Other</a></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- end #header -->
		
		<div id="page">
			<div id="content">
				<div class="post">
					<h2 class="title">Hey, Welcome!</h2>
					<h4>This web is designed to help you perform automated data profile and extraction. </h4>
					<div style="clear: both;">&nbsp;</div>
					<div class="entry">
						
						<p>Choose the root directory which contains all your data files</p>
						<p>The directory you chose: <span id="dirSpan"></span></p>
						<div class="example">
							<div id="fileTreeDemo_1" class="demo"></div>
						</div>
						<form name="subdirs" method="get" action="dirs.jsp">
							<input class="dir" name="dir" type="hidden"></input>
							<p><input type="submit" name="submit" value="Next Step" class="more"></input></p>
						</form>
					</div>
				</div>
				<div style="clear: both;">&nbsp;</div>
			</div>
		</div>
		<!-- end #content -->

		<div id="sidebar">
		</div>
		<div style="clear: both;">&nbsp;</div>
	</div>
	<!-- end #wrapper -->
	<div id="footer">
		<p>Copyright (c) 2013 Georgia Tech, College of Computing. All rights reserved. </p>
	</div>
	<!-- end #footer -->
	</body>
</html>
