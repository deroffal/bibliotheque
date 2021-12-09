Feature: Récupération d'un utilisateur

  Cette feature décrit la récupération d'un utilisateur

  Background:
    Given les utilisateurs existants :
      | nom    | motDePasse                                                   |
      | durant | $2y$10$C0cOUkSG/YfHLpImVrK0he7aDwOQC/scWwlB9HV3DlWfKxUiOXLQa |
      | dupont | $2y$10$Z21U77uR0BtZyR6bsJwgremkofeHU0wzUR39lwlZ11XY6y36Zew2e |

  Scenario: Récupération d'un utilisateur existant
    Given le nom d'utilisateur 'durant' à chercher

    When j'appelle le service de recherche d'utilisateur

    Then j'obtiens l'utilisateur suivant :
      | nom    | motDePasse                                                   |
      | durant | $2y$10$C0cOUkSG/YfHLpImVrK0he7aDwOQC/scWwlB9HV3DlWfKxUiOXLQa |

  Scenario: Récupération d'un utilisateur inconnu
    Given le nom d'utilisateur 'dupuis' à chercher

    When j'appelle le service de recherche d'utilisateur

    Then j'obtiens une erreur comme quoi l'utilisateur 'dupuis' n'existe pas
