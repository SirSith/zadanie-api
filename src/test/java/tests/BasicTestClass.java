package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.springframework.core.annotation.Order;

import java.util.Base64;
import java.util.List;
import java.util.Random;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BasicTestClass {

    public static String generatedAccessToken;
    public static String randomParentID;

    //class name seems to be weird,
    // yet methods are executed in reversed lexicographic order with respect to the method name
    @BeforeClass
    public static void getTokenAccess () {
        String getTokenURL = "https://allegro.pl/auth/oauth/token";
        //authorization keys hidden in system environment variables for security reasons
        String authorizationHeader = Base64.getEncoder().encodeToString(
                (System.getenv("ALLEGRO_CLIENT_ID") + ":" + System.getenv("ALLEGRO_CS")).getBytes()) ;

        Response response = RestAssured
                .given()
                .header("Authorization", "Basic " + authorizationHeader)
                .header("Content-Type","application/x-www-form-urlencoded")
                .param("grant_type","client_credentials")
                .when().post(getTokenURL)
                .then()
                .log().ifError()
                .statusCode(200)
                .extract().response();

        generatedAccessToken = response.path("access_token");
    }

    @BeforeClass
    public static void getRandomCategoryID () {
        final String getIDsURL = "https://api.allegro.pl/sale/categories";
        Response getRandomID = RestAssured
                .given()
                .header("Authorization","bearer" + generatedAccessToken)
                .header("Accept","application/vnd.allegro.public.v1+json")
                .when().get(getIDsURL)
                .then()
                .statusCode(200)
                .log().ifError()
                .extract().response();
        List<String> idsList = getRandomID.jsonPath().getList("categories.id");
        Random random = new Random();
        randomParentID = idsList.get(random.nextInt(idsList.size()));
    }
}