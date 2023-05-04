package com.stepDefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

public class GitHubApiSteps {
    private RequestSpecification request;
    private Response response;
    private ValidatableResponse json;
    String uri = "https://api.github.com";

    @Given("^the user has an oauth token '(.*)'$")
    public void provideOauthToken(String oauthToken){
        request = given().auth().oauth2(oauthToken);
    }

    @When("^the user logs in$")
    public void navigateToURL(){
        RestAssured.baseURI = uri + "/user/repos";
        response = request.when().get(baseURI);
    }

    @When("^the user should see their repos$")
    public void validateResponseContainsRepos(){
        json = response.then().log().all();
    }
}
