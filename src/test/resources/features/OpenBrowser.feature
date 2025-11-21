Feature: Open Chrome Browser

  Scenario: Open Google home page in Chrome
    Given I open Chrome browser
    When I navigate to Google
    Then I should see the Google page title
