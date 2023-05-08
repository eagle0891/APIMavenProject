Feature: GitHubApi
  Scenario: User logs in via OAUTH2 authentication
    Given the user has an oauth token 'tokenToBeAdded'
    When the user logs in
    Then the user should see their repos