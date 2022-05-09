Feature: Unassign an employee from an activity
  Description: The employee unassigns an employee from an activity
  Actor: Employee

Scenario: The employee unassigns another employee from an activity
  Given there is a project
  And there is an activity named "activity1" in the project
  And there is an employee with username "foo"
  And the employee with username "foo" is logged in
  And the employee with username "foo" is assigned to the activity named "activity1" in the project
  When the employee with username "foo" is unassigned from the activity named "activity1" in the project
  Then the activity named "activity1" does not have an assigned employee named "foo"
  And the employee with username "foo" is not assigned to a activity named "activity1"



