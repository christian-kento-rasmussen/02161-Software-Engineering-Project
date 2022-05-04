Feature: Set expected work hours of activity
  Description: The project leader sets expected work hours of activity
  Actors: Project Leader

Scenario: The project leader sets the expected work hours of an activity with no expected work hours on it
  Given there is a project and it is selected
  And there is an employee with username "foo"
  And the employee with username "foo" is logged in
  And the employee with the username "foo" is the project leader of the selected project
  And there is an activity named "activity1" in the project
  And the activity named "activity1" is selected
  When the user sets the expected hours on the activity to 22 hours
  Then the expected hours on the activity is 22 hours

# TODO: add conditions for the expected work hours

Scenario: The project leader sets the expected work hours of an activity with expected work hours on it
  Given there is a project and it is selected
  And there is an employee with username "foo"
  And the employee with username "foo" is logged in
  And the employee with the username "foo" is the project leader of the selected project
  And there is an activity named "activity1" in the project
  And the activity named "activity1" is selected
  And the user sets the expected hours on the activity to 220 hours
  When the user sets the expected hours on the activity to 22 hours
  Then the expected hours on the activity is 22 hours

Scenario: Someone else than the project leader sets the expected work hours of activity
  Given there is a project and it is selected
  And there is an activity named "activity1" in the project
  And the activity named "activity1" is selected
  And there is an employee with username "foo"
  And the employee with username "foo" is logged in
  When the user sets the expected hours on the activity to 22 hours
  Then the error message "Only the project leader is allow to perform that action" is given