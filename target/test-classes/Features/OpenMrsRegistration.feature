@Registration
Feature: Registration feature for Oprnmrs
Scenario: Register a new patient
Given I am on the dashboard
When I click on the "Register a patient" menu
And I enter patient details
Then I should confirm patient information
And I should be redirected to patient details page
And The system should calculate the age correctly