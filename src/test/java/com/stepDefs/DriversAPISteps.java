package com.stepDefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class DriversAPISteps {

    private RequestSpecification request;
    private ValidatableResponse json;
    private Response response;
    private static String uri = "http://ergast.com/api/f1/drivers/";
    private static String fileType = ".json";


    @Given("^the user intends to search$")
    public void searchSetup(){
        request = given();
    }

    @When("^a specific driver '(.*)' is searched$")
    public void driverExists(String driverName){
        RestAssured.baseURI = uri + driverName + fileType;
        response = request.get(baseURI);
    }

    @Then("^the status code is '(\\d+)'$")
    public void verifyStatusCode(Integer statusCode){
        json = response.then().log().all().statusCode(statusCode);
        System.out.println(json);
    }

    @And("^the response contains the correct driver details$")
    public void verifyResponseContainsCorrectDriverDetails(DataTable dataTable){
        List<Map<String, String>> data = dataTable.asMaps();

        for (Map<String, String> row : data) {
            String field = row.get("field");
            String responsePath = row.get("responsePath");
            String expectedValue = row.get("expectedValue");

            String fieldValue = json.and().extract().path(responsePath + field);
            Assert.assertEquals("\"" + field + "\" field mismatch: Expected Value = " + expectedValue + ", Actual Value = " + fieldValue, expectedValue, fieldValue);
        }
    }
}
