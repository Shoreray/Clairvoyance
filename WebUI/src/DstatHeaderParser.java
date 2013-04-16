package dataimport.rubbos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This is to pass the dstat file and create profile by using that
 */
public class DstatHeaderParser {

    public static List<DstatProfile> process(String resultsDirectory) throws Exception {
        File resultsDir = new File(resultsDirectory);
        if (resultsDir.isDirectory()) {
            File[] dirs = resultsDir.listFiles();
            for (File dir : dirs) {
                if (dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    for (File file : files) {
                        if (file.getName().endsWith(".csv")) {
                            BufferedReader in = new BufferedReader(new FileReader(file));
                            String str;
                            while ((str = in.readLine()) != null) {
                                if (str.startsWith("\"Dstat")) {
                                    return createProfiles(in);
                                } else {
                                    in.close();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return new ArrayList<DstatProfile>();
    }

    private static List<DstatProfile> createProfiles(BufferedReader in) throws Exception {
        List<DstatProfile> dstatProfiles = new ArrayList<DstatProfile>();
        String str;
        List<String> values = new ArrayList<String>();
        while ((str = in.readLine()) != null) {
            if ("".equals(str)) continue;
            if (str.startsWith("\"")) {
                values.add(str);
                continue;
            }
            break;
        }
        if (values.size() > 2) {
            String highLevelHeaders = values.get(values.size() - 2);
            String headers = values.get(values.size() - 1);
            String[] hlValues = highLevelHeaders.split("\\,");
            String[] hValues = headers.split("\\,");
            DstatProfile currentProfile = null;
            for (int i = 0; i < hValues.length; i++) {
                if (i < hlValues.length) {
                    String hlv = hlValues[i];
                    if (hlv.startsWith("\"")) {
                        // found a new profile
                        currentProfile = new DstatProfile();
                        currentProfile.setName(constructName(hlv));
                        dstatProfiles.add(currentProfile);
                    }
                }
                if (currentProfile == null) continue;
                currentProfile.addMapping(hValues[i], i);
            }
        }
        return dstatProfiles;
    }

    private static String constructName(String in) {
        in = in.replaceAll("\"", "");
        in = in.replaceAll("/", "_");
        in = in.replaceAll(" ", "_");
        return in.toUpperCase();
    }

    public static void main(String[] args) throws Exception {
        String path = "D:\\georgia_tech\\Elba_SVN\\module\\mulini\\src\\dataimport";
        List<DstatProfile> profiles = DstatHeaderParser.process(path);
        System.out.println("profiles = " + profiles);
    }
}
