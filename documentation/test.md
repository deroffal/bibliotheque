# Test
## Test unitaire
On utilise Junit5 comme framework de test unitaire. 

Configuration du pom.xml : 
Il faut importer `junit-jupiter-api`, qui est l'API utilisée pour l'écriture des tests unitaires.

```
   <dependencies>
    ...
    <dependency>
      <groupId>org.junit.jupiter</groupId>
      <artifactId>junit-jupiter-api</artifactId>
      <scope>test</scope>
    </dependency>
    ...
  </dependencies>
```

Pour que les tests soient exécutés lors du build Maven, il faut ajouter le `maven-surefire-plugin`. On lui fournit la dépendance `junit-platform-surefire-provider` permettre l'éxécution 
globale des tests écrit à l'aide de Junit. La dépendance `junit-jupiter-engine` permet de fournir une implémentation de `TestEngine ` dans le classpath au runtime. Il s'agit de l'outil founit par Junit pour découvrir et exécuter 
les tests.

```
<build>
    <plugins>
        ...
        <plugin>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.19.1</version>
            <dependencies>
                <dependency>
                    <groupId>org.junit.platform</groupId>
                    <artifactId>junit-platform-surefire-provider</artifactId>
                    <version>1.1.1</version>
                </dependency>
                <dependency>
                    <groupId>org.junit.jupiter</groupId>
                    <artifactId>junit-jupiter-engine</artifactId>
                    <version>5.1.1</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```

### Couche de persistance
### Couche de service
Cas d'exemple : [UserServiceTest.java](../../master/src/test/java/fr/deroffal/portail/authentification/service/UserServiceTest.java)

On va utiliser Mockito pour bouchonner les injections des dépendances de nos services.
Il n'existe pas encore de runner pour remplacer le `@RunWith` de Junit4. On va utiliser la classe [MockitoExtension.java](../../master/src/main/java/fr/portail/deroffal/util/MockitoExtension.java)
qui est reprise de [Junit](https://github.com/junit-team/junit5-samples/blob/master/junit5-mockito-extension/src/main/java/com/example/mockito/MockitoExtension.java) pour 
initiliser automatiquement nos mocks à l'aide de l'annotation : `@ExtendWith(MockitoExtension.class)`.
### Couche de contrôleur
