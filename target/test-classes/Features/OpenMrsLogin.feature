@Login
Feature: Login feature for Oprnmrs
Scenario Outline: Redirect to dashboard after successful login
Given I am on the login page
When I enter username "<username>" and password "<password>"
Then I should be redirected to the dashboard

Examples:
      | username   | password   |
      | Admin      | Admin123   |
