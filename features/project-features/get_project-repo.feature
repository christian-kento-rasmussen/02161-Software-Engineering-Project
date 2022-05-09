Feature: Get the project repo
  Description: Employee gets the project repo
  Actor: Employee

Scenario: There is no projects and the size of the project repo is 0
  Given there is 0 projects in the management application
  Then the size of the project repo is 0


Scenario: There is an amount of projects and the size of the project repo corresponds to that amount
  Given there is 22 projects in the management application
  Then the size of the project repo is 22