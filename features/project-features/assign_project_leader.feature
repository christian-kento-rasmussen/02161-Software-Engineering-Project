Feature: Assign a project leader to a project
    Description: An employee assigns a given employee as project leader for a given project
    Actor: Employee

Scenario: Assign a project leader to a project
  Given there is a project and it is selected
  And there is an employee with username "foo"
  When the employee assigns the employee with the username "foo" to be project leader of the selected project
  Then the employee with the username "foo" is the project leader of the selected project
