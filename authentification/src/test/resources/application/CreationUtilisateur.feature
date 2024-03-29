Feature: Utilisateur - création

  Cette feature décrit la création d'un utilisateur

  Background:
    Given les utilisateurs existants :
      | nom    |
      | durant |
      | dupont |
    And les mots de passes avec leur mot de passe encodé correspondant :
      | motDePasse | motDePasseEncode                                             |
      | azerty     | $2y$10$h1B8uI9THrsPHE0MTcaO.eFoUmSLfY2fo4f9Gneh4aLuzlHMH5hTK |

  Scenario: Création d'un utilisateur en succès
    Given l'utilisateur suivant à créer :
      | nom   | motDePasse |
      | admin | azerty     |

    When je demande la création de l'utilisateur

    Then l'utilisateur suivant est créé :
      | nom   | motDePasse                                                   |
      | admin | $2y$10$h1B8uI9THrsPHE0MTcaO.eFoUmSLfY2fo4f9Gneh4aLuzlHMH5hTK |


  Scenario: Création d'un utilisateur impossible car en doublon
    Given l'utilisateur suivant à créer :
      | nom    | motDePasse |
      | dupont | azerty     |

    When je demande la création de l'utilisateur

    Then j'obtiens une erreur comme quoi l'utilisateur 'dupont' existe déjà
