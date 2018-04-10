# Test
## Test unitaire
On utilise Junit5 comme framework de test unitaire.  
### Couche de persistance
### Couche de service
Cas d'exemple : [UserServiceTest.java](../../master/src/main/java/fr/portail/deroffal/authentification/service/UserServiceTest.java)

On va utiliser Mockito pour bouchonner les injections des dépendances de nos services.
Il n'existe pas encore de runner pour remplacer le `@RunWith` de Junit4. On va utiliser la classe [MockitoExtension.java](../../master/src/main/java/fr/portail/deroffal/util/MockitoExtension.java)
qui est reprise de [Junit](https://github.com/junit-team/junit5-samples/blob/master/junit5-mockito-extension/src/main/java/com/example/mockito/MockitoExtension.java) pour 
initiliser automatiquement nos mocks à l'aide de l'annotation : `@ExtendWith(MockitoExtension.class)`.
### Couche de contrôleur
