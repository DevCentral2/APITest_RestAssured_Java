import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Methods {

    static QuoteResponse GetSpecificQuote (int quoteId, String authToken)
    {
        Response response = given()
                .header("Authorization", "Token token=" + authToken)
                .when()
                .get("/quotes/" + quoteId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();

        return response.getBody().as(QuoteResponse.class);
    }
}
