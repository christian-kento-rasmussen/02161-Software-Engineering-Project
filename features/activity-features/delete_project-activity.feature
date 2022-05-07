Feature: Delete a project activity
	Description: Employee deletes a project activity
	Actors: Employee

Scenario: Delete project activity with no employees attached to the activity
	Given there is a project
	And there is an activity named "activity1" in the project
	When the activity named "activity1" is deleted from the project
	Then the project does not have an activity named "activity1"

Scenario: Delete project activity with an employee attached to the activity
	Given there is a project
	And there is an employee with username "foo"
	And there is an activity named "activity1" in the project
	And the employee with username "foo" is assigned to the activity named "activity1" in the project
	When the activity named "activity1" is deleted from the project
	Then the project does not have an activity named "activity1" and the employee with username "foo" is not assigned to the activity

