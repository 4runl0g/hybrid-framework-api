import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.JsonUtil;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;

public class eulaTenantTest {

    public static final String KOSMOS_URIs_JSON_FILE_PATH = System.getProperty("user.dir")
            + "/src/main/resources/EulaApis.json";
    public static final String Testcase_JSON_FILE_PATH = System.getProperty("user.dir")
            + "/src/main/java/dataprovider/1KosmosTestcase.json";

    //Dataprovider to read testcase parameters and iterate for test suite
    @DataProvider(name = "getData", parallel = true)
    public Iterator<Object[]> getTestcaseData(Method m) throws IOException {
        return new JsonUtil().setTestcaseData(Testcase_JSON_FILE_PATH);
    }

    //return status code for health check api
    public int eulaHealthCheck() throws IOException {
        JSONObject jsonObject = new JsonUtil().apiJSONReader(KOSMOS_URIs_JSON_FILE_PATH, "health");
        System.out.println("Request body ==> "+jsonObject.toString());
        String serviceUrl = jsonObject.get("uri").toString();
        Response response = new ApiMethod().getMethod(serviceUrl);
        return response.getStatusCode();
    }

    //Execute all enabled test cases (execute=true flag) in "1KosmosTestcase.json" file
    //Json File path ~ /src/main/java/dataprovider
    @Test(dataProvider = "getData")
    public void eulaTest(String tenantParam) throws IOException {
        int code = eulaHealthCheck();
        System.out.println("StatusCode ==> "+code);
        JSONObject jsonObject = new JsonUtil().apiJSONReader(KOSMOS_URIs_JSON_FILE_PATH, "eula");
        String serviceUrl = jsonObject.get("uri").toString()+tenantParam;
        System.out.println("Eula APIs ==> "+serviceUrl);
        Response response = new ApiMethod().getMethod(serviceUrl);

    }
}
