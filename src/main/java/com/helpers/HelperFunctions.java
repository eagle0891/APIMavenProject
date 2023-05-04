package com.helpers;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class HelperFunctions {
    Response response;

    //gets the last id of the last object in the response body, and adds 1 to it to create a unique id
    public int createUniqueObjectId(String baseUri, String path, String field){
        response = RestAssured.given().get(baseUri + path);
        JsonPath jsonPath = new JsonPath(response.getBody().asString());
        int size = jsonPath.getList("$").size();
        return jsonPath.getInt("[" + (size - 1) + "]." + field);
    }
}
