Feature: Create new project activity
	Description: Employee adds an activity to a project
	Actors: Employee

Scenario: An employee adds an activity to a project
	Given there is a project
	And the project does not have an activity named "activity1"
	When an activity named "activity1" is added to the project
	Then the project has an activity named "activity1"




	