Feature: Login to OrangeHRM using Excel data

  Scenario: Login OrangeHRM with multiple users from Excel
    Given I open Chrome browser
    When I login to OrangeHRM with users from Excel
    Then I close the browser
