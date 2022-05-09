Feature: Delete a project
  Description: Employee deletes a project
  Actor: Employee

Scenario: Delete a project with no activities and no assigned employees to those activities
  Given a project is created
  When the project is deleted
  Then the project is no longer in the projectRepo


Scenario: Delete a project with activities and assigned employees to those activities
  Given a project is created
  And there is an employee with username "foo"
  And there is an employee with username "bar"
  And there is an activity named "activity1" in the project
  And there is an activity named "activity2" in the project
  And the employee with username "foo" is assigned to the activity named "activity1" in the project
  And the employee with username "bar" is assigned to the activity named "activity2" in the project
  When the project is deleted
  Then the project is no longer in the projectRepo
  And the employee with username "foo" is not assigned to a activity named "activity1"
  And the employee with username "bar" is not assigned to a activity named "activity2"


