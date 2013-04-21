<jsp:useBean id="data" scope="session" class="edu.gatech.clairvoyance.session.Data"/>
<%
	String xml=request.getParameter("xml");

	String filename = "output.xml"; 
	String filepath = data.getOutputFile();
	response.setContentType("APPLICATION/OCTET-STREAM"); 
	response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\""); 

	out.write(xml);
	//java.io.FileInputStream fileInputStream = new java.io.FileInputStream(filepath);
		  
 	//int i; 
	//while ((i=fileInputStream.read()) != -1) {
    //	out.write(i); 
	//} 
	//fileInputStream.close();
%> 