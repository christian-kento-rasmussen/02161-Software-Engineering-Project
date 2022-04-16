Feature: Assign a project leader to a project
    Description: An employee assigns a given employee as project leader for a given project
    Actor: Employee

Scenario: Assign a project leader to a project
  Given there is a project
  And there is a given employee in the system
  When the employee assigns the given employee to be project leader of the given project
  Then the given employee is the project leader of the given project
