Feature: Get remaining work hours on a project
  Description: The project leader queries for the expected hours on a project
  Actor: Project Leader

  Scenario: The project leader queries for the expected hours on a project
    Given there is a project
    And there is an employee with username "foo"
    And the employee with username "foo" is logged in
    And the employee with the username "foo" is the project leader of the given project
    And there is an activity named "activity1" in the project
    And the user sets the expected hours on the activity named "activity1" to 22 hours
    And there is an activity named "activity2" in the project
    And the user sets the expected hours on the activity named "activity2" to 6 hours
    When the user queries for the expected hours on the project
    Then the expected hours on the project is 28 hours


  Scenario: Someone else than the project leader queries for the expected hours on an project
    Given there is a project
    And there is an employee with username "foo"
    And the employee with username "foo" is logged in
    And there is an activity named "activity1" in the project
    And the user sets the expected hours on the activity named "activity1" to 22 hours
    And there is an activity named "activity2" in the project
    And the user sets the expected hours on the activity named "activity2" to 6 hours
    When the user queries for the expected hours on the project
    Then the error message "Only the project leader is allow to perform that action" is given