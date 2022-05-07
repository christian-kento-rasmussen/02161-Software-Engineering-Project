Feature: Register work hours on an activity
  Description: The employee registers their work hours on an activity
  Actors: Employee

  Scenario: The employee registers work hours on an activity
    Given there is a project
    And there is an employee with username "foo"
    And the employee with username "foo" is logged in
    And there is an activity named "activity1" in the project
    When the user registers 2.5 hours spent on the activity named "activity1" in the project
    Then the users registered hours on the activity is 2.5 hours

  Scenario: The employee modifies the registered work hours to a lower amount of hours
    Given there is a project
    And there is an employee with username "foo"
    And the employee with username "foo" is logged in
    And there is an activity named "activity1" in the project
    And the user registers 25 hours spent on the activity named "activity1" in the project
    When the user registers 2.5 hours spent on the activity named "activity1" in the project
    Then the users registered hours on the activity is 2.5 hours

  Scenario: The employee registers work hours on an activity, but hours is negative
    Given there is a project
    And there is an employee with username "foo"
    And the employee with username "foo" is logged in
    And there is an activity named "activity1" in the project
    And the user registers 0.5 hours spent on the activity named "activity1" in the project
    When the user registers -2.5 hours spent on the activity named "activity1" in the project
    Then the error message "Time must be positive or 0" is given
    And the users registered hours on the activity is 0.5 hours

  Scenario: The employee registers work hours on an activity, but time is not divisible by 0.5 hours
    Given there is a project
    And there is an employee with username "foo"
    And the employee with username "foo" is logged in
    And there is an activity named "activity1" in the project
    And the user registers 0.5 hours spent on the activity named "activity1" in the project
    When the user registers 2.25 hours spent on the activity named "activity1" in the project
    Then the error message "Time must be given in half hours" is given
    And the users registered hours on the activity is 0.5 hours