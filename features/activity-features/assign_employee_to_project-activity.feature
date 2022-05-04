Feature: Assign an employee to an activity
  Description: The employee assigns an employee to an activity
  Actor: Employee

  Scenario: The employee assigns another employee to an activity
    Given there is a project and it is selected
    And there is an activity named "activity1" in the project
    And the activity named "activity1" is selected
    And there is an employee with username "foo"
    When the employee assigns the other employee with username "foo" to the selected activity
    Then the other employee with username "foo" is assigned to the activity named "activity1" and vice versa

  Scenario: The employee assigns another employee to an activity, but the other employee is already assigned to the activity
    Given there is a project and it is selected
    And there is an activity named "activity1" in the project
    And the activity named "activity1" is selected
    And there is an employee with username "foo"
    And the other employee with username "foo" is already assigned to the activity
    When the employee assigns the other employee with username "foo" to the selected activity
    Then the error message "Employee is already assigned to the activity" is given

