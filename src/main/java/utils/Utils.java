package Utils;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.*;

public class Utils {
    //Global Setup Variables
    public static String path;
    public static String jsonPathTerm;

    //Sets Base URI
    public static void setBaseURI (){
        RestAssured.baseURI = "http://api.5min.com";
    }

    //Sets base path
    public static void setBasePath(String basePathTerm){
        RestAssured.basePath = basePathTerm;
    }

    //Reset Base URI (after test)
    public static void resetBaseURI (){
        RestAssured.baseURI = null;
    }

    //Reset base path
    public static void resetBasePath(){
        RestAssured.basePath = null;
    }

    //Sets ContentType
    public static void setContentType (ContentType Type){
        given().contentType(Type);
    }

    //Sets Json path term
    public static void  setJsonPathTerm(String jsonPath){
        jsonPathTerm = jsonPath;
    }

    //Created search query path
    public static void  createSearchQueryPath(String searchTerm, String param, String paramValue) {
        path = searchTerm + "/" + jsonPathTerm + "?" + param + "=" + paramValue;
    }

    //Returns response
    public static Response getResponse() {
        //System.out.print("path: " + path +"\n");
        return get(path);
    }

    //Returns JsonPath object
    public static JsonPath getJsonPath (Response res) {
        String json = res.asString();
        //System.out.print("returned json: " + json +"\n");
        return new JsonPath(json);
    }
}
