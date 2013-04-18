<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="data" scope="session" class="edu.gatech.clairvoyance.session.Data"/>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		
		<!-- -->
		<style type="text/css">
			BODY,
			HTML {
				padding: 0px;
				margin: 0px;
			}
			BODY {
				font-family: Verdana, Arial, Helvetica, sans-serif;
				font-size: 11px;
				background: #EEE;
				padding: 15px;
			}
			
			H1 {
				font-family: Georgia, serif;
				font-size: 20px;
				font-weight: normal;
			}
			
			H2 {
				font-family: Georgia, serif;
				font-size: 16px;
				font-weight: normal;
				margin: 0px 0px 10px 0px;
			}
			
			.example {
				float: left;
				margin: 15px;
			}
			
			.demo {
				width: 200px;
				height: 400px;
				border-top: solid 1px #BBB;
				border-left: solid 1px #BBB;
				border-bottom: solid 1px #FFF;
				border-right: solid 1px #FFF;
				background: #FFF;
				overflow: scroll;
				padding: 5px;
			}
		</style>
		 
		 
		<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
		<script src="js/jquery.easing.1.3.js" type="text/javascript"></script>
		<script src="js/jqueryFileTree.js" type="text/javascript"></script>
		<link href="css/jqueryFileTree.css" rel="stylesheet" type="text/css" media="screen" />
		
		<script type="text/javascript">	
			$(document).ready( function() {
				$.get("Config/server.xml", function(data) {
					var root = $(data).find('root').text();
					
					$('#fileTreeDemo_1').fileTree({ root: root, script: 'connectors/jqueryFileTreeDirOnly.jsp' }, function(file) { 

					}, function(dir){
						$('#dirSpan').text(dir);
						$('.dir').val(dir);
				    });
				})
				.fail(function() { 
					alert("can't find config file, will set the root directory to '/''"); 
					
					$('#fileTreeDemo_1').fileTree({ root: "/", script: 'connectors/jqueryFileTreeDirOnly.jsp' }, function(file) { 

					}, function(dir){
						$('#dirSpan').text(dir);
						$('.dir').val(dir);
				    });
				});
			});
		</script>
		
		<title>Clairvoyance</title>
		
	</head>
	
	<body>
		<h1>Clairvoyance project</h1>
		<h2>Choose the directory you want to browse for files:</h2>
		<h3>The directory you chose: <span id="dirSpan"></span></h3>
		<form name="files" method="get" action="files.jsp">
			<input class="dir" name="dir" type="hidden"></input>
			<input type="submit" name="submit" value="Find files">
		</form>
		<form name="subdirs" method="get" action="dirs.jsp">
			<input class="dir" name="dir" type="hidden"></input>
			<input type="submit" name="submit" value="Find sub directories">
		</form>
		<div class="example">
			<div id="fileTreeDemo_1" class="demo"></div>
		</div>
	</body>
</html>
