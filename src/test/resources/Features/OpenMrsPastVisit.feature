@PastVisit
Feature: Past Visit feature for Oprnmrs


Scenario: Add a past visit and verify the date picker
Given I am on the patient details page
When I click on the "Add Past Visit" menu
Then I should verify the future date is not clickable in the date picker
