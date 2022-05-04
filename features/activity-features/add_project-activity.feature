Feature: Create new activity
	Description: Employee or ProjectLeader adds an activity
	Actors: Employee, ProjectLeader

Scenario: add activity to a project
	Given there is a project and it is selected
	And the project does not have an activity named "activity1"
	When an activity named "activity1" is added to the project
	Then the project has an activity named "activity1"




	