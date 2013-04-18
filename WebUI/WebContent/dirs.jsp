<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    import="java.io.File,java.io.FilenameFilter,java.util.Arrays"%>
<jsp:useBean id="data" scope="session" class="edu.gatech.clairvoyance.session.Data"/>
<%
	String dir=request.getParameter("dir");
	data.dir=dir;
	String test="fail";
	if (new File(dir).exists()) {
		test="success";
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
				var root = $('#dir').text();
				var modes = "<select id='mode' name='mode'>"
						+ "<option value='read only'>read only</option>"
						+ "<option value='read write'>read write</option>"
						+ "</select>";
				var text = "<input type='text' name='text' id='text'>";
				$.post('connectors/jqueryFileTreeForSubDir.jsp', 
						{ dir: root, mode : modes, text : text}, function(data) {
					$('#test').append(data);
				});
			});
		</script>
		<title>Insert title here</title>
	</head>
	<body>
		<h2>Read directory <%=test%>!</h2>
		<h2>The sub-directories in : <span id="dir"><%=dir%></span></h2>
		<%=data.dir%>
		<h2>Please select mode and enter texts</h2>
		<div id="test"></div>
	</body>
</html>