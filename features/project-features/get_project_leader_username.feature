Feature: Get project leader username
  Description: Employee queries for the username of a project leader
  Actor: Employee

Scenario: Employee queries for the username of a project leader
  Given there is a project
  And there is an employee with username "foo"
  And the employee assigns the employee with the username "foo" to be project leader of the given project
  When the user queries for the username of the project leader
  Then the username of the project leader is "foo"

Scenario: Employee queries for the username of a project leader with no project leader 
  Given there is a project
  When the user queries for the username of the project leader
  Then the error message "No project leader selected." is given