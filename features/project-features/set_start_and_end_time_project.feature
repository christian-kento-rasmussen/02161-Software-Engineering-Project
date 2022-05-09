Feature: Set start and end time of a project
  Description: The project leader sets the start time of a project
  Actors: Project Leader

  Scenario: The project leader sets start and end time of a project
    Given there is a project
    And there is an employee with username "foo"
    And the employee with username "foo" is logged in
    And the employee with the username "foo" is the project leader of the given project
    When the user sets the start and end time of the project to 202207 and 202305, respectively
    Then the start and end time of the project is 202207 and 202305, respectively


  Scenario: The project leader sets start and end time of a project where end time is before start time
    Given there is a project
    And there is an employee with username "foo"
    And the employee with username "foo" is logged in
    And the employee with the username "foo" is the project leader of the given project
    When the user sets the start and end time of the project to 202307 and 202207, respectively
    Then the error message "The start week cannot be after the end week" is given

  Scenario: Someone else than the project leader sets start and end time of a project
    Given there is a project
    And there is an employee with username "foo"
    And the employee with username "foo" is logged in
    When the user sets the start and end time of the project to 202207 and 202305, respectively
    Then the error message "Only the project leader is allow to perform that action" is given