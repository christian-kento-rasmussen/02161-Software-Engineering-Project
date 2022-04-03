Feature: Delete an activity
	Description: Employee or ProjectLeader deletes an activity
	Actors: Employee, ProjectLeader

Scenario: delete activity for a project with no employees attached to activity
	Given there is a project
	And there is an activity with activityName "save the world" contained in the project
	When the activity is deleted from project
	Then the activity with activityName "save the world" is not contained in the project

#Scenario: delete activity for a project with employee attached to activity
#	Given there is a project
#	And the project has two employees attached with username "MrBe" and "PewD" respectively
#	And there is an activity with activityName "save the world" contained in project
#	And employee "MrBe" is attached to activity "save the world"
#	When the activity is delete from project
#	Then the error message "employee(s) attached to activity. Please remove activity from employee"

