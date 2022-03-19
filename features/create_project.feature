Feature: Create new project
	Description: Employee adds a new project
	Actors: Employee

Scenario: create new project
	When a project is created
	Then There exist a project with number "1"

Scenario: create two new project
	When a project is created
	And a project is created
	Then There exist a project with number "2"