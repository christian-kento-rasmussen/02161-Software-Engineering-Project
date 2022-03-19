Feature: Register time spent on an activity
  Description: The employee registers the time spent on an activity
  Actors: User

  Scenario: Register time spent on an activity
    Given there is an activity with name "Brainstorming"
    And the active employee has spent 0.5 hours on the activity
    When the active employee registers 1.5 hours spent on the activity
    Then the active employees time spent on the activity is 2 hours

  Scenario: Employee registers time spent on an activity, but time is not divisible by 0.5 hours
    Given there is an activity with name "Brainstorming"
    And the active employee has spent 0.5 hours on the activity
    When the active employee registers 1.25 hours spent on the activity
    Then the error message "Time must be given in half hours" is given
    And the active employees time spent on the activity is 0.5 hours


