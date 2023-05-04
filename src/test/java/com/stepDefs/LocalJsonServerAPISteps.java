package com.stepDefs;

import com.model.PostsPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stepDefs.APITestHelpers.TestHelpers;
import com.utils.FakerUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.Arrays;

import static io.restassured.RestAssured.*;

public class LocalJsonServerAPISteps {
    FakerUtils fakerUtils;
    PostsPojo postPojo;
    TestHelpers testHelpers;
    RequestSpecification request;
    Response response;
    ValidatableResponse jsonResponse;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String BASE_URI = "http://localhost:3000";

    @Given("^I have provided the request spec '(.*)'$")
    public void provideRequestSpec(String contentType) {
        if (contentType.isBlank()) {
            System.out.println("**** Running @Given step ****");
            request = given().log().all();
        } else {
            request = given().log().all().contentType(contentType);
        }
    }

    @When("^I send a request for posts$")
    public void sendRequest() {
        System.out.println("**** Running @When step ****");
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = "/posts";
        response = request.when().log().all().get(baseURI + basePath);
    }

    @Then("^I will receive a list of posts$")
    public void validateTheResponse() {
        System.out.println("**** Running @Then step ****");
        jsonResponse = response.then().log().all().statusCode(200);
    }

    @When("^I send a request to create a post '(.*)' '(.*)'$")
    public void createPost(String path, String field) throws JsonProcessingException {
        postPojo = new PostsPojo();
        fakerUtils = new FakerUtils();
        testHelpers = new TestHelpers();
        postPojo.setId(testHelpers.createUniqueObjectId(BASE_URI, path, field) + 1);
        postPojo.setTitle(fakerUtils.generateBookName());
        postPojo.setAuthor(fakerUtils.generateAuthorName());
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = path;
        String jsonData = MAPPER.writeValueAsString(postPojo);
        response = request.body(jsonData).when().log().all().post(baseURI + basePath).andReturn();
    }

    @Then("^the response will be '(.*)'$")
    public void validateResponseStatus(String statusCode) {
        jsonResponse = response.then().log().all().statusCode(Integer.parseInt(statusCode));
    }

    @And("^the new post is present$")
    public void validateNewPost() {
        PostsPojo postsPojo = response.as(PostsPojo.class);
        System.out.println("New post record is: " + postsPojo.toString());
    }

    @And("^all posts are returned correctly '(.*)'$")
    public void validateAllPostList(String path) {
        RestAssured.basePath = path;
        PostsPojo[] allPosts = RestAssured.get(BASE_URI + basePath).as(PostsPojo[].class);
        System.out.println("New post record is (second method): " + Arrays.toString(allPosts));
    }
}