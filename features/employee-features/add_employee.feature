Feature: Add new employee
  Description: Employee adds a new employee
  Actor: Employee

Scenario: Employee adds a new employee with the username a correct username
  Given the employeeRepo does not contain an employee with the username "foo"
  When the employee adds a new employee with the username "foo"
  Then the employeeRepo contains and employee with the username "foo"

Scenario: Employee adds a new employee with no username provided
  When the employee adds a new employee with the username ""
  Then the error message "The username needs to be between one and four letters long." is given

Scenario: Employee adds a new employee with too long a username
  When the employee adds a new employee with the username "fooooooo"
  Then the error message "The username needs to be between one and four letters long." is given

Scenario: Employee adds a new employee with other characters than alphabetical characters
  When the employee adds a new employee with the username "b4r"
  Then the error message "The username cannot contain non-alphabetical characters (a-zA-Z)." is given

Scenario: Employee adds a new employee with a username that is already in use
  Given there is an employee with username "foo"
  When the employee adds a new employee with the username "foo"
  Then the error message "An employee with that username already exists." is given