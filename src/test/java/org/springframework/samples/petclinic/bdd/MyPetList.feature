Feature: MyPetList 
   As an owner I can view my pets
  
  Scenario: Successful view the Pet List (Positive)
    Given I am logged in the system as "owner1"
    When I press the button my pets
    Then the pet list appears 

#    Scenario: Successful view the Pet List (Negative)
#    Given I am logged in the system as "owner1"
#    When I try to enter as "owner2"  
#    Then The oups message appears 