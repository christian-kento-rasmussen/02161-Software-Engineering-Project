Feature: See expected remaining workhours on an activity
	Description: Employee or ProjectLeader sees the expected remaining workhours on activity
	Actors: Employee, ProjectLeader

Scenario: ProjectLeader wants to see the expected remaining workhours
	Given there is a project
	And the project has an activity in it
	And there is an employee logged in to the system
	And the employee using the system is the project leader of the project
	When the user sets the expected work hours of the activity to 22 hours
	And an employee registers 10 hours spend on the activity
	Then the remaining work remaining hours on the activity is 12 hours

Scenario: Employee wants to see the expected remaining workhours
	Given there is a project
	And the project has an activity in it
	And the the activity has expected work hours set to 22
	And there is an employee logged in to the system
	And the employee using the system is not the project leader of the project
	When get the remaining workhours on the activity
	Then the error message "Only the project leader is allow to perform that action" is given




	