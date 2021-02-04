package utils;


import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

public class JsonUtil {

    public JSONObject apiJSONReader(String jsonFilePath, String apiName) throws IOException {
        File file = new File(jsonFilePath);
        String content = FileUtils.readFileToString(new File(file.getCanonicalPath()), "utf-8");
        JSONObject apiObject = new JSONObject(content);
        JSONArray serviceApi = new JSONArray(apiObject.get("services").toString());
        JSONObject serviceObj = new JSONObject();
        for (int i = 0; i < serviceApi.length(); i++) {
            if (serviceApi.getJSONObject(i).names().toString().contains(apiName)) {
                JSONObject data = (JSONObject) serviceApi.get(i);
                serviceObj = (JSONObject) data.get(apiName);
            }
        }
        return serviceObj;
    }

    public Iterator<Object[]> setTestcaseData(String jsonFilePath) throws IOException {
        Collection<Object[]> provider = new ArrayList<Object[]>();
        File file = new File(jsonFilePath);
        String content = FileUtils.readFileToString(new File(file.getCanonicalPath()), "utf-8");
        JSONObject object = new JSONObject(content);
        JSONArray testSuite = new JSONArray(object.get("TestCases").toString());
        for (int i = 0; i < testSuite.length(); i++) {
            JSONObject testCase = (JSONObject) testSuite.get(i);
            try {
                if (testCase.getBoolean("execute")) {
                    String tenantParam = testCase.getJSONObject("testCaseParameters").getString("tenant");

                    provider.add(new Object[]{tenantParam});

                } else {
                    System.out.print("<********** Testcase NOT ENABLED in Dataprovider or Testcase name DOES NOT MATCH **********>");
                }
            } catch (Exception e) {
                System.out.print(e);
            }
        }
        return provider.iterator();
    }
}
