package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import java.util.Base64;

public class BasicTestClass {

    public static String generatedAccessToken;

    @Before
    public void getAccessToken () {
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
}