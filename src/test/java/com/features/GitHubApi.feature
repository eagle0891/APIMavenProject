Feature: GitHubApi
  Scenario: User logs in via OAUTH2 authentication
    Given the user has an oauth token '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'
    When the user logs in
    Then the user should see their repos