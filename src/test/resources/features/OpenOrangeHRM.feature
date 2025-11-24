Feature: Open OrangeHRM

  Scenario: Open OrangeHRM login page
    Given I open Chrome browser
    When I navigate to OrangeHRM
    Then I should see OrangeHRM login page
