# Accéder à une base de données

L'accès à la base de données va se faire tout d'abord par une base de données embarquée.

## Configuration
Il faut tout d'abord ajouter les dépendances suivantes dans le pom.xml :
```
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.hsqldb</groupId>
        <artifactId>hsqldb</artifactId>
        <scope>runtime</scope>
    </dependency>
```
Le starter de Springboot va nous permettre d'importer en particulier les dépendances relatives à JPA tandis que la seconde dépendance permet d'importer le driver pour notre base embarquée hsqldb.

## Modèle de données
On va pouvoir créer la classe [UserEntity.java](../master/src/main/java/fr/deroffal/portail/user/entity/UserEntity.java) qui va être la représentation d'un utilisateur en base de données.
On annote la classe des annotations `@Entity` pour signifier que la classe représente une table en base, et l'annotation `@Table` permet de donner des informations à propos de celle-ci (nom, schéma, ...).

De même, on peut annoter chaque attribut de `@Column` pour donner les informations des-dites colonnes.
Enfin, l'attribut correspondant à la clef primaire est annoté par `@Id`.
Pour définir la stratégie de génération de la clef primaire, on utilise l'annotation `@GeneratedValue(strategy = GenerationType.AUTO)`. On indique ici que la génération se fera automatiquement par Hibernate.

## Génération automatique
Au démarrage, Springboot va exécuter les scripts `schema.sql` et `data.sql`, situés à la racine du répertoire de resources, pour créer la structure de la base de données et lui fournir des données.
Il faut alors ajouter dans le fichier `application.properties` la propriété `spring.jpa.hibernate.ddl-auto=validate` pour seulement valider que la structure de la base de données correspond aux entités, et non re-créer la base.