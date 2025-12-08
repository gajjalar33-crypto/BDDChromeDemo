Feature: DemoQA WebTables automation

  Scenario: open demoqa homepage in chrome
    Given I open Chrome browser
    When I navigate to demoqa
    Then I should see demoqa page title
    And I should see demoqa home page
    When I edit the last row salary to "25000"

  Scenario: Edit last row salary after scroll
    Given I navigate to DemoQA Web Tables page
    And I wait for the WebTable to load
    And I scroll down to the WebTable
    When I edit the last row salary to "25000"
    And I click the submit button
    Then I should see the last row salary as "25000"
    And I click the add button
