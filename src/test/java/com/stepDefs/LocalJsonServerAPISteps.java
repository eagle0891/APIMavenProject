package com.stepDefs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.helpers.PostServiceHelper;
import com.model.PostsPojo;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static org.junit.Assert.assertEquals;

public class LocalJsonServerAPISteps {

    private final PostServiceHelper postServiceHelper;
    RequestSpecification request;
    Response response;
    ValidatableResponse jsonResponse;

    public LocalJsonServerAPISteps(PostServiceHelper postServiceHelper) {
        this.postServiceHelper = postServiceHelper;
    }

    @Given("^I have provided the request spec$")
    public void postRequestSpec(){
        request = postServiceHelper.provideRequestSpec();
    }

    @When("^I send a request to get posts$")
    public void sendRequestToGetPosts() {
        response = postServiceHelper.getAllPosts();
    }

    @Then("^the posts response status will be (.*)$")
    public void validatePostsResponseStatus(int statusCode){
        assertEquals(response.getStatusCode(), statusCode);
    }

    @And("^I will receive a list of posts in the response$")
    public void validatePostsListIsReturned() throws Exception {
        String postsList = postServiceHelper.getPostPojoList().toString();
        System.out.println(postsList);
    }

    @When("^I send a request to create a post$")
    public void createPost() {
        response = postServiceHelper.createPost();
    }

    @And("^the new post is present$")
    public void validateNewPost() {
        PostsPojo postsPojo = response.as(PostsPojo.class);
        System.out.println("New post record is: " + postsPojo.toString());
    }

    @And("^all posts are returned correctly$")
    public void validateAllPostList() {
        response = postServiceHelper.getAllPosts();
        System.out.println("New post record is (second method): " + response.asString());
    }
}