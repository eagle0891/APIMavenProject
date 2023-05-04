package com.stepDefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import junit.framework.Assert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;

public class FirstAPITest {
//    ConsoleLogger consoleLogger = new ConsoleLogger();
    String jsonResponse;
    JsonPath jsonPath;
    static ValidatableResponse response;

    @Given("^the URI '(.*)' is called and the endpoint is '(.*)' and the status code is '(.*)' then the response matches the schema$")
    public ValidatableResponse driversRequest(String uri, String endpoint, Integer statusCode){
        RestAssured.baseURI = uri;

        response =
            given().log().all()
                    .when()
                    .get(endpoint)
                    .then()
                    .assertThat()
                    .statusCode(statusCode)
                    .and()
                    .body(notNullValue())
                    .and()
                    .body(matchesJsonSchemaInClasspath("drivers-schema.json"));
        return response;
    }

    @Given("^the URI '(.*)' is called and the endpoint is '(.*)' and the status code is '(.*)' then the responsePath '(.*)' and field '(.*)' contains '(.*)'$")
    public void driversResponseFields(String uri, String endpoint, Integer statusCode, String responsePath, String field, String expectedValue) {
        RestAssured.baseURI = uri;

        String driversObject = given()
                .when()
                .get(endpoint)
                .then().log().all()
                .assertThat()
                .statusCode(statusCode)
                .extract().path(responsePath+field);

        System.out.println(driversObject);
        Assert.assertEquals("field not found", expectedValue, driversObject);
    }

    @Given("^the endpoint is called successfully then the responsePathFields should contain the expected values$")
    public void driversResponseFieldsViaDataTable(DataTable dataTable) {
        System.out.println(dataTable);
        List<List<String>> data = dataTable.cells();
        String uri = data.get(1).get(0);
        String endPoint = data.get(1).get(1);
        String statusCode = data.get(1).get(2);
        String responsePath = data.get(1).get(3);
        String field = data.get(1).get(4);
        String expectedValue = data.get(1).get(5);

        RestAssured.baseURI = uri;

        String driversObject = given()
                .when()
                .get(endPoint)
                .then().log().all()
                .assertThat()
                .statusCode(Integer.parseInt(statusCode))
                .extract().path(responsePath+field);

        System.out.println(driversObject);
        System.out.println(expectedValue);
        Assert.assertEquals("Field value does not match", expectedValue, driversObject);
    }

    @Given("^the endpoint is called successfully then the responsePathFields should contain the expected values with asMaps$")
    public void driversResponseFieldsViaDataTableAsMaps(DataTable dataTable) {
        System.out.println(dataTable);
        System.out.println(dataTable.asLists());
        System.out.println(dataTable.asMaps());

        List<Map<String, String>> data = dataTable.asMaps();

        for (Map<String, String> row : data) {
            String uri = row.get("uri");
            String endpoint = row.get("endpoint");
            String statusCode = row.get("statusCode");
            String responsePath = row.get("responsePath");
            String field = row.get("field");
            String expectedValue = row.get("expectedValue");

            RestAssured.baseURI = uri;

            String driversObject = given()
                    .when()
                    .get(endpoint)
                    .then().log().all()
                    .assertThat()
                    .statusCode(Integer.parseInt(statusCode))
                    .extract().path(responsePath + field);
            System.out.println(field);
            System.out.println(driversObject);
            Assert.assertEquals("\"" + field + "\" field mismatch: Expected Value = " + expectedValue + ", Actual Value = " + driversObject, expectedValue, driversObject);
        }
    }

    @Given("^the endpoint is called successfully$")
    public void theEndpointIsCalledSuccessfully() {

    }

    @Then("^the responsePathFields should contain the expected values$")
    public void theResponsePathFieldsShouldContainTheExpectedValuesWithAsMaps() {
    }
}
