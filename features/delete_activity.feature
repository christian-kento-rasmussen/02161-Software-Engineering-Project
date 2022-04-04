Feature: Delete an activity
	Description: Employee or ProjectLeader deletes an activity
	Actors: Employee, ProjectLeader

Scenario: delete activity for a project with no employees attached to activity
	Given there is a project
	And there is an activity with activityName "save the world" contained in the project
	When the activity is deleted from project
	Then the activity with activityName "save the world" is not contained in the project

Scenario: delete activity for a project with employee attached to activity
	Given there is a project
	And there is an employee with username "BLIB"
	And there is an employee with username "BLOB"
	And there is an activity with activityName "save the world" contained in the project
	And the other employee with initials "BLOB" is already assigned to the activity
	When the activity is deleted from project
	Then the error message "employee(s) attached to activity. Please remove activity from employee" is given

