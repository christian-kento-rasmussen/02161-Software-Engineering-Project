Feature: Assign an employee to an activity
  Description: The employee assigns an employee to an activity
  Actor: Employee

  Scenario: The employee assigns another employee to an activity
    Given a project is created
    And there is an activity with activityName "save the world"
    And there is an employee with username "BLIB"
    And there is an employee with username "BLOB"
    And the employee with username "BLIB" is signed in
    When the employee assigns the other employee with initials "BLOB" to the activity
    Then the other employee with initials "BLOB" is assigned to the activity

  Scenario: The employee assigns another employee to an activity, but the other employee is already assigned to the activity
    Given a project is created
    And there is an activity with activityName "save the world"
    And there is an employee with username "BLIB"
    And there is an employee with username "BLOB"
    And the employee with username "BLIB" is signed in
    And the other employee with initials "BLOB" is already assigned to the activity
    When the employee assigns the other employee with initials "BLOB" to the activity
    Then the error message "Employee is already assigned to the activity" is given

