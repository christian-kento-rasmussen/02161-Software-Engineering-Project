Feature: Add activity to a project
	Description: Employee or ProjectLeader adds an activity
	Actors: Employee, ProjectLeader

Scenario: add activity to a project
	Given there is a project with name "TeamTrees"
	And the project has two employees attached
	And there is an activity with activityNum 0, activityName "save the world"
	And activity is not already in the project
	When the activity is added to the project "TeamTrees"
	Then the activity with activityNum 0, activityName "save the world" is contained in project "TeamTrees"


