Feature: Set expected work hours of activity
  Description: The project leader sets expected work hours of activity
  Actors: User

Scenario: Set expected work hours of activity with no expected work hours on it
  Given a project exists
  And the project has an activity in it
  And the current user is the project leader of the project
  When the user sets the expected work hours of the activity to 22 hours
  Then the expected work hours on the activity is 22

Scenario: Set expected work hours of activity with existing expected work hours on it
  Given a project exists
  And the project has an activity in it
  And the the activity has expected work hours set to 1
  And the current user is the project leader of the project
  When the user sets the expected work hours of the activity to 22 hours
  Then the expected work hours on the activity is 22



Scenario: Set expected work hours of activity as someone else than project leader
  Given a project exists
  And the project has an activity in it
  And the current user is not the project leader of the project
  When the user sets the expected work hours of the activity to 22 hours
  Then the error message "Only the project leader of this project can set the amount of expected hours of an activity" is given