Feature: Search Products, add products, delete products

  Background: Open the amazon URL and login into system
    Given User navigates to URL : "http://www.amazon.com" application
    And the user moves to login page
    And the user enters "gojektest51@gmail.com" in "email" field
    And the user enters "tauqir1234" in "password" field
    And clicks on the SignIn button
    And home page is displayed with "Hello, Tauqir" message

  @Regression
  Scenario: Select headphone and add to cart
    When the user selects shopall menu
    And clicks "Electronics" option from shopall list
    And the user selects "HEADPHONES" from main category
    And selects 1 product "Headphones" from top in list
    And clicks add to cart button
    Then item is added successfully
    And the user logoff from the system

  @Regression
  Scenario: Search Macbook pro and add to cart with quantity as 2
    When the user enters "MacBook Pro" in main search bar
    And selects 2 product "MacBook Pro" from top in list
    And the user selects quantity as "2"
    And clicks add to cart button
    Then item is added successfully
    And the user logoff from the system

  @Regression
  Scenario: View Cart and delete headphones from cart
    When the user views the cart items
    And the user removes "Headphones" item from cart
    And the user selects the quantity as "1" after delete
    And Clicks on proceed to checkout button

  @Regression
  Scenario: Search different items
    When the user enters "laptop bag for women" in main search bar
    Then products "Laptop Bag for Women" are visible on the searching page
    When the user enters "laptop bag for men" in main search bar
    Then products "Laptop Computer" are visible on the searching page
    When the user enters "mobile charger" in main search bar
    Then products "Charger" are visible on the searching page
    When the user enters "shoes for girls" in main search bar
    Then products "Shoe" are visible on the searching page
    When the user enters "glasses case" in main search bar
    Then products "Glasses" are visible on the searching page
    When the user enters "shirts for men" in main search bar
    Then products "Shirt" are visible on the searching page
    When the user enters "trouser socks women" in main search bar
    Then products "Trouser" are visible on the searching page
    And the user logoff from the system