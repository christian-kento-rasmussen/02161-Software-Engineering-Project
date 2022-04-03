Feature: Create new activity
	Description: Employee or ProjectLeader adds an activity
	Actors: Employee, ProjectLeader

Scenario: add activity to a project
	Given there is a project
	And there is an activity with activityName "save the world"
	And activity is not already in the project
	When the activity is added to the project
	Then the activity with activityName "save the world" is contained in project




	