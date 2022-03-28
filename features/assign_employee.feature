Feature: Assign an employee to an activity
  Description: The employee assigns an employee to an activity
  Actor: Employee

  Scenario: The employee assigns another employee to an activity
    Given there is an activity with name "Brainstorming"
    And the signed in employee has the initials "blib"
    And there is another employee with initials "blob"
    When the employee assigns the other employee with initials "blob" to the activity
    Then the other employee with initials "blob" is assigned to the activity

  Scenario: The employee assigns another employee to an activity, but the other employee is already assigned to the activity
    Given there is an activity with name "Brainstorming"
    And the signed in employee has the initials "blib"
    And there is another employee with initials "blob"
    And the other employee with initials "blob" is assigned to the activity
    When the employee assigns the other employee with initials "blob" to the activity
    Then the error message "Employee is already assigned to the activity" is given

