Feature: Create new project
	Description: Employee adds a new project
	Actors: Employee

Scenario: create new project
	Given a project is created
	Then There exist a project with the project number in format yy-0001 where yy is the last two digits of the current year

Scenario: create two new project
	Given two projects are created
	Then There exist a project with the project number in format yy-0002 where yy is the last two digits of the current year

