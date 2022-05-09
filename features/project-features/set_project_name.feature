Feature: Change the name of a project
  Description: Employee changes the name of a project
  Actors: Employee

Scenario: The employee changes the name of a project
  Given there is a project
  And the name of the project is "Unnamed project"
  When the user sets the name of the project to be "project1"
  Then the name of the project is "project1"

Scenario: The employees changes the name of a project such that two projects have the same name (projectNum is the defining factor of projects, so the same name is allowed)
  Given there is a project
  And the user sets the name of the project to be "project1"
  And there is a project
  When the user sets the name of the project to be "project1"
  Then the name of the project is "project1"