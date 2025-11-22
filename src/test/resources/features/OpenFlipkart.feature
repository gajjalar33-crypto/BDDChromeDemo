Feature: open flipkart browser
  Scenario: open flipkart homepage in chrome
    Given  I open Chrome browser
    When I navigate to Flipkart
    Then I should see Flipkart page title
