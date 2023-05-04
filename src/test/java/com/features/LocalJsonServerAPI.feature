Feature: Local jsonServer API

  Scenario Outline: Get posts
    Given I have provided the request spec '<contentType>'
    When I send a request for posts
    Then I will receive a list of posts
    Examples:
      | contentType |
      |             |

  Scenario Outline: Create post
    Given I have provided the request spec '<contentType>'
    When I send a request to create a post '<path>' '<field>'
    Then the response will be '<statusCode>'
    And the new post is present
    And all posts are returned correctly '<path>'
    Examples:
      | contentType      | path   | statusCode | id  | field |
      | application/json | /posts | 201        | /10 | id    |