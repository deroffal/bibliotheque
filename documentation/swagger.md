# Swagger

L'application est prête à être lancée. On peut créer un premier point d'entrée pour vérifier que l'application est bien lancée à l'aide de la classe [PingController.java](../master/src/main/java/fr/deroffal/controller/PingController.java). 
Il s'agit d'un service REST répondant à un GET de l'URL `/public/ping`

Nous allons ajouter Swagger au projet pour nous permettre de tester simplement nos services.
 
## Configuration
La configuration de Swagger est simple : il faut ajouter au pom.xml les deux dépendances relatives à `io.springfox` et créer la classe [SwaggerConfiguration.java](../master/src/main/java/fr/deroffal/SwaggerConfiguration.java).

Dans cette classe, on définit un bean `Docket` dont la méthode *select()* va nous permettre de choisir les services que nous allons présenter, soit par contrôleur (méthode *api()* qui peut 
permettre de filtrer selon des expressions régulières des classes/packages, filtrer des classes annotées, ... ) ou par URL (méthode *paths()*).
On peut utiliser ces filtres pour regrouper les services exposés dans différents groupes, en utilisant la méthode *groupName()*. Ainsi, chaque bean *Docket* correspondra à un groupe de 
services.

On peut ensuite aller sur [l'url](http://localhost:8080/swagger-ui.html) pour tester les services.

## Documentation des APIs
Il est possible de documenter le code à l'aide d'annotation Swagger qui font permettre de donner des informations sur les APIs, les méthodes, les paramètres, etc...
 [Voir doc](https://springfox.github.io/springfox/docs/snapshot/#support-for-documentation-from-property-file-lookup)
