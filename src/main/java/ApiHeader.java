import io.restassured.builder.RequestSpecBuilder;

public class ApiHeader {

    //To add multiple api headers for an api
    public void setHeaders(RequestSpecBuilder builder) {
        builder.addHeader("Content-Type","application/json");
    }
}