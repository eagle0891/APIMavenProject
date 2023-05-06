Feature: Local jsonServer API

  Scenario: Get posts
    Given I have provided the request spec
    When I send a request to get posts
    Then the posts response status will be 200
    And I will receive a list of posts in the response

  Scenario Outline: Create post
    Given I have provided the request spec
    When I send a request to create a post '<path>' '<field>'
    Then the response will be '<statusCode>'
    And the new post is present
    And all posts are returned correctly '<path>'
    Examples:
      | path   | statusCode | field |
      | /posts | 201        | id    |