
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;



public class APITests {

    private final String authToken = "7cc40bfb9e3312eeafa099fe6739a7c8";

    @BeforeClass
    public void setUp() {
        // Set the base URL of the API
        RestAssured.baseURI = "https://favqs.com/api/";
    }

    @Test
    public void getQuoteOfTheDayResponse_RequestSuccessful() {

        given()
                .when()
                .get("/qotd")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void getQuoteOfTheDay_AuthorAndQuoteReturned() {

        given()
                .when()
                .get("/qotd")
                .then()
                .assertThat()
                .statusCode(200)
                .body(not(emptyString()))
                .body("quote.author", not(emptyString()))
                .body("quote.body", not(emptyString()));
    }

    @Test
    public void checkUnauthorisedRequest_StatusIsUnauthorised() {

        given()
                .when()
                .get("/quotes")
                .then()
                .assertThat()
                .statusCode(401);
    }

    @Test
    public void checkAuthorisedRequest_RequestSuccessful() {

        given()
                .header("Authorization", "Token token=" + authToken)
                .when()
                .get("/quotes")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void getQuotes_GetSpecificQuote_CheckCorrectQuoteId() {

        // Input Data
          int quoteId = 248;

        Response response = given()
                .header("Authorization", "Token token=" + authToken)
                .when()
                .get("/quotes/" + quoteId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();

        QuoteResponse quote = response.getBody().as(QuoteResponse.class);

        System.out.println("ID: " + quote.getId());
        System.out.println("Author: " + quote.getAuthor());
        System.out.println("Body: " + quote.getBody());

        assertEquals(quote.getId(), quoteId, "Quote expected was " + quoteId + " but quote retrieved was " + quote.getId());
    }

    @Test
    public void filterQuotesByKeyword_KeywordFoundInQuote() {

        // Input Data
        String keyword = "education";

        Response response = given()
                .header("Authorization", "Token token=" + authToken)
                .get("/quotes/?filter=" + keyword);

        QuotesResponse quotesResponse = response.getBody().as(QuotesResponse.class);
        List<QuoteResponse> quotes = quotesResponse.getQuotes();

        for (QuoteResponse quote : quotes) {
            boolean containsQuote = quote.getBody().toLowerCase().contains(keyword.toLowerCase());
            assertThat("Quote found does not contain keyword " + keyword + ". Quote found: Author: " + quote.getAuthor() + ", Quote: " + quote.getBody(), containsQuote);
        }
    }

    @Test
    public void filterQuotesByAuthor_AuthorFoundInQuote() {

        // Input Data
         String author = "Albert Einstein";

        Response response = given()
                .header("Authorization", "Token token=" + authToken)
                .get("quotes/?filter=" + author.replace(" ", "+") + "&type=author");

        QuotesResponse quotesResponse = response.getBody().as(QuotesResponse.class);
        List<QuoteResponse> quotes = quotesResponse.getQuotes();

        for (QuoteResponse quote : quotes) {
            boolean containsQuote = quote.getAuthor().toLowerCase().contains(author.toLowerCase());
            assertThat("Quote found does not contain author " + author + ". Quote found: Author: " + quote.getAuthor() + ", Quote: " + quote.getBody(), containsQuote);
        }
    }


    @Test
    public void GetSpecificQuotes_CorrectDataFound() {

        // Input Data
        List<Quote> quotesData = List.of(
                new Quote(248, "Mark Twain", "Never let your schooling interfere with your education."),
                new Quote(396, "Abraham Lincoln", "I never had a policy; I have just tried to do my very best each and every day."),
                new Quote(434, "Albert Einstein", "The formulation of a problem is often more essential than its solution, which may be merely a matter of mathematical or experimental skill.")
        );

        for (Quote quoteData : quotesData) {

            QuoteResponse quoteResponse = Methods.GetSpecificQuote(quoteData.getId(), authToken);
            assertEquals(quoteData.getId(), quoteResponse.getId(), "Quote expected was " + quoteData.getId() + " but quote retrieved was " + quoteResponse.getId());
            System.out.println("Id: " + quoteData.getId() + ", Author: " + quoteData.getAuthor() + ", Quote: " + quoteData.getQuoteText());
        }
    }
}


