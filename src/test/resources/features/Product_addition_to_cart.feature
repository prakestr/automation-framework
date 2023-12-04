# product_addition_to_cart.feature

@cart
Feature: Product Addition to Cart
  As a user,
  I want to search for a product,
  Add it to the cart,
  And validate that it has been added to the cart

  Scenario Outline: Adding a product to the cart and proceeding to checkout
    Given I am on the homepage
    When I search for "<Search_Product_Name>" and add the product with name "<Product_Name>" to the cart
    Then I validate that the product with SKU "<SKU>" and name "<Product_Name>" is in the cart
    And I proceed to checkout as a guest

    Examples:
      | Search_Product_Name | Product_Name                                     | SKU       |
      | Digital Storm       | Digital Storm VANQUISH 3 Custom Performance PC  | DS_VA3_PC |
      | Lenovo Thinkpad     | Lenovo Thinkpad X1 Carbon Laptop                | LE_TX1_CL |
