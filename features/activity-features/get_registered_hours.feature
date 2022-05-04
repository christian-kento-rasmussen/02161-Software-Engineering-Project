Feature: Get registered work hours on activity
  Description: The employee queries their registered work hours on an activity
  Actors: Employee

  Scenario: The employee queries their registered work hours on an activity
    Given there is a project and it is selected
    And there is an employee with username "foo"
    And the employee with username "foo" is logged in
    And there is an activity named "activity1" in the project
    And the activity named "activity1" is selected
    And the user registers 2.5 hours spent on the activity
    When the employee queries their registered work hours on an activity
    Then the result of the query is 2.5 work hours

  Scenario: The employee queries their registered work hours on an activity without having worked on the activity
    Given there is a project and it is selected
    And there is an employee with username "foo"
    And the employee with username "foo" is logged in
    And there is an activity named "activity1" in the project
    And the activity named "activity1" is selected
    When the employee queries their registered work hours on an activity
    Then the result of the query is 0 work hours