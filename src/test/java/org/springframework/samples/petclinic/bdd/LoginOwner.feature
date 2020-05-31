Feature: Traditional Login 
   I can do login in the system with my user name and password.
 
  Scenario: Successful login as owner1 (Positive)
    Given I am not logged in the system
   When I do login as user "owner1"
    Then "owner1" appears as the current user
    
  Scenario: Login fail (Negative)
  	Given I am not logged in the system
    When I try to do login as user "owner1" with an invalid password
    Then the login form is shown again
