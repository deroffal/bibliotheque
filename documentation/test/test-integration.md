# Tests d'intégration
Le but est de tester l'ensemble d'une fonctionnalité, c'est à dire en partant de l'appel REST et en redescendant jusqu'à la récupération des informations en base de données.

Cette fois, c'est le plugin `maven-failsafe-plugin` qui est chargé de l'exécution de ces tests. Si les tests unitaires sont joués dans la phase `test` de Maven, les tests d'intégration 
sont joués plus tard, lors de la phase `verify `. En effet, il est nécessaire d'avoir construit l'applicatif avant de pouvoir jouer ces tests.

Par défaut, on peut nommer nos tests d'intégration avec le suffixe "IT" pour qu'il soit détecté par le plugin.
```
      <plugin>
        <artifactId>maven-failsafe-plugin</artifactId>
        <version>${maven-surefire-plugin.version}</version>
        <dependencies>
          <dependency>
            <groupId>org.junit.platform</groupId>
            <artifactId>junit-platform-surefire-provider</artifactId>
            <version>${junit-platform.version}</version>
          </dependency>
          <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${junit-jupiter.version}</version>
          </dependency>
        </dependencies>
      </plugin>
```

Chaque test d'intégration pourra hériter de la classe [AbstractIntegrationTest.java](../../master/src/test/java/fr/deroffal/portail/AbstractIntegrationTest.java). Il s'agit de la classe 
qui va configurer le contexte Spring et la base de données DBUnit.

## Configuration du test d'intégration
La classe est annotée par : 
```
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
```
 * `@ExtendWith(SpringExtension.class)` permet d'initialiser un contexte Spring.
 * `@TestPropertySource(locations = "classpath:application-test.properties")` va fournir à ce contexte la bonne configuration (sinon il chargerait la configuration du *main* à la place de
  celle du *test*).
 * `@SpringBootTest` d'effecuter les tests d'intégration. On va alors pouvoir utiliser un `TestRestTemplate` pour effectuer les appels REST sur notre application à tester. Le 
 `webEnvironment` permet de démarrer le service sur un port autre que le 8080, généré aléatoirement par Spring. On le récupère grâce à l'attribut `protected int port`
 qui est annoté par `@LocalServerPort` 

## DBUnit
Grâce à DBUnit, on va pouvoir créer nos jeux de tests dans par exemple des fichiers xml. Le fonctionnement est annalogue à l'utilisation d'un fichier *data-[...].sql* sauf qu'on va 
pouvoir associer un fichier xml à une classe de test, et donc pouvoir potentiellement varier nos cas de test.
Notre classe doit étendre `org.dbunit.DBTestCase`. Dans le constructeur, nous allons initialiser les propriétés de la base de données embarquée in-memory (ici une h2) :
```
	public AbstractIntegrationTest() {
		super();
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:h2:mem:portailtest");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, PortailConfigutation.SCHEMA_AUTHENTIFICATION);
	}
```
Il ne reste plus qu'à utiliser les *setUp()* et *tearDown()* pour initialiser et terminer nos tests. Pour initiliser le jeu de données, il faut simplement surcharger la méthode *IDataSet
 getDataSet()*, ici par exemple en fournissant un fichier xml :
```
	protected IDataSet getDataSet() throws Exception {
		final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		return builder.build(this.getClass().getResourceAsStream("emplacement du fichier dans src/test/resources"));
	}
```
Le fichier `application-test.properties` va indiquer qu'il faut construire le modèle de base de données :
`spring.jpa.hibernate.ddl-auto=create-drop`, mais il faut quand même initialiser le schéma qui n'est pas créé par lui-même. On peut le faire par le fichier `schema-h2.sql`. Attention à ne
 pas avoir un fichier `schema.sql` ou `schema-h2.sql` dans le `src\main\resources`, car il serait lui aussi lu, et cela pourrait causer des problèmes de création d'objets déjà existant.