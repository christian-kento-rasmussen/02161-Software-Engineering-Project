Feature: Remove employee
  Description: Employee removes an employee
  Actor: Employee

Scenario: Employee removes an employee from the system
  Given there is a project
  And there is an employee with username "foo"
  And the employee with the username "foo" is assigned as the project leader of the given project
  And there is an activity named "activity1" in the project
  And the employee with username "foo" is assigned to the activity named "activity1" in the project
  When the employee removes the employee with the username "foo"
  Then the employeeRepo does not contain an employee with the username "foo"
  And the project leader is null
  And the activity named "activity1" does not have an assigned employee named "foo"

Scenario: Employee removes an employee that is not in the system
  When the employee removes and employee that is not in the system
  Then the error message "Should not remove employee that is not in the system" is given
