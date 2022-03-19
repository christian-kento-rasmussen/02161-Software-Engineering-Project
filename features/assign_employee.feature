Feature: Assign an employee to an activity
  Description: The user assigns an employee to an activity
  Actor: User

  Scenario: The user assigns an employee to an activity
    Given there is an activity with name "Brainstorming"
    And there is an employee with initials "blob"
    When the user assigns the employee with initials "blob" to the activity
    Then the employee with initials "blob" is assigned to the activity

  Scenario: The user assigns an employee to an activity, but the employee is already assigned to the activity
    Given there is an activity with name "Brainstorming"
    And there is an employee with initials "blob"
    And the employee with initials "blob" is assigned to the activity
    When the user assigns the employee with initials "blob" to the activity
    Then the error message "Employee is already assigned to the activity" is given

