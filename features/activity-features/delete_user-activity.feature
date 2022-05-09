Feature: Delete a user activity
    Description: Employee deletes an activity
    Actors: Employee

Scenario: Delete a user activity
    Given there is an employee with username "foo"
    And the employee with username "foo" is logged in
    And the employee has an activity named "userActivity"
    When the employee activity named "userActivity" is deleted
    Then the employee does not have an activity named "activity1"
