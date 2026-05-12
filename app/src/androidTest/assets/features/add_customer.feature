Feature: Add customer

  Background:
    Given I am on the add customer screen


  Scenario Outline: Success adding a customer
    When I type "<name>" in the name field
    And I type "<email>" in the email field
    And I click on the save button
    Then The app navigate to the main screen
    And The customer named "<name>" appear in the list

    Examples:
      | name          | email                 |
      | Jean Dupont  | jean.dupont@email.fr  |
      | Alfred Baron | alfred.baron@email.fr |

  Scenario Outline: All field should be correctly filled
    When I type "<name>" in the name field
    And I type "<email>" in the email field
    And I click on the save button
    Then I should see the error message "<message>"
    And I should stay on the add customer screen

    Examples:
      | name           | email                   | message                               |
      | Amelie Lottin |                         | Veuillez renseigner l'email du client |
      |               | amelie.lottin@email.com | Veuillez renseigner le nom du client  |
      | Amelie Lottin | amelie.lottin@email     | Email invalide                        |
      | Amelie Lottin | @email.com              | Email invalide                        |

  Scenario: The save button should be disabled when both field are empty
    When I type "" in the name field
    And I type "" in the email field
    Then The save button should be disabled
