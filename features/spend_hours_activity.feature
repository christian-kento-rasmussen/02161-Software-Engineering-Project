Feature: See total spend hours on an activity
  Description: The project queries for the total spend hours on an activity
  Actor: Project leader

Scenario: The project leader sees the total spend hours on an activity
  Given there is a project
  And the project has an activity in it
  And an employee registers 22 hours spend on the activity
  And there is an employee logged in to the system
  And the employee using the system is the project leader of the project
  When the employee queries for the total spend hours on the activity
  Then the total spend hours on the activity matches the registered hours


Scenario: Someone else than the project leader tries to see the total spend hours on an activity
  Given there is a project
  And the project has an activity in it
  And an employee registers 22 hours spend on the activity
  And there is an employee logged in to the system
  And the employee using the system is not the project leader of the project
  When the employee queries for the total spend hours on the activity
  Then the error message "Only the project leader is allow to perform that action" is given

