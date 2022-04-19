Feature: Set starting and end time of project
  Description: The Project Leader sets the start time of a project
  Actors: Project Leader

  Scenario: The Project Leader sets start and end time
    Given there is a project
    And there is a given employee in the system
    And the employee assigns the given employee to be project leader of the given project
    When the given employee sets the start time of the project to 5 weeks from now.
    And the given employee sets the end time of the project to 5 weeks from now.
    Then the start time of the project is 5 weeks from now.
    And the end time of the project is 5 weeks from now.

  Scenario: The Project Leader sets start time after end time
    Given there is a project
    And there is a given employee in the system
    And the employee assigns the given employee to be project leader of the given project
    And the given employee sets the start time of the project to 5 weeks from now.
    When the given employee sets the end time of the project to 4 weeks from now.
    Then the error message "The start week cannot be after the end week" is given