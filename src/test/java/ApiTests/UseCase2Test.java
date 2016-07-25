package ApiTests;

import Utils.Utils;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) //For Ascending order test execution
public class UseCase2Test {
    private Response res = null; //Response
    private JsonPath jp = null; //JsonPath

    //Instantiate a Helper Test Methods (htm) Object
    HelperTestMethods htm = new HelperTestMethods();

    @Before
    public void setup (){
        //Test Setup
        Utils.setBaseURI(); //Setup Base URI
        Utils.setBasePath("video"); //Setup Base Path
        //Create a path (It can be done via Utils method. I created manually.)
        Utils.path = "list/info.json?video_ids=518793166&num_related_return=5";
        Utils.setContentType(ContentType.JSON); //Setup Content Type
        res = Utils.getResponse(); //Get response
        jp = Utils.getJsonPath(res); //Set JsonPath
    }

    @Test
    public void T01_StatusCodeTest() {
        //Verify the http response status returned. Check Status Code is 200?
        htm.checkStatusIs200(res);
    }

    @Test
    public void T02_SearchTermTest() {
        //Verify the response contained the relevant search term (518793166)
        assertEquals("Id does not match!", "518793166", htm.getVideoIdList(jp).get(0).toString());
    }

    @Test
    public void T03_verifyExtraFiveVideosReturned() {
        //Verify that extra 5 video entries were returned as related videos
        Assert.assertEquals("Related video Size is not equal to 5", 5, htm.getRelatedVideoIdList(jp).size());
    }

    @Test
    public void T04_duplicateVideoVerification() {
        //Check duplicate videos exist?
        assertTrue("Duplicate videos exist!", htm.findDuplicateVideos(getMergedVideoLists()));
    }

    @Test
    public void T05_printAttributes() {
        //Print attributes
        printAttributes(jp);
    }

    @After
    public void afterTest (){
        //Reset Values
        Utils.resetBaseURI();
        Utils.resetBasePath();
    }

    //*******************
    //***Local Methods***
    //*******************
    //Returns Merged Video Lists (Video List + Related Video List)
    private ArrayList getMergedVideoLists (){
        return htm.mergeLists(htm.getVideoIdList(jp), htm.getRelatedVideoIdList(jp));
    }

    //Prints Attributes
    private void printAttributes(JsonPath jp) {
        for(int i=0; i <getMergedVideoLists().size(); i++ ) {
                //Prints Video List Attributes
                if(jp.get("items.title[" + i + "]") != null) {
                    System.out.println("title: " + jp.get("items.title[" + i + "]"));
                    System.out.println("Tablets: " + jp.get("items.permittedDeviceTypes.Tablets[" + i + "]"));
                    System.out.println("Handsets: " + jp.get("items.permittedDeviceTypes.Handsets[" + i + "]"));
                    System.out.println("ConnectedDevices: " + jp.get("items.permittedDeviceTypes.ConnectedDevices[" + i + "]"));
                    System.out.println("Computers: " + jp.get("items.permittedDeviceTypes.Computers[" + i + "]"));
                    System.out.println("Duration: " + jp.get("items.duration[" + i + "]"));
                    System.out.print("\n");

                    //Check that sent video has related videos? If yes print their attributes
                    if (jp.get("items.related.title[" + i + "][" + i + "]") != null) {
                        for (int j = 0; j < htm.getRelatedVideoIdList(jp).size(); j++) {
                            System.out.println("title: " + jp.get("items.related.title[0][" + j + "]"));
                            System.out.println("Tablets: " + jp.get("items.related.permittedDeviceTypes.Tablets[0][" + j + "]"));
                            System.out.println("Handsets: " + jp.get("items.related.permittedDeviceTypes.Handsets[0][" + j + "]"));
                            System.out.println("ConnectedDevices: " + jp.get("items.related.permittedDeviceTypes.ConnectedDevices[0][" + j + "]"));
                            System.out.println("Computers: " + jp.get("items.related.permittedDeviceTypes.Computers[0][" + j + "]"));
                            System.out.println("Duration: " + jp.get("items.related.duration[0][" + j + "]"));
                            System.out.print("\n");
                        }
                    }
                }
        }
    }

}
