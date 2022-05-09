Feature: Get users username
  Description: Employee gets users username
  Actors: Employee

Scenario: The employee is logged in and gets the users username
  Given there is an employee with username "foo"
  And the employee with username "foo" is logged in
  When the user queries for the users username
  Then the user gets the username "foo"

Scenario: No employee is logged in and the users username is queried for
  When the user queries for the users username
  Then the error message "No employee is logged in" is given