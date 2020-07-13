package steps;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;
import java.util.List;
import java.util.Random;



public class GetIDsSteps {

    private static final String getIDsURL = "https://api.allegro.pl/sale/categories";

    @Step
    public static void getIDs(String accessToken) {
        Response response = RestAssured
                .given()
                .header("Authorization","bearer" + accessToken)
                .header("Accept","application/vnd.allegro.public.v1+json")
                .when().get(getIDsURL)
                .then()
                .log().ifError()
                .statusCode(200)
                .extract().response();
        List<String> idsList = response.jsonPath().getList("categories.id");
        Assert.assertFalse(idsList.isEmpty());
    }

    @Step
    public static void getIDsWithParent(String accessToken) {
        Response response = RestAssured
                .given()
                .header("Authorization","bearer" + accessToken)
                .header("Accept","application/vnd.allegro.public.v1+json")
                .param("parent.id","954b95b6-43cf-4104-8354-dea4d9b10ddf")
                .when().get(getIDsURL)
                .then()
                .log().ifError()
                .statusCode(200)
                .extract().response();
        List<String> idsList = response.jsonPath().getList("categories.id");
        Assert.assertFalse(idsList.isEmpty());
    }

    @Step
    public static void getIDsWithNonexistentParent(String accessToken) {
        Response response = RestAssured
                .given()
                .header("Authorization","bearer" + accessToken)
                .header("Accept","application/vnd.allegro.public.v1+json")
                .param("parent.id","test")
                .when().get(getIDsURL)
                .then()
                .log().ifError()
                .statusCode(404)
                .extract().response();
        String errorMsg = response.jsonPath().getString("errors.message.");
        Assert.assertEquals(errorMsg,"[Category 'test' not found]");

    }

    @Step
    public static void getIDsFromChild (String accessToken) {
        Response getRandomID = RestAssured
                .given()
                .header("Authorization","bearer" + accessToken)
                .header("Accept","application/vnd.allegro.public.v1+json")
                .when().get(getIDsURL)
                .then()
                .log().ifError()
                .extract().response();
        List<String> idsList = getRandomID.jsonPath().getList("categories.id");
        Random random = new Random();
        String randomParentID = idsList.get(random.nextInt(idsList.size()));

        Response response = RestAssured
                .given()
                .header("Authorization","bearer " + accessToken)
                .header("Accept","application/vnd.allegro.public.v1+json")
                .param("parent.id", randomParentID)
                .when().get(getIDsURL)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
        List<String> parentIDsList = response.jsonPath().getList("categories.parent.id");
        for (String s : parentIDsList) {
            if (s.equals(randomParentID)) {
                Assert.assertTrue(true);
            } else {
                Assert.fail();
            }
        }
    }
}
