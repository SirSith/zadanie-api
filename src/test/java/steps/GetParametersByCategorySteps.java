package steps;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;


public class GetParametersByCategorySteps {

    private static final String getParametersURL = "https://api.allegro.pl/sale/categories/";

    @Step
    public static void getParametersByID(String accessToken, String randomCategoryID) {
        Response response = RestAssured
                .given()
                .header("Authorization", "bearer" + accessToken)
                .header("Accept","application/vnd.allegro.public.v1+json")
                .when().get(getParametersURL + randomCategoryID + "/parameters")
                .then()
                .log().ifError()
                .statusCode(200)
                .extract().response();

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("name"));
        Assert.assertTrue(responseBody.contains("type"));
        Assert.assertTrue(responseBody.contains("required"));
        Assert.assertTrue(responseBody.contains("requiredForProduct"));
        Assert.assertTrue(responseBody.contains("unit"));
        Assert.assertTrue(responseBody.contains("options"));
        Assert.assertTrue(responseBody.contains("restrictions"));
        Assert.assertFalse(responseBody.contains("error"));

        String parametersTypes = response.path("parameters.type").toString();
        String dictionaryPresent = response.path("parameters.dictionary").toString();
        if (parametersTypes.contains("dictionary")) {
            Assert.assertTrue(dictionaryPresent.contains("id"));
        } else {
            Assert.fail();
        }
    }

    @Step
    public static void getParametersByNonexistentID(String accessToken) {
        Response response = RestAssured
                .given()
                .header("Authorization", "bearer" + accessToken)
                .header("Accept","application/vnd.allegro.public.v1+json")
                .when().get(getParametersURL + "test/parameters")
                .then()
                .statusCode(404)
                .extract().response();

        String errorMsg = response.jsonPath().getString("errors.message.");
        Assert.assertEquals(errorMsg,"[Category 'test' not found]");
    }
}