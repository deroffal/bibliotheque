# Initialisation du projet

Création du projet Maven : on peut utiliser le template *maven-archetype-quickstart* qui va créer un projet avec le répertoire `src/main/java` et un `pom.xml`. 

Pour utiliser Springboot, il suffit de rajouter la dépendance au pom parent de Springboot. Il nous permet de bénéficier de la gestion des dépendances et des plugins Maven. Il serait 
possible d'importer Springboot avec une simple dépendance si l'on souhaite conserver la main sur le pom parent, mais on perdrait le bénéfice de la gestion des plugins ([voir la doc de 
Spring](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-build-systems.html#using-boot-maven-without-a-parent)).
```
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.0.0.RELEASE</version>
  </parent>
```
Ajouter la dépendance au starter web pour démarrer notre application.
```
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
```

Créer une première classe : [PortailConfiguration.java](../../src/main/java/fr/deroffal/portail/PortailConfiguration.java). Elle sera chargée de lancer l'application grâce à la méthode *main*. Elle est annotée `@SpringBootApplication`, cela correspond aux trois annotations suivantes :
 * `@Configuration` : La classe va pouvoir contenir des beans Spring et pourra donc être utilisée pour les définir.
 * `@EnableAutoConfiguration` Active une configuration "intelligente" de Springboot qui va scanner ce qu'il y a dans notre classpath pour activer des configurations qui pourront nous être utiles.
 * `@ComponentScan` Indique les packages dans lesquels on va scanner à la recherche de bean. S'il n'y a rien d'indiqué, on va partir du package de la classe annotée.
