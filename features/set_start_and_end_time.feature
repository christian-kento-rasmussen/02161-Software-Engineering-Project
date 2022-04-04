Feature: Set start and end time of activity
  Description: The project leader sets start and end time of activity
  Actor: User

#Scenario: Set start and end time of activity
#  Given a project exists
#  And the project has an activity in it
#  And the current user is the project leader of the project
#  When the user sets the start and end time of the activity to 2022-07 and 2023-05, respectively
#  Then the start and end time of the activity is 2022-07 and 2023-05, respectively
#
#
#
#Scenario: Set start and end time of activity as someone else than project leader
#  Given a project exists
#  And the project has an activity in it
#  And the current user is not the project leader of the project
#  When the user sets the start and end time of the activity to 2022-07 and 2023-05, respectively
#  Then the error message "Only the project leader of this project can set start and end time of an activity" is given
#
#Scenario: Set start and end time of activity where end time is before start time
#  Given a project exists
#  And the project has an activity in it
#  And the current user is not the project leader of the project
#  When the user sets the start and end time of the activity to 2025-07 and 2023-05, respectively
#  Then the error message "The start time cannot be after the end time" is given