package edu.gatech.clairvoyance.xml;

public class XmlHelper {
	
	public static String fromTag(String tag,String value){
		if(value==null){
			return "<"+tag+" />\n";
		}else{
			StringBuilder buffer=new StringBuilder();
			buffer.append("<");
			buffer.append(tag);
			buffer.append(">");
			buffer.append(value);
			buffer.append("</");
			buffer.append(tag);
			buffer.append(">\n");
			return buffer.toString();
		}
	}

}
