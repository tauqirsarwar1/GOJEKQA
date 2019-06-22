Feature: Search Products

  Background: Open the amazon website URL
    Given User navigates to URL : "http://www.amazon.com" application

  @Regression
  Scenario: Perform a simple login flow on the site
    When the user moves to login page
    And the user enters "gojektest51@gmail.com" in "email" field
    And the user enters "tauqir1234" in "password" field
    And clicks on the SignIn button
    Then home page is displayed with "Hello, Tauqir" message
    When the user logoff from the system