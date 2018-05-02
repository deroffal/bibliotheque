# Accès aux données

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
Le starter de Springboot va nous permettre d'importer en particulier les dépendances relatives à JPA tandis que la seconde dépendance permet d'importer le driver pour notre base 
embarquée hsqldb.

## Modèle de données
### Création d'une entitée.
On va pouvoir créer la classe [UserEntity.java](../../../master/src/main/java/fr/deroffal/portail/authentification/entity/UserEntity.java) qui va être la représentation d'un utilisateur en 
base de données. On annote la classe des annotations `@Entity` pour signifier que la classe représente une table en base, et l'annotation `@Table` permet de donner des informations à 
propos de celle-ci (nom, schéma, ...). On peut annoter chaque attribut de `@Column` pour donner les informations des-dites colonnes (nom, longueur, si la valeur peut être null, unique, ...). 

### Génération des IDs.
L'attribut correspondant à la clef primaire est annoté par `@Id`. Pour définir la stratégie de génération de la clef primaire, on utilise l'annotation `@GeneratedValue`, qui possède 
l'attribut *strategy*. Les stratégies possibles pour la génération de l'ID sont les suivantes : 
 * **GenerationType.AUTO :** C'est l'implémentation de JPA qui va gérer la génération de la séquence à partir d'une table `hibernate_sequence`, commune à tout le schéma.
 * **GenerationType.IDENTITY :** C'est une table spécifique à la base de données qui va gérer la séquence.
 * **GenerationType.SEQUENCE :** La génération de la séquence se fait à l'aide d'une séquence, qui peut être renseignée à l'aide de l'attribut *generator* qui référence une séquence 
 définit dans une annotation `@SequenceGenerator`.
 * **GenerationType.TABLE :** La génération de la séquence se fait ici à l'aide d'une table qui peut référencer plusieurs séquences. Elle est alors associée à une annotation 
 `@TableGenerator`.
 
### Lien entre les entitées.
Ici, le lien entre un utilisateur et un rôle est dit *ManyToMany* : un utilisateur peut avoir plusieurs rôles, et un rôle est possédé par plusieurs utilisateur. On n'a pas besoin de 
représenter la table d'association entre celle-ci. On utilise l'annotation `@JoinTable` pour la caractériser par son schéma (*schema*), son nom de table (*name*) et le nom des colonne dans 
cette table qui correspondent à la clef primaire de la table portant le lien (*joinColumns*) et à la clef primaire de la table référencée (*inverseJoinColumns*). Ici, le rôle n'a pas 
besoin de connaitre les utilisateurs donc on ne map pas l'association de son côté, mais il est possible de le faire avec l'annotation `	@ManyToMany(mappedBy = "roles")` sur un attribut 
`Collection<UserEntity> users;` de l'entitée `RoleEntity`. L'attribut *mappedBy* correspond au nom de l'attribut dans l'entitée `UserEntity`.

L'annotation `@ManyToMany` possède l'attribut *fetch* qui va indiquer la manière dont on va récupérer les données de l'association. Il y a deux valeurs possibles, que l'on va présenter 
pour le cas de la récupération d'un utilisateur, sur son association avec son rôle.
 * **FetchType.EAGER :** On va ramener dans tous les cas l'association, au moment de la requête. Hibernate va ici générer 2 requêtes : une pour récupérer l'utilisateur, puis une seconde 
 pour récupérer les rôles associés.
 * **FetchType.LAZY :** On choisit de ne pas ramener les rôles par défaut. On peut alors ajouter au besoin dans le DAO l'annotation `@EntityGraph` sur les méthodes pour préciser les 
 champs supplémentaires que l'on souhaite. Pour cela, on peut soit indiquer les champs voulus (*attributePaths*), soit le nom de l'entityGraph à utiliser (*value*).

## Chargement de la base de données en mémoire.
Au démarrage, Spring Boot va exécuter les scripts `schema.sql` et `data.sql`, situés à la racine du répertoire de resources, pour créer la structure de la base de données et lui fournir 
des données. De plus, Spring Boot peut charger des fichier `schema-${platform}.sql` and `data-${platform}.sql`, où *${platform}* est la valeur de la propriété `spring.datasource.platform`
. Cela permet de pouvoir faire une création de table et/ou un chargement de donnée spécifique à la base de données utilisée.

Il faut alors ajouter dans le fichier `application.properties` la propriété `spring.jpa.hibernate.ddl-auto=validate` pour seulement valider que la structure de la base de données 
correspond aux entités, et non re-créer la base. En effet, on ne peut pas à la fois utiliser un fichier *schema\*.sql* et laisser Hibernate générer le schéma de base de données.

Documentation de Spring : [ici](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html#howto-initialize-a-database-using-spring-jdbc)