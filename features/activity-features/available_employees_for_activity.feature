Feature: Find available employees for activity
	Description: The project leader finds all available employees for activity
	Actors: Project Leader

Scenario: The project leader queries for available employees for an activity
	Given there is a project and it is selected
	And there is an employee with username "foo"
	And there is an employee with username "bar"
	And the employee with username "foo" is logged in
	And the employee with the username "foo" is the project leader of the selected project
	And there is an activity named "activity1" in the project with start week 202210 and end week 202215
	And the employee with username "foo" is assigned to the activity named "activity1"
	And there is an activity named "activity2" in the project with start week 202216 and end week 202222
	And the employee with username "bar" is assigned to the activity named "activity2"
	And the activity named "activity1" is selected
	When the user queries for the available employees for the selected activity
	Then the available employee for the selected activity is the employee with the username "bar"

Scenario: The project leader queries for available employees for an activity, but finds none available
	Given there is a project and it is selected
	And there is an employee with username "foo"
	And there is an employee with username "bar"
	And the employee with username "foo" is logged in
	And the employee with the username "foo" is the project leader of the selected project
	And there is an activity named "activity1" in the project with start week 202210 and end week 202215
	And the employee with username "foo" is assigned to the activity named "activity1"
	And there is an activity named "activity2" in the project with start week 202212 and end week 202222
	And the employee with username "bar" is assigned to the activity named "activity2"
	And the activity named "activity1" is selected
	When the user queries for the available employees for the selected activity
	Then there is no available employees for the selected activity

Scenario: Someone else than the project leader queries for available employees for an activity
	Given there is a project and it is selected
	And there is an employee with username "foo"
#	And there is an employee with username "bar"
	And the employee with username "foo" is logged in
#	And the employee with the username "foo" is the project leader of the selected project
#	And there is an activity named "activity1" in the project with start week 202210 and end week 202215
#	And the employee with username "foo" is assigned to the activity named "activity1"
#	And there is an activity named "activity2" in the project with start week 202216 and end week 202222
#	And the employee with username "bar" is assigned to the activity named "activity2"
#	And the activity named "activity1" is selected
#	And the employee with username "bar" is logged in
	When the user queries for the available employees for the selected activity
	Then the error message "Only the project leader is allow to perform that action" is given