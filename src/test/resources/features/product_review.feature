@ProductReviewFlow
Feature: End-to-end user interaction with product search and review

  Background: User Registration and Login
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

  Scenario Outline: User searches for a product, sorts the results, and submits a review for the lowest priced product
    Given I have registered a new user
    When I search for "<Brand>" products and sort the results by price from low to high
    And I select the product with the lowest price
    And I verify the product details for "<Product>"
    And I submit a review with title "<Title>", text "<Text>", and rating "<Rating>"
    Then the review submission should be successful
    And my review with title "<Title>" and text "<Text>" should be displayed on the product details page

    Examples:
      | Brand   | Product                                       | Title                  | Text                                                 | Rating    |
      | Nike    | Nike Tailwind Loose Short-Sleeve Running Shirt | Impressive Performance | This shirt provides great comfort during long runs. | Excellent |
      | Notebook| HP Spectre XT Pro UltraBook                   | Durable and Stylish    | Perfect for both casual wear and active sports.     | Good      |
