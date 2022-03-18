Feature: delete activity for a project
	Description: Employee or ProjectLeader deletes an activity
	Actors: Employee, ProjectLeader

Scenario: delete activity for a project
	Given there is a project with name "TeamTrees"
	And the project has two employees attached with username "Mr Beast" and "PewDiePie" respectively
	And there is an activity with activityNum 0, activityName "save the world" contained in project "TeamTrees"
	And employee "Mr Beast" is attached to activity "save the world"
	When the activity is delete from project "TeamTrees"
	Then the activity with activityNum 0, activityName "save the world" is not contained in project "TeamTrees"
	And the activity is removed from employee "Mr Beast"


