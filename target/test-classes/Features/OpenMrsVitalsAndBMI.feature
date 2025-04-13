@VitalsBMI
Feature: Vitals and BMI calculation feature for Oprnmrs

Scenario: Capture vitals and BMI calculation
Given Start Visit Again
When Click on "Capture Vitals" menu
And I enter vitals data
Then I should verify the BMI calculation and save the form
Then Click End Visit and redirect to patient details page
And Verify height and weight and BMI