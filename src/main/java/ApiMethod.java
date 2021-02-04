import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;

public class ApiMethod {

    public Response getMethod(String serviceUrl) {
        //Set headers for APIs
        RequestSpecBuilder builder = new RequestSpecBuilder();
        new ApiHeader().setHeaders(builder);

        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        Response response = request.get(serviceUrl);
        Assert.assertEquals(response.getStatusCode(), 200);
        return response;
    }
}
