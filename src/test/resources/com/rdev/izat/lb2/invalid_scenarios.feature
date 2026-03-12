Feature: Invalid parameters and operations
  All supported calculator parameters and operations

  Scenario: Add 1 and asdqwe
    Given first number is "1"
    Given second number is "asdqwe"
    Given operation is "+"
    When I calculate the result
    Then I should get error "Invalid number!"

  Scenario: Add asdasd and 5
    Given first number is "asdasd"
    Given second number is "5"
    Given operation is "+"
    When I calculate the result
    Then I should get error "Invalid number!"

  Scenario: Invalid operation **
    Given first number is "5"
    Given second number is "5"
    Given operation is "**"
    When I calculate the result
    Then I should get error "Invalid operation!"

  Scenario: Divide by 0
    Given first number is "5"
    Given second number is "0"
    Given operation is "/"
    When I calculate the result
    Then I should get error "Can't divide by zero!"