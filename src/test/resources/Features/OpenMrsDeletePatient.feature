@LastScenario
Feature: Deleting a patient feature for Oprnmrs

Scenario: Delete a patient and verify deletion
Given I am on the patient details page 
When I click on "Delete Patient"
Then I should verify the patient is deleted and not visible in the search results 