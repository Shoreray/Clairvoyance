package edu.gatech.clairvoyance.profile.processor;

import java.util.HashMap;
import java.util.Map;
import edu.gatech.clairvoyance.profile.*;

/**
 * User: Deepal Jayasinghe
 * Date: 8/23/12
 * Time: 4:11 PM
 */
public class DstatProfile extends Profile{
    private String name;
    private Map<String, Integer> col2Index;

    public DstatProfile() {
        col2Index = new HashMap<String, Integer>();
    }

    public void addMapping(String colName, int index) {
        col2Index.put(colName, index);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\t\t" + "<profile>\n");
        buffer.append("\t\t\t" + "<separator>,</separator>\n");
        buffer.append("\t\t\t" + "<resource-name>").append(name).append("</resource-name>\n");
        buffer.append("\t\t\t" + "<processor-class>dataimport.filter.CSVFileProcessor</processor-class>\n");
        for (String key : col2Index.keySet()) {
            buffer.append("\t\t\t" + "<column index=\"")
                    .append(col2Index.get(key))
                    .append("\" colname=")
                    .append(key)
                    .append(" datatype=\"double\"/>\n");
        }
        buffer.append("\t\t\t" + "<start-index>10</start-index>\n");
        buffer.append("\t\t\t" + "<end-index>0</end-index>\n");
        buffer.append("\t\t" + "</profile>\n");
        return buffer.toString();
    }
}
