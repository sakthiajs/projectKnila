@Attachment
Feature: File Attatchment feature for Oprnmrs

Scenario: Attach a file to patient record
Given Start visit and confirm visit
When I click on the "Attachment" menu 
And I upload a file
Then I should see a success message for the attachment