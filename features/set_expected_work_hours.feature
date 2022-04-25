Feature: Set expected work hours of activity
  Description: The project leader sets expected work hours of activity
  Actors: User

Scenario: Set expected work hours of activity with no expected work hours on it
  Given there is a project
  And the project has an activity in it
  And there is an employee logged in to the system
  And the employee using the system is the project leader of the project
  When the user sets the expected work hours of the activity to 22 hours
  Then the expected work hours on the activity is 22


Scenario: Set expected work hours of activity with existing expected work hours on it
  Given there is a project
  And the project has an activity in it
  And the the activity has expected work hours set to 1
  And there is an employee logged in to the system
  And the employee using the system is the project leader of the project
  When the user sets the expected work hours of the activity to 22 hours
  Then the expected work hours on the activity is 22
#
#
#
Scenario: Set expected work hours of activity as someone else than project leader
  Given there is a project
  And the project has an activity in it
  And there is an employee logged in to the system
  And the employee using the system is not the project leader of the project
#  And the current user is not the project leader of the project
  When the user sets the expected work hours of the activity to 22 hours
  Then the error message "Only the project leader is allow to perform that action" is given