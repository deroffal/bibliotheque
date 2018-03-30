# PORTAIL

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

L'application est prête à être lancée. On peut créer un premier point d'entré pour vérifier que l'application : Voir la classe [PingController.java](../master/src/main/java/fr/deroffal/controller/PingController.java). Nous avons deux solutions pour tester le service créé :
 1. Appeler simplement l'application à l'aide du navigateur (facile, car il s'agit d'un GET) : http://localhost:8080/ping
 2. Ajouter Swagger au projet pour nous permettre de tester simplement nos services.
La configuration est simple : il faut ajouter au pom.xml les deux dépendances relatives à `io.springfox` et créer la classe [SwaggerConfiguration.java](../master/src/main/java/fr/deroffal/SwaggerConfiguration.java)
On peut ensuite aller sur [l'url](http://localhost:8080/swagger-ui.html) pour tester les services.
