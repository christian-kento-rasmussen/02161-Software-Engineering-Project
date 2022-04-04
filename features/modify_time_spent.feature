Feature: Modify time spent on an activity
  Description: The employee modifies their time spent on an activity
  Actors: User

#  Scenario: Employee modifies time spent on an activity
#    Given there is an activity with name "Brainstorming"
#    And the active employee has spent 1.5 hours on the activity
#    When the active employee modifies their time spent on the activity to 1 hours
#    Then the active employees time spent on the activity is 1 hours

#  Scenario: Employee modifies time spent on an activity, but time is not divisible by 0.5 hours
#    Given there is an activity with name "Brainstorming"
#    And the active employee has spent 1.5 hours on the activity
#    When the active employee modifies their time spent on the activity to 1.25 hours
#    Then the error message "Time must be given in half hours" is given
#    And the active employees time spent on the activity is 1.5 hours

#    Scenario: Employee modifies time spent on an activity, but time is negative
#    Given there is an activity with name "Brainstorming"
#    And the active employee has spent 1.5 hours on the activity
#    When the active employee modifies their time spent on the activity to -1.25 hours
#    Then the error message "Time must be positive or 0" is given
#    And the active employees time spent on the activity is 1.5 hours
