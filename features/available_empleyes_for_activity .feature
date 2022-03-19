Feature: Find available employees for activity
	Description: Find all available employees for activity
	Actors: ProjectLeader

Scenario: Find available employees for activity
	Given there is a project
	And the project has two employees attached with username "Mr Beast" and "PewDiePie" respectively
	And there is an activity with activityName "save the world", start week 10, and end week 15 contained in project "TeamTrees"
	And employee "Mr Beast" is attached to activity "save the world"
	And there is an activity with activityName "From the algorithm", start week 16, and end week 20 contained in project "TeamTrees"
	Then available employees for activityName "From the algorithm" is PewDiePie

Scenario: No available employees for activity
	Given there is a project
	And the project has two employees attached with username "Mr Beast" and "PewDiePie" respectively
	And there is an activity with activityName "save the world", start week 10, and end week 15 contained in project "TeamTrees"
	And employee "Mr Beast" is attached to activity "save the world"
	And there is an activity with activityName "From the algorithm", start week 12, and end week 20 contained in project "TeamTrees"
	Then there is no available employees for activityName "From the algorithm"