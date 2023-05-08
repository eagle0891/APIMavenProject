package com.helpers;

import com.constants.Endpoints;
import com.fasterxml.jackson.core.type.TypeReference;
import com.model.PostsPojo;
import com.utils.ConfigManager;
import com.utils.FakerUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

public class PostServiceHelper extends ConfigManager{

    public PostServiceHelper(){
        super();
    }

    FakerUtils fakerUtils;
    HelperFunctions helperFunctions;
    PostsPojo postsPojo;

    public static final String BASE_URI = "http://localhost:3000"; //prop.getProperty("baseUrl");

    public String getBaseUri(){
        RestAssured.baseURI = BASE_URI;
        useRelaxedHTTPSValidation();
        return BASE_URI;
    }

    public RequestSpecification provideRequestSpec(){
            return given().log().all().contentType(ContentType.JSON);
    }

    public Response getAllPosts(){
        RestAssured.baseURI = "http://localhost:3000";
        return provideRequestSpec().when().log().all().get(baseURI + Endpoints.GET_ALL_POSTS).andReturn();
    }

    public List<PostsPojo> getPostPojoList() throws Exception {
        Type type = new TypeReference<List<PostsPojo>>() {}.getType();
        List<PostsPojo> postList = getAllPosts().as(type);
        System.out.println(postList);
        return postList;
    }

    public Response createPost(){
        fakerUtils = new FakerUtils();
        helperFunctions = new HelperFunctions();
        postsPojo = new PostsPojo();

        postsPojo.setId(helperFunctions.createUniqueObjectId(BASE_URI, Endpoints.CREATE_POST, "id") + 1);
        postsPojo.setTitle(fakerUtils.generateBookName());
        postsPojo.setAuthor(fakerUtils.generateAuthorName());

//        Response response = given().log().all().contentType(ContentType.JSON).when().body(postsPojo).post(Endpoints.CREATE_POST).andReturn();
//        assertEquals(String.valueOf(response.getStatusCode()), HttpStatus.SC_CREATED, "Created");

        return provideRequestSpec().when().body(postsPojo).post(BASE_URI + Endpoints.CREATE_POST).andReturn();
    }


}
