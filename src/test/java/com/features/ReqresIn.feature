Feature: ReqresIn API

  Scenario Outline: Sanity checks - get requests
    Given I have provided the request specification '<queryParameter>'
    When I request to see data via the '<path>'
    Then the data will be returned with the correct status '<expectedStatusCode>'
    And the response is extracted and stored
    Examples:
      | path            | expectedStatusCode | queryParameter |
      | /api/users      | 200                |                |
      | /api/users      | 200                | page=2         |
      | /api/users/2    | 200                |                |
      | /api/users/23   | 404                |                |
      | /api/unknown    | 200                |                |
      | /api/unknown/2  | 200                |                |
      | /api/unknown/23 | 404                |                |
      | /api/users      | 200                | delay=3        |

  Scenario Outline: Validate requests against the schema
    Given I have provided the request specification
    When I request to see data via the '<path>'
    Then the data will adhere to the json-schema provided '<schema>'
    Examples:
      | path              | schema                                    |
      | /api/users        | ReqresInSchemas/usersPage2-schema.json    |
      | /api/users?page=2 | ReqresInSchemas/listUsers-schema.json     |
      | /api/users/2      | ReqresInSchemas/singleUser-schema.json    |
      | /api/unknown      | ReqresInSchemas/listResources-schema.json |

  Scenario Outline: Create user
    Given I want to create a new user '<newUserData>'
    When I create the new user '<baseUri>'
    Then the status code should be correct '<statusCode>'
    And the new user is created '<idField>' '<createdDateField>'
    Examples:
      | newUserData                           | baseUri                     | statusCode | idField | createdDateField |
      | {"name": "morpheus", "job": "leader"} | https://reqres.in/api/users | 201        | id      | createdAt        |