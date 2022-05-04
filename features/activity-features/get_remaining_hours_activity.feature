Feature: Get remaining work hours on an activity
	Description: The project leader queries for the remaining work hours on an activity
	Actors: Project Leader

Scenario: The project leader queries for the remaining work hours on an activity
	Given there is a project and it is selected
	And there is an employee with username "foo"
	And the employee with username "foo" is logged in
	And the employee with the username "foo" is the project leader of the selected project
	And there is an activity named "activity1" in the project
	And the activity named "activity1" is selected
	And the user registers 10 hours spent on the activity
	And the user sets the expected hours on the activity to 22 hours
	When the user queries for the remaining hours on the activity
	Then the remaining work hours on the activity is 12 hours

Scenario: Someone else than the project leader queries for the remaining work hours on an activity
	Given there is a project and it is selected
	And an activity named "activity1" is added to the project
	And the activity named "activity1" is selected
	And there is an employee with username "foo"
	And there is an employee with username "bar"
	And the employee with username "foo" is logged in
	And the employee with the username "foo" is the project leader of the selected project
	And the employee with username "bar" is logged in
	When the user queries for the remaining hours on the activity
	Then the error message "Only the project leader is allow to perform that action" is given




	