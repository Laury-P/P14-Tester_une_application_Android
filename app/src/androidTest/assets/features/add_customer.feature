Feature: Gestion de l'ajout de client

  Background:
    Given Je suis l'écran d'ajout d'un client

  Scenario Outline: Ajout réussi d'un client valide
    When Je saisis "<nom>" dans le champs nom
    And Je saisis "<email>" dans le champs email
    And Je clique sur le bouton de sauvegarde
    Then L'application navigue vers l'ecran d'acceuil
    And Le client "<nom>" est présent dans la liste

    Examples:
      | nom          | email                 |
      | Jean Dupont  | jean.dupont@email.fr  |
      | Alfred Baron | alfred.baron@email.fr |

  Scenario Outline: Validation des champs obligatoires
    When Je saisis "<nom>" dans le champs nom
    And Je saisis "<email>" dans le champs email
    And Je clique sur le bouton de sauvegarde
    Then Je devrait voir le message d'erreur "<message>"
    And Je devrait rester sur l'écran d'ajout

    Examples:
      | nom           | email                   | message                               |
      | Amelie Lottin |                         | Veuillez renseigner l'email du client |
      |               | amelie.lottin@email.com | Veuillez renseigner le nom du client  |
      | Amelie Lottin | amelie.lottin@email     | Email invalide                        |
      | Amelie Lottin | @email.com              | Email invalide                        |

    Scenario: Le bouton de sauvegarde rest désactivé si les champs sont vides
      When Je saisis "" dans le champs nom
      And Je saisis "" dans le champs email
      Then Le bouton de sauvegarde doit être désactivé
