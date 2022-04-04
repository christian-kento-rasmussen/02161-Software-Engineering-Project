Feature: Register work hours on an activity
  Description: The employee registers their work hours on an activity
  Actors: Employee

  Scenario: The employee registers work hours on an activity
    Given a project is created
    And there is an activity with activityName "save the world"
    And there is an employee with username "BLIB"
    And the employee with username "BLIB" is signed in
    And the employee has spent 0.5 hours on the activity
    When the employee registers 1.5 hours spent on the activity
    Then the employees work hours on the activity is 2 hours

  Scenario: The employee registers work hours on an activity, but hours is negative
    Given a project is created
    And there is an activity with activityName "save the world"
    And there is an employee with username "BLIB"
    And the employee with username "BLIB" is signed in
    And the employee has spent 0.5 hours on the activity
    When the employee registers -1.25 hours spent on the activity
    Then the error message "Time must be positive or 0" is given
    And the employees work hours on the activity is 0.5 hours

  Scenario: The employee registers work hours on an activity, but time is not divisible by 0.5 hours
    Given a project is created
    And there is an activity with activityName "save the world"
    And there is an employee with username "BLIB"
    And the employee with username "BLIB" is signed in
    And the employee has spent 0.5 hours on the activity
    When the employee registers 1.25 hours spent on the activity
    Then the error message "Time must be given in half hours" is given
    And the employees work hours on the activity is 0.5 hours