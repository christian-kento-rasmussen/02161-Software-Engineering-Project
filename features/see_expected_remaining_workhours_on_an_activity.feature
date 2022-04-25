Feature: See expected remaining workhours on an activity
	Description: Employee or ProjectLeader sees the expected remaining workhours on activity
	Actors: Employee, ProjectLeader

Scenario: Employee wants to see the expected remaining workhours
	Given there is a project
	And there is an activity with activityName "save the world"
	And activity is not already in the project
	When the activity is added to the project
	Then the activity with activityName "save the world" is contained in project




	