Feature: open Amazon browser
  Scenario: open Amazon homepage in chrome
    Given  I open Chrome browser
    When I navigate to Amazon
    Then I should see Amazon page title
    And I should see Amazon home page
    And I click on Air conditioners
    And I add Voltas AC to cart
    And I go back to Amazon home page
    And I click the bike carousel right arrow
    And I click on the first bike item
    Then I scroll down and print customer reviews
    And I scroll to footer and print footer sections
    And I go back to Amazon home page

