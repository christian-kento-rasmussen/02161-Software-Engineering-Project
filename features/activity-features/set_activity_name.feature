Feature: Change the name of an activity
  Description: Employee changes the name of an activity
  Actor: Employee

Scenario: The employee changes the name of a project activity
  Given there is a project
  And there is an employee with username "foo"
  And the employee with username "foo" is logged in
  And there is an activity named "activity1" in the project
  When the user changes the name of the project activity named "activity1" to "thisIsAProjectActivity" 
  Then the project does not have an activity named "activity1"
  And the project has an activity named "thisIsAProjectActivity"

Scenario: The employee changes the name of a project activity, but the project already has an activity with that name
  Given there is a project
  And there is an employee with username "foo"
  And the employee with username "foo" is logged in
  And there is an activity named "activity1" in the project
  And there is an activity named "activity2" in the project
  When the user changes the name of the project activity named "activity1" to "activity2"
  Then the error message "The project already contains an activity with that name" is given

Scenario: The employee changes the name of a user activity
  Given there is an employee with username "foo"
  And the employee with username "foo" is logged in
  And the user creates a new user activity named "userActivity1"
  When the user changes the name of the user activity named "userActivity1" to "thisIsMyActivity"
  Then the user does not have a user activity with the name "userActivity1"
  And the user has a user activity named "thisIsMyActivity"
  
Scenario: The employee changes the name of a user activity, but the user already has an activity with that name
  Given there is an employee with username "foo"
  And the employee with username "foo" is logged in
  And the user creates a new user activity named "userActivity1"
  And the user creates a new user activity named "userActivity2"
  When the user changes the name of the user activity named "userActivity1" to "userActivity2"
  Then the error message "The current user already has an activity with that name" is given
    