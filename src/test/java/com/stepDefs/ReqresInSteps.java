package com.stepDefs;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.*;

public class ReqresInSteps {
    RequestSpecification request;
    Response response;
    ValidatableResponse json;

    @Given("^I have provided the request specification$")
    public void provideRequestSpecification(){
        request = given().log().all();
    }

    @Given("^I have provided the request specification '(.*)'$")
    public void provideRequestSpecification(String queryParameter){
        if (queryParameter.equals("")) {
            request = given().log().all();
        } else {
            request = given().log().all().queryParam(queryParameter);
        }
    }

    @When("^I request to see data via the '(.*)'$")
    public void requestData(String path){
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = path;
        response = request.when().log().all().get(baseURI + basePath);
    }

    @Then("^the data will be returned with the correct status '(.*)'$")
    public void returnData(String expectedStatusCode){
        json = response.then().log().all().statusCode(Integer.parseInt(expectedStatusCode));
    }

    @And("^the response is extracted and stored$")
    public ValidatableResponse jsonResponse(){
        String extractedJsonResponse = json.extract().jsonPath().toString(); //not working as I would like
        System.out.println(extractedJsonResponse);
        return json;
    }

    @Then("^the data will adhere to the json-schema provided '(.*)'$")
    public void validateResponseAgainstSchema(String schema){
        json = response.then().log().all().body(matchesJsonSchemaInClasspath(schema));
    }

    @Given("^I want to create a new user '(.*)'$")
    public void provideNewUserData(String newUserData){
        request = given().log().all()
                .body(newUserData);
    }

    @When("^I create the new user '(.*)'$")
    public void createNewUser(String baseUri){
        RestAssured.baseURI = baseUri;
        response = request.when().log().all()
                .post(baseURI);
    }

    @Then("^the status code should be correct '(.*)'$")
    public void validateStatusCode(String statusCode){
        json = response.then().log().all()
                .statusCode(Integer.parseInt(statusCode));
    }

    @And("^the new user is created '(.*)' '(.*)'$")
    public void validateNewUserIsCreated(String idField, String createdDateField){
        json = response.then().log().all()
                .body(idField, is(notNullValue()))
                .body(createdDateField, is(notNullValue()));
    }
}