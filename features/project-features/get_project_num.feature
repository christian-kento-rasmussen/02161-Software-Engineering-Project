Feature: Get project num
  Description: Employee queries for the project number of a project
  Actors: Employee

Scenario: Employee queries for the project num of a project
  Given there is a project
  When the user queries for the project num of the project
  Then the user gets the project num of the project which is yy0001 where yy is the last two digits of the current year

Scenario: Employee queries for the project num of a project when theres more than one project
  Given there is a project
  And there is a project
  When the user queries for the project num of the project
  Then the user gets the project num of the project which is yy0002 where yy is the last two digits of the current year