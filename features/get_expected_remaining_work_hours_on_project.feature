Feature: Get expected remaining work hours on project
  Description: The project queries for the total spend hours on a project
  Actor: Project leader

  Scenario: The project leader sees the total remaining work hours on a project
    Given there is a project
    And the project has an activity in it
    And the activity gets 25 hours allocated to it
    And an employee registers 22 hours spend on the activity
    And the project has an activity in it
    And an employee registers 5 hours spend on the activity
    And the activity gets 10 hours allocated to it
    And there is an employee logged in to the system
    And the employee using the system is the project leader of the project
    When the employee queries for the total remaining work hours on the project
    Then the total remaining work hours on the project matches the missing registered hours

  Scenario: Someone else than the project leader tries to see the total remaining work hours on an project
    Given there is a project
    And the project has an activity in it
    And the activity gets 25 hours allocated to it
    And an employee registers 22 hours spend on the activity
    And the project has an activity in it
    And the activity gets 10 hours allocated to it
    And an employee registers 5 hours spend on the activity
    And there is an employee logged in to the system
    And the employee using the system is not the project leader of the project
    When the employee queries for the total remaining work hours on the project
    Then the error message "Only the project leader is allow to perform that action" is given