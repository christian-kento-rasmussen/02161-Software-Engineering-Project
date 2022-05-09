Feature: Get activity type of activity
  Description: The employee gets the activity type of an activity
  Actor: Employee

Scenario: The employee gets the type of a project activity
  Given there is a project
  And there is an activity named "activity1" in the project
  When the user queries for the type of the project activity named "activity1"
  Then the type of the activity is project activity

Scenario: The employee gets the type of a user activity
  Given there is an employee with username "foo"
  And the employee with username "foo" is logged in
  And the user creates a new user activity named "userActivity1"
  When the user queries for the type of the user activity named "userActivity1"
  Then the type of the activity is user activity