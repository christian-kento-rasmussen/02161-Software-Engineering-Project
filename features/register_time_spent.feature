Feature: Register time spent on an activity
  Description: The user registers the time spent on an activity
  Actors: User

  Scenario: Register time spent on an activity
    Given there is an activity with name "Brainstorming"
    And the user has spent 0.5 hours on the activity
    When the user registers 1.5 hours spent on the activity
    Then the user time spent on the activity is 2 hours

  Scenario: The user registers time spent on an activity, but time is negative
    Given there is an activity with name "Brainstorming"
    And the user has spent 0.5 hours on the activity
    When the user registers -1.25 hours spent on the activity
    Then the error message "Time must be positive or 0" is given
    And the users time spent on the activity is 0.5 hours


