Feature: Get the employee repo
  Description: Employee gets the employee repo
  Actor: Employee

Scenario: There is no projects and the size of the employee repo is 0
  Given there is zero employee in the management application
  Then the size of the employee repo is 0


Scenario: There is an amount of employees and the size of the employee repo corresponds to that amount
  Given there is five employee in the management application
  Then the size of the employee repo is 5