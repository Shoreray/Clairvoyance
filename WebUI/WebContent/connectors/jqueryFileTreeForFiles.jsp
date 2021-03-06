<%@ page
	import="java.io.File,java.io.FilenameFilter,java.util.Arrays"%>
<%
/**
  * jQuery File Tree JSP Connector
  * Version 1.0
  * Copyright 2008 Joshua Gould
  * 21 April 2008
*/	
    String dir = request.getParameter("dir");
	String option = request.getParameter("option");
    if (dir == null) {
    	return;
    }
	
	if (dir.charAt(dir.length()-1) == '\\') {
    	dir = dir.substring(0, dir.length()-1) + "/";
	} else if (dir.charAt(dir.length()-1) != '/') {
	    dir += "/";
	}
	
	dir = java.net.URLDecoder.decode(dir, "UTF-8");	
	
    if (new File(dir).exists()) {
		String[] files = new File(dir).list(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
				return name.charAt(0) != '.';
		    }
		});
		Arrays.sort(files, String.CASE_INSENSITIVE_ORDER);
		out.print("<ul>");
		// All dirs
//		for (String file : files) {
//		    if (new File(dir, file).isDirectory()) {
//				out.print("<li><div href=\"#\" rel=\"" + dir + file + "/\">"
//					+ file + "</div></li>");
//		    }
//		}
		// All files
		for (String file : files) {
		    if (!new File(dir, file).isDirectory()) {
				String node = "<select class='mode' name='" + file + "'>" + option + "</select>";
				out.print("<li><span class='file'>" + file + "</span>" + node + "</li>");
		    }
		}
		out.print("</ul>");
    }
%>