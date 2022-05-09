Feature: Create user activity
  Description: Employee creates user activity that is not bound to a project
  Actors: Employee

Scenario: The employee adds a new user activity
  Given there is an employee with username "foo"
  And the employee with username "foo" is logged in
  When the user creates a new user activity named "userActivity1"
  Then the user has a user activity named "userActivity1"


Scenario: The employee adds a new user/personal activity with the same name as an existing user/personal activity
  Given there is an employee with username "foo"
  And the employee with username "foo" is logged in
  And the user creates a new user activity named "userActivity1"
  When the user creates a new user activity named "userActivity1"
  Then the error message "An activity with that name already exists." is given