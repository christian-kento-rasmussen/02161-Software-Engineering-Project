Feature: Set start and end time of an activity
  Description: The project leader sets start and end time of an activity
  Actor: Project Leader

Scenario: The project leader sets start and end time of an activity
  Given there is a project and it is selected
  And there is an employee with username "foo"
  And the employee with username "foo" is logged in
  And the employee with the username "foo" is the project leader of the selected project
  And there is an activity named "activity1" in the project
  And the activity named "activity1" is selected
  When the user sets the start and end time of the activity to 202207 and 202305, respectively
  Then the start and end time of the activity is 202207 and 202305, respectively

Scenario: The project leader sets start and end time of an activity where end time is before start time
  Given there is a project and it is selected
  And there is an employee with username "foo"
  And the employee with username "foo" is logged in
  And the employee with the username "foo" is the project leader of the selected project
  And there is an activity named "activity1" in the project
  And the activity named "activity1" is selected
  When the user sets the start and end time of the activity to 202307 and 202207, respectively
  Then the error message "The start week cannot be after the end week" is given

Scenario: Someone else than the project leader sets start and end time of an activity
  Given there is a project and it is selected
  And there is an activity named "activity1" in the project
  And the activity named "activity1" is selected
  And there is an employee with username "foo"
  And the employee with username "foo" is logged in
  When the user sets the start and end time of the activity to 202207 and 202305, respectively
  Then the error message "Only the project leader is allow to perform that action" is given
