@ManufacturersAndViewAll
Feature: Verify Category List and 'View all' link under 'Manufacturers'

  @CategoryList
  Scenario: Ensure category list is visible on the Computers page
    Given I am on the homepage
    And I navigate to the Computers category page
    Then I should see the categories listed from "Desktops" through "Software"

  @ViewAllManufacturers
  Scenario: Click on the 'View all' link under the 'Manufacturers' block and verify the manufacturers
    Given I am on the homepage
    When I click on the 'View all' link under the 'Manufacturers' block
    Then I am redirected to a page listing all manufacturers
    And I verify the "Manufacturer List" is displayed
    And I verify that manufacturers "Apple", "HP", and "Nike" are displayed
