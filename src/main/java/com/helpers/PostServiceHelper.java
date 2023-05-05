package com.helpers;

import com.constants.Endpoints;
import com.fasterxml.jackson.core.type.TypeReference;
import com.model.PostsPojo;
import com.utils.ConfigManager;
import com.utils.FakerUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Assert;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PostServiceHelper {
    FakerUtils fakerUtils;
    HelperFunctions helperFunctions;
    PostsPojo postsPojo;

    public static String BASE_URI;

    static {
        try {
            BASE_URI = ConfigManager.getInstance().getString("baseUrl");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PostServiceHelper(){
        RestAssured.baseURI = BASE_URI;
        RestAssured.useRelaxedHTTPSValidation();
    }

    public List<PostsPojo> getAllPosts(){
        Response response = RestAssured.given().contentType(ContentType.JSON).get(Endpoints.GET_ALL_POSTS).andReturn();

        Type type = new TypeReference<List<PostsPojo>>() {}.getType();
        assertEquals(String.valueOf(response.getStatusCode()), HttpStatus.SC_OK, "OK");

        List<PostsPojo> postList = response.as(type);
        return postList;
    }

    public Response createPost(){
        fakerUtils = new FakerUtils();
        helperFunctions = new HelperFunctions();
        postsPojo = new PostsPojo();

        postsPojo.setId(helperFunctions.createUniqueObjectId(BASE_URI, Endpoints.CREATE_POST, "id") + 1);
        postsPojo.setTitle(fakerUtils.generateBookName());
        postsPojo.setAuthor(fakerUtils.generateAuthorName());

        Response response = RestAssured.given().log().all().contentType(ContentType.JSON).when().body(postsPojo).post(Endpoints.CREATE_POST).andReturn();
        assertEquals(String.valueOf(response.getStatusCode()), HttpStatus.SC_CREATED, "Created");

        return null;
    }
}
