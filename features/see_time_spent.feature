Feature: See work hours on an activity
  Description: The employee queries their registered work hours on an activity
  Actors: Employee

  Scenario: The employee queries their registered work hours on an activity
    Given a project is created
    And there is an activity with activityName "save the world"
    And there is an employee with username "BLIB"
    And the employee with username "BLIB" is signed in
    And the employee has spent 1.5 hours on the activity
    And the employee registers 0.5 hours spent on the activity
    When the employee queries their registered work hours on an activity
    Then the result of the query is 2 work hours

  Scenario: The employee queries their registered work hours on an activity without having worked on the activity
    Given a project is created
    And there is an activity with activityName "save the world"
    And there is an employee with username "BLIB"
    And the employee with username "BLIB" is signed in
    When the employee queries their registered work hours on an activity
    Then the result of the query is 0 work hours