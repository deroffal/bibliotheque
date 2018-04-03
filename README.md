# PORTAIL

* [Initialisation du projet](#initialisation-du-projet)
* [Swagger](#swagger)
  - [Configuration](#configuration)
  - [Documentation des APIs](#documentation-des-apis)


## Initialisation du projet

Création du projet Maven (maven-archetype-quickstart)

Utilisation de Springboot. Il suffit de rajouter la dépendance au pom parent de Springboot.
```
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.0.RELEASE</version>
  </parent>
```
Ajouter la dépendance au starter web
```
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
```

Créer une première classe : [Application.java](../master/src/main/java/fr/deroffal/Application.java). Elle sera chargée de lancer l'application grâce à la méthode *main*. Elle est annotée `@SpringBootApplication`, cela correspond aux trois annotations suivantes :
 * `@Configuration` : La classe va pouvoir contenir des beans Spring et pourra donc être utilisée pour les définir.
 * `@EnableAutoConfiguration` Active une configuration "intelligente" de Springboot qui va scanner ce qu'il y a dans notre classpath pour activer des configurations qui pourront nous être utiles.
 * `@ComponentScan` Indique les packages dans lesquels on va scanner à la recherche de bean. S'il n'y a rien d'indiqué, on va partir du package de la classe annotée.

## Swagger

L'application est prête à être lancée. On peut créer un premier point d'entré pour vérifier que l'application : Voir la classe [PingController.java](../master/src/main/java/fr/deroffal/controller/PingController.java).
Nous avons deux solutions pour tester le service créé :
 1. Appeler simplement l'application à l'aide du navigateur (facile, car il s'agit d'un GET) : http://localhost:8080/ping
 2. Ajouter Swagger au projet pour nous permettre de tester simplement nos services.
 
### Configuration
La configuration de Swagger est simple : il faut ajouter au pom.xml les deux dépendances relatives à `io.springfox` et créer la classe [SwaggerConfiguration.java](../master/src/main/java/fr/deroffal/SwaggerConfiguration.java).
Dans cette classe, on définit un bean `Docket` dont la méthode *select()* va nous permettre de choisir les services que nous allons présenter, soit par contrôleur (méthode *api()* qui peut permettre de filtrer selon des expressions régulières des classes/packages, filtrer des classes annotées, ... ) ou par URL (méthode *paths()*).
Il est possible de regrouper les services exposés dans différents groupes en utilisant la méthode *groupName()* dans plusieurs beans *Docket*.

On peut ensuite aller sur [l'url](http://localhost:8080/swagger-ui.html) pour tester les services.

### Documentation des APIs
Il est possible de documenter le code à l'aide d'annotation Swagger qui font permettre de donner des informations sur les APIs, les méthodes, les paramètres, etc...
 [Voir doc](https://springfox.github.io/springfox/docs/snapshot/#support-for-documentation-from-property-file-lookup)
