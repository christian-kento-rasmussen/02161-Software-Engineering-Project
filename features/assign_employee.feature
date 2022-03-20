Feature: Assign an employee to an activity
  Description: The user assigns an employee to an activity
  Actor: User

  Scenario: The user assigns an employee to an activity
    Given there is an activity with name "Brainstorming"
    And there is an employee with initials "blob"
    When the user assigns the employee with initials "blob" to the activity
    Then the employee with initials "blob" is assigned to the activity

