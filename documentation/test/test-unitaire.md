# Tests unitaires
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
globale des tests écrit à l'aide de Junit. La dépendance `junit-jupiter-engine` permet de fournir une implémentation de `TestEngine ` dans le classpath au runtime. Il s'agit de l'outil 
founit par Junit pour découvrir et exécuter 
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

## Couche de persistance
Pour tester unitairement nos DAO, on va annoter la classe de test par : 
```
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@DataJpaTest
```
 * `@ExtendWith(SpringExtension.class)` permet d'initialiser un contexte Spring.
 * `@TestPropertySource(locations = "classpath:application-test.properties")` va fournir à ce contexte la bonne configuration (sinon il chargerait la configuration du *main* à la place de
  celle du *test*).
 * `@DataJpaTest` va nous permettre d'injecter un `TestEntityManager`.
 
 Cas d'exemple : [UserDaoTest.java](../../src/test/java/fr/deroffal/portail/authentification/dao/UserDaoTest.java)
 
 Afin d'effectuer des tests on peut initialiser des données en les persistant (par exemple dans une méthode *beforeEach()*). Il ne reste plus qu'à appeler notre DAO et vérifier que les 
 données qu'il retourne sont celles persistée précédemment.

## Couche de service
Cas d'exemple : [UserServiceTest.java](../../src/test/java/fr/deroffal/portail/authentification/service/UserServiceTest.java)

## Couche de contrôleur
Pour tester unitairement nos contrôleurs REST, on va annoter la classe de test par : 
```
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
```
 * `@ExtendWith(SpringExtension.class)` permet d'initialiser un contexte Spring.
 * `@WebMvcTest(UserController.class)` indique le contrôleur qui est testé.
 
 Pour initialiser nos tests, il va falloir créer un `MockMvc` à partir du `WebApplicationContext` de l'application. Il va nous permettre de simuler les appels HTTP à notre contôleur.
 ```
 	@Autowired
 	private WebApplicationContext wac;
 
 	private MockMvc mockMvc;
 
 	@BeforeEach
 	void setup() {
 		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
 	}
 	...
 ```
 Ce `MockMvc` possède une méthode *perform* qui va être capable d'effectuer une requête (get, post, ...) à une URL donnée.  Il est aussi capable d'effectuer lui-même les assertions sur 
 ses appels.
 
 Cas d'exemple : [UserControllerTest.java](../../src/test/java/fr/deroffal/portail/authentification/controller/UserControllerTest.java)
 
 ## Test des POJO
 Que cela soit pour augmenter la couverture de code ou pour avoir une validation des règles de développement (bonne génération, pas d'intelligence des getter/setter), il est possible 
 d'automatiser les test des POJO (*plain old java object*). La classe [GetSetTest.java](../../src/test/java/fr/deroffal/portail/GetSetTest.java) présente le test utilisé pour tester
 tout getter ou setter trouvé dans une classe de l'application.
 
 Plus d'information : [OpenPojo](https://github.com/OpenPojo/openpojo/wiki) sur Github.