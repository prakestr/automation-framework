@UserRegistration
Feature: User Registration and Login

  Scenario: Successful registration and subsequent login of a new user
    Given I am on the homepage
    When I click on "Register"
    And I enter the registration details
    And I click on Register button
    Then I should see "Your registration completed" message
    And I click on "Continue" button
    When I click on "Login in" link
    And I enter Email and Password
    And I click on "Log In" button
    Then I should see "Log out" link
