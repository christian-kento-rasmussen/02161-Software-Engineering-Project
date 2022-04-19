Feature: Set start and end time of activity
  Description: The project leader sets start and end time of activity
  Actor: Project leader

Scenario: Set start and end time of activity
  Given there is a project
  And the project has an activity in it
  And there is an employee logged in to the system
  And the employee using the system is the project leader of the project
  When the current employee using the system sets the start and end time of the activity to 202207 and 202305, respectively
  Then the start and end time of the activity is 202207 and 202305, respectively



Scenario: Set start and end time of activity as someone else than project leader
  Given there is a project
  And the project has an activity in it
  And there is an employee logged in to the system
  And the employee using the system is not the project leader of the project
  When the current employee using the system sets the start and end time of the activity to 202207 and 202305, respectively
  Then the error message "Only the project leader is allow to perform that action" is given

Scenario: Set start and end time of activity where end time is before start time
  Given there is a project
  And the project has an activity in it
  And there is an employee logged in to the system
  And the employee using the system is the project leader of the project
  When the current employee using the system sets the start and end time of the activity to 202305 and 202207, respectively
  Then the error message "The start week cannot be after the end week" is given