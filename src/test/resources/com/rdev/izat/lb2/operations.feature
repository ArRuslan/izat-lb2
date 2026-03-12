Feature: Calculator operations
  All supported calculator operations

  Scenario: Add 1 and 2
    Given first number is "5"
    Given second number is "3"
    Given operation is "+"
    When I calculate the result
    Then I should be told "8"

  Scenario: Subtract 2 from 1
    Given first number is "1"
    Given second number is "2"
    Given operation is "-"
    When I calculate the result
    Then I should be told "-1"

  Scenario: Multiply 5 from 100
    Given first number is "5"
    Given second number is "100"
    Given operation is "*"
    When I calculate the result
    Then I should be told "500"

  Scenario: Divide 2 by 1
    Given first number is "2"
    Given second number is "1"
    Given operation is "/"
    When I calculate the result
    Then I should be told "2"

  Scenario: Divide 1 by 2
    Given first number is "1"
    Given second number is "2"
    Given operation is "/"
    When I calculate the result
    Then I should be told "0.500"
