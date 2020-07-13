package steps;

import io.restassured.RestAssured;
import io.restassured.response.*;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;


public class GetCategoriesByIDSteps {

    private static final String getIDsURL = "https://api.allegro.pl/sale/categories";

    @Step
    public static void getCategoryByID(String accessToken, String randomParentID) {
        Response response = RestAssured
                .given()
                .header("Authorization", "bearer" + accessToken)
                .header("Accept","application/vnd.allegro.public.v1+json")
                .when().get(getIDsURL + "/" + randomParentID)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("id"));
        Assert.assertTrue(responseBody.contains("leaf"));
        Assert.assertTrue(responseBody.contains("name"));
        Assert.assertTrue(responseBody.contains("options"));
        Assert.assertTrue(responseBody.contains("parent"));
        Assert.assertTrue(responseBody.contains("advertisement"));
        Assert.assertTrue(responseBody.contains("advertisementPriceOptional"));
        Assert.assertTrue(responseBody.contains("variantsByColorPatternAllowed"));
        Assert.assertTrue(responseBody.contains("offersWithProductPublicationEnabled"));
        Assert.assertTrue(responseBody.contains("productCreationEnabled"));
        Assert.assertTrue(responseBody.contains("productEANRequired"));
        Assert.assertFalse(responseBody.contains("error"));
    }

    @Step
    public static void getNonexistentCategory(String accessToken){
        Response response = RestAssured
                .given()
                .header("Authorization", "bearer" + accessToken)
                .header("Accept","application/vnd.allegro.public.v1+json")
                .when().get(getIDsURL + "/test")
                .then()
                .log().ifError()
                .statusCode(404)
                .extract().response();
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("error"));
    }
}
