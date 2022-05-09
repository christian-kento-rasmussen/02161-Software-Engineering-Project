Feature: Get the name of an activity
  Description: Employee queries for the name of an activity
  Actors: Employee

  Scenario: The employee queries for the name of a project activity
    Given there is a project
    And there is an employee with username "foo"
    And the employee with username "foo" is logged in
    And there is an activity named "activity1" in the project
    When the user queries for the name of the project activity named "activity1"
    Then the user gets the activity name "activity1"


  Scenario: The employee queries for the name of a user activity
    Given there is an employee with username "foo"
    And the employee with username "foo" is logged in
    And the user creates a new user activity named "userActivity1"
    When the user queries for the name of the user activity named "userActivity1"
    Then the user gets the activity name "userActivity1"