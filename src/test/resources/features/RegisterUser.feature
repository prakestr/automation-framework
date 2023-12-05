@UserRegistration
Feature: User Registration

  Scenario: Successful registration of a new user
    Given I am on the homepage
    When I click on "Register"
    And I enter the registration details
    And I click on Register button
    Then I should see "Your registration completed" message
