Feature: See total spend hours on an activity
  Description: The project queries for the total spend hours on an activity
  Actor: Project Leader

Scenario: The project leader queries for the total spend hours on an activity
  Given there is a project
  And there is an employee with username "foo"
  And the employee with username "foo" is logged in
  And the employee with the username "foo" is the project leader of the given project
  And there is an activity named "activity1" in the project
  And the user registers 10 hours spent on the activity named "activity1" in the project
  When the user queries for the spend hours on the activity named "activity1" in the project
  Then the spend hours on the activity is 10 hours


Scenario: Someone else than the project leader queries for the total spend hours on an activity
  Given there is a project
  And there is an activity named "activity1" in the project
  And there is an employee with username "foo"
  And the employee with username "foo" is logged in
  When the user queries for the spend hours on the activity named "activity1" in the project
  Then the error message "Only the project leader is allow to perform that action" is given

