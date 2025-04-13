@Merge
Feature: Merge Visit feature for Oprnmrs

Scenario: Merge two visits for the same patient
Given Recent visit has two entries
When I select two visits to merge
Then I should see the visits merged