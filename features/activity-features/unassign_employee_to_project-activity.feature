Feature: Unassign an employee from an activity
  Description: The employee unassigns an employee from an activity
  Actor: Employee

  Scenario: The employee unassigns another employee to an activity
    Given there is a project
    And there is an activity named "activity1" in the project
    And there is an employee with username "foo"
    And the employee with username "foo" is logged in
    And the employee with username "foo" is assigned to the activity named "activity1" in the project
    When the employee with username "foo" is unassigned from the activity named "activity1" in the project
    Then the employee with username "foo" is unassigned from the activity named "activity1" and vice versa




  Scenario: The employee unassigns another employee to an activity, but the other employee is already unassigned from the activity
    Given there is a project
    And there is an activity named "activity1" in the project
    And there is an employee with username "foo"
    And the employee with username "foo" is logged in
    When the employee with username "foo" is unassigned from the activity named "activity1" in the project
    Then the error message "Employee is already unassigned from this activity" is given



